FROM adoptopenjdk/openjdk11:alpine

COPY target/spring-cloud-open-telemetry2-1.0.0-SNAPSHOT.jar spring-cloud-open-telemetry.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/spring-cloud-open-telemetry.jar"]