/*
 * SvcCustomer deployment resources
 */

resource "kubernetes_deployment" "SvcCustomer" {

  metadata {
    name = "svccustomer"
    labels = {
      app = "SvcCustomer"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "SvcCustomer"
      }
    }

    template {
      metadata {
        labels = {
	      app = "SvcCustomer"
        }
      }

      spec {
        image_pull_secrets {
          name = "docker-config"
        }
        
      
        container {
          image = "inanimate/echo-server"
          name  = "svccustomer-httpd"
          env {
            name = "PORT"
            value = "80"
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "SvcCustomer" {
	metadata {
	  name = "svccustomer"
	}
	
	spec {
	
	  selector = {
		app = "SvcCustomer"
	  }
	  
	  session_affinity = "ClientIP"
	  port {
		port = 80
	  }
  
	  //type = "LoadBalancer"
	}
  }
  