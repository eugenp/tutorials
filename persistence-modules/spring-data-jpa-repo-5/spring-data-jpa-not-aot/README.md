Compile not AOT

```shell
mvn clean install
```

Compilation time: `4.337 s`

1) run using maven and the spring-boot plugin:

```shell
mvn spring-boot:run
```

Startup times:
`Root WebApplicationContext: initialization completed in 466 ms`
`Started Application in 2.131 seconds`

```shell
java -jar target/spring-data-jpa-not-aot-0.0.1-SNAPSHOT.jar
```

Startup times:
`Root WebApplicationContext: initialization completed in 555 ms`
`Started Application in 2.641 seconds`

## Performance

### Time startup

from root `sudo ./scripts/startup.sh non-aot`:

```shell
time elapsed 4490 millis
Threads:       57
Memory/CPU (RSS KB / VSZ KB / %CPU / CPU Time): 407584 523866624 236.2   0:10.97
```

### Time startup

from root `sudo ./scripts/load-test.sh non-aot`:

```shell
time elapsed 4399 millis
Threads:       93
Memory/CPU START (RSS KB / VSZ KB / %CPU / CPU Time): 416096 523735552 249.0   0:11.09
Memory/CPU END (RSS KB / VSZ KB / %CPU / CPU Time): 866608 490269984 268.3   0:50.67
```
