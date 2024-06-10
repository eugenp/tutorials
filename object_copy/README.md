## About Code #

- This code contain example code used to experience object copy strategies.

### Tech Stack 

- Java 17
- Springboot 3

### Setup Guide 

1. Clone the Code

2. Install Dependencies : `` mvnw install ``

3. Run : `` mvnw spring-boot:run ``

### APIs

- --location 'http://localhost:8080/object/create' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"]
}'

- --location 'http://localhost:8080/object/shallow/copy' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"],
    "copyCartName":"MyCloneCopy",
    "copyCartItems": ["Apple"]
}'

- --location 'http://localhost:8080/object/deep/copy' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"],
    "copyCartName":"MyCloneCopy",
    "copyCartItems": ["Apple"]
}'

- --location 'http://localhost:8080/object/clone/copy' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"],
    "copyCartName":"MyCloneCopy",
    "copyCartItems": ["Apple"]
}'

- --location 'http://localhost:8080/object/deepclone/copy' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"],
    "copyCartName":"MyCloneCopy",
    "copyCartItems": ["Apple"]
}'

- --location 'http://localhost:8080/object/error/copy' \
--header 'Content-Type: application/json' \
--data '{
    "sourceCartName":"MyCart",
    "sourceCartItems":["Bread"],
    "copyCartName":"MyCloneCopy",
    "copyCartItems": ["Apple"]
}'