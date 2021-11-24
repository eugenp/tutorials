FROM openjdk:11

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

COPY --chown=1001 target/spring-project-0.1-SNAPSHOT-exec.jar /spring-app/

WORKDIR /spring-app

EXPOSE 8080
USER 1001

ENTRYPOINT ["java", "-jar", "spring-project-0.1-SNAPSHOT-exec.jar" ]