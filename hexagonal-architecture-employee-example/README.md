# Hexagonal Architecture Employee Example
This modules contains the example on Hexagonal Architecture.

## Build
The application is using Maven Build Framework to build the application. The application can be built using command:

```
mvn clean install
```

## Run the Application

Add Employee record to the persistence:

```
curl --location --request POST 'http://localhost:8080/v1/hexagonal/employees' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Vikash",
    "lastName": "Agrawal"
}'
```

Retrieve the Employee Records from the persistence:

```
curl --location --request GET 'http://localhost:8080/v1/hexagonal/employees/1'
```
