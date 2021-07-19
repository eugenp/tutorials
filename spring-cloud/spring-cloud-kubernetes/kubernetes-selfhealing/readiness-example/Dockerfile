FROM openjdk:8-jdk-alpine

# Create app directory
RUN mkdir -p /usr/opt/service

# Copy app
COPY target/*.jar /usr/opt/service/service.jar

EXPOSE 8080

ENTRYPOINT exec java -jar /usr/opt/service/service.jar