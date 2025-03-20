FROM openjdk:17-jdk-slim

COPY target/gatling-java.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080
