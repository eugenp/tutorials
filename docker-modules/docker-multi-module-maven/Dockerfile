FROM maven:3.8.5-openjdk-17 AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
COPY api /tmp/api/
COPY domain /tmp/domain/

WORKDIR /tmp/
RUN mvn clean install -X

FROM openjdk:17

COPY --from=MAVEN_TOOL_CHAIN /tmp/api/target/docker-multi-module-maven-api-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
