# GraphQL Java

## GraphQL简介

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
    - Type 类型。类型定义了可以从GraphQL服务器返回的响应数据的形式，包括与其他类型有边缘关系的字段(including fields that are edges to other Types)。
    - Input 输入：像一个类型，但定义了发送到GraphQL服务器的输入数据的形式。
    - Scalar 标量：是一个原始类型，如String、Int、Boolean、Float等。
    - Interface 接口。接口将存储字段的名称和它们的参数，因此GraphQL对象可以从它那里继承，确保使用特定的字段。
    - Schema 模式。在GraphQL中，Schema管理查询和突变，定义了允许在GraphQL服务器中执行的内容。

    1. 模式加载

        有两种方法可以将模式加载到GraphQL服务器。

        - 通过使用GraphQL的接口定义语言（IDL）。
        - 通过使用支持的编程语言。

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

        为了创建我们的例子，首先让我们开始导入所需的依赖性，它依赖于 com.graphql-java.Graphql-java-annotations 模块。

        我们还实现了一个HTTP库，以减轻我们应用程序中的设置。我们将使用[Ratpack](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22io.ratpack%22%20AND%20a%3A%22ratpack-core%22)（尽管它也可以用Vert.x、Spark、Dropwizard、Spring Boot等实现）。

        让我们也导入Ratpack的依赖性：io.ratpack.ratpack-core 。

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
        <!-- CREATE 操作 -->
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

        <!-- 检索(RETRIEVE)操作 -->
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

5. 结论

   GraphQL是一种将客户机/服务器之间的复杂性降至最低的简单且极具吸引力的方法，作为RESTAPI的替代方法。

## 从Java应用中调用GraphQL服务

1. 概述

    GraphQL是一个相对较新的概念，用于构建网络服务，作为REST的替代。最近出现了一些用于创建和调用GraphQL服务的Java库。

    在本教程中，我们将研究GraphQL模式、查询和变异。我们将看到如何在普通Java中创建和模拟一个简单的GraphQL服务器。然后，我们将探讨如何使用著名的HTTP库来调用GraphQL服务。

    最后，我们还将探索可用的第三方库，以进行GraphQL服务调用。

2. GraphQL

    GraphQL是一种网络服务的查询语言，也是一种使用类型系统执行查询的服务器端运行时间。

    GraphQL服务器使用GraphQL模式来指定API的能力。这允许GraphQL客户端准确地指定从API中检索哪些数据。这可能包括单一请求中的子资源和多个查询。

    1. GraphQL模式

        GraphQL服务器用一组类型来定义服务。这些类型描述了你可以使用该服务查询的可能数据集。

        GraphQL服务可以用任何语言编写。然而，GraphQL模式需要使用一种叫做GraphQL模式语言的DSL来定义。

        在我们的GraphQL模式的例子中，我们将定义两种类型（书籍和作者）和一个单一的查询操作来获取所有的书籍（allBooks）。

        ```GraphQL
        type Book {
            title: String!
            author: Author
        }
        type Author {
            name: String!
            surname: String!
        }
        type Query {
            allBooks: [Book]
        }
        schema {
            query: Query
        }
        ```

        Query类型很特别，因为它定义了GraphQL查询的入口点。

    2. 查询和突变

        GraphQL服务是通过定义类型和字段，以及为不同的字段提供函数来创建的。

        在其最简单的形式中，GraphQL是关于询问对象的特定字段。例如，我们可以查询获取所有书名。

        ```GraphQL
        {
            "allBooks" {
                "title"
            }
        }
        ```

        尽管它看起来很相似，但这不是JSON。它是一种特殊的GraphQL查询格式，支持参数、别名、变量等。

        一个GraphQL服务会以这样的JSON格式的响应来回应上述查询。

        ```JSON
        {
            "data": {
                "allBooks": [
                    {
                        "title": "Title 1"
                    },
                    {
                        "title": "Title 2"
                    }
                ]
            }
        }
        ```

        在本教程中，我们将专注于使用查询来获取数据。然而，有必要提及GraphQL中的另一个特殊概念--突变。

        任何可以引起修改的操作都会使用突变类型来发送。

