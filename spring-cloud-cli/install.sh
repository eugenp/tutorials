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

echo "Installing JCE"
sudo apt-get install p7zip-full
echo please go to: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
echo Download the jce_policy-8.zip after you agree to the terms
sleep 25
sudo 7z x jce_policy-8.zip
sudo mv /usr/lib/jvm/java-8-oracle/jre/lib/security/local_policy.jar /usr/lib/jvm/java-8-oracle/jre/lib/security/local_policy.jar.backup
sudo mv /usr/lib/jvm/java-8-oracle/jre/lib/security/US_export_policy.jar /usr/lib/jvm/java-8-oracle/jre/lib/security/US_export_policy.jar.backup
sudo mv UnlimitedJCEPolicyJDK8/*.jar /usr/lib/jvm/java-8-oracle/jre/lib/security/
echo

echo "Installing Spring Cloud CLI"
spring install org.springframework.cloud:spring-cloud-cli:1.3.2.RELEASE
echo

echo "Verify Installation"
spring cloud --version
echo