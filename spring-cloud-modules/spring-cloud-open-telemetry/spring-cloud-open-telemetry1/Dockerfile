FROM openjdk:17-jdk-slim
COPY target/spring-cloud-open-telemetry1-1.0.0-SNAPSHOT.jar spring-cloud-open-telemetry.jar
#ADD opentelemetry-javaagent.jar opentelemetry-javaagent.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/spring-cloud-open-telemetry.jar"]

#ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar", "-jar","/spring-cloud-open-telemetry.jar"]
