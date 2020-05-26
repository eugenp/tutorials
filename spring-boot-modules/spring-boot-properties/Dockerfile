FROM maven:3.6.0-jdk-11 AS builder
WORKDIR /code/spring-boot-modules/spring-boot-properties/
COPY ./spring-boot-modules/spring-boot-properties/pom.xml .
COPY ./spring-boot-modules/spring-boot-properties/src ./src
COPY ./parent-boot-2/pom.xml /code/parent-boot-2/pom.xml
COPY ./pom.xml /code/pom.xml
COPY ./custom-pmd-0.0.1.jar /code/custom-pmd-0.0.1.jar
COPY ./baeldung-pmd-rules.xml /code/baeldung-pmd-rules.xml
RUN mvn dependency:resolve
#RUN ["mvn", "package"]
CMD ["mvn", "spring-boot:run"]

#FROM openjdk:11-jre-slim
#COPY --from=builder /code/spring-boot-modules/spring-boot-properties/target/spring-boot-properties.jar /
#CMD ["java", "-jar", "./spring-boot-properties.jar"]