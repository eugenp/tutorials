#!/usr/bin/env bash

echo "Install SDKMan"
sudo apt-get update
sudo apt-get install unzip zip -y
sudo curl -s get.sdkman.io | bash
sudo source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version

echo "Install Spring Dependencies"
sudo sdk install groovy
sudo sdk install java
sudo sdk install maven

echo "Install Spring Boot"
sudo sdk install springboot
spring --version

# Spring Boot CLI Proper
# sdk install springboot dev /path/to/spring-boot/spring-boot-cli/target/spring-boot-cli-2.0.0.RELEASE-bin/spring-2.0.0.RELEASE/
# sdk default springboot dev
# spring --version
