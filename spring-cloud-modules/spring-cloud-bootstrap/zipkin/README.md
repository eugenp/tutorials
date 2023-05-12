# Zipkin server

Zipkin project [deprecated custom server](https://github.com/openzipkin/zipkin/tree/master/zipkin-server). 
It's no longer possible to run a custom Zipkin server compatible with Spring Cloud or even Spring Boot.

The best approach to run a Zipkin server is to use docker. We provided a docker-compose file that you can run:

```bash
$ docker compose up -d
```

After that Zipkin is accessible via [http://localhost:9411](http://localhost:9411)

Alternatively, you can run the Zipkin Jar file,

```bash
$ curl -sSL https://zipkin.io/quickstart.sh | bash -s
$ java -jar zipkin.jar
```