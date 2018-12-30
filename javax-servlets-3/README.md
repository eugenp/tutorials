## Build with maven:
mvn package

## Run with Tomcat on Docker container:
docker build --tag my-tomcat .
docker run -it --rm -p 8080:8080 my-tomcat

### Relevant Articles:
- [Java Web Application Without Web.xml]
