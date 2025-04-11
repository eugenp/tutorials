FROM openjdk:17-alpine

COPY target/spring-boot-open-telemetry2-1.0.0-SNAPSHOT.jar spring-boot-open-telemetry.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/spring-boot-open-telemetry.jar"]