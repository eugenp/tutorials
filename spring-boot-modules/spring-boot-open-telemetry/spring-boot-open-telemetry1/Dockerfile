FROM openjdk:17-alpine

COPY target/spring-boot-open-telemetry1-1.0.0-SNAPSHOT.jar spring-boot-open-telemetry.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/spring-boot-open-telemetry.jar"]