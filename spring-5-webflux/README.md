# Spring 5 WebFlux

This module contains articles about Spring 5 WebFlux

## Spring Boot Reactor Netty配置

1. 概述

    在本教程中，我们将研究Spring Boot应用程序中Reactor Netty服务器的不同配置选项。最后，我们会有一个应用来展示不同的配置方法。

2. 什么是Reactor Netty？

    在开始之前，我们先看看什么是Reactor Netty以及它与Spring Boot的关系。

    Reactor Netty是一个[异步事件驱动的网络应用框架](https://projectreactor.io/docs/netty/snapshot/reference/index.html#getting-started)  (asynchronous event-driven network application framework)。它提供非阻塞和背压就绪的TCP、HTTP和UDP客户端和服务器。顾名思义，它是基于[Netty框架](https://www.baeldung.com/netty)的。

    [Spring WebFlux](https://www.baeldung.com/spring-webflux)是Spring框架的一部分，为Web应用提供反应式编程支持。如果我们在Spring Boot应用中使用WebFlux，Spring Boot会自动将Reactor Netty配置为默认服务器。除此之外，我们可以明确地将Reactor Netty添加到我们的项目中，Spring Boot应该再次自动配置它。

    现在，我们将构建一个应用程序，以学习如何定制我们自动配置的Reactor Netty服务器。之后，我们将介绍一些常见的配置情况。

3. 依赖关系

    首先，我们将添加所需的Maven依赖项。

    为了使用Reactor Netty服务器，我们将在pom文件中添加spring-boot-starter-webflux作为依赖项。

    这也将把spring-boot-starter-reactor-netty作为一个横向依赖拉入我们的项目。

4. 服务器配置

    1. 使用属性文件

        作为第一个选择，我们可以通过属性文件来配置Netty服务器。Spring Boot在应用程序属性文件中公开了一些常见的服务器配置。

        让我们在application.properties中定义服务器端口。

        `server.port=8088`

        或者我们可以在application.yml中做同样的事情。

        除了服务器端口，Spring Boot还有许多其他可用的[服务器配置选项](https://www.baeldung.com/spring-boot-application-configuration)。以server前缀开头的属性让我们覆盖了默认的服务器配置。我们可以在[Spring文档](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)中的EMBEDDED SERVER CONFIGURATION部分轻松查找到这些属性。

    2. 使用程序化配置

        现在，让我们来看看如何通过代码来配置我们的嵌入式Netty服务器。为此，Spring Boot给了我们WebServerFactoryCustomizer和NettyServerCustomizer类。

        让我们使用这些类来[配置Netty端口](https://www.baeldung.com/spring-boot-change-port)，就像我们之前用属性文件做的那样。

        ```java
        @Component
        public class NettyWebServerFactoryPortCustomizer 
        implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

            @Override
            public void customize(NettyReactiveWebServerFactory serverFactory) {
                serverFactory.setPort(8088);
            }
        }
        ```

        Spring Boot将在启动时拾取我们的工厂定制器组件，并配置服务器端口。

        另外，我们也可以实现NettyServerCustomizer。

        ```java
        private static class PortCustomizer implements NettyServerCustomizer {
            private final int port;

            private PortCustomizer(int port) {
                this.port = port;
            }
            @Override
            public HttpServer apply(HttpServer httpServer) {
                return httpServer.port(port);
            }
        }
        ```

        并将其添加到服务器工厂中。

        `serverFactory.addServerCustomizers(new PortCustomizer(8088));`

        这两种方法在配置我们的嵌入式Reactor Netty服务器时给了我们很大的灵活性。

        此外，我们还可以对EventLoopGroup进行自定义。

        ```java
        private static class EventLoopNettyCustomizer implements NettyServerCustomizer {

            @Override
            public HttpServer apply(HttpServer httpServer) {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                eventLoopGroup.register(new NioServerSocketChannel());
                return httpServer.runOn(eventLoopGroup);
            }
        }
        ```

        然而，这种情况有一个注意事项。由于Spring Boot会自动配置Netty服务器，我们可能需要通过明确定义NettyReactiveWebServerFactory Bean来跳过自动配置。

        为此，我们应该在一个配置类中定义我们的Bean，并在那里添加我们的自定义器。

        ```java
        @Bean
        public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
            NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
            webServerFactory.addServerCustomizers(new EventLoopNettyCustomizer());
            return webServerFactory;
        }
        ```

        接下来，我们将继续讨论一些常见的Netty配置场景。

5. 配置SSL

    让我们看看如何配置SSL。

    我们将使用SslServerCustomizer类，它是NettyServerCustomizer的另一个实现。

    ```java
    @Component
    public class NettyWebServerFactorySslCustomizer 
    implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

        @Override
        public void customize(NettyReactiveWebServerFactory serverFactory) {
            Ssl ssl = new Ssl();
            ssl.setEnabled(true);
            ssl.setKeyStore("classpath:sample.jks");
            ssl.setKeyAlias("alias");
            ssl.setKeyPassword("password");
            ssl.setKeyStorePassword("secret");
            Http2 http2 = new Http2();
            http2.setEnabled(false);
            serverFactory.addServerCustomizers(new SslServerCustomizer(ssl, http2, null));
            serverFactory.setPort(8443);
        }
    }
    ```

    在这里，我们已经定义了我们的keystore相关属性，禁用了HTTP/2，并将端口设置为8443。

6. 访问日志配置

    现在，我们来看看如何[使用Logback](https://www.baeldung.com/logback)配置访问日志。

    Spring Boot允许我们在Tomcat、Jetty和Undertow的应用程序属性文件中配置访问日志。但是，Netty还没有这种支持。

    为了启用Netty访问日志，我们应该在运行应用程序时设置-Dreactor.netty.http.server.accessLogEnabled=true。

    `mvn spring-boot:run -Dreactor.netty.http.server.accessLogEnabled=true`

7. 总结

    在这篇文章中，我们已经介绍了如何在Spring Boot应用程序中配置Reactor Netty服务器。

    首先，我们使用了Spring Boot基于属性的一般配置功能。然后，我们探讨了如何以细粒度的方式编程配置Netty。

## 如何用Spring WebFlux返回404

1. 概述

    在Spring Boot 2和新的非阻塞服务器Netty中，我们不再有Servlet上下文API，所以让我们讨论如何使用新的堆栈来表达不同的HTTP状态代码。

2. 语义响应状态

    按照标准的RESTful做法，我们自然需要使用全部的HTTP状态码来正确表达API的语义。

    1. 默认返回状态

        当然，当一切顺利的时候，默认的响应状态是200（OK）。

        ```java
        @GetMapping(
        value = "/ok",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
        )
        public Flux<String> ok() {
            return Flux.just("ok");
        }
        ```

    2. 使用注解

        我们可以改变默认的返回状态，在方法中加入@ResponseStatus注解。

        ```java
        @GetMapping(
        value = "/no-content",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
        )
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public Flux<String> noContent() {
            return Flux.empty();
        }
        ```

    3. 以编程方式改变状态

        在某些情况下，根据我们服务器的行为，我们可以决定以编程方式改变返回的状态，而不是默认使用的或带有注解的前缀的返回状态。

        ```java
        @GetMapping(
        value = "/accepted",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
        )
        public Flux<String> accepted(ServerHttpResponse response) {
            response.setStatusCode(HttpStatus.ACCEPTED);
            return Flux.just("accepted");
        }
        ```

        现在我们可以在实现中直接选择返回哪个HTTP状态代码。

    4. 抛出一个异常

        当我们抛出一个异常时，默认的HTTP返回状态会被省略，Spring会尝试找到一个异常处理程序来处理它。

        ```java
        @GetMapping(
        value = "/bad-request"
        )
        public Mono<String> badRequest() {
            return Mono.error(new IllegalArgumentException());
        }
        @ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "Illegal arguments")
        @ExceptionHandler(IllegalArgumentException.class)
        public void illegalArgumentHandler() {
            // 
        }
        ```

        要了解更多关于如何做到这一点，一定要查看Baeldung上的[错误处理文章](https://www.baeldung.com/exception-handling-for-rest-with-spring)。

    5. 使用ResponseEntity

        现在让我们来看看一个有趣的选择--ResponseEntity类。

        ```java
        @GetMapping(
        value = "/unauthorized"
        )
        public ResponseEntity<Mono<String>> unathorized() {
            return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .header("X-Reason", "user-invalid")
            .body(Mono.just("unauthorized"));
        }
        ```

    6. 使用函数式端点

        在Spring 5中，我们可以用功能化的方式定义端点，因此，我们也可以通过编程方式改变默认的HTTP状态。

        ```java
        @Bean
        public RouterFunction<ServerResponse> notFound() {
            return RouterFunctions
            .route(GET("/statuses/not-found"),
                request -> ServerResponse.notFound().build());
        }
        ```

3. 总结

    在实现HTTP API时，框架提供了许多选项来智能地处理我们暴露给客户端的状态代码。

    这篇文章应该是一个很好的起点，来探索这些，并了解你如何能够推出具有简洁、RESTful语义的表达式、友好的API。

## 使用Spring Boot的RSocket

1. 概述

    RSocket是一种提供反应流语义的应用协议--例如，它的功能是替代HTTP。

    在本教程中，我们将研究使用Spring Boot的[RSocket](https://www.baeldung.com/rsocket)，特别是它如何帮助抽象出低级别的RSocket API。

2. 依赖关系

    让我们从添加[spring-boot-starter-rsocket](https://mvnrepository.com/search?q=spring-boot-starter-rsocket)依赖项开始。

    这将过渡性地拉入RSocket相关的依赖项，如rsocket-core和rsocket-transport-netty。

3. 示例应用程序

    现在，我们将继续进行我们的示例应用程序。为了突出RSocket提供的交互模型，我们将创建一个交易员应用程序。我们的交易员应用程序将由一个客户端和一个服务器组成。

    1. 服务器设置

        首先，我们来设置服务器，它将是一个引导RSocket服务器的Spring Boot应用程序。

        由于我们有spring-boot-starter-rsocket依赖项，Spring Boot为我们自动配置了一个RSocket服务器。与Spring Boot一样，我们可以以属性驱动的方式改变RSocket服务器的默认配置值。

        例如，让我们通过在application.properties文件中添加以下一行来改变我们的RSocket服务器的端口。

        `spring.rsocket.server.port=7000`

        我们还可以改变其他属性，根据我们的需要进一步修改我们的服务器。

    2. 客户端设置

        接下来，我们来设置客户端，它也将是一个Spring Boot应用程序。

        尽管Spring Boot自动配置了大部分与RSocket有关的组件，但我们还应该定义一些Bean来完成设置。

        ```java
        @Configuration
        public class ClientConfiguration {

            @Bean
            public RSocketRequester getRSocketRequester(){
                RSocketRequester.Builder builder = RSocketRequester.builder();

                return builder
                .rsocketConnector(
                    rSocketConnector ->
                    rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                )
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 7000);
            }
        }
        ```

        在这里，我们正在创建RSocket客户端，并将其配置为使用7000端口的TCP传输。请注意，这是我们之前配置的服务器端口。

        在定义了这个Bean配置后，我们有了一个基本的结构。

        接下来，我们将探索不同的交互模型，看看Spring Boot如何帮助我们。

4. 使用RSocket和Spring Boot的请求/响应

    让我们从Request/Response开始。这可能是最常见和最熟悉的交互模型，因为HTTP也采用了这种通信方式。

    在这种交互模型中，客户端发起通信并发送请求。之后，服务器执行操作并向客户端返回一个响应--这样，通信就完成了。

    在我们的交易员应用程序中，客户将要求获得某只股票的当前市场数据。作为回报，服务器将传递请求的数据。

    1. 服务器

        在服务器端，我们首先应该创建一个控制器来保存我们的处理方法。但不是像Spring MVC中的@RequestMapping或@GetMapping注释，我们将使用@MessageMapping注释。

        ```java
        @Controller
        public class MarketDataRSocketController {

            private final MarketDataRepository marketDataRepository;

            public MarketDataRSocketController(MarketDataRepository marketDataRepository) {
                this.marketDataRepository = marketDataRepository;
            }

            @MessageMapping("currentMarketData")
            public Mono<MarketData> currentMarketData(MarketDataRequest marketDataRequest) {
                return marketDataRepository.getOne(marketDataRequest.getStock());
            }
        }
        ```

        所以让我们调查一下我们的控制器。

        我们使用@Controller注解来定义一个处理程序，它应该处理传入的RSocket请求。此外，@MessageMapping注解让我们定义我们感兴趣的路由，以及如何对请求做出反应。

        在这种情况下，服务器监听currentMarketData路由，它以`Mono<MarketData>`的形式向客户端返回一个结果。

    2. 客户端

        接下来，我们的RSocket客户端应该询问一只股票的当前价格，并获得一个单一的响应。

        为了发起请求，我们应该使用RSocketRequester类。

        ```java
        @RestController
        public class MarketDataRestController {

            private final RSocketRequester rSocketRequester;

            public MarketDataRestController(RSocketRequester rSocketRequester) {
                this.rSocketRequester = rSocketRequester;
            }

            @GetMapping(value = "/current/{stock}")
            public Publisher<MarketData> current(@PathVariable("stock") String stock) {
                return rSocketRequester
                .route("currentMarketData")
                .data(new MarketDataRequest(stock))
                .retrieveMono(MarketData.class);
            }
        }
        ```

        请注意，在我们的案例中，RSocket客户端也是一个REST控制器，我们从那里调用我们的RSocket服务器。因此，我们使用@RestController和@GetMapping来定义我们的请求/响应端点。

        在端点方法中，我们使用RSocketRequester并指定路由。事实上，这就是RSocket服务器所期望的路由。然后，我们要传递请求数据。最后，当我们调用retrieveMono()方法时，Spring Boot启动了一个请求/响应互动。

5. 使用RSocket和Spring Boot的火与忘却

    接下来，我们来看看 "fire-and-forget"交互模型。顾名思义，客户端向服务器发送请求，但并不期望得到回应。

    在我们的交易员应用程序中，一些客户端将作为数据源，并将市场数据推送给服务器。

    1. 服务器

        让我们在我们的服务器应用程序中创建另一个端点。

        ```java
        @MessageMapping("collectMarketData")
        public Mono<Void> collectMarketData(MarketData marketData) {
            marketDataRepository.add(marketData);
            return Mono.empty();
        }
        ```

        同样，我们用collectMarketData的路由值定义了一个新的@MessageMapping。此外，Spring Boot自动将传入的有效载荷转换为MarketData实例。

        不过，这里最大的区别是，我们返回一个`Mono<Void>`，因为客户端不需要我们的响应。

    2. 客户端

        让我们看看如何发起我们的fire-and-forget请求。

        我们将创建另一个REST端点。

        ```java
        @GetMapping(value = "/collect")
        public Publisher<Void> collect() {
            return rSocketRequester
            .route("collectMarketData")
            .data(getMarketData())
            .send();
        }
        ```

        这里我们指定了我们的路由，我们的有效载荷将是一个MarketData实例。由于我们使用send()方法而不是retrieveMono()来发起请求，交互模型变成了fire-and-forget。

6. 使用RSocket和Spring Boot的请求流

    请求流是一种涉及面更广的交互模型，即客户端发送一个请求，但在一段时间内从服务器获得多个响应。

    为了模拟这种交互模型，客户将要求获得某只股票的所有市场数据。

    1. 服务器

        让我们从我们的服务器开始。我们将添加另一个消息映射方法。

        ```java
        @MessageMapping("feedMarketData")
        public Flux<MarketData> feedMarketData(MarketDataRequest marketDataRequest) {
            return marketDataRepository.getAll(marketDataRequest.getStock());
        }
        ```

        正如我们所见，这个处理方法与其他方法非常相似。不同的是，我们返回一个`Flux<MarketData>`，而不是`Mono<MarketData>`。最后，我们的RSocket服务器将向客户端发送多个响应。

    2. 客户端

        在客户端，我们应该创建一个端点来启动我们的请求/流通信。

        ```java
        @GetMapping(value = "/feed/{stock}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Publisher<MarketData> feed(@PathVariable("stock") String stock) {
            return rSocketRequester
            .route("feedMarketData")
            .data(new MarketDataRequest(stock))
            .retrieveFlux(MarketData.class);
        }
        ```

        让我们调查一下我们的RSocket请求。

        首先，我们要定义路由和请求的有效载荷。然后，我们用retrieveFlux()方法调用来定义我们的响应期望。这是决定交互模型的部分。

        还要注意的是，由于我们的客户端也是一个REST服务器，它将响应媒体类型定义为MediaType.TEXT_EVENT_STREAM_VALUE。

7. 异常处理

    现在让我们看看如何在我们的服务器应用程序中以声明的方式处理异常。

    当做请求/响应时，我们可以简单地使用@MessageExceptionHandler注解。

    ```java
    @MessageExceptionHandler
    public Mono<MarketData> handleException(Exception e) {
        return Mono.just(MarketData.fromException(e));
    }
    ```

    这里我们用@MessageExceptionHandler注释了我们的异常处理方法。结果，它将处理所有类型的异常，因为Exception类是所有其他类的超类。

    我们可以更具体一些，为不同的异常类型创建不同的异常处理方法。

    这当然是为了请求/响应模型，所以我们要返回一个`Mono<MarketData>`。我们希望我们这里的返回类型与我们的交互模型的返回类型一致。

## Spring MVC Async vs Spring WebFlux

在本教程中，我们将探讨Spring MVC中的@Async注解，然后我们将熟悉Spring WebFlux。我们的目标是更好地理解这两者之间的区别。

1. 实施场景

    在这里，我们想选择一个场景来展示我们如何用这些API中的每一个来实现一个简单的Web应用。此外，我们特别想看到更多关于线程管理和每个案例中的阻塞或非阻塞I/O。

    让我们选择一个有一个返回字符串结果的端点的Web应用。这里的重点是，请求将通过一个有200ms小延迟的Filter，然后Controller需要500ms来计算和返回结果。

    接下来，我们要在两个端点上用[Apache ab](https://httpd.apache.org/docs/2.4/programs/ab.html)模拟一个负载，并用JConsole监控我们的应用行为。

    可能值得一提的是，在这篇文章中，我们的目标不是这两个API之间的基准，只是一个小的负载测试，所以我们可以跟踪线程管理。

2. Spring MVC异步

    Spring 3.0引入了@Async注解。@Async的目标是允许应用程序在一个单独的线程上运行重负荷的工作。另外，如果有兴趣的话，调用者可以等待结果。因此，返回类型不能是空的，而且可以是[Future](https://www.baeldung.com/java-future)、[CompletableFuture](https://www.baeldung.com/java-completablefuture)或ListenableFuture中的任何一种。

    此外，Spring 3.2引入了org.springframework.web.context.request.async包，与Servlet 3.0一起，为Web层带来了异步过程的乐趣。因此，从Spring 3.2开始，@Async可以在注释为@Controller或@RestController的类中使用。

    当客户端发起请求时，它会经过过滤器链中所有匹配的过滤器，直到它到达DispatcherServlet实例。

    然后，Servlet负责请求的异步调度。它通过调用AsyncWebRequest#startAsync将请求标记为开始，将请求处理转移到WebSyncManager的一个实例，并在不提交响应的情况下完成其工作。过滤器链也以相反的方向遍历到根。

    WebAsyncManager在其关联的ExecutorService中提交请求处理工作。每当结果准备好了，它就通知DispatcherServlet将响应返回给客户端。

3. Spring异步实现

    让我们通过编写我们的应用类AsyncVsWebFluxApp来开始实现。在这里，@EnableAsync为我们的Spring Boot应用程序实现了启用异步的魔法。

    ```java
    @SpringBootApplication
    @EnableAsync
    public class AsyncVsWebFluxApp {
        public static void main(String[] args) {
            SpringApplication.run(AsyncVsWebFluxApp.class, args);
        }
    }
    ```

    然后我们有AsyncFilter，它实现了javax.servlet.Filter。不要忘记在doFilter方法中模拟延迟。

    ```java
    @Component
    public class AsyncFilter implements Filter {
        ...
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
            // sleep for 200ms 
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    ```

    最后，我们用"/async_result"端点开发我们的AsyncController。

    ```java
    @RestController
    public class AsyncController {
        @GetMapping("/async_result")
        @Async
        public CompletableFuture getResultAsyc(HttpServletRequest request) {
            // sleep for 500 ms
            return CompletableFuture.completedFuture("Result is ready!");
        }
    }
    ```

    由于getResultAsync上面的@Async，这个方法是在应用程序默认的[ExecutorService](https://www.baeldung.com/java-executor-service-tutorial)上的一个独立线程中执行的。然而，可以为我们的方法[设置一个特定的ExecutorService](https://www.baeldung.com/spring-async)。

    测试时间! 让我们运行应用程序，安装Apache ab，或任何工具来模拟负载。然后我们可以通过 "async_result"端点发送一堆并发的请求。我们可以执行JConsole，并将其附加到我们的java应用中，以监控这个过程：

    `ab -n 1600 -c 40 localhost:8080/async_result`

4. Spring WebFlux

    Spring 5.0引入了WebFlux，以非阻塞的方式[支持反应式网络](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)。WebFlux是基于reactor API的，只是另一个很棒的[反应式流](https://www.reactive-streams.org/)(reactive stream)的实现。

    Spring WebFlux支持反应式背压和Servlet 3.1+的非阻塞I/O。因此，它可以在Netty、Undertow、Jetty、Tomcat或任何Servlet 3.1+兼容的服务器上运行。

    尽管所有的服务器都不使用相同的线程管理和并发控制模型，但只要它们支持非阻塞I/O和反应式反压，Spring WebFlux就能正常工作。

    Spring WebFlux允许我们用Mono、Flux和它们丰富的操作符集以声明的方式分解逻辑。此外，除了它的@Controller注解的端点外，我们还可以有功能端点，尽管我们现在也可以在Spring MVC中使用这些端点。

5. Spring WebFlux的实现

    对于WebFlux的实现，我们走的是与Sync相同的道路。所以首先，让我们创建AsyncVsWebFluxApp。

    ```java
    @SpringBootApplication
    public class AsyncVsWebFluxApp {
        public static void main(String[] args) {
            SpringApplication.run(AsyncVsWebFluxApp.class, args);
        }
    }
    ```

    然后让我们编写我们的WebFluxFilter，它实现了WebFilter。我们将产生一个有意的延迟，然后将请求传递给过滤器链。

    ```java
    @Component
    public class WebFluxFilter implements org.springframework.web.server.WebFilter {

        @Override
        public Mono filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
            return Mono
            .delay(Duration.ofMillis(200))
            .then(
                webFilterChain.filter(serverWebExchange)
            );
        }
    }
    ```

    最后，我们有了我们的WebFluxController。它暴露了一个名为"/flux_result "的端点，并返回一个`Mono<String>`作为响应。

    ```java
    @RestController
    public class WebFluxController {

        @GetMapping("/flux_result")
        public Mono getResult(ServerHttpRequest request) {
        return Mono.defer(() -> Mono.just("Result is ready!"))
            .delaySubscription(Duration.ofMillis(500));
        }
    }
    ```

    对于测试，我们采取的方法与我们的同步示例应用程序相同。下面是示例的结果。

    `ab -n 1600 -c 40 localhost:8080/flux_result`

    WebFlux jconsoleWebFlux ab

6. 有什么区别？

    Spring Async支持Servlet 3.0规范，但Spring WebFlux支持Servlet 3.1+。它带来了一些差异。

    - Spring Async的I/O模型在与客户端通信时是阻塞的。这可能会造成慢速客户端的性能问题。另一方面，Spring WebFlux提供了一个非阻塞的I/O模型。
    - 在Spring Async中，读取请求主体或请求部分是阻塞的，而在Spring WebFlux中是无阻塞的。
    - 在Spring Async中，过滤器和Servlets是同步工作的，但Spring WebFlux支持完全异步通信。
    - 与Spring Async相比，Spring WebFlux与更多的Web/应用服务器兼容，如Netty和Undertow。

    此外，Spring WebFlux支持反应式反压，因此与Spring MVC Async和Spring MVC相比，我们对应该如何对快速生产者做出反应有更多控制。

    由于Reactor API的支持，Spring Flux还切实地转向了功能性编码风格和声明性API分解。

    所有这些项目都导致我们使用Spring WebFlux吗？好吧，Spring Async甚至Spring MVC可能是很多项目的正确答案，这取决于所需的负载扩展性或系统的可用性。

    关于可扩展性，Spring Async比Spring MVC的同步实现给了我们更好的结果。Spring WebFlux，由于其反应性，为我们提供了弹性和更高的可用性。

## 在Spring 5 Webflux WebClient中设置超时时间

Spring 5增加了一个全新的框架--[Spring WebFlux](https://www.baeldung.com/spring-webflux)，它支持我们的Web应用中的反应式编程。为了执行HTTP请求，我们可以使用WebClient接口，它提供了一个基于Reactor项目的功能性API。

在本教程中，我们将重点讨论WebClient的超时设置。我们将讨论不同的方法，如何正确地设置不同的超时，包括在整个应用程序中的全局和特定的请求。

1. WebClient和HTTP客户端

    在我们继续之前，让我们做一个简单的回顾。Spring WebFlux包括自己的客户端，即[WebClient类](https://www.baeldung.com/spring-5-webclient)，以反应式方式执行HTTP请求。WebClient还需要一个HTTP客户端库才能正常工作。Spring提供了对其中一些的[内置支持](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client)，但默认使用的是[Reactor Netty](https://github.com/reactor/reactor-netty)。

    大部分的配置，包括超时，都可以用这些客户端来完成。

2. 通过HTTP客户端配置超时

    正如我们之前提到的，在我们的应用程序中设置不同的WebClient超时的最简单方法是使用底层HTTP客户端全局设置。这也是最有效的方法。

    由于Netty是Spring WebFlux的一个默认客户端库，我们将使用[Reactor Netty HttpClient类](https://projectreactor.io/docs/netty/release/reference/index.html#http-client)来介绍我们的例子。

    1. 响应超时

        响应超时是指我们在发送请求后等待接收响应的时间。我们可以使用responseTimeout()方法来为客户端配置它。

        `HttpClient client = HttpClient.create().responseTimeout(Duration.ofSeconds(1));`

        在这个例子中，我们将超时时间配置为1秒。Netty默认不会设置响应超时。

        之后，我们可以将HttpClient提供给Spring WebClient。

        ```java
        WebClient webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
        ```

        这样做之后，WebClient会继承底层HttpClient为所有发送的请求提供的所有配置。

    2. 连接超时

        连接超时是指客户端和服务器之间必须建立连接的期限。我们可以使用不同的通道选项键和option()方法来进行配置。

        `HttpClient client = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);`

        提供的值是以毫秒为单位的，所以我们把超时时间配置为10秒。Netty默认将该值设置为30秒。

        此外，我们可以配置keep-alive选项，它将在连接空闲时发送TCP检查探测。

        ```java
        HttpClient client = HttpClient.create()
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(EpollChannelOption.TCP_KEEPIDLE, 300)
        .option(EpollChannelOption.TCP_KEEPINTVL, 60)
        .option(EpollChannelOption.TCP_KEEPCNT, 8);

        // create WebClient...
        ```

        因此，我们已经启用了保持性检查，在空闲5分钟后进行探测，间隔时间为60秒。我们还设置了连接中断前的最大探测次数为8次。

        当连接在给定的时间内没有建立起来或被放弃时，就会抛出一个[ConnectTimeoutException](https://netty.io/4.1/api/io/netty/channel/ConnectTimeoutException.html)。

    3. 读取和写入超时

        当在一定时间内没有读到数据时，就会出现读超时，而当写操作在特定时间内不能完成时，就会出现写超时。HttpClient允许配置额外的处理程序来配置这些超时。

        ```java
        HttpClient client = HttpClient.create()
        .doOnConnected(conn -> conn
            .addHandler(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
            .addHandler(new WriteTimeoutHandler(10)));

        // create WebClient...
        ```

        在这种情况下，我们通过[doOnConnected()](https://projectreactor.io/docs/netty/release/api/reactor/netty/transport/ClientTransport.html#doOnConnect-java.util.function.Consumer-)方法配置了一个连接的回调，在这里我们创建了额外的处理程序。为了配置超时，我们添加了[ReadTimeOutHandler](https://netty.io/4.1/api/io/netty/handler/timeout/ReadTimeoutHandler.html)和[WriteTimeOutHandler](https://netty.io/4.1/api/io/netty/handler/timeout/WriteTimeoutHandler.html)实例。我们将它们都设置为10秒。

        这些处理程序的构造函数接受两种不同的参数。对于第一个参数，我们提供了一个带有TimeUnit规范的数字，而第二个参数则将给定的数字转换为秒。

        底层的Netty库相应地提供[ReadTimeoutException](https://netty.io/4.1/api/io/netty/handler/timeout/ReadTimeoutException.html)和[WriteTimeoutException](https://netty.io/4.1/api/io/netty/handler/timeout/WriteTimeoutException.html)类来处理错误。

    4. SSL/TLS超时

        握手超时是系统在停止操作前尝试建立SSL连接的时间长度。我们可以通过[secure()](https://projectreactor.io/docs/netty/release/api/reactor/netty/http/client/HttpClient.html#secure--)方法设置SSL配置。

        ```java
        HttpClient.create()
        .secure(spec -> spec.sslContext(SslContextBuilder.forClient())
            .defaultConfiguration(SslProvider.DefaultConfigurationType.TCP)
            .handshakeTimeout(Duration.ofSeconds(30))
            .closeNotifyFlushTimeout(Duration.ofSeconds(10))
            .closeNotifyReadTimeout(Duration.ofSeconds(10)));

        // create WebClient...
        ```

        如上所述，我们将握手超时设置为30秒（默认：10s），而close_notify flush（默认：3s）和read（默认：0s）超时为10秒。所有方法都由[SslProvider.Builder](https://projectreactor.io/docs/netty/release/api/reactor/netty/tcp/SslProvider.Builder.html)接口提供。

    5. 代理超时

        HttpClient还支持代理功能。如果与对等体的连接建立尝试没有在代理超时内完成，则连接尝试失败。我们在[proxy()](https://projectreactor.io/docs/netty/release/api/reactor/netty/transport/ClientTransport.html#proxy-java.util.function.Consumer-)的配置中设置这个超时。

        ```java
        HttpClient.create()
        .proxy(spec -> spec.type(ProxyProvider.Proxy.HTTP)
            .host("proxy")
            .port(8080)
            .connectTimeoutMillis(30000));

        // create WebClient...
        ```

        我们使用[connectTimeoutMillis()](https://projectreactor.io/docs/netty/release/api/reactor/netty/transport/ProxyProvider.Builder.html#connectTimeoutMillis-long-)将超时设置为30秒，而默认值为10。

        Netty库也实现了自己的[ProxyConnectException](https://netty.io/4.1/api/io/netty/handler/proxy/ProxyConnectException.html)，以防止任何失败。

3. 请求级别的超时

    我们使用HttpClient全局配置了不同的超时。然而，我们也可以独立于全局设置来设置响应请求的特定超时。

    1. 响应超时 - 使用HttpClientRequest

        如前所述，我们也可以在请求层面上配置响应超时。

        ```java
        webClient.get()
        .uri("https://baeldung.com/path")
        .httpRequest(httpRequest -> {
            HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
            reactorRequest.responseTimeout(Duration.ofSeconds(2));
        });
        ```

        在上面的案例中，我们使用WebClient的[httpRequest()](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.RequestHeadersSpec.html#httpRequest-java.util.function.Consumer-)方法，从底层Netty库中获得对本地[HttpClientRequest](https://projectreactor.io/docs/netty/release/api/reactor/netty/http/client/HttpClientRequest.html)的访问。接下来，我们用它来设置超时值为2秒。

        这种响应超时设置覆盖了HttpClient层面上的任何响应超时。我们也可以把这个值设置为null，以删除之前配置的任何值。

    2. 反应式超时 - 使用Reactor Core

        Reactor Netty使用Reactor Core作为其Reactive Streams实现。要配置另一个超时，我们可以使用Mono和Flux发布者提供的[timeout()](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html#timeout-java.time.Duration-)操作符。

        ```java
        webClient.get()
        .uri("https://baeldung.com/path")
        .retrieve()
        .bodyToFlux(JsonNode.class)
        .timeout(Duration.ofSeconds(5));
        ```

        在这种情况下，如果在给定的5秒内没有项目到达，就会出现TimeoutException。

        请记住，最好使用Reactor Netty中更具体的超时配置选项，因为它们为特定的目的和用例提供了更多的控制。

        timeout()方法适用于整个操作，从建立与远程对等体的连接到接收响应。它不会覆盖任何HttpClient相关的设置。

4. 异常处理

    每种类型的超时都提供了一个专门的异常，所以我们可以使用Ractive Streams和onError块轻松地处理它们。

    ```java
    webClient.get()
    .uri("https://baeldung.com/path")
    .retrieve()
    .bodyToFlux(JsonNode.class)
    .timeout(Duration.ofSeconds(5))
    .onErrorMap(ReadTimeoutException.class, ex -> new HttpTimeoutException("ReadTimeout"))
    .onErrorReturn(SslHandshakeTimeoutException.class, new TextNode("SslHandshakeTimeout"))
    .doOnError(WriteTimeoutException.class, ex -> log.error("WriteTimeout"))
    ...
    ```

    我们可以重复使用之前描述的任何异常，并使用Reactor编写我们自己的处理方法。

    此外，我们还可以根据HTTP状态添加一些逻辑。

    ```java
    webClient.get()
    .uri("https://baeldung.com/path")
    .onStatus(HttpStatus::is4xxClientError, resp -> {
        log.error("ClientError {}", resp.statusCode());
        return Mono.error(new RuntimeException("ClientError"));
    })
    .retrieve()
    .bodyToFlux(JsonNode.class)
    ...
    ```

## Spring WebFlux中的重试指南

1. 概述

    当我们在分布式云环境中构建应用程序时，我们需要为失败进行设计。这通常涉及重试。

    Spring WebFlux为我们提供了一些重试失败操作的工具。

    在本教程中，我们将研究如何在Spring WebFlux应用程序中添加和配置重试。

2. 使用案例

    在我们的例子中，我们将使用[MockWebServer](https://www.baeldung.com/spring-mocking-webclient#mockwebserver)，模拟一个外部系统暂时不可用，然后变得可用。

    让我们为一个连接到这个REST服务的组件创建一个简单的测试。

    ```java
    @Test
    void givenExternalServiceReturnsError_whenGettingData_thenRetryAndReturnResponse() {

        mockExternalService.enqueue(new MockResponse()
        .setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse()
        .setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse()
        .setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse()
        .setBody("stock data"));

        StepVerifier.create(externalConnector.getData("ABC"))
        .expectNextMatches(response -> response.equals("stock data"))
        .verifyComplete();

        verifyNumberOfGetRequests(4);
    }
    ```

3. 添加重试

    Mono和Flux的API中内置了两个关键的重试操作。

    1. 使用重试

        首先，让我们使用重试方法，它可以防止应用程序立即返回错误，并重新订阅指定次数。

        ```java
        public Mono<String> getData(String stockId) {
            return webClient.get()
                .uri(PATH_BY_ID, stockId)
                .retrieve()
                .bodyToMono(String.class)
                .retry(3);
        }
        ```

        这将重试最多三次，无论从网络客户端回来的是什么错误。

    2. 使用retryWhen

        接下来，让我们试试使用retryWhen方法的可配置策略。

        ```java
        public Mono<String> getData(String stockId) {
            return webClient.get()
                .uri(PATH_BY_ID, stockId)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.max(3));
        }
        ```

        这样我们就可以配置一个[Retry](https://projectreactor.io/docs/core/release/api/reactor/util/retry/Retry.html)对象来描述所需的逻辑。

        在这里，我们使用了max策略来重试最大的尝试次数。这等同于我们的第一个例子，但允许我们有更多的配置选项。特别是，我们应该注意到，在这种情况下，每次重试都尽可能快地发生。

4. 增加延时

    在没有任何延迟的情况下重试的主要缺点是，这没有给失败的服务以恢复的时间。它可能会使它不堪重负，使问题变得更糟，减少恢复的机会。

    1. 使用fixedDelay重试

        我们可以使用fixedDelay策略，在每次尝试之间增加一个延迟。

        ```java
        public Mono<String> getData(String stockId) {
            return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
        }
        ```

        这种配置允许两次尝试之间有两秒的延迟，这可能会增加成功的机会。然而，如果服务器正经历较长时间的中断，那么我们应该等待更长时间。但是，如果我们把所有的延迟都配置成一个很长的时间，短的突发事件会使我们的服务更加缓慢。

    2. 带后退的重试

        我们可以使用backoff策略，而不是以固定的时间间隔进行重试。

        ```java
        public Mono<String> getData(String stockId) {
            return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
        }
        ```

        实际上，这在尝试之间增加了一个逐渐增加的延迟--在我们的例子中大致是2、4、然后8秒的间隔。这让外部系统有更好的机会从普通的连接问题中恢复过来或处理积压的工作。

    3. 带抖动的重试

        后退策略的另一个好处是，它为计算的延迟间隔增加了随机性或抖动。因此，抖动([jitter](https://www.baeldung.com/resilience4j-backoff-jitter#jitter))可以帮助减少多个客户端同步重试的重试风暴。

        默认情况下，这个值被设置为0.5，这相当于最多计算延迟的50%的抖动。

        让我们使用jitter方法来配置一个不同的值0.75，以代表最多计算延迟的75%的抖动。

        ```java
        public Mono<String> getData(String stockId) {
            return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).jitter(0.75));
        }
        ```

        我们应该注意，可能的数值范围在0（无抖动）和1（抖动最多为计算延迟的100%）之间。

5. 筛选错误

    在这一点上，来自服务的任何错误都会导致重试，包括4xx错误，如400:Bad Request或401:Unauthorized。

    显然，我们不应该在这种客户端错误上重试，因为服务器的响应不会有任何不同。因此，让我们看看如何只在特定错误的情况下应用重试策略。

    首先，让我们创建一个异常来表示服务器错误。

    ```java
    public class ServiceException extends RuntimeException {
        
        public ServiceException(String message, int statusCode) {
            super(message);
            this.statusCode = statusCode;
        }
    }
    ```

    接下来，我们将用我们的5xx错误例外创建一个错误Mono，并使用过滤方法来配置我们的策略。

    ```java
    public Mono<String> getData(String stockId) {
        return webClient.get()
        .uri(PATH_BY_ID, stockId)
        .retrieve()
        .onStatus(HttpStatus::is5xxServerError, 
            response -> Mono.error(new ServiceException("Server error", response.rawStatusCode())))
        .bodyToMono(String.class)
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
            .filter(throwable -> throwable instanceof ServiceException));
    }
    ```

    现在我们只在WebClient管道中抛出ServiceException时重试。

6. 处理用尽的重试

    最后，我们可以考虑到所有重试都不成功的可能性。在这种情况下，该策略的默认行为是传播一个RetryExhaustedException，将最后一个错误包裹起来。

    相反，让我们通过使用onRetryExhaustedThrow方法来重写这一行为，并为我们的ServiceException提供一个生成器。

    ```java
    public Mono<String> getData(String stockId) {
        return webClient.get()
        .uri(PATH_BY_ID, stockId)
        .retrieve()
        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ServiceException("Server error", response.rawStatusCode())))
        .bodyToMono(String.class)
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
            .filter(throwable -> throwable instanceof ServiceException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                throw new ServiceException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
            }));
    }
    ```

    现在，请求将在一系列失败的重试结束时以我们的ServiceException失败。

7. 总结

在这篇文章中，我们研究了如何使用retry和retryWhen方法在Spring WebFlux应用程序中添加重试。

最初，我们为失败的操作添加了最大的重试次数。然后，我们通过使用和配置各种策略来引入尝试之间的延迟。

最后，我们研究了对某些错误的重试，以及当所有尝试都用完时的自定义行为。

## Relevant articles

- [x] [Spring Boot Reactor Netty Configuration](https://www.baeldung.com/spring-boot-reactor-netty)
- [x] [How to Return 404 with Spring WebFlux](https://www.baeldung.com/spring-webflux-404)
- [x] [RSocket Using Spring Boot](https://www.baeldung.com/spring-boot-rsocket)
- [x] [Spring MVC Async vs Spring WebFlux](https://www.baeldung.com/spring-mvc-async-vs-webflux)
- [x] [Set a Timeout in Spring 5 Webflux WebClient](https://www.baeldung.com/spring-webflux-timeout)
- [x] [Guide to Retry in Spring WebFlux](https://www.baeldung.com/spring-webflux-retry)
