# Hexagonal Architecture

## How to run
```sh
mvn spring-boot:run
```

## Example requests

**POST**
http://localhost:8080/book
```
{
	"id": 1,
    "title": "The Witcher"
}
```

**GET**
http://localhost:8080/book/1

**DELETE**
http://localhost:8080/book/1
