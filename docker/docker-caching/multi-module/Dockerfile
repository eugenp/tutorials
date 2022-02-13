FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD pom.xml $HOME
ADD core/pom.xml $HOME/core/pom.xml
ADD application/pom.xml $HOME/application/pom.xml

RUN mvn -pl core verify --fail-never
ADD core $HOME/core
RUN mvn -pl core install
RUN mvn -pl application verify --fail-never
ADD application $HOME/application
RUN mvn -pl core,application package

FROM openjdk:8-jdk-alpine

COPY --from=build /usr/app/application/target/application-0.0.1-SNAPSHOT.jar /app/runner.jar

ENTRYPOINT java -jar /app/runner.jar