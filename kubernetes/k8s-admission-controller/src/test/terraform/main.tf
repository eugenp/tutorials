
locals {
  prefix = var.image_prefix != "" ? "${var.image_prefix}/":""
  image = "${local.prefix}${var.image}:${var.image_version}"
  cloud_sdk_image = "${local.prefix}frapsoft/openssl"
  ns = data.kubernetes_namespace.ns.metadata[0].name

  # Spring SSL Configuration
  webhook_config_json = jsonencode({
    server = {
      port = 443
      ssl = {
        "key-store" =  "/shared-config/webhook.p12"
        "key-store-type" = "PKCS12"
        "key-alias" = "webhook"
        "key-store-password" = ""
      }
    }

    admission-controller = {
      disabled = false
      image-prefix = "gcr.io/sandboxbv-01"
    }
  })

}


# Resource namespace
data "kubernetes_namespace" "ns" {
  metadata {
    name = var.namespace
  }
}

# TLS Key
resource "tls_private_key" "tls" {
  algorithm = "RSA"
}

# CSR 
resource "tls_cert_request" "tls" {
  key_algorithm = "RSA"
  private_key_pem = tls_private_key.tls.private_key_pem
  subject {
    common_name = "${var.deployment_name}.${var.namespace}.svc"
  }

  dns_names = [
    var.deployment_name,
    "${var.deployment_name}.${var.namespace}",
    "${var.deployment_name}.${var.namespace}.svc",
    "${var.deployment_name}.${var.namespace}.svc.cluster.local"
  ]

}

# HTTPS Certificate 
resource "tls_self_signed_cert" "tls" {
  key_algorithm = tls_private_key.tls.algorithm
  private_key_pem = tls_private_key.tls.private_key_pem

  subject {
    common_name = "${var.deployment_name}.${local.ns}"
  }

  validity_period_hours = 24*365*20

  dns_names = [
    var.deployment_name,
    "${var.deployment_name}.${var.namespace}",
    "${var.deployment_name}.${var.namespace}.svc",
    "${var.deployment_name}.${var.namespace}.svc.cluster.local"
  ]

  allowed_uses = [
    "key_encipherment",
    "digital_signature",
    "server_auth"
  ]
}

# Certificado
# Obs: Desativado pois o certificado fica preso no estado "Issued"
resource "kubernetes_certificate_signing_request" "tls" {
  count = 0
  metadata {
    name = "${var.deployment_name}.${var.namespace}"
  }

  auto_approve = true

  spec {
    usages = [ 
      "key encipherment",
      "digital signature",
      "server auth"
    ]

    signer_name = "kubernetes.io/kubelet-serving"

    request = tls_cert_request.tls.cert_request_pem
  }
  
}

# Secret to store TLS key/cert
resource "kubernetes_secret" "tls" {
  metadata {
    namespace = local.ns
    name = var.deployment_name
  }

  data = {
    "webhook-key.pem" = tls_private_key.tls.private_key_pem
    "webhook-cert.pem" = tls_self_signed_cert.tls.cert_pem
   }

}

# Deployment
resource "kubernetes_deployment" "main" {
    metadata {
      name = var.deployment_name
      namespace = local.ns
    }

    spec {
        replicas = var.replicas
        selector {
            match_labels = {
                app = var.deployment_name
            }
        }

        template {
            metadata {
                labels = {
                    app = var.deployment_name
                }
            }

            spec {
                container {
                    image = local.image
                    name = var.deployment_name 
                    volume_mount {
                      mount_path = "/shared-config"
                      name = "shared-config"
                    }

                    env {
                      name = "SPRING_APPLICATION_JSON"
                      value = local.webhook_config_json
                    }

                }

                init_container {
                  name = "setup-keystore"
                  image = local.cloud_sdk_image

                  args = [ 
                   "pkcs12", "-export", 
                   "-in", "/secret/webhook-cert.pem",
                   "-inkey", "/secret/webhook-key.pem",
                   "-name", "webhook",
                   "-out", "/shared-config/webhook.p12",
                   "-passout", "pass:"
                  ]

                  volume_mount {
                    mount_path = "/shared-config"
                    name = "shared-config"
                  }

                  volume_mount {
                    mount_path = "/secret/webhook-cert.pem"
                    name = "webhook-secret"
                    sub_path = "webhook-cert.pem"
                  }

                  volume_mount {
                    mount_path = "/secret/webhook-key.pem"
                    name = "webhook-secret"
                    sub_path = "webhook-key.pem"
                  }

                }

              volume {
                name = "shared-config"
                empty_dir {}
              }

              volume {
                name = "webhook-secret"
                secret {
                  secret_name = kubernetes_secret.tls.metadata[0].name
                  items {
                    key = "webhook-cert.pem"
                    path = "webhook-cert.pem"
                  }
                  items {
                    key = "webhook-key.pem"
                    path = "webhook-key.pem"
                  }
                }
              }

            }
        }
    }
}

# Service
resource "kubernetes_service" "svc" {
    metadata {
        name = var.deployment_name
        namespace = local.ns
    }

    spec {
      selector = {
        "app" = var.deployment_name
      }

      port {
        name = "https"
        port = 443
        protocol = "TCP"
        target_port = 443
      }

      type = "ClusterIP"
    }
}

# Admission Controller
resource "kubernetes_mutating_webhook_configuration" "waitforit" {
    metadata {
        name = var.deployment_name
    }

    webhook {
      name = var.admission_controller_name
      admission_review_versions = [ "v1", "v1beta1" ]

      #failure_policy = "Ignore" # 

      client_config {
      
        service {
          name = kubernetes_service.svc.metadata[0].name
          namespace = local.ns
          path = "/mutate"
          port = 443
        }

        # IMPORTANT: CA_BUNDLE must be Base64-encoded
        ca_bundle = tls_self_signed_cert.tls.cert_pem
      }

      rule {
        api_groups = [ "*" ]
        api_versions = [ "*" ]
        operations = [ "CREATE", "UPDATE" ]
        resources = [ "deployments", "statefulsets" ]
      }

      side_effects = "None" # 
    }

    depends_on = [ 
      kubernetes_deployment.main
     ]
}
