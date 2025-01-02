FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

FROM tomcat:10-jdk17

# Remove the default web apps
RUN rm -rf /usr/local/tomcat/webapps/*

RUN pwd && ls

COPY --from=build /app/target/trivia-webservice.war /usr/local/tomcat/webapps/trivia-webservice.war

# Expose the port on which Tomcat runs
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
