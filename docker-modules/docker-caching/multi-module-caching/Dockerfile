FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD pom.xml $HOME
ADD core/pom.xml $HOME/core/pom.xml
ADD runner/pom.xml $HOME/runner/pom.xml

RUN mvn -pl core verify --fail-never
ADD core $HOME/core
RUN mvn -pl core install
RUN mvn -pl runner verify --fail-never
ADD runner $HOME/runner
RUN mvn -pl core,runner package

FROM openjdk:8-jdk-alpine

COPY --from=build /usr/app/runner/target/runner-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/runner.jar

ENTRYPOINT java -jar /app/runner.jar