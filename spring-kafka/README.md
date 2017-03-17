# Spring Kakfa

This is a simple Spring Boot app to demonstrate sending and receiving of messages in Kafka using spring-kafka.

As Kafka topics are not created automatically by default, this application requires that a topic named 'baeldung' is created manually.

`$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic baeldung`

Two listeners with group Ids **foo** and **bar** are configured. When run successfully, the *Hello World!* message will be received by both the listeners and logged on console.
