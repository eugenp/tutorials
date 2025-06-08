FROM openjdk:17-jdk-alpine
COPY target/inventory-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]
