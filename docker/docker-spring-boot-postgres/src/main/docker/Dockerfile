FROM adoptopenjdk:11-jre-hotspot
MAINTAINER baeldung.com

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]