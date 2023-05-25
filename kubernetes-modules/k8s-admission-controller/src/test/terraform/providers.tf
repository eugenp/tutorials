terraform {
  required_providers {
    kubernetes = {
      source = "hashicorp/kubernetes"
      version = "2.3.2"
    }
  }
}

# Use standard kubectl environment to get connection details
provider "kubernetes" {
    config_context = var.k8s_config_context
    config_path = var.k8s_config_path
}