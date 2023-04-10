# Spring Cloud Azure

# Relevant Articles
- [A Guide to Spring Cloud Azure Key Vault](https://www.baeldung.com/spring-cloud-azure-key-vault)
# Azure KeyVault: 
In order to create the secrets, follow these steps: 
- create an Azure account 
- install the Azure Cli an run the following commands
- login on Azure -> _az-login_
- create a resource group: _az group create --name spring_cloud_azure --location eastus_
- create a keyvault storage: _az keyvault create --name new_keyvault --resource-group spring_cloud_azure --location eastus_
- create the secrets: > az keyvault secret set --name my-database-secret --value my-database-secret-value --vault-name new_keyvault,> az keyvault secret set --name my-secret --value my-secret-value --vault-name new_keyvault
```
