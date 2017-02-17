FROM openjdk:8-jre-alpine
ADD target/mesos-marathon-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]