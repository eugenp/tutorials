# Quarkus Testcontainers

From the [order-service](order-service) run the following 2 commands:
```
mvn package
```

```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/order-service-jvm .
```

This will package and build the image needed to run the [tests](customer-service/src/test/java/info/customer/CustomerResourceTest.java) in [customer-service](customer-service) in your IDE.

Otherwise from [customer-service](customer-service) run:
```
mvn test
```