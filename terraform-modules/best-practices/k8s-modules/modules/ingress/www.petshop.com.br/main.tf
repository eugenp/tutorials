/*
 * Kubernetes Ingress module
 */
locals {
    iname = var.ingress_name == "" ? join("-",["ingress",sha1(uuid())]) : var.ingress_name
}

resource "kubernetes_ingress" "ingress" {
    metadata {
        name = local.iname
        annotations = map(
            "nginx.ingress.kubernetes.io/rewrite-target","/"
        )
    }
    spec {
        rule {
            http {
                path {
                    backend {
                        service_name = "svccustomer"
                        service_port = 80
                    }
                    path = "/customers"
                }
                path {
                    backend {
                        service_name = "svcfeedback"
                        service_port = 80
                    }
                    path = "/feedback"
                }
            }
        }
/*
    tls {
      secret_name = "tls-secret"
    }
*/
  }
}

