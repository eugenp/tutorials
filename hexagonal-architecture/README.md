# A quick and practical example of Hexagonal Architecture in Java

This is a quick and practical example of Hexagonal Architecture in java with spring boot.


## Prerequisite
1. JDK 16

```
git clone https://github.com/ketankvishwakarma/baeldung-evaluation.git
cd baeldung-evaluation
git checkout dev
./mvnw spring-boot:run
```


## Add Pome

```
curl --location --request POST 'localhost:8080/poems' \
--header 'Content-Type: application/json' \
--data-raw '{
  "title": "Stopping by Woods on a Snowy Evening",
  "author": "Robert Frost"
}'
```


## Get Poems

```curl
curl --location --request GET 'localhost:8080/poems' \
--header 'Content-Type: application/json' \
--data-raw '{
  "title": "Stopping by Woods on a Snowy Evening",
  "author": "Robert Frost"
}'
```
