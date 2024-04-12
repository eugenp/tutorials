# EC2 Basic Sample

This Terraform sample project creates a single EC2 instance in the configured region.

IMPORTANT NOTICE: In order to run this sample you must have an active AWS Account. As you probably know, creating resources on AWS
may result in additional charges in your bill. We recommend creating a test account to run this test as you can then use AWS's free tier
to play around. When finished, ALWAYS REMEMBER TO DESTROY YOUR RESOURCES !!!

# Setup instructions

1. Make sure you have a working AWS environment. Use a simple command such as _aws ec2 describe-instances_ and check its output.
   If you get a list of existing EC2 instances, you're good to go. Otherwise, please refer to AWS documentation in order to setup your CLI.
2. Download the Terraform package for your environment from Hashicorp's site. Unzip it and put the _terraform_ binary somewhere
   in the OS's PATH.
3. Open a command prompt and _cd_ into this folder
4. Run the following commands:
'''
  $ terraform init
  $ terraform apply -auto-approve
'''
5. Wait until Terraform create all resources and run _aws ec2 describe-instances_. The output should list the newly creates EC2 instance
6. Run _terraform destroy_ to remove the previously creates namespace.