3. GraphQL服务器

    让我们使用上面定义的模式在Java中创建一个简单的GraphQL服务器。我们将利用[GraphQL Java](https://www.graphql-java.com/)库来实现我们的GraphQL服务器。

    我们将首先定义我们的GraphQL查询，并实现我们的示例GraphQL模式中指定的allBooks方法。

    ```java
    public class GraphQLQuery implements GraphQLQueryResolver {
        private BookRepository repository;
        public GraphQLQuery(BookRepository repository) {
            this.repository = repository;
        }
        public List<Book> allBooks() {
            return repository.getAllBooks();
        }
    }
    ```

    接下来，为了暴露我们的GraphQL端点，我们将创建一个Web servlet。

    参见 graphql.server/GraphQLEndpoint.java

    在servlet init方法中，我们将解析位于资源文件夹中的GraphQL模式。最后，利用解析后的模式，我们可以创建一个SimpleGraphQLHttpServlet的实例。

    我们将使用[maven-war-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-war-plugin)来打包我们的应用程序，并使用[jetty-maven-plugin](https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-maven-plugin)来运行它。

    `mvn jetty:run`

    现在，我们已经准备好运行和测试我们的GraphQL服务，发送一个请求到。

    `http://localhost:8080/graphql?query={allBooks{title}}`

    - [x] [ERROR] Failed to execute goal org.eclipse.jetty:jetty-maven-plugin:10.0.7:run (default-cli) on project graphql-java: Execution default-cli of goal org.eclipse.jetty:jetty-maven-plugin:10.0.7:run failed: Unable to load the mojo 'run' in the plugin 'org.eclipse.jetty:jetty-maven-plugin:10.0.7' due to an API incompatibility: org.codehaus.plexus.component.repository.exception.ComponentLookupException: org/eclipse/jetty/maven/plugin/JettyRunMojo has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0
      需要运行在 java11 环境下。

    - [X] [ERROR] jetty-maven-plugin:10.0.14:run (default-cli) @ graphql-java --- [INFO] Packaging type [jar] is unsupported
      jetty-maven-plugin 不加载 jar 包，添加 `<configuration><supportedPackagings>jar</supportedPackagings></configuration>`
    - [ ] Failed startup of context o.e.j.m.p.MavenWebAppContext@31e84f10 java.util.ServiceConfigurationError: org.apache.juli.logging.Log: org.eclipse.jetty.apache.jsp.JuliLog not a subtype

4. HTTP客户端

    与REST服务一样，GraphQL服务是通过HTTP协议公开的。因此，我们可以使用任何Java HTTP客户端来调用GraphQL服务。

    1. 发送请求

        让我们试着向我们在上一节中创建的GraphQL服务发送一个请求。

        参见  graphql.clients/ApacheHttpClient.callGraphQLService()

        在我们的例子中，我们使用了[Apache HttpClient](https://www.baeldung.com/httpclient4)。然而，任何Java HTTP客户端都可以使用。

    2. 解析响应

        接下来，我们来解析来自GraphQL服务的响应。GraphQL服务发送JSON格式的响应，与REST服务相同。

        ```java
        HttpResponse httpResponse = callGraphQLService(serviceUrl, "{allBooks{title}}");
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name());
        Response parsedResponse = objectMapper.readValue(actualResponse, Response.class);
        assertThat(parsedResponse.getData().getAllBooks()).hasSize(2);
        ```

        在我们的例子中，我们使用了流行的Jackson库中的ObjectMapper。然而，我们可以使用任何Java库进行JSON序列化/反序列化。

    3. Mocking响应

        与其他通过HTTP暴露的服务一样，我们可以模拟GraphQL服务器的响应，以达到测试目的。

        我们可以利用[MockServer](https://www.baeldung.com/mockserver)库来模拟外部GraphQL HTTP服务。

        参见 graphql/GraphQLMockServer.java

        我们的示例模拟服务器将接受一个GraphQL查询作为参数，并在正文中响应一个JSON响应。

5. 外部库

    最近出现了几个Java GraphQL库，允许更简单的GraphQL服务调用。

    1. 美国运通Nodes

        Nodes是美国运通的一个GraphQL客户端，旨在从标准模型定义中构建查询。要开始使用它，我们应该首先添加所需的依赖性。

        com.github.americanexpress.nodes.nodes.0.5.0

        该库目前托管在JitPack上，我们也应该把它添加到我们的Maven安装仓库中。

        ```xml
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
        ```

        一旦解决了依赖关系，我们就可以利用GraphQLTemplate来构建一个查询，并调用我们的GraphQL服务。

        参见 graphql.clients/AmericanExpressNodes.GraphQLResponseEntity()

        节点将使用我们指定的类解析来自GraphQL服务的响应。

        ```java
        GraphQLResponseEntity<Data> responseEntity = callGraphQLService(serviceUrl, "{allBooks{title}}");
        assertThat(responseEntity.getResponse().getAllBooks()).hasSize(2);
        ```

        我们应该注意，Nodes仍然要求我们构建自己的DTO类来解析响应。

    2. GraphQL Java生成器

        [GraphQL Java Generator](https://github.com/graphql-java-generator/graphql-maven-plugin-project) 库利用了基于GraphQL模式生成Java代码的能力。

        这种方法类似于SOAP服务中使用的WSDL代码生成器。要开始使用它，我们应该首先添加所需的依赖性。

        com.graphql-java-generator.graphql-java-runtime.1.18

        接下来，我们可以配置graphql-maven-plugin来执行generateClientCode目标。

        ```xml
        <plugin>
            <groupId>com.graphql-java-generator</groupId>
            <artifactId>graphql-maven-plugin</artifactId>
            <version>1.18</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generateClientCode</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <packageName>com.baeldung.graphql.generated</packageName>
                <copyRuntimeSources>false</copyRuntimeSources>
                <generateDeprecatedRequestResponse>false</generateDeprecatedRequestResponse>
                <separateUtilityClasses>true</separateUtilityClasses>
            </configuration>
        </plugin>
        ```

        一旦我们运行Maven构建命令，该插件将生成调用GraphQL服务所需的DTO和实用类。

        生成的QueryExecutor组件将包含调用GraphQL服务和解析其响应的方法。

        生成的QueryExecutor组件将包含调用我们的GraphQL服务和解析其响应的方法。

        ```java
        public List<Book> allBooks(String queryResponseDef, Object... paramsAndValues)
        throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
            logger.debug("Executing query 'allBooks': {} ", queryResponseDef);
            ObjectResponse objectResponse = getAllBooksResponseBuilder()
            .withQueryResponseDef(queryResponseDef).build();
            return allBooksWithBindValues(objectResponse, 
            graphqlClientUtils.generatesBindVariableValuesMap(paramsAndValues));
        }
        ```

        然而，它是为与Spring框架一起使用而构建的。

## 从GraphQL返回Map

1. 概述

    多年来，GraphQL已被广泛接受为网络服务的通信模式之一。虽然它在使用上很丰富和灵活，但在某些情况下可能会带来挑战。其中之一就是从一个查询中返回一个Map，这是一个挑战，因为Map在GraphQL中不是一个类型。

    在本教程中，我们将学习从GraphQL查询中返回Map的技术。

2. 例子

    让我们以一个产品数据库为例，它有无限多的自定义属性。

    一个产品，作为一个数据库实体，可能有一些固定的字段，如名称、价格、类别等。但是，它也可能有不同类别的属性。这些属性应该以一种保持其识别键的方式返回给客户。

    为了这个目的，我们可以使用Map作为这些属性的类型。

3. 返回Map

    为了返回一个Map，我们有三个选择。

    - 作为JSON字符串返回
    - 使用GraphQL自定义[标量类型](https://graphql.org/learn/schema/#scalar-types)
    - 以键值对列表的形式返回

    对于前两个选项，我们将使用以下GraphQL查询。

    ```GraphQL
    query {
        product(id:1){ 
            id 
            name 
            description 
            attributes 
        }
    }
    ```

    参数属性将以Map格式表示。

    接下来，我们来看看这三个选项。

    1. JSON字符串

        这是最简单的选项。我们将在产品解析器中把Map序列化为JSON字符串格式。

        `String attributes = objectMapper.writeValueAsString(product.getAttributes());`

        GraphQL模式本身如下。

        ```GraphQL
        type Product {
            id: ID
            name: String!
            description: String
            attributes:String
        }
        ```

        下面是这样实现后的查询结果。

        ```json
        {
        "data": {
            "product": {
            "id": "1",
            "name": "Product 1",
            "description": "Product 1 description",
            "attributes": "{\"size\": {
                                            \"name\": \"Large\",
                                            \"description\": \"This is custom attribute description\",
                                            \"unit\": \"This is custom attribute unit\"
                                            },
                        \"attribute_1\": {
                                            \"name\": \"Attribute1 name\",
                                            \"description\": \"This is custom attribute description\",
                                            \"unit\": \"This is custom attribute unit\"
                                            }
                                }"
            }
        }
        }
        ```

        这个选项有两个问题。第一个问题是，JSON字符串需要在客户端处理成一个可行的格式。第二个问题是我们不能在属性上有一个子查询。

        为了克服第一个问题，GraphQL自定义标量类型的第二个选项可以帮助我们。

    2. GraphQL自定义标量类型

        在实现方面，我们将利用Java中GraphQL的[扩展标量库](https://github.com/graphql-java/graphql-java-extended-scalars)。

        首先，我们将在pom.xml中包含graphql-java-extended-scalars依赖项。

        ```xml
        <dependency>
            <groupId>com.graphql-java</groupId>
            <artifactId>graphql-java-extended-scalars</artifactId>
            <version>2022-04-06T00-10-27-a70541e</version>
        </dependency>
        ```

        然后，我们将在 GraphQL 配置组件中注册我们选择的标量类型。在这种情况下，标量类型是JSON。

        ```java
        @Bean
        public GraphQLScalarType json() {
            return ExtendedScalars.Json;
        }
        ```

        最后，我们将相应地更新我们的GraphQL模式。

        ```GraphQL
        type Product {
            id: ID
            name: String!
            description: String
            attributes: JSON
        }
        scalar JSON
        ```

        下面是这样实现后的结果。

        ```json
        {
            "data": {
                "product": {
                "id": "1",
                "name": "Product 1",
                "description": "Product 1 description",
                "attributes": {
                        "size": {
                            "name": "Large",
                            "description": "This is custom attribute description",
                            "unit": "This is a custom attribute unit"
                        },
                        "attribute_1": {
                            "name": "Attribute1 name",
                            "description": "This is custom attribute description",
                            "unit": "This is a custom attribute unit"
                        }
                    }
                }
            }
        }
        ```

        有了这种方法，我们就不需要在客户端处理属性图了。然而，标量类型也有自己的限制。

        在GraphQL中，标量类型是查询的叶子，这表明它们不能被进一步查询。

    3. 键值对的列表

        如果需求是进一步查询Map，那么这是最可行的方案。我们将把Map对象转化为一个键值对对象的列表。

        下面是我们代表键值对的类。

        ```java
        public class AttributeKeyValueModel {
            private String key;
            private Attribute value;
            public AttributeKeyValueModel(String key, Attribute value) {
                this.key = key;
                this.value = value;
            }
        }
        ```

        在产品解析器中，我们将添加以下实现。

        ```java
        List<AttributeKeyValueModel> attributeModelList = new LinkedList<>();
        product.getAttributes().forEach((key, val) -> attributeModelList.add(new AttributeKeyValueModel(key, val)));
        ```

        最后，我们将更新模式：

        ```GraphQL
        type Product {
            id: ID
            name: String!
            description: String
            attributes:[AttributeKeyValuePair]
        }
        type AttributeKeyValuePair {
            key:String
            value:Attribute
        }
        type Attribute {
            name:String
            description:String
            unit:String
        }
        ```

        既然我们已经更新了模式，我们也要更新查询。

        ```GraphQL
        query {
            product(id:1){ 
                id 
                name 
                description 
                attributes {
                    key 
                    value {
                        name 
                        description 
                        unit
                    }
                } 
            }
        }
        ```

        现在，让我们看一下结果。

        ```json
        {
        "data": {
                "product": {
                    "id": "1",
                    "name": "Product 1",
                    "description": "Product 1 description",
                    "attributes": [
                        {
                        "key": "size",
                        "value": {
                                "name": "Large",
                                "description": "This is custom attribute description",
                                "unit": "This is custom attribute unit"
                            }
                        },
                        {
                        "key": "attribute_1",
                        "value": {
                                "name": "Attribute1 name",
                                "description": "This is custom attribute description",
                                "unit": "This is custom attribute unit"
                            }
                        }
                    ]
                }
            }
        }
        ```

        这个选项也有两个问题。GraphQL查询变得有点复杂。而且对象结构需要硬编码。未知的map对象在这种情况下不会工作。

4. Run

    - [ ] Exception in thread "main" java.lang.NoSuchMethodError: `graphql.execution.ExecutionStrategy.<init>(Lgraphql/execution/DataFetcherExceptionHandler;)V`

## Relevant articles

- [ ] [Introduction to GraphQL](https://www.baeldung.com/graphql)
- [ ] [Make a Call to a GraphQL Service from a Java Application](https://www.baeldung.com/java-call-graphql-service)
- [ ] [Return Map from GraphQL](https://www.baeldung.com/java-graphql-return-map)

## Code

一如既往，这个例子可以在我们的[GitHub](https://github.com/eugenp/tutorials/tree/master/graphql-modules/graphql-java)仓库中找到。
