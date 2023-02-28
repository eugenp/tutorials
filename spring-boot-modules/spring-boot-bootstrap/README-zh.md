# Spring Boot Bootstrap

This module contains articles about bootstrapping Spring Boot applications.

## Spring Boot教程–引导一个简单的应用程序

1. 概述

    Spring Boot是对Spring平台的一个有见解的补充，专注于约定而非配置——对于以最小的努力开始并创建独立的生产级应用程序非常有用。

    本教程是Boot的起点，换句话说，是一种以简单方式开始使用基本web应用程序的方法。

    我们将介绍一些核心配置、前端、快速数据操作和异常处理。

    进一步阅读：

    [如何更改Spring Boot中的默认端口](https://www.baeldung.com/spring-boot-change-port)

    看看如何更改Spring Boot应用程序中的默认端口。

    [Spring Boot Starters简介](https://www.baeldung.com/spring-boot-starters)

    快速概述最常见的Spring Boot Starter，以及如何在实际项目中使用它们的示例。

2. 设置

    首先，让我们使用[Spring Initializer](https://start.spring.io/)为项目生成基础。

    生成的项目依赖于Boot父级：

    ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.2</version>
        <relativePath />
    </parent>
    ```

    最初的依赖关系非常简单：

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
    ```

3. 应用程序配置

    接下来，我们将为应用程序配置一个简单的主类：

    ```java
    @SpringBootApplication
    public class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }
    ```

    注意我们如何使用@SpringBootApplication作为我们的主要应用程序配置类。在幕后，这相当于@Configuration、@EnableAutoConfiguration和@ComponentScan。

    最后，我们将定义一个简单的application.properties文件，该文件目前只有一个属性：

    `server.port=8081`

    server.port将服务器端口从默认8080更改为8081；当然，还有更多[Spring Boot properties available](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)。

4. 简单MVC视图

    现在让我们使用Thymelaaf添加一个简单的前端。

    首先，我们需要在pom.xml中添加spring boot starter thymelaf依赖项：

    ```xml
    <dependency> 
        <groupId>org.springframework.boot</groupId> 
        <artifactId>spring-boot-starter-thymeleaf</artifactId> 
    </dependency>
    ```

    默认情况下，这将启用Thymelaf。无需额外配置。

    现在，我们可以在应用程序中配置它。属性：

    ```properties
    spring.thymeleaf.cache=false
    spring.thymeleaf.enabled=true
    spring.thymeleaf.prefix=classpath:/templates/
    spring.thymeleaf.suffix=.html
    spring.application.name=Bootstrap Spring Boot
    ```

    接下来，我们将定义一个简单的控制器和一个带有欢迎消息的基本主页：

    web/SimpleController.java

    最后，添加我们的resources/home.html。

    请注意我们如何使用在财产中定义的属性，然后注入该属性，以便在主页上显示它。

5. 安全

    接下来，让我们通过首先包括安全启动器来为应用程序添加安全性：

    ```xml
    <dependency> 
        <groupId>org.springframework.boot</groupId> 
        <artifactId>spring-boot-starter-security</artifactId> 
    </dependency>
    ```

    现在，我们可以注意到一个模式：大多数Spring库都可以使用simple Boot starters轻松导入到我们的项目中。

    一旦spring-boot-starter-security依赖项位于应用程序的类路径上，所有端点都会默认受到保护，根据spring-security的内容协商策略使用httpBasic或formLogin。

    这就是为什么，如果我们在类路径上有启动器，我们通常应该定义自己的自定义安全配置：

    config/SecurityConfig.java

    在我们的示例中，我们允许不受限制地访问所有端点。

    当然，SpringSecurity是一个广泛的主题，不容易在几行配置中涵盖。因此，我们绝对鼓励[深入阅读该主题](https://www.baeldung.com/security-spring)。

6. 简单持久化

    让我们首先定义数据模型，一个简单的Book实体：

    persistence.model/book.java

    以及它的存储库，在这里充分利用Spring Data：

    persistence.repo/BookRepository.java
    最后，我们当然需要配置新的持久层：

    ```java
    @EnableJpaRepositories("com.baeldung.persistence.repo") 
    @EntityScan("com.baeldung.persistence.model")
    @SpringBootApplication 
    public class Application {
    ...
    }
    ```

    请注意，我们使用的是：

    - @EnableJpaRepositories扫描指定包中的存储库
    - @EntityScan获取我们的JPA实体
    为了简单起见，我们在这里使用了H2内存数据库。这样我们在运行项目时就不会有任何外部依赖。

    一旦我们包括H2依赖项，Spring Boot会自动检测并设置持久性，而不需要额外的配置，除了数据源财产：

    ```properties
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.url=jdbc:h2:mem:bootapp;DB_CLOSE_DELAY=-1
    spring.datasource.username=sa
    spring.datasource.password=
    ```

    当然，与安全性一样，持久性是一个比这里的基本设置更广泛的主题，肯定需要[进一步探讨](https://www.baeldung.com/persistence-with-spring-series)。

7. Web和控制器

    接下来，让我们看一看web层。我们将从设置一个简单的控制器BookController开始。

    我们将通过一些简单的验证来实现基本的CRUD操作，公开Book资源：

    web/BookController.java

    考虑到应用程序的这一方面是一个API，我们在这里使用了@RestController注释-这相当于@Controller和@ResponseBody-以便每个方法都将返回的资源封送至HTTP响应。

    请注意，我们在这里公开Book实体作为外部资源。这对于这个简单的应用程序来说很好，但在实际应用程序中，我们可能需要将这[两个概念分开](https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application)。

8. 错误处理

    现在核心应用程序已经准备就绪，让我们使用@ControllerAdvice关注一个简单的集中错误处理机制：

    web/RestExceptionHandler.java

    这让我们了解了这种全局异常处理机制的可能性。要查看完整的实现，请查看[深入教程](https://www.baeldung.com/exception-handling-for-rest-with-spring)。

    注意，默认情况下，Spring Boot还提供了/error映射。我们可以通过创建一个简单的resources/templates/error.html来自定义其视图：

    像Boot中的大多数其他方面一样，我们可以使用一个简单的属性来控制它：

    `server.error.path=/error2`

9. 测试

    最后，让我们测试一下新的Books API。

    我们可以使用[@SpringBootTest](https://www.baeldung.com/spring-boot-testing)加载应用程序上下文，并验证在运行应用程序时没有错误：

    SpringContextTest.java

    接下来，让我们添加一个JUnit测试，使用[REST Assured](https://www.baeldung.com/rest-assured-tutorial)验证对我们编写的API的调用。

    首先，我们将添加[rest-assured](https://search.maven.org/artifact/io.rest-assured/rest-assured)的依赖关系：

    ```xml
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <scope>test</scope>
    </dependency>
    ```

    现在我们可以添加测试类：SpringBootBootstrapLiveTest.java

10. 结论

    这是对Spring Boot的快速但全面的介绍。

    当然，我们在这里几乎没有触及表面。这个框架比我们在一篇介绍文章中所能涵盖的要多得多。

    这正是为什么我们在网站上有[不止一篇关于Boot的文章](https://www.baeldung.com/tag/spring-boot/)。

    和往常一样，我们这里示例的完整源代码已经在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-bootstrap)上完成了。

## Relevant Articles

- [x] [Spring Boot Tutorial – Bootstrap a Simple Application](https://www.baeldung.com/spring-boot-start)
- [Thin JARs with Spring Boot](https://www.baeldung.com/spring-boot-thin-jar)
- [Deploying a Spring Boot Application to Cloud Foundry](https://www.baeldung.com/spring-boot-app-deploy-to-cloud-foundry)
- [Deploy a Spring Boot Application to Google App Engine](https://www.baeldung.com/spring-boot-google-app-engine)
- [Deploy a Spring Boot Application to OpenShift](https://www.baeldung.com/spring-boot-deploy-openshift)
- [Deploy a Spring Boot Application to AWS Beanstalk](https://www.baeldung.com/spring-boot-deploy-aws-beanstalk)
- [Implement Health Checks in OpenShift](https://www.baeldung.com/ops/openshift-health-checks)
