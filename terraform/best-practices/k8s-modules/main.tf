/*
 * Top-level definitions
 */
 
//================================================================== Ingress

module "ingress_www_petshop_com_br" {
 	source = "./modules/ingress/www.petshop.com.br"
  ingress_host = "www.petshop.com.br"
} 
 
//================================================================== Deployments

module "SvcCustomer" {
  source = "./modules/SvcCustomer"
} 

module "SvcFeedback" {
  source = "./modules/SvcFeedback"
} 







