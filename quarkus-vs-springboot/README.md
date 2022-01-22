# Spring Boot vs Quarkus

To follow this tutorial, you will need the following things:
- GRAALVM (https://www.graalvm.org/)
- VisualVM (https://visualvm.github.io/)
- Maven (Embedded, IDE, or local installation)
- Docker (https://www.docker.com/)
- Jmeter (https://jmeter.apache.org/)

To create this test, I used some custom features from Jmeter. You can install the Jmeter plugin manager here:
https://loadium.com/blog/how-to-install-use-jmeter-plugin. After that, please install the following plugins:
- https://jmeter-plugins.org/?search=jpgc-casutg

The test file is `load_test.jmx` in case of any change need. You can open it with the Jmeter GUI. For example, to run the start, you can execute the file `run_test.sh` or run the comment bellow:

```
$jmeter_home/bin/jmeter -n -t load_test.jmx -l log.csv -e -o ./report
```

Just remember to change the variable `jmeter_home` with the path to the JMeter folder. The path to the data files is relative, so either keep them in the same folder as the test or use Jmeter GUI to change it.

Open the VisualVM application and select your application to start monitoring before running the test, and of course, start the sample application first.

## Spring Boot
To build the application, you only need to run the following command in the Spring project root:
```
./mvnw package -f pom.xml
```
Or this one in case you want to build the native one:
```
./mvnw -DskipTests package -Pnative -f pom.xml
```
In this case, you will need to have the `GRAALVM_HOME` env variable defined. You only need this if you want to build the image locally. Otherwise, you can build it using docker by leveraging the Spring Boot maven plugin. It will pull a docker image of the GraalVM, and with that, it will create the native image of the app. To do that, run:
```
./mvnw spring-boot:build-image
```
You can also create a docker image with the JVM version of the app running the script `build_jvm_docker.sh` or:
```
docker build -f src/main/docker/Dockerfile.jvm -t spring-project:0.1-SNAPSHOT .
```

You can execute the script `start_app.sh` or `start_jvm.sh` to run the application locally. In this case, you will need the Postgres DB. You can run it in docker with the command:
```
docker run -e POSTGRES_PASSWORD=example -p 5432:5432 postgres
```
You can also run both application and DB from docker, using:
```
docker-compose -f src/main/docker/spring.yml up
```
But remember to rebuild the image to switch between native and JVM versions.

## Quarkus
The process to build and run the Quarkus application is very similar to the Spring Boot one. First, to create the native image, you also need either the GRAALVM installed and the `GRAALVM_HOME` env variable set, or we can use docker to build the native image.

To build the native version locally, run the command:
```
./mvnw package -Pnative -f pom.xml
```
Or this one to build using docker:
```
./mvnw package -Pnative -Dquarkus.native.container-build=true -f pom.xml
```
And to the JVM version:
```
./mvnw package -f pom.xml
```

To start the application locally, use either the scripts `start_app.sh` and `start_jvm.sh` with the docker DB:
```
docker run -e POSTGRES_PASSWORD=example -p 5432:5432 postgres
```
Or use the script to build the docker image of the application, running:
```bash
./build.sh

## script content
## ./mvnw quarkus:add-extension -Dextensions=container-image-docker
## ./mvnw package -Dquarkus.container-build=true -f pom.xml &&
## docker build -f src/main/docker/Dockerfile.jvm -t quarkus-project:0.1-SNAPSHOT .
```
To build the docker image of the JVM version, and running the following command to the native version:
```bash
./build.sh native

## script content
## ./mvnw quarkus:add-extension -Dextensions=container-image-docker
## ./mvnw package -Pnative -Dquarkus.native.container-build=true -f pom.xml &&
## docker build -f src/main/docker/Dockerfile.native -t quarkus-project:0.1-SNAPSHOT .
```
Then, once again, you can also run both application and DB from docker, using:
```
docker-compose -f src/main/docker/quarkus.yml up
```

Now you have all you need to reproduce the tests with your machine.

### Relevant Articles:

- [Spring Boot vs Quarkus](https://www.baeldung.com/spring-boot-vs-quarkus)
