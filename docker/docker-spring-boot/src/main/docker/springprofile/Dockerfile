# To build and run docker-with-spring-profile:
#
# docker build -f src/main/docker/springprofile/Dockerfile --tag=docker-with-spring-profile:latest .
# docker run docker-with-spring-profile:latest
#
# To run with profiles:
# docker run -e "SPRING_PROFILES_ACTIVE=test1,test2,test3" docker-with-spring-profile:latest

FROM openjdk:11
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
