FROM adoptopenjdk:11-jre-hotspot
MAINTAINER baeldung.com

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]