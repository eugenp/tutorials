#!/usr/bin/env bash

# For Ubuntu 14.04
# Inspired from: https://github.com/curran/setupHadoop/blob/master/setupHadoop.sh
# Use from the user directory

# Install Java
sudo apt-get update
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get install -y oracle-java8-installer

# Install Hadoop
curl -O http://mirror.cogentco.com/pub/apache/hadoop/common/hadoop-2.8.2/hadoop-2.8.2.tar.gz
tar xfz hadoop-2.8.2.tar.gz
sudo mv hadoop-2.8.2 /usr/local/hadoop
rm hadoop-2.8.2.tar.gz

# Environmental Variables
echo export JAVA_HOME=/usr/lib/jvm/java-8-oracle >> ~/.bashrc
echo export HADOOP_PREFIX=/usr/local/hadoop >> ~/.bashrc
echo export PATH=\$PATH:/usr/local/hadoop/bin >> ~/.bashrc
echo export PATH=\$PATH:/usr/local/hadoop/sbin >> ~/.bashrc
source ~/.bashrc

# Copy configuration files
cp master/* /usr/local/hadoop/etc/hadoop/

# Format HDFS
hdfs namenode -format

# SSH keys for Hadoop to use.
ssh-keygen -t rsa -P 'password' -f ~/.ssh/id_rsa.pub
sudo mv ~/.ssh/id_rsa.pub ~/.ssh/authorized_keys

# SSH
ssh localhost
# authenticate with osboxes.org

# Start NameNode daemon and DataNode daemon
start-dfs.sh
# stop-dfs.sh

# Install Maven
sudo apt-get install maven

# Access Hadoop - http://localhost:50070