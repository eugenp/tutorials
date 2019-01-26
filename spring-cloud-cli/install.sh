#!/usr/bin/env bash
echo See: https://howtoprogram.xyz/2016/08/28/install-spring-boot-command-line-interface-on-linux/
echo

echo "Setting up Java JDK 8"
echo See: http://tipsonubuntu.com/2016/07/31/install-oracle-java-8-9-ubuntu-16-04-linux-mint-18/
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-set-default
echo

echo "Downloading Spring Boot CLI 1.5.7"
wget http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.5.7.RELEASE/spring-boot-cli-1.5.7.RELEASE-bin.tar.gz
echo

echo "Extracting and Installing"
sudo mkdir /opt/spring-boot
sudo tar xzf spring-boot-cli-1.5.7.RELEASE-bin.tar.gz -C /opt/spring-boot
export SPRING_HOME=/opt/spring-boot/spring-1.5.7.RELEASE
export PATH=$SPRING_HOME/bin:$PATH
source /etc/profile
echo

echo "Verifying Install of Spring CLI"
spring --version
echo

echo "Maven Install"
sudo apt-get install maven
echo

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
sudo mkdir /opt/spring-boot/spring-1.5.7.RELEASE/lib/ext
sudo chown -R $USER:$USER /opt/spring-boot/spring-1.5.7.RELEASE/lib/ext
echo see: https://repo.spring.io/snapshot/org/springframework/cloud/spring-cloud-cli/ if manual install required
spring install org.springframework.cloud:spring-cloud-cli:1.3.2.RELEASE
echo

echo "Verify Installation"
spring cloud --version
echo