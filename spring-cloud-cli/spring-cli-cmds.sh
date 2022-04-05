#!/usr/bin/env bash
echo "Run Config Server"
echo "spring cloud configserver"
echo "http://localhost:8888"
spring cloud configserver
echo
echo "Run Eureka Server"
echo "spring cloud eureka"
echo "http://localhost:8761"
spring cloud eureka
echo
echo "Run H2 Server"
echo "spring cloud h2"
echo "http://localhost:9095"
spring cloud h2
echo
echo "Run Kafka Server"
echo "spring cloud kafka"
echo "http://localhost:9091"
spring cloud kafka
echo
echo "Run Zipkin Server"
echo "spring cloud zipkin"
echo "http://localhost:9411"
spring cloud zipkin
echo
echo "Run Dataflow Server"
echo "spring cloud dataflow"
echo "http://localhost:9393"
spring cloud dataflow
echo
echo "Run Hystrixdashboard Server"
echo "spring cloud hystrixdashboard"
echo "http://localhost:7979"
spring cloud hystrixdashboard
echo
echo "List Services"
echo "spring cloud --list"
spring cloud --list
echo