#!/usr/bin/env bash
echo "Run Groovy Rest API Server"
echo "spring run restapi.groovy"
echo "http://localhost:8080/api/get"
spring run restapi.groovy
echo
echo "Run Groovy Eureka Server"
echo "spring run eureka.groovy"
echo "http://localhost:8761"
spring run eureka.groovy
echo