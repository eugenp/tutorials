# Spring Cloud CLI简介

1. 简介

    在这篇文章中，我们来看看Spring Boot Cloud CLI（简称Cloud CLI）。该工具为Spring Boot CLI提供了一套命令行增强功能，有助于进一步抽象和简化Spring Cloud部署。

    该CLI于2016年底推出，允许使用命令行、.yml配置文件和Groovy脚本快速自动配置和部署标准Spring Cloud服务。

2. 设置

    Spring Boot Cloud CLI 1.3.x需要Spring Boot CLI 1.5.x，所以请确保从[Maven Central](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-boot-cli%22)（[安装说明](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-manual-cli-installation)）抓取最新版本的Spring Boot CLI，并从[Maven Repository](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-cloud-cli%22)（[Spring官方仓库](https://repo.spring.io/snapshot/org/springframework/cloud/spring-cloud-cli/)）抓取最新版本的Cloud CLI!

    要确保CLI已经安装并可以使用，只需运行。

    `$ spring --version`

    在验证了Spring Boot CLI的安装后，安装最新的Cloud CLI稳定版本。

    `$ spring install org.springframework.cloud:spring-cloud-cli:1.3.2.RELEASE`

    然后验证Cloud CLI。

    `$ spring cloud --version`

    高级安装功能可以在官方的Cloud CLI页面上找到!

3. 默认服务和配置

    CLI提供了七个核心服务，可以通过单行命令来运行和部署。

    要在 <http://localhost:8888> 上启动一个云配置服务器。

    `$ spring cloud configserver`

    要在 <http://localhost:8761> 上启动一个Eureka服务器。

    `$ spring cloud eureka`

    要在 <http://localhost:9095> 上启动一个 H2 服务器。

    `$ spring cloud h2`

    要在 <http://localhost:9091> 上启动一个 Kafka 服务器。

    `$ spring cloud kafka`

    要在 <http://localhost:9411> 上启动 Zipkin 服务器。

    `$ spring cloud zipkin`

    要在 <http://localhost:9393> 上启动 Dataflow 服务器。

    `$ spring cloud dataflow`

    要在 <http://localhost:7979> 上启动 Hystrix 仪表板。

    `$ spring cloud hystrixdashboard`

    列出当前运行的云服务。

    `$ spring cloud --list`

    方便的帮助命令。

    `$ spring help cloud`

    关于这些命令的更多细节，请查看官方[博客](https://spring.io/blog/2016/11/02/introducing-the-spring-cloud-cli-launcher)。

4. 用YML定制云服务

    可以通过云计算CLI部署的每个服务也可以使用相应命名的.yml文件进行配置。

    ```yml
    spring:
    profiles:
        active: git
    cloud:
        config:
        server:
            git:
            uri: https://github.com/spring-cloud-samples/config-repo
    ```

    这构成了一个简单的配置文件，我们可以用它来启动云配置服务器。

    例如，我们可以指定一个Git仓库作为URI源，当我们发出 "spring cloud configserver "命令时，该仓库将被自动克隆和部署。

    云CLI在引擎盖下使用了Spring Cloud Launcher。这意味着Cloud CLI支持大部分的Spring Boot配置机制。[这里](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)是Spring Boot属性的官方列表。

    Spring Cloud的配置符合'*spring.cloud...*'惯例。Spring Cloud和Spring Config Server的设置可以在这个链接中找到。

    我们还可以直接在cloud.yml中指定几个不同的模块和服务。

    ```yml
    spring:
    cloud:
        launcher:
        deployables:
            - name: configserver
            coordinates: maven://...:spring-cloud-launcher-configserver:1.3.2.RELEASE
            port: 8888
            waitUntilStarted: true
            order: -10
            - name: eureka
            coordinates: maven:/...:spring-cloud-launcher-eureka:1.3.2.RELEASE
            port: 8761
    ```

    cloud.yml允许添加自定义服务或模块，并允许使用Maven和Git存储库。

5. 运行自定义Groovy脚本

    由于Cloud CLI可以编译和部署Groovy代码，因此可以用Groovy编写自定义组件并进行高效部署。

    下面是一个最小REST API实现的例子。

    ```java
    @RestController
    @RequestMapping('/api')
    class api {
        @GetMapping('/get')
        def get() { [message: 'Hello'] }
    }
    ```

    假设该脚本被保存为rest.groovy，我们可以像这样启动我们的最小服务器。

    `$ spring run rest.groovy`

    Pinging <http://localhost:8080/api/get> ，应该可以看到。

    `{"message": "Hello"}`

6. 加密/解密

    Cloud CLI还提供了一个用于加密和解密的工具（在org.springframework.cloud.cli.command.*包中找到），可以直接通过命令行使用，也可以通过向Cloud Config Server端点传递一个值间接使用。

    让我们把它设置好，看看如何使用它。

    1. 设置

        云CLI以及Spring Cloud Config Server都使用org.springframework.security.crypto.encrypt.*来处理加密和解密命令。

        因此，两者都需要Oracle[在此](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)提供的JCE无限强度扩展。

    2. 按命令加密和解密

        要通过终端加密'my_value'，请调用。

        `$ spring encrypt my_value --key my_key`

        文件路径可以通过使用路径后的'@'来代替密钥名称（例如上面的'my_key'）（通常用于RSA公钥）。

        `$ spring encrypt my_value --key @${WORKSPACE}/foos/foo.pub`

        'my_value'现在将被加密为类似的东西。

        `c93cb36ce1d09d7d62dffd156ef742faaa56f97f135ebd05e90355f80290ce6b`

        此外，它将被存储在内存中的密钥'my_key'下。这使得我们可以通过命令行将'my_key'解密成'my_value'。

        `$ spring decrypt --key my_key`

        我们现在还可以在配置YAML或属性文件中使用加密的值，在加载时云配置服务器会自动解密。

        `encrypted_credential: "{cipher}c93cb36ce1d09d7d62dffd156ef742faaa56f97f135ebd05e90355f80290ce6b"`

    3. 使用配置服务器加密和解密

        Spring Cloud配置服务器提供了RESTful端点，密钥和加密值对可以存储在Java安全存储或内存中。

        关于如何正确设置和配置你的云计算配置服务器以接受对称或非对称加密的更多信息，请查看我们的[文章](https://www.baeldung.com/spring-cloud-configuration)或官方[文档](https://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.3.3.RELEASE/single/spring-cloud-config.html#_encryption_and_decryption)。

        一旦Spring云计算配置服务器使用 "spring cloud configserver" 命令配置好并开始运行，你就可以调用其API了。

        ```bash
        $ curl localhost:8888/encrypt -d mysecret
        //682bc583f4641835fa2db009355293665d2647dade3375c0ee201de2a49f7bda
        $ curl localhost:8888/decrypt -d 682bc583f4641835fa2db009355293665d2647dade3375c0ee201de2a49f7bda
        //mysecret
        ```

7. 总结

    我们在这里重点介绍了Spring Boot Cloud CLI。欲了解更多信息，请查看官方[文档](https://cloud.spring.io/spring-cloud-static/spring-cloud-cli/1.3.2.RELEASE/)。

    本文中使用的配置和bash示例可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-cli)上找到。

## Relevant Articles

- [x] [Introduction to Spring Cloud CLI](https://www.baeldung.com/spring-cloud-cli)
