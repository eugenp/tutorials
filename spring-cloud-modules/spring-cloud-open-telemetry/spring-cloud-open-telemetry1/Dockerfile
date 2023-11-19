FROM adoptopenjdk/openjdk11:alpine

COPY target/spring-cloud-open-telemetry1-1.0.0-SNAPSHOT.jar spring-cloud-open-telemetry.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/spring-cloud-open-telemetry.jar"]