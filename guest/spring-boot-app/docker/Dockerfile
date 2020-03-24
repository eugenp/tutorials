# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash

# copy fat WAR
COPY spring-boot-app-0.0.1-SNAPSHOT.war /app.war

# copy fat WAR
COPY logback.xml /logback.xml

COPY run.sh /run.sh

# runs application
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "-Dlogging.config=/logback.xml", "/app.war"]

ENTRYPOINT ["/run.sh"]
