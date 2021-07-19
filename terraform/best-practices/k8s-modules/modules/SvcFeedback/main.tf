/*
 * SvcFeedback deployment resources
 */

resource "kubernetes_deployment" "SvcFeedback" {

  metadata {
    name = "svcfeedback"
    labels = {
      app = "SvcFeedback"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "SvcFeedback"
      }
    }

    template {
      metadata {
        labels = {
	      app = "SvcFeedback"
        }
      }

      spec {
        image_pull_secrets {
          name = "docker-config"
        }
        
      
        container {
          image = "inanimate/echo-server"
          name  = "svcfeedback-httpd"
          env {
            name = "PORT"
            value = "80"
          }
        }

      }
    }
  }
}

resource "kubernetes_service" "SvcFeedback" {
	metadata {
	  name = "svcfeedback"
	}
	
	spec {
	
	  selector = {
		app = "SvcFeedback"
	  }
	  
	  session_affinity = "ClientIP"
	  port {
		port = 80
	  }
  
	  //type = "LoadBalancer"
	}
  }
  