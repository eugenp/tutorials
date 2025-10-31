Compile AOT

```shell
mvn clean install -Paot-repo
```

Compilation time: `14.200 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run -Dspring.aot.enabled=true -Dspring.aot.repositories.enabled=true
```

Startup times:
`Root WebApplicationContext: initialization completed in 477 ms`
`Started Application in 2.031 seconds`

2) run using the jar (same as 1 mostly)

```shell
java -Dspring.aot.enabled=true \
     -Dspring.aot.repositories.enabled=true \
     -agentlib:native-image-agent=config-output-dir=target/native-image-hints \
     -jar target/spring-data-jpa-aot-repository-0.0.1-SNAPSHOT.jar
```

Startup times:
`Root WebApplicationContext: initialization completed in 452 ms`
`Started Application in 3.26 seconds`

**NOTE**:
AOT is a mandatory step to transform a Spring application to a native executable, so it is automatically enabled when running within a native image. However it is also possible to use AOT optimizations on the JVM by setting the spring.aot.enabled System
property to true.