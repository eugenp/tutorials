# Kubernetes multimodule sample

This sample deploys two services behind a Kubernetes ingress.

# Setup instructions

1. Make sure you have a working Kubernetes environment. Use a simple command such as _kubectl get nodes_ and check its output.
   If you get a list of nodes that contains at least one _ready_ module, you're good to go
2. Download the Terraform package for your environment from Hashicorp's site. Unzip it and put the _terraform_ binary somewhere
   in the OS's PATH.
3. Open a command prompt and _cd_ into this folder
4. Run the following commands:
'''
  $ terraform init
  $ terraform apply -auto-approve
'''
5. Wait until Terraform create all resources and run _kubectl get services_. The output should now have a few services.
6. Run _terraform destroy_ to remove the previously creates namespace.


