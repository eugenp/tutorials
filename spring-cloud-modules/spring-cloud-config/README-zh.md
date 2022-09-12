# Spring Cloud 配置快速入门

1.概述

Spring Cloud Config 是 Spring 的客户端/服务器方法，用于跨多个应用程序和环境存储和提供分布式配置。

此配置存储理想地在 Git 版本控制下进行版本控制，并且可以在应用程序运行时进行修改。 虽然它非常适合使用所有受支持的配置文件格式以及 Environment、[PropertySource or @Value](https://www.baeldung.com/properties-with-spring) 等结构的 Spring 应用程序，但它可以在运行任何编程语言的任何环境中使用。

在本教程中，我们将重点介绍如何设置 Git 支持的配置服务器，在简单的 REST 应用程序服务器中使用它，以及设置包括加密属性值的安全环境。

2.项目设置和依赖

首先，我们将创建两个新的 Maven 项目。 服务器项目依赖于 [spring-cloud-config-server](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-config-server%22) 模块，以及 [spring-boot-starter-security](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.boot%22%20AND%20a%3A%22spring-boot-starter-security%22) 和 [spring-boot-starter-web](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.boot%22%20AND%20a%3A%22spring-boot-starter-web%22) 启动包。

但是，对于客户端项目，我们只需要 spring-cloud-starter-config 和 spring-boot-starter-web 模块。

3.实现配置服务器 Config-Server

应用程序的主要部分是一个配置类，更具体地说是一个@SpringBootApplication，它通过自动配置注解@EnableConfigServer 引入所有必需的设置。

现在我们需要配置我们的服务器正在监听的服务器端口和一个 Git-url，它提供了我们版本控制的配置内容。后者可以与 http、ssh 或本地文件系统上的简单文件等协议一起使用。

> 提示：如果计划使用多个指向同一个配置存储库的配置服务器实例，我们可以配置服务器以将我们的存储库克隆到本地临时文件夹中。但请注意具有双重身份验证的私有存储库；他们很难处理！在这种情况下，在本地文件系统上克隆它们并使用副本会更容易。

还有一些用于配置 repository-url 的占位符变量和搜索模式；但是，这超出了我们文章的范围。如果您有兴趣了解更多信息，可以从官方文档开始。

我们还需要在 /spring-cloud-config-server/resources/application.properties 中为 Basic-Authentication 设置用户名和密码，以避免在每次应用程序重新启动时自动生成密码。

```properties
server.port=8888
spring.cloud.config.server.git.uri=ssh://localhost/config-repo
spring.cloud.config.server.git.clone-on-start=true
spring.security.user.name=root
spring.security.user.password=s3cr3t
```

> 通过 ssh://localhost/config-repo 无法访问 MacOS 上建的Git服务。直接指向 Files Path：`spring.cloud.config.server.git.uri=/Users/wangkan/git/config-repo`，Windows 用户应确保在复制路径时使用 / 而不是 \。

也可使用属性 spring.cloud.config.server.native.search-locations 获取本地存储配置文件的位置。 将值替换为文件系统上将保存这些文件的文件夹。 例如，文件://${user.home}/config。

`spring.cloud.config.server.native.search-locations=/path/to/config/folder`

4.作为配置存储的 Git 存储库

为了完成我们的服务器，我们必须在配置的 url 下初始化一个 Git 存储库，创建一些新的属性文件，并用一些值填充它们。

配置文件的名称像普通的 Spring application.properties 一样组成，但不是使用单词 “application”，而是使用客户端的配置名称，例如属性“spring.application.name”的值， 后跟一个破折号和活动配置文件。 例如：

```bash
$> git init
$> echo 'user.role=Developer' > config-client-development.properties
$> cat 'microservice-client.value=10' > config-client-development.properties
$> echo 'user.role=User'      > config-client-production.properties
$> git add .
$> git commit -m 'Initial config-client properties'
```

properties 文件存放在 /spring-cloud-config-client/ 下。

> 故障排除：如果我们遇到与 ssh 相关的身份验证问题，我们可以在 ssh 服务器上仔细检查 ~/.ssh/known_hosts 和 ~/.ssh/authorized_keys。

5.查询配置

现在我们可以启动我们的服务器了。 可以使用以下路径查询服务器提供的 Git 支持的配置 API：

```txt
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

{label} 占位符表示 Git 分支，{application} 表示客户端的应用程序名称，{profile} 表示客户端当前活动的应用程序配置文件。

因此，我们可以通过以下方式检索在分支 master 的开发配置文件下运行的计划配置客户端的配置：

```bash
$> curl http://root:s3cr3t@localhost:8888/config-client/development/master
{"name":"config-client","profiles":["development"],"label":"master","version":"572d30a23476560d080bd0bcec65cdabcfc927c2","state":null,"propertySources":[{"name":"/Users/wangkan/git/config-repo/config-client-development.properties","source":{"user.role":"Developer"}}]}%
```

6.客户端实现

接下来，让我们处理客户。 这将是一个非常简单的客户端应用程序，由一个带有一个 GET 方法的 REST 控制器组成。

见 spring.cloud.config.client.ConfigClient.java

```java
@SpringBootApplication
@RestController
public class ConfigClient {
    
    @Value("${user.role}")
    private String role;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient.class, args);
    }

    @GetMapping(value = "/whoami/{username}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello! You're %s and you'll become a(n) %s...\n", username, role);
    }
}
```

要获取我们的服务器，必须将配置放在 application.properties 文件中。 Spring Boot 2.4 引入了一种使用 spring.config.import 属性加载配置数据的新方法，现在这是绑定到 Config Server 的默认方式。

除了应用程序名称之外，我们还将活动配置文件和连接详细信息放在我们的 /spring-cloud-config-client/resources/application.properties 中：

```properties
spring.application.name=config-client
spring.profiles.active=development
spring.config.import=optional:configserver:http://root:s3cr3t@localhost:8888
```

这将连接到位于 <http://localhost:8888> 的配置服务器，并且在启动连接时还将使用 HTTP 基本安全性。 我们也可以分别使用 spring.cloud.config.username 和 spring.cloud.config.password 属性分别设置用户名和密码。

在某些情况下，如果服务无法连接到配置服务器，我们可能希望它的启动失败。 如果这是所需的行为，我们可以删除可选的：前缀以使客户端停止并出现异常（prefix to make the client halt with an exception）。

为了测试配置是否从我们的服务器正确接收，并且角色值被注入到我们的控制器方法中，我们只需在启动客户端后 curl 它：

`$> curl http://localhost:8080/whoami/Mr_Pink`

