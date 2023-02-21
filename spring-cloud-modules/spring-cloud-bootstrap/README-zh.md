# Guide to Microservices: with Spring Boot and Spring Cloud Ebook

This module contains articles about bootstrapping Spring Cloud applications that are part of the Guide to Microservices: with Spring Boot and Spring Cloud Ebook.

## Spring Cloud - 引导

1. 概述

    Spring Cloud是一个用于构建强大的云应用程序的框架。该框架通过为转移到分布式环境时面临的许多常见问题提供解决方案，促进了应用程序的开发。

    以微服务架构运行的应用程序旨在简化开发、部署和维护。应用程序的分解性质允许开发者一次只关注一个问题。可以在不影响系统的其他部分的情况下引入改进。

    另一方面，当我们采取微服务的方法时，会出现不同的挑战。

    - 将配置外部化，使其灵活，不需要在变化时重建服务
    - 服务发现
    - 隐藏部署在不同主机上的服务的复杂性

    在这篇文章中，我们将构建五个微服务：配置服务器、发现服务器、网关服务器、图书服务，以及最后的评级服务。这五个微服务构成了一个坚实的基础应用，可以开始云计算的开发，并解决上述的挑战。

2. 配置服务器

    在开发云计算应用时，有一个问题是维护和分发配置给我们的服务。我们真的不想在横向扩展我们的服务之前花时间去配置每个环境，或者冒着安全漏洞的风险把我们的配置烘烤到我们的应用中。

    为了解决这个问题，我们将把所有的配置整合到一个Git仓库中，并把它连接到一个管理所有应用程序配置的应用程序上。我们将建立一个非常简单的实现。

    要想了解更多细节并看到一个更复杂的例子，请看我们的[Spring Cloud配置](https://www.baeldung.com/spring-cloud-configuration)文章。

    1. 设置

        导航到<https://start.spring.io>，选择Maven和SpringBoot 2.7.x。

        将工件设置为 "config"。在依赖项部分，搜索 "config server"，并添加该模块。然后按下生成按钮，我们就能下载一个压缩文件，里面有一个预配置的项目，可以随时使用。

        另外，我们也可以生成一个Spring Boot项目，并在POM文件中手动添加一些依赖项。

        这些依赖关系将在所有项目之间共享（通过引入共同 parent pom）。

        ```xml
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.7.8</version>
            <relativePath/>
        </parent>

        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>
            <!-- 需要为配置服务器添加一个依赖项。 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-config-server</artifactId>
            </dependency>
        </dependencies> 

        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>2021.0.5</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
        ```

        > 注意：spring-boot 与 spring-cloud 兼容性，具体参见 <https://spring.io/projects/spring-cloud>。

    2. Spring配置

        为了启用配置服务器，我们必须在主应用程序类中添加一些注解。

        ```java
        @SpringBootApplication
        @EnableConfigServer
        public class ConfigApplication {...}
        ```

        @EnableConfigServer将把我们的应用程序变成一个配置服务器。

    3. 属性

        让我们在 src/main/resources 中添加 application.properties。

        其中配置服务器最重要的设置是 git.uri 参数。目前这个参数被设置为一个相对的文件路径，在Windows下一般解析为c:\Users\{username}或在*nix下为/Users/{username}/。这个属性指向一个Git仓库，所有其他应用程序的属性文件都存储在那里。如果有必要，它可以被设置为一个绝对文件路径。

        *nix / macOS 查看user路径命令：`% echo $HOME`

        - [ ] 提示：在windows机器上用 "file:///" 作为值的前缀，在*nix上则用 "file://" 。

    4. Git 仓库

        导航到 spring.cloud.config.server.git.uri 所定义的文件夹，并添加文件夹 application-config。CD进入该文件夹并输入git init。这将初始化一个Git仓库，我们可以在那里存储文件并跟踪其变化。

    5. 运行

        让我们运行config server并确保它在工作。在命令行中输入`mvn spring-boot:run`。这将启动服务器。

        我们应该看到这样的输出，表明服务器正在运行。

        `Tomcat started on port(s): 8081 (http)`

    6. 引导配置

        在我们后续的服务器中，我们将希望它们的应用属性由这个配置服务器管理。要做到这一点，我们实际上需要做一点鸡生蛋蛋生鸡的事。在每个应用程序中配置属性，知道如何与这个服务器对话。

        这是一个引导过程，这些应用程序中的每一个都将有一个叫做bootstrap.properties的文件。它将包含像application.properties一样的属性，但有一个转折(twist)。

        父级Spring ApplicationContext会先加载 bootstrap.properties。这一点至关重要，这样Config Server才能开始管理application.properties中的属性。正是这个特殊的 ApplicationContext 也将解密任何加密的应用程序属性。

        Bootstrap.properties是为了让配置服务器准备好，而application.properties则是为了我们的应用程序的特定属性。不过，从技术上讲，可以将应用程序属性放在 bootstrap.properties 中。

        最后，既然配置服务器在管理我们的应用程序属性，人们可能会问为什么还要有application.properties？答案是，这些东西作为默认值还是很有用的，也许Config Server并没有。

3. 发现

    现在我们已经解决了配置问题，我们需要一种方法让我们所有的服务器能够找到对方。我们将通过设置Eureka发现服务器来解决这个问题。由于我们的应用程序可以在任何IP/端口组合上运行，我们需要一个中央地址注册表，可以作为应用程序的地址查询。

    当一个新的服务器被配置时，它将与发现服务器通信，并注册它的地址，以便其他人可以与它通信。这样，其他应用程序在发出请求时就可以消费这些信息。

    要了解更多细节并看到一个更复杂的发现实现，请看[Spring Cloud Eureka](https://www.baeldung.com/spring-cloud-netflix-eureka)文章。

    1. 设置

        我们将再次导航到[start.spring.io](https://start.spring.io/)。将工件设置为 "discovery"。搜索 "eureka server" 并添加该依赖。搜索 "config client" 并添加该依赖项。最后，生成该项目。

        或者，我们可以创建一个Spring Boot项目，从config server中复制POM的内容，然后换入这些依赖项。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        ```

    2. Spring配置

        让我们在主类中添加Java配置。

        ```java
        @SpringBootApplication
        @EnableEurekaServer
        public class DiscoveryApplication {...}
        ```

        @EnableEurekaServer将配置此服务器为使用Netflix Eureka的发现服务器。Spring Boot将自动检测classpath上的配置依赖性，并从配置服务器上查找该配置。

    3. 属性

        现在我们将添加两个属性文件。

        首先，我们在 src/main/resources 中添加 bootstrap.properties。

        ```properties
        spring.cloud.config.name=discovery
        spring.cloud.config.uri=http://localhost:8081
        ```

        这些属性将让发现服务器在启动时查询配置服务器。

        其次，我们将discovery.properties添加到我们的Git存储库中。

        `cd $HOME/application-config`

        vim discovery.properties

        ```properties
        spring.application.name=discovery
        server.port=8082
        eureka.instance.hostname=localhost
        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        eureka.client.register-with-eureka=false
        eureka.client.fetch-registry=false
        ```

        文件名必须符合spring.application.name属性。

        此外，我们告诉这个服务器它是在默认区域运行的，这与配置客户端的区域设置相匹配。我们还告诉服务器不要向另一个发现实例注册。

        在生产中，我们会有不止一个这样的实例，以便在发生故障时提供冗余，并且该设置会是真的。

        让我们把文件提交到 Git 仓库。否则，该文件将不会被检测到。

    4. 向配置服务器(Config Server)添加依赖关系

        在配置服务器的POM文件中添加这个依赖关系。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        ```

        作为参考，我们可以在Maven中心找到该包（eureka-client）。

        在配置服务器(Config)的src/main/resources中的application.properties文件中添加这些属性。

        ```properties
        eureka.client.region = default
        eureka.client.registryFetchIntervalSeconds = 5
        eureka.client.serviceUrl.defaultZonehttp://localhost:8082/eureka/
        ```

    5. 运行

        使用相同的命令启动发现服务器(discovery)，mvn spring-boot:run。

        ```log
        ***************************
        APPLICATION FAILED TO START
        ***************************
        Description:
        No spring.config.import property has been defined
        Action:
        Add a spring.config.import=configserver: property to your configuration.
                If configuration is not required add spring.config.import=optional:configserver: instead.
                To disable this check, set spring.cloud.config.enabled=false or 
                spring.cloud.config.import-check.enabled=false.
        ```

        产生问题的原因是bootstrap.properties比application.properties的优先级要高，由于bootstrap.properties是系统级的资源配置文件，是用在程序引导执行时更加早期配置信息读取；而application.properties是用户级的资源配置文件，是用来后续的一些配置所需要的公共参数。但是在SpringCloud 2021.* 版本把bootstrap禁用了，导致在读取文件的时候读取不到而报错，所以我们只要把bootstrap从新导入进来就会生效了。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>
        ```

        - [ ] 尝试通过 spring.config.import 配置解决？

        ```log
        ***************************
        APPLICATION FAILED TO START
        ***************************
        Description:
        Parameter 0 of method springSessionRepositoryFilter in org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration required a bean of type 'org.springframework.session.SessionRepository' that could not be found.
        Action:
        Consider defining a bean of type 'org.springframework.session.SessionRepository' in your configuration.
        ```

        命令行的输出应该包括。

        ```log
        Fetching config from server at: http://localhost:8081
        ...
        Tomcat started on port(s): 8082 (http)
        ```

        停止并重新运行配置服务。如果一切正常，输出结果应该是这样的。

        ```log
        DiscoveryClient_CONFIG/10.1.10.235:config:8081: registering service...
        Tomcat started on port(s): 8081 (http)
        DiscoveryClient_CONFIG/10.1.10.235:config:8081 - registration status: 204
        ```

4. 网关

    现在我们已经解决了配置和发现的问题，我们仍然有一个问题，就是客户端访问我们所有的应用程序。

    如果我们把一切都留在分布式系统中，那么我们将不得不管理复杂的CORS头，以允许客户端的跨源请求。我们可以通过创建一个网关服务器来解决这个问题。这将充当一个反向代理，将请求从客户端传送到我们的后端服务器。

    网关服务器在微服务架构中是一个很好的应用，因为它允许所有的响应都来自一个单一的主机。这将消除对CORS的需求，并给我们一个方便的地方来处理像认证这样的常见问题。

    1. 设置

        现在我们知道该怎么做了。导航到 <https://start.spring.io/> 。将工件设置为 "gateway"。搜索 "zuul "并添加该依赖项。搜索 "config client" 并添加该依赖项。搜索 "eureka discovery" 并添加该依赖关系。最后，生成该项目。

        或者，我们可以用这些依赖项创建一个Spring Boot应用程序。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zuul</artifactId>
        </dependency>
        ```

        作为参考，我们可以在Maven中心找到该捆绑包（config-client、eureka-client、zuul）。

    2. Spring配置

        让我们把配置添加到主类中。

        ```properties
        @SpringBootApplication
        @EnableZuulProxy
        @EnableEurekaClient
        @EnableFeignClients
        public class GatewayApplication {...}
        ```

    3. 属性

        现在我们将添加两个属性文件。

        bootstrap.properties在src/main/resources中。

        ```properites
        spring.cloud.config.name=gateway
        spring.cloud.config.discovery.service-id=config
        spring.cloud.config.discovery.enabled=true
        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        ```

        gateway.properties在我们的Git存储库中。

        ```properties
        spring.application.name=gateway
        server.port=8080

        eureka.client.region = default
        eureka.client.registryFetchIntervalSeconds = 5

        zuul.routes.book-service.path=/book-service/**
        zuul.routes.book-service.sensitive-headers=Set-Cookie,Authorization
        hystrix.command.book-service.execution.isolation.thread.timeoutInMilliseconds=600000

        zuul.routes.rating-service.path=/rating-service/**
        zuul.routes.rating-service.sensitive-headers=Set-Cookie,Authorization
        hystrix.command.rating-service.execution.isolation.thread.timeoutInMilliseconds=600000

        zuul.routes.discovery.path=/discovery/**
        zuul.routes.discovery.sensitive-headers=Set-Cookie,Authorization
        zuul.routes.discovery.url=http://localhost:8082
        hystrix.command.discovery.execution.isolation.thread.timeoutInMilliseconds=600000
        ```

        zuul.routes属性允许我们定义一个应用程序，根据ant URL匹配器来路由某些请求。我们的属性告诉Zuul将任何来自/book-service/**的请求路由到一个spring.application.name为book-service的应用程序。然后Zuul将使用应用程序名称从发现服务器上查找主机，并将请求转发到该服务器。

        记住要在版本库中提交修改!

    4. 运行

        运行配置和发现程序，等待配置程序在发现服务器上注册。如果它们已经在运行，我们就不必重新启动它们。一旦完成，运行网关服务器。网关服务器应该在8080端口启动，并在发现服务器上注册自己。控制台的输出应该包含。

        ```log
        Fetching config from server at: http://10.1.10.235:8081/
        ...
        DiscoveryClient_GATEWAY/10.1.10.235:gateway:8080: registering service...
        DiscoveryClient_GATEWAY/10.1.10.235:gateway:8080 - registration status: 204
        Tomcat started on port(s): 8080 (http)
        ```

        一个容易犯的错误是在配置服务器在Eureka上注册之前启动服务器。在这种情况下，我们会看到有这样的输出日志。

        Fetching config from server at: <http://localhost:8888>

        这是配置服务器的默认URL和端口，表明我们的发现服务在发出配置请求时没有一个地址。只需等待几秒钟，再试一次，一旦配置服务器在Eureka注册，问题就会解决。

5. 图书服务

    在微服务架构中，我们可以自由地做出尽可能多的应用来满足商业目标。通常情况下，工程师会按领域划分他们的服务。我们将遵循这种模式，创建一个图书服务来处理我们应用程序中所有的图书操作。

    1. 设置

        再来一次。导航到 <https://start.spring.io> 。将工件设置为 "book-service"。搜索 "web "并添加该依赖项。搜索 "config client" 并添加该依赖关系。搜索 "eureka discovery" 并添加该依赖关系。生成该项目。

        或者，将这些依赖项添加到项目中。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        ```

        作为参考，我们可以在Maven中心找到该捆绑包（config-client、eureka-client、web）。

    2. Spring配置

        让我们修改一下我们的主类。

        ```java
        @SpringBootApplication
        @EnableEurekaClient
        @RestController
        @RequestMapping("/books")
        public class BookServiceApplication {
            public static void main(String[] args) {
                SpringApplication.run(BookServiceApplication.class, args);
            }

            private List<Book> bookList = Arrays.asList(
                new Book(1L, "Baeldung goes to the market", "Tim Schimandle"),
                new Book(2L, "Baeldung goes to the park", "Slavisa")
            );

            @GetMapping("")
            public List<Book> findAllBooks() {
                return bookList;
            }

            @GetMapping("/{bookId}")
            public Book findBook(@PathVariable Long bookId) {
                return bookList.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
            }
        }
        ```

        我们还添加了一个REST控制器和一个由我们的属性文件设置的字段，以返回一个我们将在配置过程中设置的值。

        现在让我们来添加书的POJO。

        see svcbook.book/Book.java

    3. 属性

        现在我们只需要添加我们的两个属性文件。

        bootstrap.properties 在 src/main/resources 中。

        ```properties
        spring.cloud.config.name=book-service
        spring.cloud.config.discovery.service-id=config
        spring.cloud.config.discovery.enabled=true

        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        ```

        book-service.properties在我们的Git存储库中。

        ```properties
        spring.application.name=book-service
        server.port=8083
        eureka.client.region = default
        eureka.client.registryFetchIntervalSeconds = 5
        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        ```

        让我们把修改提交到版本库。

    4. 运行

        一旦所有其他应用程序都启动了，我们就可以启动图书服务。控制台的输出应该是这样的。

        ```log
        DiscoveryClient_BOOK-SERVICE/10.1.10.235:book-service:8083: registering service...
        DiscoveryClient_BOOK-SERVICE/10.1.10.235:book-service:8083 - registration status: 204
        Tomcat started on port(s): 8083 (http)
        ```

        一旦启动，我们就可以用浏览器来访问我们刚刚创建的端点。导航到<http://localhost:8080/book-service/books>，我们会得到一个JSON对象，里面有我们在控制器中添加的两本书。注意，我们不是直接访问8083端口的图书服务，而是通过网关服务器。

6. 评级服务

    像我们的图书服务一样，我们的评级服务将是一个领域驱动的服务，将处理与评级有关的操作。

    1. 设置

        再来一次。导航到 <https://start.spring.io> 。将工件设置为 "评级服务"。搜索 "web" 并添加该依赖项。搜索 "config client" 并添加该依赖关系。搜索 "eureka discovery" 并添加该依赖关系。然后，生成该项目。

        或者，将这些依赖项添加到项目中。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        ```

        作为参考，我们可以在Maven中心找到该捆绑包（config-client、eureka-client、web）。

    2. 春季配置

        让我们修改一下我们的主类。

        ```java
        @SpringBootApplication
        @EnableEurekaClient
        @RestController
        @RequestMapping("/ratings")
        public class RatingServiceApplication {
            public static void main(String[] args) {
                SpringApplication.run(RatingServiceApplication.class, args);
            }

            private List<Rating> ratingList = Arrays.asList(
                new Rating(1L, 1L, 2),
                new Rating(2L, 1L, 3),
                new Rating(3L, 2L, 4),
                new Rating(4L, 2L, 5)
            );

            @GetMapping("")
            public List<Rating> findRatingsByBookId(@RequestParam Long bookId) {
                return bookId == null || bookId.equals(0L) ? Collections.EMPTY_LIST : ratingList.stream().filter(r -> r.getBookId().equals(bookId)).collect(Collectors.toList());
            }

            @GetMapping("/all")
            public List<Rating> findAllRatings() {
                return ratingList;
            }
        }
        ```

        我们还添加了一个REST控制器和一个由我们的属性文件设置的字段，以返回我们将在配置时设置的值。

        让我们来添加评级POJO。

        see svcrating.rating/Rating.java

    3. 属性

        现在我们只需要添加我们的两个属性文件。

        bootstrap.properties 在 src/main/resources 中。

        ```properties
        spring.cloud.config.name=rating-service
        spring.cloud.config.discovery.service-id=config
        spring.cloud.config.discovery.enabled=true
        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        ```

        rating-service.properties在我们的Git存储库中。

        ```properties
        spring.application.name=rating-service
        server.port=8084
        eureka.client.region = default
        eureka.client.registryFetchIntervalSeconds = 5
        eureka.client.serviceUrl.defaultZone=http://localhost:8082/eureka/
        ```

        让我们把修改提交到版本库。

    4. 运行

        一旦所有其他应用程序都启动了，我们就可以启动评级服务。控制台的输出应该是这样的。

        ```log
        DiscoveryClient_RATING-SERVICE/10.1.10.235:rating-service:8083: registering service...
        DiscoveryClient_RATING-SERVICE/10.1.10.235:rating-service:8083 - registration status: 204
        Tomcat started on port(s): 8084 (http)
        ```

        一旦启动，我们就可以用浏览器来访问我们刚刚创建的端点。导航到<http://localhost:8080/rating-service/ratings/all>，我们会得到包含我们所有评级的JSON。注意，我们不是直接访问8084端口的评级服务，而是通过网关服务器访问。

7. 结论

    现在我们能够将Spring Cloud的各个部分连接成一个有效的微服务应用程序。这形成了一个基础，我们可以用来开始建立更复杂的应用程序。

    像往常一样，我们可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-bootstrap)上找到这个源代码。

## Relevant Articles

- [x] [Spring Cloud – Bootstrapping](http://www.baeldung.com/spring-cloud-bootstrapping)
- [Spring Cloud – Securing Services](http://www.baeldung.com/spring-cloud-securing-services)
- [Spring Cloud – Tracing Services with Zipkin](http://www.baeldung.com/tracing-services-with-zipkin)
- [Spring Cloud Series – The Gateway Pattern](http://www.baeldung.com/spring-cloud-gateway-pattern)
- [Spring Cloud – Adding Angular 4](http://www.baeldung.com/spring-cloud-angular)
- [How to Share DTO Across Microservices](https://www.baeldung.com/java-microservices-share-dto)

## Running the Project

- First, you need a redis server running on the default port
- To run the project:
  - copy the appliction-config folder to c:\Users\{username}\ on Windows or /home/{username}/ on *nix. Then open a git bash terminal in application-config and run:
    - git init
    - git add .
    - git commit -m "First commit"
  - start the config server
  - start the discovery server
  - start all the other servers in any order (gateway, svc-book, svc-rating, zipkin)
