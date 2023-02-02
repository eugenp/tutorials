# Spring云配置的快速介绍

1. 概述

    Spring Cloud Config是Spring的客户端/服务器方法，用于存储和服务跨多个应用程序和环境的分布式配置。

    这种配置存储在Git版本控制下是理想的版本，可以在应用运行时进行修改。虽然它很适合在Spring应用程序中使用所有支持的配置文件格式以及像Environment、[PropertySource或@Value](https://www.baeldung.com/properties-with-spring)这样的结构，但它可以在运行任何编程语言的任何环境中使用。

    在本教程中，我们将重点介绍如何建立一个由Git支持的配置服务器，在一个简单的REST应用服务器中使用它，并建立一个包括加密属性值的安全环境。

2. 项目设置和依赖关系

    首先，我们将创建两个新的Maven项目。服务器项目依赖[spring-cloud-config-server](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-config-server%22)模块，以及[spring-boot-starter-security](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.boot%22%20AND%20a%3A%22spring-boot-starter-security%22)和[spring-boot-starter-web](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.boot%22%20AND%20a%3A%22spring-boot-starter-web%22) starter捆绑包。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    ```

    然而，对于客户项目，我们只需要spring-cloud-starter-config和spring-boot-starter-web模块。

3. 一个配置服务器的实现

    该应用程序的主要部分是一个配置类，更确切地说，是一个@SpringBootApplication，它通过自动配置注解@EnableConfigServer拉入所有需要的设置。

    参见 .spring.cloud.config.server/ConfigServer.java

    现在我们需要配置服务器监听的端口和Git-url，它提供了我们的版本控制的配置内容。后者可以使用http、ssh等协议，或本地文件系统上的一个简单文件。

    > 提示：如果我们计划使用多个配置服务器实例指向同一个配置仓库，我们可以配置服务器将我们的 repo 克隆到一个本地临时文件夹中。但要注意有双因素认证的私有资源库，它们很难处理 在这种情况下，在我们的本地文件系统上克隆它们并使用副本会更容易。

    还有一些占位变量和搜索模式可用于配置仓库-url；然而，这超出了我们文章的范围。 如果你有兴趣了解更多，官方文档是一个好的开始。

    我们还需要在application.properties中为Basic-Authentication设置一个用户名和密码，以避免每次应用重启时自动生成密码。

    ```properties
    server.port=8888
    spring.cloud.config.server.git.uri=ssh://localhost/config-repo
    spring.cloud.config.server.git.clone-on-start=true
    spring.security.user.name=root
    spring.security.user.password=s3cr3t
    ```

4. 作为配置存储的 Git 仓库

    为了完成我们的服务器，我们必须在配置的URL下初始化一个Git仓库，创建一些新的属性文件，并在其中填充一些值。

    配置文件的名称和普通的Spring application.properties一样，但不使用 "application "这个词，而是使用配置的名称，比如客户端的属性 "spring.application.name "的值，后面加一个破折号和活动配置文件。比如说

    ```bash
    $> git init
    $> echo 'user.role=Developer' > config-client-development.properties
    $> echo 'user.role=User'      > config-client-production.properties
    $> git add .
    $> git commit -m 'Initial config-client properties'
    ```

    故障排除。如果我们遇到与 ssh 有关的认证问题，我们可以在 ssh 服务器上仔细检查 ~/.ssh/known_hosts 和 ~/.ssh/authorized_keys。

5. 查询配置

    现在我们可以启动我们的服务器了。我们的服务器提供的由Git支持的配置API可以通过以下路径进行查询。

    ```txt
    /{application}/{profile}[/{label}]
    /{application}-{profile}.yml
    /{label}/{application}-{profile}.yml
    /{application}-{profile}.properties
    /{label}/{application}-{profile}.properties
    ```

    {label}占位符指的是一个Git分支，{application}指的是客户端的应用程序名称，{profile}指的是客户端当前的活动应用程序配置文件。

    因此，我们可以通过以下方式为我们计划中的配置客户端检索配置，该客户端在分支master的开发配置文件下运行。

    `$> curl http://root:s3cr3t@localhost:8888/config-client/development/master`

6. 客户端的实现

    接下来，让我们来处理一下客户端的问题。这将是一个非常简单的客户端应用程序，由一个有一个GET方法的REST控制器组成。

    为了获取我们的服务器，配置必须放在application.properties文件中。Spring Boot 2.4引入了一种使用spring.config.import属性加载配置数据的新方法，现在它是绑定配置服务器的默认方式。

    ```java
    @SpringBootApplication
    @RestController
    public class ConfigClient {
        
        @Value("${user.role}")
        private String role;

        public static void main(String[] args) {
            SpringApplication.run(ConfigClient.class, args);
        }

        @GetMapping(
        value = "/whoami/{username}",  
        produces = MediaType.TEXT_PLAIN_VALUE)
        public String whoami(@PathVariable("username") String username) {
            return String.format("Hello! 
            You're %s and you'll become a(n) %s...\n", username, role);
        }
    }
    ```

    除了应用程序名称，我们还在application.properties中放入活动配置文件和连接细节。

    ```properties
    spring.application.name=config-client
    spring.profiles.active=development
    spring.config.import=optional:configserver:http://root:s3cr3t@localhost:8888
    ```

    这将连接到配置服务器<http://localhost:8888>，在启动连接时也将使用HTTP基本安全。我们还可以分别使用spring.cloud.config.username和spring.cloud.config.password属性来设置用户名和密码。

    在某些情况下，如果一个服务无法连接到配置服务器，我们可能希望它的启动失败。如果这是我们想要的行为，我们可以删除可选的: 前缀，使客户端以异常的方式停止运行。

    为了测试配置是否从我们的服务器上正确接收，以及角色值是否被注入到我们的控制器方法中，我们可以在启动客户端后简单地curl它。

    `$> curl http://localhost:8080/whoami/Mr_Pink`

    如果响应如下，我们的Spring Cloud配置服务器和它的客户端目前工作正常。

    `Hello! You're Mr_Pink and you'll become a(n) Developer...`

7. 加密和解密

    > 要求。为了使用加密强度高的密钥以及Spring的加密和解密功能，我们需要在JVM中安装 "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files"。例如，这些文件可以从Oracle下载。要安装，请按照下载中的说明进行。一些Linux发行版也通过其软件包管理器提供可安装的软件包。

    由于配置服务器支持属性值的加密和解密，我们可以使用公共存储库作为敏感数据的存储，如用户名和密码。加密值的前缀是字符串{cipher}，如果服务器被配置为使用对称密钥或密钥对，可以通过REST-调用路径"/encrypt "来生成。

    一个解密的端点也是可用的。这两个端点都接受一个包含应用程序名称和当前配置文件占位符的路径。'/*/{name}/{profile}'。这对于控制每个客户端的加密技术特别有用。然而，在它们发挥作用之前，我们必须配置一个加密密钥，我们将在下一节进行。

    > 提示：如果我们使用curl调用en-/decryption API，最好使用-data-urlencode选项（而不是-data/-d），或者将 "Content-Type "头明确设置为 "text/plain"。这可以确保正确处理加密值中的特殊字符如'+'。

    如果一个值在通过客户端获取时不能被自动解密，它的密钥就会被重命名为名称本身，前缀为'无效'。这应该可以防止使用加密的值作为密码。

    > 提示：当设置一个包含YAML文件的版本库时，我们必须用单引号包围我们的加密值和前缀值。然而，属性就不是这样了。

    1. CSRF

        默认情况下，Spring Security为所有发送到我们应用程序的请求启用[CSRF](https://www.baeldung.com/spring-security-csrf)保护。

        因此，为了能够使用/encrypt和/decrypt端点，让我们为它们禁用CSRF。

        参见 spring.cloud.config.server/SecurityConfiguration.java

    2. 密钥管理

        默认情况下，配置服务器能够以对称或非对称的方式加密属性值。

        要使用对称加密(symmetric cryptography)，我们只需在application.properties中设置 "encrypt.key" 属性为我们选择的秘密。或者，我们也可以通过环境变量ENCRYPT_KEY。

        对于非对称加密技术(asymmetric cryptography)，我们可以将'encrypt.key'设置为一个PEM编码的字符串值，或者配置一个密钥库来使用。

        由于我们的演示服务器需要一个高度安全的环境，我们将选择后者，同时用Java keytool首先生成一个新的密钥库，包括一个RSA密钥对。

        ```bash
        $> keytool -genkeypair -alias config-server-key \
            -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
            -dname 'CN=Config Server,OU=Spring Cloud,O=Baeldung' \
            -keypass my-k34-s3cr3t -keystore config-server.jks \
            -storepass my-s70r3-s3cr3t
        ```

        然后我们将创建的keystore添加到我们服务器的application.properties中，并重新运行。

        ```properties
        encrypt.keyStore.location=classpath:/config-server.jks
        encrypt.keyStore.password=my-s70r3-s3cr3t
        encrypt.keyStore.alias=config-server-key
        encrypt.keyStore.secret=my-k34-s3cr3t
        ```

        接下来，我们将查询加密端点，并将响应作为一个值添加到我们版本库的配置中。

        ```zsh
        $> export PASSWORD=$(curl -X POST --data-urlencode d3v3L \
            http://root:s3cr3t@localhost:8888/encrypt)
        $> echo "user.password={cipher}$PASSWORD" >> config-client-development.properties
        $> git commit -am 'Added encrypted password'
        $> curl -X POST http://root:s3cr3t@localhost:8888/refresh
        ```

        为了测试我们的设置是否正确，我们将修改ConfigClient类并重新启动我们的客户端。

        参见 spring.cloud.config.client/ConfigClient.java

        最后，对我们的客户端进行查询，将显示我们的配置值是否被正确解密了。

        ```bash
        $> curl http://localhost:8080/whoami/Mr_Pink
        Hello! You're Mr_Pink and you'll become a(n) Developer, \
        but only if your password is 'd3v3L'!
        ```

    3. 使用多个密钥

        如果我们想使用多个密钥进行加密和解密，比如为每个被服务的应用程序使用一个专用的密钥，我们可以在{cipher}前缀和BASE64编码的属性值之间添加另一个{name:value}形式的前缀。

        配置服务器可以理解{secret:my-crypto-secret}或{key:my-key-alias}这样的前缀，几乎开箱即用。后一个选项需要在我们的application.properties中配置一个keystore。这个钥匙库会被搜索出一个匹配的钥匙别名。比如说

        ```properties
        user.password={cipher}{secret:my-499-s3cr3t}AgAMirj1DkQC0WjRv...
        user.password={cipher}{key:config-client-key}AgAMirj1DkQC0WjRv...
        ```

        对于没有钥匙库的场景，我们必须实现一个TextEncryptorLocator类型的@Bean，它处理查找并为每个钥匙返回一个TextEncryptor-Object。

    4. 提供加密的属性

        如果我们想禁用服务器端的加密技术并在本地处理属性值的解密，我们可以在服务器的 application.properties 中加入以下内容。

        `spring.cloud.config.server.encrypt.enabled=false`

        此外，我们可以删除所有其他'encrypt.*'属性，以禁用REST端点。

8. 总结

    现在我们已经能够创建一个配置服务器，从Git仓库向客户端应用程序提供一组配置文件了。此外，我们还可以用这样的服务器做一些其他事情。

    比如说：

    - 以YAML或Properties格式而不是JSON格式来提供配置，也可以解决占位符。当在非Spring环境中使用它时，这可能很有用，因为配置并不直接映射到PropertySource。
    - 反过来提供纯文本配置文件，可以选择解决占位符。例如，这对于提供与环境相关的日志配置很有用。
    - 将配置服务器嵌入到一个应用程序中，它从Git仓库中配置自己，而不是作为一个独立的应用程序运行，为客户端服务。因此，我们必须设置一些属性和/或删除@EnableConfigServer注解，这取决于用例。
    - 让配置服务器在Spring Netflix Eureka服务发现中可用，并在配置客户端中启用自动服务器发现。如果服务器没有固定的位置，或者它的位置在移动，这就变得很重要。

    像往常一样，代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-config)上找到。

## 在Spring Cloud配置中重写远程属性的值

1. 概述

    Spring Cloud Config是Spring Cloud伞式项目的一部分。它通过集中式服务管理应用程序的配置数据，从而将其与你部署的微服务截然分开。Spring Cloud Config有自己的属性管理库，但也与Git、Consul和Eureka等开源项目集成。

    在这篇文章中，我们将看到在Spring Cloud Config中覆盖远程属性值的不同方式，Spring从2.4版本开始施加的限制，以及3.0版本的变化。在本教程中，我们将使用spring-boot 2.7.2版本。

2. 创建一个Spring配置服务器

    为了将配置文件外部化，让我们使用Spring Cloud Config创建我们的边缘服务。它被定位为一个配置文件的分发服务器。在本文中，我们将使用一个文件系统仓库。

    1. 创建配置文件

        在application.properties文件中定义的配置可与所有客户端应用程序共享。也可以为一个应用程序或一个特定的配置文件定义特定的配置。

        让我们从创建配置文件开始，该文件包含提供给我们的客户端应用程序的属性。我们将把我们的客户端应用程序命名为 "baeldung"。在spring-cloud-config-overriding-properties-server项目的 /resources/config文件夹中，让我们创建一个baeldung.properties文件。

    2. 添加属性

        让我们在baeldung.properties文件中添加一些属性，然后在我们的客户端应用程序中使用这些属性。

        让我们也在resources/config/application.properties文件中添加一个共享属性shared-property，Spring将在所有客户端中共享这个属性。

    3. Spring-Boot配置服务器应用程序

        现在，让我们创建一个Spring应用程序，为我们的配置服务。我们还需要spring-cloud-config-server这个依赖项。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        ```

        一旦安装完毕，让我们创建应用程序并启用配置服务器。

        spring.cloud.config.overridingproperties/ConfigServer.java

        让我们在应用程序的application.properties文件中添加以下属性，告诉它在8081端口启动，并加载之前定义的配置。

        ```properties
        server.port=8081
        spring.cloud.config.server.native.searchLocations=classpath:/config
        ```

        现在，让我们启动我们的服务器应用程序，激活本地配置文件并允许我们使用文件系统作为配置库。

        `mvn spring-boot:run -Drun.profiles=native`

        我们的服务器现在正在运行并为我们的配置服务。让我们验证一下我们的共享属性是否可以访问。

        ```zsh
        $ curl localhost:8081/unknownclient/default
        {
        "name": "unknownclient",
        "profiles": [
            "default"
        ],
        "label": null,
        "version": null,
        "state": null,
        "propertySources": [
            {
            "name": "classpath:/config/application.properties",
            "source": {
                "shared-property": "This property is shared accross all client applications"
            }
            }
        ]
        }
        ```

        以及特定于我们应用程序的属性。

        ```zsh
        $ curl localhost:8081/baeldung/default
        {
        "name": "baeldung",
        "profiles": [
            "default"
        ],
        "label": null,
        "version": null,
        "state": null,
        "propertySources": [
            {
            "name": "classpath:/config/baeldung.properties",
            "source": {
                "hello": "Hello Jane Doe!",
                "welcome": "Welcome Jane Doe!"
            }
            },
            {
            "name": "classpath:/config/application.properties",
            "source": {
                "shared-property": "This property is shared accross all client applications"
            }
            }
        ]
        }
        ```

        我们向服务器指出我们的应用程序的名称以及使用的配置文件，即默认。

        我们没有禁用 spring.cloud.config.server.accept-empty 属性，其默认值为 true。如果应用程序是未知的（unknownclient），配置服务器无论如何都会返回共享属性。

3. 客户端应用程序

    现在让我们创建一个客户端应用程序，在启动时加载由服务器提供的配置。

    1. 项目设置和依赖关系

        让我们在 pom.xml 中添加 spring-cloud-starter-config 依赖项来加载配置，以及 spring-boot-starter-web 来创建控制器。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        ```

    2. 创建客户端应用程序

        接下来，让我们创建客户端应用程序，从spring-cloud-config服务器读取配置。

        ```java
        @SpringBootApplication
        public class Client {
            public static void main(String[] args) {
                SpringApplication.run(Client.class, args);
            }
        }
        ```

    3. 获取配置

        让我们修改我们的application.properties文件，告诉spring-boot从我们的服务器获取配置。

        ```properties
        spring.cloud.config.name=baeldung
        spring.config.import=optional:configserver:http://localhost:8081
        ```

        我们还指定我们的应用程序的名称为 "baeldung"，以利用专用属性。

    4. 添加一个简单的控制器

        现在让我们创建一个控制器，负责显示我们配置的特定属性，以及共享属性。

        spring.cloud.config.overridingproperties/HelloController.java

        现在我们可以导航到这三个URL，以验证我们的配置是否被考虑到了。

        ```bash
        $ curl http://localhost:8080/hello
        Hello Jane Doe!
        $ curl http://localhost:8080/welcome
        Welcome Jane Doe!
        $ curl http://localhost:8080/shared
        This property is shared accross all client applications
        ```

4. 覆盖服务器上的属性

    我们可以通过修改服务器配置来覆盖为特定应用程序定义的属性。

    让我们编辑服务器的 resources/application.properties 文件以覆盖 hello 属性。

    `spring.cloud.config.server.overrides.hello=Hello Jane Doe – application.properties!`

    让我们再次测试对/hello控制器的调用，以验证重载是否被考虑在内。

    ```bash
    $ curl http://localhost:8080/hello
    Hello Jane Doe - application.properties!
    ```

    我们可以在resources/config/application.properties文件的共享配置层添加这个重载。在这种情况下，它将优先于上面定义的那个。

5. 在客户端重写属性

    自Spring Boot 2.4版以来，不再可能通过客户端应用程序的application.properties文件来覆盖属性了。

    1. 使用Spring配置文件

        不过，我们可以使用[Spring配置文件](https://www.baeldung.com/spring-profiles)。在配置文件中本地定义的属性比在服务器级别为应用程序定义的优先级更高。

        让我们为我们的客户端应用程序添加一个application-development.properties配置文件，并重写hello属性。

        `hello=Hello local property!`

        现在，让我们通过激活开发配置文件来启动我们的客户端。

        ```bash
        mvn spring-boot:run -Drun.profiles=development
        ```

        我们现在可以再次测试我们的控制器/hello，以验证重载是否正确工作。

        ```bash
        $ curl http://localhost:8080/hello
        Hello local property!
        ```

    2. 使用占位符

        我们可以使用占位符对属性进行估值。因此，服务器将提供一个默认值，它可以被客户端定义的属性所覆盖。

        让我们从服务器的resources/application.properties文件中删除hello属性的重载，并修改config/baeldung.properties中的重载，以纳入占位符的使用。

        `hello=${app.hello:Hello Jane Doe!}`

        这样，服务器提供了一个默认值，如果客户端声明了一个名为app.hello的属性，这个默认值可以被覆盖。

        让我们编辑客户端的resources/application.properties文件来添加该属性。

        `app.hello=Hello, overriden local property!`

        让我们再次测试我们的控制器hello，以验证是否正确地考虑到了超限因素。

        ```bash
        $ curl http://localhost:8080/hello
        Hello, overriden local property!
        ```

        注意，如果hello属性也是在配置文件配置中定义的，后者将优先考虑。

6. 遗留配置

    从spring-boot 2.4开始，可以使用 "legacy configuration遗留配置"。这允许我们在spring-boot 2.4版本的修改之前使用旧的属性管理系统。

    1. 加载外部配置

        在2.4版本之前，外部配置的管理是由一个引导程序来保证的。我们需要 spring-cloud-starter-bootstrap 这个依赖项。让我们把它添加到我们的 pom.xml 中。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>
        ```

        接下来，我们将在资源文件夹中创建 bootstrap.properties 文件，并配置到我们服务器的访问 URL。

        ```properties
        spring.cloud.config.name=baeldung
        spring.cloud.config.uri=http://localhost:8081
        ```

        让我们在application.properties中启用遗留配置。

        `spring.config.use-legacy-processing=true`

    2. 启用重写能力

        在服务器端，我们需要表明属性重载是可能的。让我们以这种方式修改我们的baeldung.properties文件。

        `spring.cloud.config.overrideNone=true`

        这样，外部属性就不会比应用JAR中定义的属性有优先权。

    3. 覆盖服务器的属性

        现在我们可以通过 application.properties 文件在客户端应用程序中重写 hello 属性。

        `hello=localproperty`

        让我们测试一下对我们控制器的调用。

        ```bash
        $ curl http://localhost:8080/hello
        localproperty
        ```

    4. 遗留配置的贬值

        自Spring Boot 3.0版本以来，启用遗留配置已不再可能。在这种情况下，我们应该使用上面提出的其他方法。

7. 总结

    在这篇文章中，我们看到了在Spring Cloud Config中覆盖远程属性值的不同方法。

    我们有可能从服务器上覆盖那些为特定应用程序定义的属性。也可以在客户端使用配置文件或占位符来覆盖属性。我们还研究了如何激活传统的配置以返回到旧的属性管理系统。

    像往常一样，源代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-config)上找到。

## Relevant Articles

- [x] [Quick Intro to Spring Cloud Configuration](http://www.baeldung.com/spring-cloud-configuration)
- [x] [Overriding the Values of Remote Properties in Spring Cloud Config](https://www.baeldung.com/spring-cloud-config-remote-properties-override)

## 问题

**Error**: `exception is org.eclipse.jgit.api.errors.TransportException: ssh://developer@localhost/Users/developer/git/config-repo/.git: Cannot log in at localhost:22`

环境：在 MacOS 上搭建的Git服务，且可通过 ssh 执行 git 命令，但 spring cloud 无法通过 `git.uri=ssh://developer@localhost/Users/developer/git/config-repo` 访问时报错。
