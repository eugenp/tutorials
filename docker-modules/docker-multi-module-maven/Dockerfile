FROM maven:3.8.5-openjdk-17 AS DEPENDENCIES

WORKDIR /opt/app
COPY api/pom.xml api/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY pom.xml .

RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=domain

FROM maven:3.8.5-openjdk-17 AS BUILDER

WORKDIR /opt/app
COPY --from=DEPENDENCIES /root/.m2 /root/.m2
COPY --from=DEPENDENCIES /opt/app/ /opt/app
COPY api/src /opt/app/api/src
COPY domain/src /opt/app/domain/src

RUN mvn -B -e clean install -DskipTests

FROM openjdk:17-slim

WORKDIR /opt/app
COPY --from=BUILDER /opt/app/api/target/*.jar /app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
