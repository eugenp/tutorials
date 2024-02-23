# Event Driven Microservices using Conductor

This is an example project showing how to build event driven applications using [Conductor](https://github.com/conductor-oss/conductor)

# Pre-requisites
1. Docker
2. Running conductor server

**Start the conductor server**

```shell
docker run --init -p 8080:8080 -p 1234:5000 conductoross/conductor-standalone:3.15.0
```

### Relevant Articles
- [Event-Driven Microservices With Orkes Conductor](https://www.baeldung.com/orkes-conductor-guide)
