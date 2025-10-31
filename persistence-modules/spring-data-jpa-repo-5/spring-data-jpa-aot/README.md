Compile AOT

```shell
mvn clean install -Paot
```

Compilation time: `14.747 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run -Dspring.aot.enabled=true -Dspring.aot.repositories.enabled=false
```

Startup times:
`Root WebApplicationContext: initialization completed in 470 ms`
`Started Application in 2.036 seconds`

```shell
java -Dspring.aot.enabled=true \
     -Dspring.aot.repositories.enabled=false \
     -agentlib:native-image-agent=config-output-dir=target/native-image-hints \
     -jar target/spring-data-jpa-aot-0.0.1-SNAPSHOT.jar
```

Startup times:
`Root WebApplicationContext: initialization completed in 461 ms`
`Started Application in 3.29 seconds`

## Performance

### Time startup
from root `sudo ./scripts/startup.sh aot`:
```shell
time elapsed 5217 millis
Threads:       56
Memory/CPU (RSS KB / VSZ KB / %CPU / CPU Time): 448448 557256448 173.9   0:11.70
```