如果响应如下，我们的 Spring Cloud Config Server 和它的客户端现在工作正常：

`Hello! You're Mr_Pink and you'll become a(n) Developer...`

7.加解密

要求：要使用加密强密钥以及 Spring 加密和解密功能，我们需要在我们的 JVM 中安装“Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files”。例如，这些可以从 [Oracle](https://www.oracle.com/java/technologies/javase-jce8-downloads.html) 下载。要安装，请按照下载中包含的说明进行操作。一些 Linux 发行版还通过其包管理器提供可安装的包。

由于配置服务器支持属性值的加密和解密，我们可以使用公共存储库作为敏感数据的存储，如用户名和密码。加密值以字符串 {cipher} 为前缀，如果服务器配置为使用对称密钥或密钥对，则可以通过对路径 '/encrypt' 的 REST-call 调用生成。

还可以使用解密端点。两个端点都接受包含应用程序名称及其当前配置文件的占位符的路径：'/*/{name}/{profile}'。这对于控制每​​个客户端的加密特别有用。但是，在它们有用之前，我们必须配置一个加密密钥，我们将在下一节中进行。

> 提示：如果我们使用 curl 调用 en-/decryption API，最好使用 –data-urlencode 选项（而不是 –data/-d ），或者将 'Content-Type' 标头显式设置为 'text/plain' 。这可确保正确处理加密值中的“+”等特殊字符。

如果一个值在通过客户端获取时无法自动解密，则其密钥将使用名称本身重命名，并以单词 'invalid' 为前缀。这应该防止使用加密值作为密码。

> 提示：在设置包含 YAML 文件的存储库时，我们必须用单引号将我们的加密值和前缀值括起来。但是，属性并非如此。

7.1.CSRF

默认情况下，Spring Security 为发送到我们应用程序的所有请求启用 CSRF(Cross-site request forgery) 保护。

因此，为了能够使用 /encrypt 和 /decrypt 端点，让我们为它们禁用 CSRF：

```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
          .ignoringAntMatchers("/encrypt/**")
          .ignoringAntMatchers("/decrypt/**");
        super.configure(http);
    }
}
```

7.2.密钥管理

默认情况下，配置服务器能够以对称或非对称方式加密属性值。

要使用对称加密，我们只需将 application.properties 中的属性 'encrypt.key' 设置为我们选择的 secret 。 或者，我们可以传入环境变量 ENCRYPT_KEY。

对于非对称加密，我们可以将 'encrypt.key' 设置为 PEM 编码的字符串值或配置要使用的密钥库。

由于我们的演示服务器需要一个高度安全的环境，我们将选择后一个选项，并首先使用 Java 密钥工具生成一个新的密钥库，包括一个 RSA 密钥对：

```bash
$> keytool -genkeypair -alias config-server-key \
       -keyalg RSA -keysize 4096 -sigalg SHA512withRSA \
       -dname 'CN=Config Server,OU=Spring Cloud,O=Baeldung' \
       -keypass my-k34-s3cr3t -keystore config-server.jks \
       -storepass my-s70r3-s3cr3t
```

然后我们将创建的密钥库添加到我们服务器的 /spring-cloud-config-server/resources/application.properties 并重新运行它：

```properties
encrypt.keyStore.location=classpath:/config-server.jks
encrypt.keyStore.password=my-s70r3-s3cr3t
encrypt.keyStore.alias=config-server-key
encrypt.keyStore.secret=my-k34-s3cr3t
```

接下来，我们将查询加密端点，并将响应作为值添加到我们存储库中的配置中：

```bash
$> export PASSWORD=$(curl -X POST --data-urlencode d3v3L http://root:s3cr3t@localhost:8888/encrypt)
$> echo "user.password={cipher}$PASSWORD" >> config-client-development.properties
$> git commit -am 'Added encrypted password'
$> curl -X POST http://root:s3cr3t@localhost:8888/refresh
```

为了测试我们的设置是否正常工作，我们将修改 spring.cloud.config.client.ConfigClient.java 类并重新启动我们的客户端：

```java
public class ConfigClient {
    ... 
    @Value("${user.password}")
    private String password;
    ...
    @GetMapping(value = "/whoami/{username}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello %s! and you'll become are a(n) %s, " + "but only your password is '%s'.\n",
                username, role, password);
    }
}
```

最后，针对我们的客户端的查询将显示我们的配置值是否被正确解密：

```bash
$> curl http://localhost:8080/whoami/Mr_Pink
Hello Mr_Pink! and you'll become are a(n) Developer, but only your password is 'd3v3L'.
```

7.3.使用多个键

如果我们想使用多个密钥进行加密和解密，例如为每个服务的应用程序使用一个专用密钥，我们可以在 {cipher} 前缀和 BASE64 编码的属性值之间添加另一个 {name:value} 形式的前缀。

配置服务器几乎可以直接理解 {secret:my-crypto-secret} 或 {key:my-key-alias} 之类的前缀。后一个选项需要在我们的 application.properties 中配置一个密钥库。搜索此密钥库以查找匹配的密钥别名。

The config server understands prefixes like {secret:my-crypto-secret} or {key:my-key-alias} almost out-of-the-box. The latter option needs a configured keystore in our application.properties. This keystore is searched for a matching key alias.

例如：

```txt
user.password={cipher}{secret:my-499-s3cr3t}AgAMirj1DkQC0WjRv...
user.password={cipher}{key:config-client-key}AgAMirj1DkQC0WjRv...
```

对于没有密钥库的场景，我们必须实现一个 TextEncryptorLocator 类型的 @Bean，它处理查找并为每个密钥返回一个 TextEncryptor-Object。

7.4.提供加密属性

如果我们想禁用服务器端加密并在本地处理属性值的解密，我们可以将以下内容放入服务器的 application.properties 中：

`spring.cloud.config.server.encrypt.enabled=false`

此外，我们可以删除所有其他“encrypt.*”属性以禁用 REST 端点。

8.结论

现在我们可以创建一个配置服务器，将一组配置文件从 Git 存储库提供给客户端应用程序。我们还可以用这样的服务器做一些其他的事情。

例如：

- 以 YAML 或属性格式而不是 JSON 提供配置，也解析了占位符。在非 Spring 环境中使用它时，这可能很有用，其中配置不直接映射到 PropertySource。
- 依次提供纯文本配置文件，可选择使用已解析的占位符。例如，这对于提供依赖于环境的日志记录配置很有用。
- 将配置服务器嵌入到应用程序中，它从 Git 存储库进行自我配置，而不是作为服务客户端的独立应用程序运行。因此，我们必须设置一些属性和/或我们必须删除 @EnableConfigServer 注释，这取决于用例。
- 在 Spring Netflix Eureka 服务发现中使配置服务器可用，并在配置客户端中启用自动服务器发现。如果服务器没有固定位置或在其位置移动，这将变得很重要。

## 问题

**Error**: `exception is org.eclipse.jgit.api.errors.TransportException: ssh://developer@localhost/Users/developer/git/config-repo/.git: Cannot log in at localhost:22`

环境：在 MacOS 上搭建的Git服务，且可通过 ssh 执行 git 命令，但 spring cloud 无法通过 `git.uri=ssh://developer@localhost/Users/developer/git/config-repo` 访问时报错。

## Relevant Articles

- [x] [Quick Intro to Spring Cloud Configuration](http://www.baeldung.com/spring-cloud-configuration)
