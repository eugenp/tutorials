FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never

ADD . $HOME
RUN mvn package

FROM openjdk:8-jdk-alpine 

COPY --from=build /usr/app/target/single-module-caching-1.0-SNAPSHOT-jar-with-dependencies.jar /app/runner.jar

ENTRYPOINT java -jar /app/runner.jar