variable "namespace" {
    type = string
    description = "Namespace where the Admission Controller will be deploymed"
}

variable "deployment_name" {
    type = string
    description = "Admission Controller Deployment Name"
}

variable "replicas" {
    type = number
    description = "Number of replicas used in the deployment"
    default = 3
}

variable "image" {
    type = string
    description = "Admission Controller image name"
}

variable "image_version" {
    type = string
    description = "Admission Controller image version name"
    default = "latest"
}

variable "image_prefix" {
    type = string
    description = "Image repository prefix"
    default = "gcr.io/baeldung"
}

variable "admission_controller_name" {
    type = string
    description = "Admission Controller name"
    default = "wait-for-it.service.local"
}

variable "k8s_config_context" {
  type = string
  description = "Name of the K8S config context"
}

variable "k8s_config_path" {
    type = string
    description = "Location of the standard K8S configuration"
    default = "~/.kube/config"
  
}