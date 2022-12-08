# GraphQL Java

## GraphQL简介

通过学习Spring课程，开始学习Spring 5和Spring Boot 2。

1. 概述

    [GraphQL](https://graphql.github.io/)是一种查询语言，由Facebook创建，目的是在直观和灵活的语法基础上建立客户端应用程序，用于描述他们的数据需求和互动。

    传统的REST调用的主要挑战之一是客户端无法请求定制（有限的或扩展的）数据集。在大多数情况下，一旦客户端向服务器请求信息，它要么得到所有的字段，要么没有。

    另一个困难是工作和维护多个端点。随着一个平台的发展，随之而来的是数量的增加。因此，客户端经常需要从不同的端点请求数据。

    当建立一个GraphQL服务器时，只需要一个URL来获取和改变所有的数据。因此，客户端可以通过向服务器发送一个查询字符串，描述他们想要的东西，来请求一组数据。

2. 基本的GraphQL命名法

    让我们看一下GraphQL的基本术语。

    - Query 查询：是一个向GraphQL服务器请求的只读操作。
    - Mutation 变异：是向GraphQL服务器请求的读写操作。
    - Resolver 解析器：在GraphQL中，Resolver负责映射操作和在后端运行的代码，后者负责处理请求。它类似于RESTFul应用程序中的MVC后端。
    - Type 类型。类型定义了可以从GraphQL服务器返回的响应数据的形式，包括与其他类型有边缘关系的字段。
    - Input 输入：像一个类型，但定义了发送到GraphQL服务器的输入数据的形式。
    - Scalar 标量：是一个原始类型，如String、Int、Boolean、Float等。
    - Interface 接口。接口将存储字段的名称和它们的参数，因此GraphQL对象可以从它那里继承，确保使用特定的字段。
    - Schema 模式。在GraphQL中，Schema管理查询和突变，定义了允许在GraphQL服务器中执行的内容。

    1. 模式加载

        有两种方法可以将模式加载到GraphQL服务器。

        - 通过使用GraphQL的接口定义语言（IDL）。
        - 通过使用支持的编程语言之一。

        让我们来演示一个使用IDL的例子。

        ```txt
        type User {
            firstName: String
        }
        ```

        现在，一个使用Java代码的模式定义的例子。

        ```java
        GraphQLObjectType userType = newObject()
            .name("User")  
            .field(newFieldDefinition()
            .name("firstName")
            .type(GraphQLString))
            .build();
        ```

3. 接口定义语言

    接口定义语言（Interface Definition Language, IDL）或模式定义语言（Schema Definition Language, SDL）是指定GraphQL模式的最简洁的方法。语法是明确定义的，并将在官方GraphQL规范中采用。

    例如，让我们为用户/电子邮件创建一个GraphQL模式，可以像这样指定。

    ```java
    schema {
        query: QueryType
    }

    enum Gender {
        MALE
        FEMALE
    }

    type User {
        id: String!
        firstName: String!
        lastName: String!
        createdAt: DateTime!
        age: Int! @default(value: 0)
        gender: [Gender]!
        emails: [Email!]! @relation(name: "Emails")
    }

    type Email {
        id: String!
        email: String!
        default: Int! @default(value: 0)
        user: User @relation(name: "Emails")
    }
    ```

4. GraphQL-java

GraphQL-java是一个基于规范和[JavaScript参考实现](https://github.com/graphql/graphql-js)的实现。请注意，它至少需要Java 8才能正常运行。

   1. GraphQL-java注解

        GraphQL还可以使用[Java注释](https://github.com/graphql-java/graphql-java-annotations)来生成其模式定义，而不需要使用传统IDL方法所产生的所有模板代码。

   2. 依赖关系

        为了创建我们的例子，首先让我们开始导入所需的依赖性，它依赖于com.graphql-java.Graphql-java-annotations模块。

        我们还实现了一个HTTP库，以减轻我们应用程序中的设置。我们将使用[Ratpack](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22io.ratpack%22%20AND%20a%3A%22ratpack-core%22)（尽管它也可以用Vert.x、Spark、Dropwizard、Spring Boot等实现）。

        让我们也导入Ratpack的依赖性。io.ratpack.ratpack-core

   3. 实施

        让我们创建我们的例子：一个简单的API，为用户提供一个 "CRUDL"（创建、检索、更新、删除和列表）。首先，让我们创建我们的用户POJO。

        见：graphql.entity.User.java

        在这个POJO中，我们可以看到@GraphQLName（“user”）注释，这表明这个类是由GraphQL映射的，每个字段都用@GraphSQLField注释。

        接下来，我们将创建UserHandler类。该类从所选的HTTP连接器库（在本例中为Ratpack）继承了一个处理程序方法，该方法将管理和调用GraphQL的Resolver特性。因此，将请求（JSON有效载荷）重定向到正确的查询或变异操作：

        见：graphql.handler.UserHandler.java

        现在，是将支持查询操作的类，即UserQuery。如上所述，从服务器向客户端检索数据的所有方法都由此类管理：graphql.query.UserQuery.java

        与UserQuery类似，现在我们创建UserMutation，它将管理所有打算更改服务器端存储的某些给定数据的操作：graphql.mutation.UserMutation.java

        值得注意的是UserQuery和UserMutation类中的注释：@GraphQLName（“query”）和@GraphQName（“mutation”）。这些注释分别用于定义查询和变异操作。

        由于GraphQL java服务器能够运行查询和变异操作，我们可以使用以下JSON有效负载来测试客户端对服务器的请求：

        ```json
        <!-- CREATE操作 -->
        {
            "query": "mutation($name: String! $email: String!){
            createUser (name: $name email: $email) { id name email age } }",
            "parameters": {
                "name": "John",
                "email": "john@email.com"
            }
        }

        <!-- 响应 -->
        {
            "data": {
                "createUser": {
                    "id": 1,
                    "name": "John",
                    "email": "john@email.com"
                }
            } 
        }
        <!-- 检索操作 -->
        {
            "query": "query($id: String!){ retrieveUser (id: $id) {name email} }",
            "parameters": {
                "id": 1
            }
        }
        <!-- 响应 -->
        {
            "data": {
                "retrieveUser": {
                    "name": "John",
                    "email": "john@email.com"
                }
            }
        }
        ```

        GraphQL提供了客户端可以自定义响应的功能。因此，在最后一个用作示例的RETRIVE操作中，我们可以不返回姓名和电子邮件，例如，只返回电子邮件：

        ```json
        {
            "query": "query($id: String!){ retrieveUser (id: $id) {email} }",
            "parameters": {
                "id": 1
            }
        }
        ```

        因此，GraphQL服务器返回的信息将只返回请求的数据：

        ```json
        {
            "data": {
                "retrieveUser": {
                    "email": "john@email.com"
                }
            }
        }
        ```

   4. GraphQL是一种将客户机/服务器之间的复杂性降至最低的简单且极具吸引力的方法，作为RESTAPI的替代方法。

## Relevant articles

- [x] [Introduction to GraphQL](https://www.baeldung.com/graphql)
- [Make a Call to a GraphQL Service from a Java Application](https://www.baeldung.com/java-call-graphql-service)
- [Return Map from GraphQL](https://www.baeldung.com/java-graphql-return-map)
