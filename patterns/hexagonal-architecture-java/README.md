## Project

### Build

```
mvn clean package
```

### Run

```
java -jar infrastructure/target/infrastructure-1.0-jar-with-dependencies.jar
```

## Database

### Install MySQL

Tested with MySQL Community version 8.0.20.
Download it here: https://dev.mysql.com/downloads/installer/

### Create table

```
USE books_database;
CREATE TABLE books (
    id VARCHAR(40) PRIMARY KEY, 
    title VARCHAR(100) NOT NULL, 
    author VARCHAR(100) NOT NULL, 
    description VARCHAR(100) NOT NULL
);
```