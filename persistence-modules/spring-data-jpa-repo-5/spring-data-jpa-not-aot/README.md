## Compile not AOT

```shell
mvn clean install
```

Compilation time: `Total time:  11.076 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run
```

Startup times:
`Root WebApplicationContext: initialization completed in 1103 ms`
`Started Application in 4.431 seconds (process running for 4.723)`

```shell
java -jar target/spring-data-jpa-not-aot-0.0.1-SNAPSHOT.jar
```

Startup times:
`Started Application in 8.199 seconds (process running for 9.138)`

## Performance

### Time startup

from root `sudo ./scripts/startup-linux.sh non-aot`:

```shell
==== RESULTS ====
time elapsed 10148 millis
Process Specific Memory/CPU (RSS KB / CPU Time): 289840 00:00:28
```

### Time startup

from root `sudo ./scripts/load-test-linux.sh non-aot`:

```shell
==== RESULTS ====
Total requests: 6688
Success (2xx): 6688
Failed: 0
Avg time (curl): 0.00895849s
Avg duration (measured): 51.7629ms
P95: 0.022913s
Max: 0.388895s

Max memory utilised: 331888
Memory/CPU (RSS KB / TIME):
Before: 263536 00:00:23
After : 328644 00:01:11

```
