# Spring Cloud Gateway

This module contains articles about Spring Cloud Gateway

## 探索新的Spring Cloud Gateway

1. 概述

    在本教程中，我们将探索[Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/)项目的主要功能，这是一个基于Spring 5、Spring Boot 2和Project Reactor的新API。

    该工具提供了开箱即用的路由机制，通常在微服务应用中使用，作为一种将多个服务隐藏在单一门面背后的方式。

    关于没有Spring Cloud Gateway项目的Gateway模式的解释，请查看我们之前的[文章](https://www.baeldung.com/spring-cloud-gateway-pattern)。

2. 路由处理程序

    由于专注于路由请求，Spring Cloud Gateway将请求转发给Gateway Handler Mapping，该Mapping决定对匹配特定路由的请求应该做什么。

    让我们从一个快速的例子开始，看看Gateway处理程序是如何通过使用RouteLocator来解决路由配置的。

    ```java
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        .route("r1", r -> r.host("**.baeldung.com")
            .and()
            .path("/baeldung")
            .uri("http://baeldung.com"))
        .route(r -> r.host("**.baeldung.com")
            .and()
            .path("/myOtherRouting")
            .filters(f -> f.prefixPath("/myPrefix"))
            .uri("http://othersite.com")
            .id("myOtherID"))
        .build();
    }
    ```

    注意我们是如何利用这个API的主要构建模块的。

    - Route - 网关的主要API。它由一个给定的标识（ID）、一个目的地（URI）和一组谓词和过滤器定义。
    - Predicate - 一个Java 8的谓词--用于使用头信息、方法或参数来匹配HTTP请求。
    - Filter - 一个标准的Spring WebFilter。

3. 动态路由

    就像[Zuul](https://www.baeldung.com/spring-rest-with-zuul-proxy)一样，Spring Cloud Gateway提供了将请求路由到不同服务的方法。

    路由配置可以通过使用纯Java（RouteLocator，如第2节中的例子所示）或使用属性配置来创建。

    ```yaml
    spring:
    application:
        name: gateway-service  
    cloud:
        gateway:
        routes:
        - id: baeldung
            uri: baeldung.com
        - id: myOtherRouting
            uri: localhost:9999
    ```

4. 路由工厂

    Spring Cloud Gateway使用Spring WebFlux HandlerMapping基础设施匹配路由。

    它还包括许多内置的路由谓词因子。所有这些谓词都匹配HTTP请求的不同属性。多个路由谓词因子可以通过逻辑 "和 "来组合。

    路由匹配可以通过编程方式应用，也可以通过配置属性文件使用不同类型的路由谓词因子。

    我们的文章[Spring Cloud Gateway Routing Predicate Factories](https://www.baeldung.com/spring-cloud-gateway-routing-predicate-factories)更详细地探讨了路由工厂。

5. WebFilter Factories

    路由过滤器使修改传入的HTTP请求或传出的HTTP响应成为可能。

    Spring Cloud Gateway包括许多内置的WebFilter Factories，以及创建自定义过滤器的可能性。

    我们的文章[Spring Cloud Gateway WebFilter Factories](https://www.baeldung.com/spring-cloud-gateway-webfilter-factories)更详细地探讨了WebFilter工厂。

6. 支持Spring Cloud DiscoveryClient

    Spring Cloud Gateway可以很容易地与服务发现和注册库集成，如Eureka Server和Consul。

    ```java
    @Configuration
    @EnableDiscoveryClient
    public class GatewayDiscoveryConfiguration {
        @Bean
        public DiscoveryClientRouteDefinitionLocator 
        discoveryClientRouteLocator(DiscoveryClient discoveryClient) {
            return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
        }
    }
    ```

    1. LoadBalancerClient过滤器

        LoadBalancerClientFilter使用 *`ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR`* 寻找交换属性属性中的URI。

        如果URL有一个lb scheme（例如，lb://baeldung-service），它将使用Spring Cloud LoadBalancerClient将名称（即baeldung-service）解析为实际的主机和端口。

        未经修改的原始URL被放在 *`ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR`* 属性中。

7. 监控

    Spring Cloud Gateway使用了Actuator API，这是一个著名的Spring Boot库，为监控应用程序提供了几个开箱即用的服务。

    一旦安装和配置了Actuator API，就可以通过访问 /gateway/endpoint 来可视化网关的监控功能。

8. 实施

    现在我们将创建一个简单的例子，说明Spring Cloud Gateway作为代理服务器使用路径谓词的用法。

    1. 依赖关系

        Spring Cloud Gateway目前在里程碑软件库中，版本为2.0.0.RC2。这也是我们在这里使用的版本。

        为了添加该项目，我们将使用依赖性管理系统。

        ```xml
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-gateway</artifactId>
                    <version>2.0.0.RC2</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
        ```

        接下来，我们将添加必要的依赖项。

        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        ```

    2. 代码实现

        现在我们在application.yml文件中创建一个简单的路由配置。

        ```yaml
        spring:
        cloud:
            gateway:
            routes:
            - id: baeldung_route
                uri: http://baeldung.com
                predicates:
                - Path=/baeldung/
        management:
        endpoints:
            web:
            exposure:
                include: "*'
        ```

        而这里是Gateway应用程序的代码。

        ```java
        @SpringBootApplication
        public class GatewayApplication {
            public static void main(String[] args) {
                SpringApplication.run(GatewayApplication.class, args);
            }
        }
        ```

        应用程序启动后，我们可以访问网址 "<http://localhost/actuator/gateway/routes/baeldung_route> "来检查所有创建的路由配置。

        ```json
        {
            "id":"baeldung_route",
            "predicates":[{
                "name":"Path",
                "args":{"_genkey_0":"/baeldung"}
            }],
            "filters":[],
            "uri":"http://baeldung.com",
            "order":0
        }
        ```

        我们看到，相对网址"/baeldung"被配置为一个路由。因此，点击url "http://localhost/baeldung"，我们将被重定向到 "http://baeldung.com"，就像我们的例子中配置的那样。

9. 总结

    在这篇文章中，我们探索了作为Spring Cloud Gateway一部分的一些功能和组件。这个新的API为网关和代理支持提供了开箱即用的工具。

    这里介绍的例子可以在我们的[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-gateway)资源库中找到。

## Relevant Articles

- [x] [Exploring the New Spring Cloud Gateway](http://www.baeldung.com/spring-cloud-gateway)
- [Writing Custom Spring Cloud Gateway Filters](https://www.baeldung.com/spring-cloud-custom-gateway-filters)
- [Spring Cloud Gateway Routing Predicate Factories](https://www.baeldung.com/spring-cloud-gateway-routing-predicate-factories)
- [Spring Cloud Gateway WebFilter Factories](https://www.baeldung.com/spring-cloud-gateway-webfilter-factories)
- [Using Spring Cloud Gateway with OAuth 2.0 Patterns](https://www.baeldung.com/spring-cloud-gateway-oauth2)
- [URL Rewriting With Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway-url-rewriting)
- [Processing the Response Body in Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway-response-body)
