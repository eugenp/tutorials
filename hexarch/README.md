## A Quick and Practical Example of Hexagonal Architecture in Java

### Compile and Run

```shell script
gradle clean bootRun
```

### Test examples


#### 1. Receive no messages:
```shell script
~ curl -X GET http://localhost:8080/chat/receive/alice
[]%
````

#### 2. Send a message to existent user:
```shell script
~ curl -X POST  -H 'Content-Type: application/json' -d'{"text":"hi there123456"}' \
  http://localhost:8080/chat/send/alice
true%
```

#### 3. Receive a message for existent user:
```shell script
~ curl -X GET http://localhost:8080/chat/receive/alice
[{"text":"hi there123456"}]
``` 