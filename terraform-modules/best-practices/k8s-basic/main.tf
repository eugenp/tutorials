#
# Resource definitions
#

resource "kubernetes_namespace" "hello" {
    metadata {
        labels = {
            terraform = "true"
        }        
        name = var.namespace_name
    }
}