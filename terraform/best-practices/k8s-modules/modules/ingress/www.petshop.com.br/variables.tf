/*
 * Kubernetes Ingress module
 */


variable "ingress_name" {
    type = string
    description = "Ingress name. Defaults to a random name."
    default = ""
}

variable "ingress_host" {
    type = string
    description = "Ingress hostname"
    default = "www.petshop.com.br"
}


	

