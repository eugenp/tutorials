# Spring Boot vs Quarkus

To follow this tutorial, you will need the following things:
- GRAALVM (https://www.graalvm.org/)
- VisualVM (https://visualvm.github.io/)
- Maven (Embedded, IDE, or local installation)
- Docker (https://www.docker.com/)
- Jmeter (https://jmeter.apache.org/)
- wrk (https://github.com/wg/wrk)
- hyperfoil (https://hyperfoil.io/)
- lua (https://www.lua.org/)

To create this test, I used some custom features from Jmeter. You can install the Jmeter plugin manager here:
https://loadium.com/blog/how-to-install-use-jmeter-plugin. After that, please install the following plugins:
- https://jmeter-plugins.org/?search=jpgc-casutg

The test file is `load_test.jmx` in case of any change need. You can open it with the Jmeter GUI. For example, to run the start, you can execute the file `run_test.sh` or run the comment bellow:

```
$jmeter_home/bin/jmeter -n -t load_test.jmx -l log.csv -e -o ./report
```

Just remember to change the variable `jmeter_home` with the path to the JMeter folder. The path to the data files is relative, so either keep them in the same folder as the test or use Jmeter GUI to change it. Rememeber that as mentioned in the article, we cannot consider the response times recorded by Jmeter due to the Coordinated Omission Problem.

Open the VisualVM application and select your application to start monitoring before running the test, and of course, start the sample application first.

## Spring Boot
To build the application, you only need to run the following command in the Spring project root:
```
./mvnw clean package -f pom.xml
```
Or this one in case you want to build the native one:
```
./mvnw clean package -Pnative -f pom.xml
```
In this case, you will need to have the `GRAALVM_HOME` env variable defined. You only need this if you want to build the image locally. Otherwise, you can build it using docker by leveraging the Spring Boot maven plugin. It will pull a docker image of the GraalVM, and with that, it will create the native image of the app. To do that, run:
```
./mvnw clean package spring-boot:build-image -Pnative -f pom.xml
```
You can also create a docker image with the JVM version one of the app running the script `build.sh` or:
```
./mvnw clean package spring-boot:build-image -f pom.xml

```

You can execute the script `start_app.sh` or `start_jvm.sh` to run the application locally. In this case, you will need the Mysql DB. You can run it in docker with the command:
```
docker run --name mysqldb --network=host -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=baeldung -d mysql:5.7.38 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
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
docker run --name mysqldb --network=host -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=baeldung -d mysql:5.7.38 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
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

## Wrk
Another option to execute the load test is to use the wrk. This library is capable of generation a pretty high load only using a single core. To install it you only have to checkout the project compile it (using make) and define the `wrk_home` envvar. To run the test use:

```
./run_test_wrk.sh
```
You will need to have installed lua in your machine.

### Tips
If you want to run the applications in your machine you can use the following command to restrict the CPUs available to the app:

```
cpulimit -l 300 -p ## 300 means at most 3 cores.
```

This will make sure the load is on the application and not in the DB.
## Hyperfoil

To the hyperfoil test to get a report regarding the performance of the application, its throughput and response time. You can run the `docker_run.sh` from the hyperfoil folder, or the following: 

```
docker run -it -v volume:/benchmarks:Z -v tmp/reports:/tmp/reports:Z --network=host quay.io/hyperfoil/hyperfoil cli
```
And then:
```
start-local && upload /benchmarks/benchmark.hf.yaml && run benchmark
```
Optionally, we can extract a html report from it, by running:
```
report --destination=/tmp/reports
```

### Relevant Articles:

- [Spring Boot vs Quarkus](https://www.baeldung.com/spring-boot-vs-quarkus)
