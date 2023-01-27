# Spring Boot MVC

This module contains articles about Spring Web MVC in Spring Boot projects.

## Spring MVC中的功能控制器

Spring 5引入了[WebFlux](https://www.baeldung.com/spring-5-functional-web)，这是一个新的框架，可以让我们使用[反应式](https://www.baeldung.com/spring-reactor)编程模型构建Web应用。

在本教程中，我们将看到如何将这种编程模型应用于Spring MVC的功能控制器。

1. Maven设置

    我们将使用Spring Boot来演示新的API。

    这个框架支持我们熟悉的基于注解的定义控制器的方法。但它也增加了一种新的特定领域语言，提供了一种定义控制器的功能方式。

    从Spring 5.2开始，[Spring Web MVC](https://www.baeldung.com/spring-mvc-tutorial)框架也将提供这种功能化方法。与WebFlux模块一样，RouterFunctions和RouterFunction是这个API的主要抽象。

    因此，让我们从导入[spring-boot-starter-web](https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-web)依赖项开始。

2. RouterFunction与@Controller

    在功能领域，网络服务被称为路由，传统的@Controller和@RequestMapping的概念被RouterFunction取代。

    为了创建我们的第一个服务，让我们采取一个基于注解的服务，看看如何将其转化为功能上的等价物。

    我们将使用一个返回产品目录中所有产品的服务的例子。

    ```java
    @RestController
    public class ProductController {

        @RequestMapping("/product")
        public List<Product> productListing() {
            return ps.findAll();
        }
    }
    ```

    现在，让我们来看看它的功能等价物。见 ProductController.java:

    `public RouterFunction<ServerResponse> productListing(ProductService ps) {}`

    1. 路由定义

        我们应该注意到，在函数式方法中，productListing()方法返回一个RouterFunction而不是响应体。这是对路由的定义，而不是对请求的执行。

        RouterFunction包括路径、请求头信息、一个处理函数，它将被用来生成响应体和响应头信息。它可以包含单个或一组Web服务。

        当我们研究嵌套路由时，我们将更详细地讨论网络服务组。

        在这个例子中，我们使用了RouterFunctions中的静态route()方法来创建一个RouterFunction。一个路由的所有请求和响应属性都可以用这个方法提供。

    2. 请求谓词

        在我们的例子中，我们在route()上使用GET()方法来指定这是一个GET请求，路径以字符串形式提供。

        当我们想指定请求的更多细节时，我们也可以使用RequestPredicate。

        例如，前面的例子中的路径也可以用RequestPredicate指定为。

        `RequestPredicates.path("/product")`

        在这里，我们使用了静态工具RequestPredicates来创建一个RequestPredicate的对象。

    3. 响应

        类似地，ServerResponse包含静态工具方法，用于创建响应对象。

        在我们的例子中，我们使用ok()将HTTP状态200添加到响应头文件中，然后使用body()来指定响应体。

        此外，ServerResponse还支持使用EntityResponse从自定义数据类型构建响应。我们还可以通过RenderingResponse使用Spring MVC的ModelAndView。

    4. 注册路由

        接下来，让我们使用@Bean注解来注册这个路由，将其添加到应用上下文中。

        ```java
        @SpringBootApplication
        public class SpringBootMvcFnApplication {
            @Bean
            RouterFunction<ServerResponse> productListing(ProductController pc, ProductService ps) {
                return pc.productListing(ps);
            }
        }
        ```

        现在，让我们用函数式方法实现一些我们在开发Web服务时遇到的常见用例。

3. 嵌套路由

    在一个应用程序中拥有一堆Web服务，并且根据功能或实体将它们划分为逻辑组，这是很常见的。例如，我们可能希望所有与产品有关的服务，都以./product开始。

    让我们在现有的/product路径上添加另一个路径，以通过它的名字找到一个产品。

    ```java
    public RouterFunction<ServerResponse> productSearch(ProductService ps) {
        return route().nest(RequestPredicates.path("/product"), builder -> {
            builder.GET("/name/{name}", req -> ok().body(ps.findByName(req.pathVariable("name"))));
        }).build();
    }
    ```

    在传统的方法中，我们会通过向@Controller传递路径来实现这一目标。然而，分组Web服务的功能等同于route()的nest()方法。

    在这里，我们首先提供我们想要分组的路径，也就是/product。接下来，我们使用构建器对象来添加路由，就像在前面的例子中一样。

    nest()方法负责将添加到构建器对象的路由与主RouterFunction合并。

4. 错误处理

    另一个常见的用例是要有一个自定义的错误处理机制。我们可以使用route()的onError()方法来定义一个自定义的异常处理程序。

    这等同于在基于注解的方法中使用@ExceptionHandler。但它要灵活得多，因为它可以用来为每组路由定义单独的异常处理程序。

    让我们在前面创建的产品搜索路由中添加一个异常处理程序，以处理未找到产品时抛出的一个自定义异常。

    ```java
    public RouterFunction<ServerResponse> productSearch(ProductService ps) {
        return route()...
        .onError(ProductService.ItemNotFoundException.class,
            (e, req) -> EntityResponse.fromObject(new Error(e.getMessage()))
            .status(HttpStatus.NOT_FOUND)
            .build())
        .build();
    }
    ```

    onError()方法接受Exception类对象，并期望从功能实现中得到一个ServerResponse。

    我们使用了EntityResponse，它是ServerResponse的一个子类型，在这里从自定义的数据类型Error构建一个响应对象。然后我们添加状态并使用EntityResponse.build()返回一个ServerResponse对象。

5. 过滤器

    实现认证以及管理跨领域问题（如日志和审计）的一个常见方法是使用过滤器。过滤器被用来决定是否继续或中止对请求的处理。

    让我们举个例子，我们想要一个新的路由，将一个产品添加到目录中。

    ```java
    public RouterFunction<ServerResponse> adminFunctions(ProductService ps) {
        return route().POST("/product", req -> ok().body(ps.save(req.body(Product.class))))
        .onError(IllegalArgumentException.class, 
            (e, req) -> EntityResponse.fromObject(new Error(e.getMessage()))
            .status(HttpStatus.BAD_REQUEST)
            .build())
            .build();
    }
    ```

    由于这是一个管理函数，我们还想对调用服务的用户进行认证。

    我们可以通过在route()上添加一个filter()方法来做到这一点。

    ```java
    public RouterFunction<ServerResponse> adminFunctions(ProductService ps) {
    return route().POST("/product", req -> ok().body(ps.save(req.body(Product.class))))
        .filter((req, next) -> authenticate(req) ? next.handle(req) : 
        status(HttpStatus.UNAUTHORIZED).build())
        ....;
    }
    ```

    在这里，由于filter()方法提供了请求以及next handler，我们用它来做一个简单的认证，如果成功的话，允许产品被保存，如果失败的话，则向客户端返回一个UNAUTHORIZED错误。

6. Cross-Cutting关注

    有时，我们可能想在一个请求之前、之后或周围执行一些行动。例如，我们可能想记录传入请求和传出响应的一些属性。

    让我们在每次应用程序找到匹配的传入请求时记录一条语句。我们将使用route()上的before()方法来做这个。

    ```java
    @Bean
    RouterFunction<ServerResponse> allApplicationRoutes(ProductController pc, ProductService ps) {
        return route()...
        .before(req -> {
            LOG.info("Found a route which matches " + req.uri()
                .getPath());
            return req;
        })
        .build();
    }
    ```

    同样地，我们可以在请求被处理后，使用route()上的after()方法添加一个简单的日志语句。

    ```java
    @Bean
    RouterFunction<ServerResponse> allApplicationRoutes(ProductController pc, ProductService ps) {
        return route()...
        .after((req, res) -> {
            if (res.statusCode() == HttpStatus.OK) {
                LOG.info("Finished processing request " + req.uri()
                    .getPath());
            } else {
                LOG.info("There was an error while processing request" + req.uri());
            }
            return res;
        })          
        .build();
    }
    ```

## 用多种MIME类型测试REST

1. 概述

    本文将重点测试具有多种媒体类型/表现形式的REST服务。

    我们将编写集成测试，能够在API支持的多种类型的代表之间切换。我们的目标是能够运行完全相同的测试，消耗完全相同的服务URI，只是要求不同的媒体类型。

2. 目标

    任何REST API都需要使用一种或多种媒体类型将其资源作为表示方法公开。客户端将设置接受头来选择它从服务中要求的表示类型。

    由于资源可以有多种表现形式，服务器将必须实现一种机制，负责选择正确的表现形式。这也被称为内容协商（Content Negotiation）。

    因此，如果客户端要求application/xml，那么它应该得到资源的XML表示。而如果它要求application/json，那么它应该得到JSON。

3. 测试基础设施

    我们将首先为marshaller定义一个简单的接口。这将是主要的抽象，允许测试在不同的媒体类型之间切换。

    TestMarshallerFactory.java

    让我们来看看这个。

    - 首先，这里使用了Spring 3.1中引入的新的环境抽象--关于这一点的更多信息，请查看[关于使用Spring属性的详细文章](https://www.baeldung.com/properties-with-spring)
    - 我们从环境中获取test.mime属性，并使用它来决定创建哪个marshaller--这里有一些Java 7对String语法的转换。
    - 接下来，默认的marshaller将是Jackson的marshaller，因为该属性根本没有被定义，所以它支持JSON。
    - 最后，这个BeanFactory只在测试场景中起作用，因为我们正在使用@Profile支持，也是在Spring 3.1中引入的。

    就是这样--该机制能够根据test.mime属性的值在不同的marshaller之间切换。

4. JSON 和 XML Marshallers

    接下来，我们将需要实际的marshaller实现--每种支持的媒体类型都有一个。

    对于JSON，我们将使用Jackson作为基础库。

    JacksonMarshaller.java

    对于XML支持，marshaller使用XStream。

    XStreamMarshaller.java

    请注意，这些marshaller不是Spring Bean本身。原因是它们将被TestMarshallerFactory引导到Spring上下文中；没有必要直接让它们成为组件。

5. 用JSON和XML来消费服务

    在这一点上，我们应该能够针对部署的服务运行一个完整的集成测试。使用marshaller很简单：我们将在测试中注入一个IMarshaller。

    ```java
    @ActiveProfiles({ "test" })
    public abstract class SomeRestLiveTest {

        @Autowired
        private IMarshaller marshaller;

        // tests
        ...

    }
    ```

    Spring将根据test.mime属性的值来决定要注入的确切的marshaller。

    如果我们没有为这个属性提供一个值，TestMarshallerFactory将简单地返回到默认的marshaller--JSON marshaller。

6. Maven和Jenkins

    如果Maven被设置为针对已经部署的REST服务运行集成测试，那么我们可以用以下方式运行它。

    `mvn test -Dtest.mime=xml`

    或者，如果该构建使用Maven生命周期中的集成测试阶段。

    `mvn integration-test -Dtest.mime=xml`

    关于如何设置Maven构建以运行集成测试的更多细节，请参见《[用Maven进行集成测试](https://www.baeldung.com/integration-testing-with-the-maven-cargo-plugin)》。

    使用Jenkins，我们必须用配置作业。

    `This build is parametrized`

    并添加字符串参数：test.mime=xml。

    一个常见的Jenkins配置是让作业针对部署的服务运行同一组集成测试--一个用XML表示，另一个用JSON表示。

## Relevant Articles

- [x] [Functional Controllers in Spring MVC](https://www.baeldung.com/spring-mvc-functional-controllers)
- [Testing REST with multiple MIME types](https://www.baeldung.com/testing-rest-api-with-multiple-media-types)
- [Testing Web APIs with Postman Collections](https://www.baeldung.com/postman-testing-collections)
- [Spring Boot Consuming and Producing JSON](https://www.baeldung.com/spring-boot-json)
- [Serve Static Resources with Spring](https://www.baeldung.com/spring-mvc-static-resources)
- [Add Header to Every Request in Postman](https://www.baeldung.com/postman-add-headers-pre-request)
- More articles: [[prev -->]](/spring-boot-modules/spring-boot-mvc)
