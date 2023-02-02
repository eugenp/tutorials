# Spring Cloud Zookeeper的介绍

1. 简介

    在这篇文章中，我们将了解Zookeeper以及它是如何被用于服务发现的，它被用作云中服务的集中知识。

    Spring Cloud Zookeeper通过自动配置和与Spring环境的绑定，为Spring Boot应用程序提供[Apache Zookeeper](https://zookeeper.apache.org/)集成。

2. 服务发现设置

    我们将创建两个应用程序。

    - 一个将提供服务的应用程序（在本文中称为 "Service Provider 服务提供商"）。
    - 一个将消费该服务的应用程序（称为 Service Consumer 服务消费者）

    Apache Zookeeper将在我们的服务发现设置中充当协调者。Apache Zookeeper的安装说明可在以下链接中找到。

3. 服务提供者注册

    我们将通过添加spring-cloud-starter-zookeeper-discovery依赖关系并在主应用程序中使用注解@EnableDiscoveryClient来启用服务注册。

    下面，我们将针对在响应GET请求时返回 "Hello World!" 的服务，逐步展示这一过程。

    1. Maven的依赖性

        首先，让我们把所需的spring-cloud-starter-zookeeper-discovery、spring-web、spring-cloud-dependencies和spring-boot-starter依赖项添加到我们的pom.xml文件。

        ```xml
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.2.6.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
                <version>5.1.14.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            </dependency>
        </dependencies>
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>Hoxton.SR4</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
        ```

    2. 服务提供者注解

        接下来，我们将用@EnableDiscoveryClient来注解我们的主类。这将使HelloWorld应用程序具有发现意识。

        ```java
        @SpringBootApplication
        @EnableDiscoveryClient
        public class HelloWorldApplication {
            public static void main(String[] args) {
                SpringApplication.run(HelloWorldApplication.class, args);
            }
        }
        ```

        还有一个简单的控制器。

        ```java
        @GetMapping("/helloworld")
        public String helloWorld() {
            return "Hello World!";
        }
        ```

    3. YAML 配置

        现在让我们创建一个YAML Application.yml文件，它将用于配置应用程序的日志级别，并通知Zookeeper该应用程序已启用发现功能。

        被注册到Zookeeper的应用程序的名称是最重要的。在以后的服务消费者中，佯装的客户将在服务发现过程中使用这个名字。

        ```yaml
        spring:
        application:
            name: HelloWorld
        cloud:
            zookeeper:
            discovery:
                enabled: true
        logging:
        level:
            org.apache.zookeeper.ClientCnxn: WARN
        ```

        spring boot应用程序在默认的2181端口寻找zookeeper。如果zookeeper位于其他地方，需要添加配置。

        ```yaml
        spring:
        cloud:
            zookeeper:
            connect-string: localhost:2181
        ```

4. 服务消费者

    现在我们将创建一个REST服务消费者，并使用Spring Netflix Feign Client注册它。

    1. Maven依赖性

        首先，让我们把所需的spring-cloud-starter-zookeeper-discovery、spring-web、spring-cloud-dependencies、spring-boot-starter-actuator和spring-cloud-starter-feign依赖项添加到pom.xml文件。

        ```xml
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-actuator</artifactId>
                <version>2.2.6.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-feign</artifactId>
            </dependency>
        </dependencies>
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>Hoxton.SR4</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
        ```

    2. 服务消费者注解

        与服务提供者一样，我们将用@EnableDiscoveryClient来注解主类，使其具有发现意识。

        ```java
        @SpringBootApplication
        @EnableDiscoveryClient
        public class GreetingApplication {
        
            public static void main(String[] args) {
                SpringApplication.run(GreetingApplication.class, args);
            }
        }
        ```

    3. 用Feign客户端发现服务

        我们将使用Spring Cloud Feign集成，这是Netflix的一个项目，可以让你定义一个声明式REST客户端。我们声明URL的模样，Feign负责连接到REST服务。

        Feign客户端是通过spring-cloud-starter-feign包导入的。我们将用@EnableFeignClients注释一个@Configuration，以便在应用程序中使用它。

        最后，我们用@FeignClient("service-name")注释了一个接口，并将其自动连接到我们的应用程序，以便我们以编程方式访问该服务。

        在这里，在注解@FeignClient(name = "HelloWorld")中，我们引用了我们之前创建的服务生产者的服务名称。

        ```java
        @Configuration
        @EnableFeignClients
        @EnableDiscoveryClient
        public class HelloWorldClient {
        
            @Autowired
            private TheClient theClient;

            @FeignClient(name = "HelloWorld")
            interface TheClient {
        
                @RequestMapping(path = "/helloworld", method = RequestMethod.GET)
                @ResponseBody
                String helloWorld();
            }
            public String HelloWorld() {
                return theClient.HelloWorld();
            }
        }
        ```

    4. 控制器类

        下面是简单的服务控制器类，它将在我们的feign客户类上调用服务提供者函数，通过注入的接口helloWorldClient对象消费服务（其细节通过服务发现抽象出来），并在响应中显示。

        ```java
        @RestController
        public class GreetingController {
        
            @Autowired
            private HelloWorldClient helloWorldClient;

            @GetMapping("/get-greeting")
            public String greeting() {
                return helloWorldClient.helloWorld();
            }
        }
        ```

    5. YAML 配置

        接下来，我们创建一个YAML文件Application.yml，与之前使用的文件非常相似。该文件配置了应用程序的日志级别。

        ```yaml
        logging:
        level:
            org.apache.zookeeper.ClientCnxn: WARN
        ```

        该应用程序在默认的2181端口寻找Zookeeper。如果Zookeeper位于其他地方，需要添加配置。

        ```yaml
        spring:
        cloud:
            zookeeper:
            connect-string: localhost:2181
        ```

5. 测试设置

    HelloWorld REST服务在部署时向Zookeeper注册了自己。然后作为服务消费者的Greeting服务使用Feign客户端调用HelloWorld服务。

    现在我们可以构建并运行这两个服务。

    最后，我们将浏览器指向<http://localhost:8083/get-greeting>，它应该显示。

    `Hello World!`

6. 总结

    在这篇文章中，我们看到了如何使用Spring Cloud Zookeeper实现服务发现，我们在Zookeeper服务器中注册了一个名为HelloWorld的服务，在不知道其位置细节的情况下，使用Feign Client来发现和消费Greeting服务。

    像往常一样，这篇文章的代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-zookeeper)上找到。
