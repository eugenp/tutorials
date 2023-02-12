# Spring Cloud OpenFeign

## Spring Cloud OpenFeign简介

1. 概述

    在本教程中，我们将介绍[Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)--Spring Boot应用程序的声明式REST客户端。

    [Feign](https://www.baeldung.com/intro-to-feign)通过可插拔的注解支持，包括Feign注解和JAX-RS注解，使编写Web服务客户端更加容易。

    此外，[Spring Cloud](https://www.baeldung.com/spring-cloud-series)还增加了对[Spring MVC注解](https://www.baeldung.com/spring-mvc-annotations)的支持，并支持使用与Spring Web中相同的[HttpMessageConverters](https://www.baeldung.com/spring-httpmessageconverter-rest)。

    使用Feign的一个好处是，除了一个接口定义外，我们不需要写任何调用服务的代码。

2. 依赖关系

    首先，我们先创建一个Spring Boot Web项目，并在pom.xml文件中添加spring-cloud-starter-openfeign依赖项。

    参见 spring-cloud-openfeign/pom.xml

    此外，我们还需要添加spring-cloud-dependencies。

    我们可以在Maven中心找到spring-cloud-starter-openfeign和spring-cloud-dependencies的最新版本。

3. Feign客户端

    接下来，我们需要在主类中添加@EnableFeignClients。

    ```java
    @SpringBootApplication
    @EnableFeignClients
    public class ExampleApplication {}
    ```

    通过这个注解，我们可以对声明自己是Feign客户端的接口进行组件扫描。

    然后我们使用@FeignClient注解来声明一个Feign客户端。

    ```java
    @FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/")
    public interface JSONPlaceHolderClient {

        @RequestMapping(method = RequestMethod.GET, value = "/posts")
        List<Post> getPosts();

        @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
        Post getPostById(@PathVariable("postId") Long postId);
    }
    ```

    在这个例子中，我们已经配置了一个客户端来读取[JSONPlaceholder APIs](https://jsonplaceholder.typicode.com/)。

    在@FeignClient注解中传递的value参数是一个强制性的、任意的客户端名称，而通过url参数，我们指定了API的基本URL。

    此外，由于这个接口是一个Feign客户端，我们可以使用Spring Web注解来声明我们要接触的API。

4. 配置

    现在，非常重要的一点是，每个Feign客户端都是由一组可定制的组件组成的。

    Spring Cloud使用FeignClientsConfiguration类为每个命名的客户端按需创建一个新的默认集，我们可以按照下一节的解释进行定制。

    上面的类包含这些Bean。

    - Decoder - ResponseEntityDecoder，它包装了SpringDecoder，用于对Response进行解码。
    - Encoder - SpringEncoder用于对RequestBody进行编码。
    - Logger - Slf4jLogger是Feign使用的默认日志器。
    - Contract - SpringMvcContract，它提供注释处理。
    - Feign-Builder - HystrixFeign.Builder被用来构建组件。
    - Client客户端 - LoadBalancerFeignClient或默认的Feign客户端。

    1. 自定义豆子配置

        如果我们想定制其中的一个或多个Bean，我们可以通过创建一个配置类来覆盖它们，然后将其添加到FeignClient注释中。

        ```java
        @FeignClient(value = "jplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = ClientConfiguration.class)

        public class ClientConfiguration {

            @Bean
            public OkHttpClient client() {
                return new OkHttpClient();
            }
        }
        ```

        在这个例子中，我们告诉Feign使用[OkHttpClient](https://www.baeldung.com/guide-to-okhttp)而不是默认的客户端来支持HTTP/2。

        Feign为不同的使用情况支持多个客户端，包括ApacheHttpClient，它可以随请求发送更多的头信息，例如，Content-Length，这是一些服务器所期望的。

        为了使用这些客户端，我们不要忘记在我们的pom.xml文件中添加所需的依赖项：feign-okhttp、feign-httpclient。

        参见 spring-cloud-openfeign/pom.xml

        我们可以在Maven中心找到feign-okhttp和feign-httpclient的最新版本。

    2. 使用属性进行配置

        我们可以不使用配置类，而是使用应用程序属性来配置Feign客户端，如application.yaml例子所示。

        ```yml
        feign:
        client:
            config:
            default:
                connectTimeout: 5000
                readTimeout: 5000
                loggerLevel: basic
        ```

        通过这个配置，我们为应用程序中的每个声明的客户端设置超时为5秒，日志级别为基本。

        最后，我们可以用default作为客户端名称来创建配置，以配置所有@FeignClient对象，或者我们可以为一个配置声明feign客户端名称。

        ```java
        feign:
        client:
            config:
            jplaceholder:
        ```

        如果我们同时拥有配置豆和配置属性，配置属性将覆盖配置豆的值。

5. Interceptors拦截器

    添加拦截器是Feign提供的另一个有用的功能。

    拦截器可以为每个HTTP请求/响应执行各种隐性任务，从认证到记录。

    在本节中，我们将实现我们自己的拦截器，以及使用Spring Cloud OpenFeign提供的开箱即用的拦截器。两者都将为每个请求添加一个基本认证头。

    1. 实现RequestInterceptor

        让我们来实现我们的自定义请求拦截器。

        ```java
        @Bean
        public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("user", username);
            requestTemplate.header("password", password);
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
        }
        ```

        另外，为了将拦截器添加到请求链中，我们只需要将这个bean添加到我们的配置类中，或者像我们之前看到的那样，在属性文件中声明它。

        ```yml
        feign:
        client:
            config:
            default:
                requestInterceptors:
                com.baeldung.cloud.openfeign.JSONPlaceHolderInterceptor
        ```

    2. 使用BasicAuthRequestInterceptor

        另外，我们也可以使用Spring Cloud OpenFeign提供的BasicAuthRequestInterceptor类。

        ```java
        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
            return new BasicAuthRequestInterceptor("username", "password");
        }
        ```

        就这么简单。现在所有的请求都将包含基本认证头。

6. Hystrix支持

    Feign支持[Hystrix](https://www.baeldung.com/spring-cloud-netflix-hystrix)，所以如果我们启用了它，我们可以实现回退模式。

    通过回退模式，当一个远程服务调用失败时，服务消费者将执行一个替代的代码路径，以尝试通过另一种方式进行操作，而不是产生异常。

    为了实现这一目标，我们需要在属性文件中添加feign.hystrix.enabled=true来启用Hystrix。

    这允许我们实现回退方法，当服务失败时被调用。

    参见 com.baeldung.cloud.openfeign.hystrix/JSONPlaceHolderFallback.java

    为了让Feign知道已经提供了回退方法，我们还需要在@FeignClient注解中设置我们的回退类。

    ```java
    @FeignClient(value = "jplaceholder",
    url = "https://jsonplaceholder.typicode.com/",
    fallback = JSONPlaceHolderFallback.class)
    public interface JSONPlaceHolderClient {
        // APIs
    }
    ```

7. 日志

    对于每个Feign客户端，默认都会创建一个日志记录器。

    要启用日志，我们应该在application.properties文件中使用客户端接口的包名声明它。

    `logging.level.com.baeldung.cloud.openfeign.client: DEBUG`

    或者，如果我们想在一个包中只为一个特定的客户端启用日志，我们可以使用全类名称。

    `logging.level.com.baeldung.cloud.openfeign.client.JSONPlaceHolderClient: DEBUG`

    注意，Feign日志只对DEBUG级别做出响应。

    我们可以为每个客户端配置的Logger.Level表示要记录多少内容。

    ```java
    public class ClientConfiguration {
        
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.BASIC;
        }
    }
    ```

    有四个日志级别可供选择。

    - NONE - 没有日志记录，这是默认的
    - BASIC - 只记录请求方法、URL和响应状态
    - HEADERS - 记录基本信息以及请求和响应头信息
    - FULL - 记录请求和响应的正文、头信息和元数据

8. 错误处理

    Feign的默认错误处理，ErrorDecoder.default，总是抛出一个FeignException。

    现在，这种行为并不总是最有用的。所以，为了定制抛出的异常，我们可以使用CustomErrorDecoder。

    参见 cloud.openfeign.customizederrorhandling.config/CustomErrorDecoder.java

    然后，正如我们之前所做的那样，我们必须通过在配置类中添加一个bean来替换默认的ErrorDecoder。

    ```java
    public class ClientConfiguration {

        @Bean
        public ErrorDecoder errorDecoder() {
            return new CustomErrorDecoder();
        }
    }
    ```

9. 总结

    在这篇文章中，我们讨论了Spring Cloud OpenFeign以及它在一个简单的示例应用程序中的实现。

    我们还看到了如何配置客户端，为我们的请求添加拦截器，并使用Hystrix和ErrorDecoder处理错误。

    像往常一样，本教程中的所有代码样本都可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-openfeign)上找到。

## Relevant Articles

- [x] [Introduction to Spring Cloud OpenFeign](https://www.baeldung.com/spring-cloud-openfeign)
- [Differences Between Netflix Feign and OpenFeign](https://www.baeldung.com/netflix-feign-vs-openfeign)
- [File Upload With Open Feign](https://www.baeldung.com/java-feign-file-upload)
- [Feign Logging Configuration](https://www.baeldung.com/java-feign-logging)
- [Provide an OAuth2 Token to a Feign Client](https://www.baeldung.com/spring-cloud-feign-oauth-token)
- [Retrieve Original Message From Feign ErrorDecoder](https://www.baeldung.com/feign-retrieve-original-message)
- [RequestLine with Feign Client](https://www.baeldung.com/feign-requestline)
- [Propagating Exceptions With OpenFeign and Spring](https://www.baeldung.com/spring-openfeign-propagate-exception)
