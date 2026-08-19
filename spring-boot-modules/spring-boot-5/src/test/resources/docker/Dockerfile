FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY target/spring-boot-5-0.0.1-SNAPSHOT.jar backend-app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "backend-app.jar"]