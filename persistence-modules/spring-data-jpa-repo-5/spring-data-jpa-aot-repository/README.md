## Compile AOT, including AOT Repositories

```shell
mvn clean install -Paot-repo
```

Compilation time: `Total time:  23.390 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run -Dspring.aot.enabled=true -Dspring.aot.repositories.enabled=true
```

Startup times:
`Root WebApplicationContext: initialization completed in 1242 ms`
`Started Application in 4.758 seconds (process running for 5.136)`

2) run using the jar (same as 1 mostly)

```shell
java -Dspring.aot.enabled=true \
     -Dspring.aot.repositories.enabled=true \
     -jar target/spring-data-jpa-aot-repository-0.0.1-SNAPSHOT.jar
```

Startup times:
`Started Application in 6.001 seconds (process running for 6.923)`

## Performance

### Time startup

from root `sudo ./scripts/startup-linux.sh aot-repo`:

```shell
==== RESULTS ====
time elapsed 8745 millis
Process Specific Memory/CPU (RSS KB / CPU Time): 292864 00:00:23
```

### Time startup

from root `sudo ./scripts/load-test-linux.sh aot-repo`:

```shell
==== RESULTS ====
Total requests: 6673
Success (2xx): 6673
Failed: 0
Avg time (curl): 0.0109863s
Avg duration (measured): 49.0766ms
P95: 0.018761s
Max: 0.839634s

Max memory utilised: 337948
Memory/CPU (RSS KB / TIME):
Before: 285532 00:00:23
After : 377504 00:01:07
```

**NOTE**:
AOT is a mandatory step to transform a Spring application to a native executable, so it is automatically enabled when
running within a native image. However, it is also possible to use AOT optimizations on the JVM by setting the
spring.aot.enabled System
property to true.
