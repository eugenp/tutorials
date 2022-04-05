FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/docker-product-server-1.0.0.jar product-server.jar
ENTRYPOINT ["java","-jar","/product-server.jar"]