## Spring Boot Graphql

This module contains articles about Spring Boot Graphql 2

### The Course
The "REST With Spring" Classes: http://bit.ly/restwithspring

### Relevant Articles:
- [GraphQL vs REST](https://www.baeldung.com/graphql-vs-rest)
- [Implementing GraphQL Mutation Without Returning Data](https://www.baeldung.com/java-graphql-mutation-no-return-data)
- [Upload Files With GraphQL in Java](https://www.baeldung.com/java-graphql-upload-file)

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
