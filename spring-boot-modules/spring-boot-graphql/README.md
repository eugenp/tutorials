## Spring Boot GraphQL

This module contains articles about  Spring Boot  combined with graphql server

### Relevant Articles:
- [Getting Started with GraphQL and Spring Boot](https://www.baeldung.com/spring-graphql)
- [Expose GraphQL Field with Different Name](https://www.baeldung.com/graphql-field-name)
- [Getting Started With GraphQL SPQR and Spring Boot](https://www.baeldung.com/spring-boot-graphql-spqr)
- [How to Test GraphQL Using Postman](https://www.baeldung.com/graphql-postman)


### GraphQL sample queries
Query
```shell script
curl \
--request POST 'localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\n    recentPosts(count: 2, offset: 0) {\n        id\n        title\n        author {\n            id\n            posts {\n                id\n            }\n        }\n    }\n}"}'
```

Mutation
```shell script
curl \
--request POST 'localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"mutation {\n    writePost(title: \"New Title\", author: \"Author2\", text: \"New Text\") {\n        id\n        category\n        author {\n            id\n            name\n        }\n    }\n}"}'
```
