## Compile AOT

```shell
mvn clean install -Paot
```

Compilation time: `Total time:  17.166 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run -Dspring.aot.enabled=true -Dspring.aot.repositories.enabled=false
```

Startup times:
`Root WebApplicationContext: initialization completed in 1370 ms`
`Started Application in 4.897 seconds (process running for 5.349)`

```shell
java -Dspring.aot.enabled=true \
     -Dspring.aot.repositories.enabled=false \
     -jar target/spring-data-jpa-aot-0.0.1-SNAPSHOT.jar
```

Startup times:
`Started Application in 5.995 seconds (process running for 6.935)`

## Performance

### Time startup

from root `sudo ./scripts/startup-linux.sh aot`:

```shell
==== RESULTS ====
time elapsed 9885 millis
Process Specific Memory/CPU (RSS KB / CPU Time): 291104 00:00:25
```

### Time startup

from root `sudo ./scripts/load-test-linux.sh aot`:

```shell
==== RESULTS ====
Total requests: 7664
Success (2xx): 7664
Failed: 0
Avg time (curl): 0.00770692s
Avg duration (measured): 43.934ms
P95: 0.017294s
Max: 0.437678s

Max memory utilised: 334000
Memory/CPU (RSS KB / TIME):
Before: 282004 00:00:24
After : 332724 00:00:51
```
