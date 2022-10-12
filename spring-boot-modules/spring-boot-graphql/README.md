## Spring Boot Graphql

This module contains articles about Spring Boot Graphql

### The Course
The "REST With Spring" Classes: http://bit.ly/restwithspring

### Relevant Articles:

- [Getting Started with GraphQL and Spring Boot](https://www.baeldung.com/spring-graphql)
- [Expose GraphQL Field with Different Name](https://www.baeldung.com/graphql-field-name)
- [Error Handling in GraphQL With Spring Boot](https://www.baeldung.com/spring-graphql-error-handling)
- [How to Test GraphQL Using Postman](https://www.baeldung.com/graphql-postman)
- [GraphQL vs REST](https://www.baeldung.com/graphql-vs-rest)

### GraphQL sample queries

Query
```shell script
curl \
--request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    recentPosts(count: 2, offset: 0) {\n        id\n        title\n        author {\n            id\n            posts {\n                id\n            }\n        }\n    }\n}"}'
```

Mutation
```shell script
curl \
--request POST 'localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"mutation {\n    createPost(title: \"New Title\", authorId: \"Author2\", text: \"New Text\") {\n id\n       category\n        author {\n            id\n            name\n        }\n    }\n}"}'
```
