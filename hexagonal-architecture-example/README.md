# hexagonal-architecture-example
This code base has the example implementation of the hexagonal architecture

The technologies that are used in this project are listed below.
-   Spring Boot
-   Spring Data JPA
-   H2 (In Memory Data Base)
-   Junit
-   gradle

## Architecture

- Listed below the representation of the classes that are used in this example.
 
![](https://github.com/dilipsundarraj1/hexagonal-architecture-example/blob/master/images/Hexagonal%20Architecturen%20-%20Item%20Service.png)

## Build the project

- Run the below command to build the project

```
./gradlew clean build
```

## Run the project

```youtrack
java -jar build/libs/hexagonal-architecture-example-0.0.1-SNAPSHOT.jar
```

### Curl Commands to perform the CRUD operation

#### Add a new Item

```
curl -d '{"id":100,"sku":"SKU001","itemDescription":"Iphone XR","itemPrice":999.99}
' -H "Content-Type: application/json" -X POST http://localhost:8080/v1/item
```

#### Get All Items

```youtrack
curl http://localhost:8080/v1/allitems

```

#### Get A Single Item

```youtrack
curl http://localhost:8080/v1/item/100
```

#### Delete an ITEM

```youtrack
curl -X "DELETE" http://localhost:8080/v1/item/100
```

####  Update An Item

```youtrack
curl -d '{"id":100,"sku":"SKU001","itemDescription":"Iphone XR","itemPrice":799.99}
' -H "Content-Type: application/json" -X PUT http://localhost:8080/v1/item

```
