FROM maven:3.6.0-jdk-11
WORKDIR /code/spring-boot-modules/spring-boot-properties/
COPY ./spring-boot-modules/spring-boot-properties/pom.xml .
COPY ./spring-boot-modules/spring-boot-properties/src ./src
COPY ./parent-boot-2/pom.xml /code/parent-boot-2/pom.xml
COPY ./pom.xml /code/pom.xml
COPY ./custom-pmd-0.0.1.jar /code/custom-pmd-0.0.1.jar
COPY ./baeldung-pmd-rules.xml /code/baeldung-pmd-rules.xml
RUN mvn dependency:resolve
CMD ["mvn", "spring-boot:run"]