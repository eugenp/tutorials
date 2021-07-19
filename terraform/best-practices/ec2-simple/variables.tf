#
# Variables
#

variable "ami_name" {
    type = string
    description = "AMI name to use for our EC2 instance. Defaults to Ubuntu 18.04 (Bionic)"
    default = "ubuntu/images/hvm-ssd/ubuntu-bionic-18.04-amd64-*"
}

variable "ami_owner" {
    type = string
    description = "AMI Owner ID to use for our EC2 instance. Defaults to 099720109477 (Canonical)"
    default = "099720109477"
}