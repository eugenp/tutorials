#
# Provider configurations
# This file will *NOT* be overwriten upon regeneration, so it's safe
# to add your own customizations
#

provider "kubernetes" {
	version = "~> 1.10"
}

provider "random" {
	version = "~> 2.2"
}