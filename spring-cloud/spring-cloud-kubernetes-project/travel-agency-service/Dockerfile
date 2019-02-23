# requires Docker version 17.05.0-ce-rc1, build 2878a85
FROM maven:3.5-jdk-8 as BUILDAGENCY

COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp
RUN mvn -f /usr/src/myapp/pom.xml clean package -DskipTests

FROM openjdk:alpine

COPY --from=BUILDAGENCY /usr/src/myapp/target/*.jar /maven/

CMD java $JAVA_OPTS -jar maven/*.jar