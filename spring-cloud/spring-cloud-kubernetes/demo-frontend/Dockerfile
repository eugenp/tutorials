FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/demo-frontend-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar