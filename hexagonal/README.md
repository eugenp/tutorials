#  A quick and practical example of Hexagonal Architecture in Java

Illustration of a simple app using the Ports and Adapters achitecture.

The app will only save in memory films and list list them. 

## Quick start

Running `com.baeldung.hexarch.demo.webadapter.WebApp` will bring up a web server in `http://localhost:8080`

You can save some films:

```shell script
curl -H "Content-Type: application/json" -d '{"id":"1", "name":"Batman", "score": "93"}' http://localhost:8080/film 
curl -H "Content-Type: application/json" -d '{"id":"2", "name":"The Searchers", "score": "92"}' http://localhost:8080/film 
curl -H "Content-Type: application/json" -d '{"id":"3", "name":"Predator", "score": "88"}' http://localhost:8080/film
```

And then you can retrieve the saved films with the following curl command
```shell script
curl -X GET http://localhost:8080/film
```
