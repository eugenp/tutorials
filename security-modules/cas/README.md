# CAS

在本教程中，我们将了解Apereo中央身份验证服务（Central Authentication Service, CAS），并了解Spring引导服务如何使用它进行身份验证。[CAS](https://apereo.github.io/cas/6.1.x/index.html)是一种企业单点登录（[SSO](https://www.baeldung.com/cs/sso-guide)）解决方案，也是开源的。

什么是SSO？当你用相同的凭据登录不同的网站时，这就是单点登录。我们将通过设置CAS服务器和Spring Boot应用程序来演示这一点。Spring Boot应用程序将使用CAS进行身份验证。

## CAS服务器设置

### CAS安装和依赖性

服务器使用Maven（Gradle）War Overlay样式来简化设置和部署：

`git clone https://github.com/apereo/cas-overlay-template.git cas-server`

此命令将把 cas-overlay-template 克隆到 cas-server 目录中。

我们将介绍的一些方面包括JSON服务注册和JDBC数据库连接。因此，我们将把它们的模块添加到构建的依赖项部分。渐变文件：

```xml
compile "org.apereo.cas:cas-server-support-json-service-registry:${casServerVersion}"
compile "org.apereo.cas:cas-server-support-jdbc:${casServerVersion}"
```

让我们确保检查[casServer](https://github.com/apereo/cas/releases)的最新版本。

### CAS服务器配置

在启动CAS服务器之前，我们需要添加一些基本配置。让我们首先在这个文件夹中创建一个 cas-server/src/main/resources 文件夹。随后将创建应用程序。文件夹中的属性：

```properties
server.port=8443
spring.main.allow-bean-definition-overriding=true
server.ssl.key-store=classpath:/etc/cas/thekeystore
server.ssl.key-store-password=changeit
```

让我们继续创建上面配置中引用的密钥存储文件。首先，我们需要在cas-server/src/main/resources中创建文件夹/etc/cas和/etc/cas/config。

然后，我们需要将目录更改为cas-server/src/main/resources/etc/cas，并运行命令生成密钥库：

`keytool -genkey -keyalg RSA -alias thekeystore -keystore thekeystore -storepass changeit -validity 360 -keysize 2048`

为了避免SSL握手错误(SSL handshake error)，我们应该使用localhost作为名字和姓氏的值。我们也应该使用相同的组织名称和单位。此外，我们需要将密钥库导入到JDK/JRE中，我们将使用它来运行我们的客户端应用程序：

`keytool -importkeystore -srckeystore thekeystore -destkeystore $JAVA11_HOME/jre/lib/security/cacerts`

源和目标密钥库的密码为changeit。在Unix系统上，我们可能必须以admin（sudo）权限运行此命令。导入后，我们应该重新启动正在运行的所有Java实例或重新启动系统。

我们使用JDK11是因为它是CAS版本6.1.x所必需的。此外，我们定义了指向其主目录的环境变量`$JAVA11_HOME`。我们现在可以启动CAS服务器：

`./gradlew run -Dorg.gradle.java.home=$JAVA11_HOME`

当应用程序启动时，我们将看到终端上打印的“READY”，服务器将在<https://localhost:8443>.

### CAS服务器用户配置

我们还无法登录，因为我们尚未配置任何用户。CAS有不同的[配置管理](https://apereo.github.io/cas/6.1.x/configuration/Configuration-Server-Management.html)方法，包括独立模式。让我们创建一个配置文件夹cas-server/src/main/resources/etc/cas/config，在其中创建一个属性文件cas.properties。现在，我们可以在属性文件中定义一个静态用户：

`cas.authn.accept.users=casuser::Mellon`

为了使设置生效，我们必须将配置文件夹的位置传递给CAS服务器。让我们更新tasks.gradle，以便我们可以从命令行将位置作为JVM参数传递：

```bash
task run(group: "build", description: "Run the CAS web application in embedded container mode") {
    dependsOn 'build'
    doLast {
        def casRunArgs = new ArrayList<>(Arrays.asList(
          "-server -noverify -Xmx2048M -XX:+TieredCompilation -XX:TieredStopAtLevel=1".split(" ")))
        if (project.hasProperty('args')) {
            casRunArgs.addAll(project.args.split('\\s+'))
        }
        javaexec {
            main = "-jar"
            jvmArgs = casRunArgs
            args = ["build/libs/${casWebApplicationBinaryName}"]
            logger.info "Started ${commandLine}"
        }
    }
}
```

然后保存文件并运行：

```zsh
./gradlew run
  -Dorg.gradle.java.home=$JAVA11_HOME
  -Pargs="-Dcas.standalone.configurationDirectory=/cas-server/src/main/resources/etc/cas/config"
```

请注意cas.standalone的值，configurationDirectory是绝对路径。我们现在可以去 <https://localhost:8443> 并使用用户名casuser和密码Mellon登录。

## CAS客户端设置

我们将使用Spring Initializer生成Spring Boot客户端应用程序。它将具有Web、Security、Freemarker和DevTools依赖关系。此外，我们还将[Spring Security CAS](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22org.springframework.security%22%20a%3A%22spring-security-cas%22)模块的依赖关系添加到其pom.xml中：

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-cas</artifactId>
    <versionId>5.3.0.RELEASE</versionId>
</dependency>
```

最后，让我们添加以下Spring Boot属性来配置应用程序：

```properties
server.port=8900
spring.freemarker.suffix=.ftl
```

## CAS服务器服务注册

客户端应用程序必须在任何身份验证之前向CAS服务器注册。CAS服务器支持使用YAML、JSON、MongoDB和LDAP客户端注册表。

在本教程中，我们将使用JSON Service Registry方法。让我们创建另一个文件夹cas-server/src/main/resources/etc/cas/services，这个文件夹将存放服务注册表JSON文件。

我们将创建一个包含客户端应用程序定义的JSON文件。文件名casSecuredApp-8900.json遵循serviceName-Id.json模式：

```json
{
  "@class" : "org.apereo.cas.services.RegexRegisteredService",
  "serviceId" : "http://localhost:8900/login/cas",
  "name" : "casSecuredApp",
  "id" : 8900,
  "logoutType" : "BACK_CHANNEL",
  "logoutUrl" : "http://localhost:8900/exit/cas"
}
```

serviceId属性定义了客户端应用程序的正则表达式URL模式。模式应该与客户端应用程序的URL匹配。

id属性应该是唯一的。换句话说，不应该有两个或多个具有相同id的服务注册到同一个CAS服务器。重复id将导致冲突和配置被覆盖。

我们还将注销类型配置为BACK_CHANNEL，将URL配置为 <http://localhost:8900/exit/cas> 这样我们以后就可以进行单次注销。
在CAS服务器可以使用我们的JSON配置文件之前，我们必须在CAS.properties中启用JSON注册表：

```properties
cas.serviceRegistry.initFromJson=true
cas.serviceRegistry.json.location=classpath:/etc/cas/services
```

## CAS客户端单点登录配置

我们的下一步是配置Spring Security以与CAS服务器配合使用。我们还应该检查[整个交互流](https://docs.spring.io/spring-security/site/docs/5.2.2.RELEASE/reference/html5/#cas-sequence)，称为CAS序列。

让我们将以下bean配置添加到Spring Boot应用程序的CasSecuredApplication类中：

```java
@Bean
public CasAuthenticationFilter casAuthenticationFilter(
  AuthenticationManager authenticationManager,
  ServiceProperties serviceProperties) throws Exception {
    CasAuthenticationFilter filter = new CasAuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.setServiceProperties(serviceProperties);
    return filter;
}

@Bean
public ServiceProperties serviceProperties() {
    logger.info("service properties");
    ServiceProperties serviceProperties = new ServiceProperties();
    serviceProperties.setService("http://cas-client:8900/login/cas");
    serviceProperties.setSendRenew(false);
    return serviceProperties;
}

@Bean
public TicketValidator ticketValidator() {
    return new Cas30ServiceTicketValidator("https://localhost:8443");
}

@Bean
public CasAuthenticationProvider casAuthenticationProvider(
  TicketValidator ticketValidator,
  ServiceProperties serviceProperties) {
    CasAuthenticationProvider provider = new CasAuthenticationProvider();
    provider.setServiceProperties(serviceProperties);
    provider.setTicketValidator(ticketValidator);
    provider.setUserDetailsService(
      s -> new User("test@test.com", "Mellon", true, true, true, true,
      AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
    provider.setKey("CAS_PROVIDER_LOCALHOST_8900");
    return provider;
}
```

ServiceProperties bean与casSecuredApp-8900.json中的serviceId具有相同的URL。这一点很重要，因为它将此客户端标识给CAS服务器。

ServiceProperties的sendRenew属性设置为false。这意味着用户只需要向服务器提供一次登录凭据。

AuthenticationEntryPoint bean将处理身份验证异常。因此，它将用户重定向到CAS服务器的登录URL进行身份验证。

总之，身份验证流程如下：

1. 用户试图访问安全页面，这会触发身份验证异常
2. 该异常触发AuthenticationEntryPoint。作为响应，AuthenticationEntryPoint会将用户带到CAS服务器登录页面–<https://localhost:8443/login>
3. 成功身份验证后，服务器将使用票证重定向回客户端
4. CasAuthenticationFilter将接收重定向并调用CasAutheenticationProvider
5. CasAuthenticationProvider将使用TicketValidator在CAS服务器上确认提交的票据
6. 如果票证有效，用户将重定向到请求的安全URL

最后，让我们配置HttpSecurity来保护WebSecurityConfig中的一些路由。在此过程中，我们还将添加用于异常处理的身份验证入口点：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers( "/secured", "/login") 
      .authenticated() 
      .and().exceptionHandling() 
      .authenticationEntryPoint(authenticationEntryPoint());
}
```

## CAS客户端单注销配置

到目前为止，我们已经处理了单点登录；现在让我们考虑一下CAS单点注销（SLO）。

使用CAS管理用户身份验证的应用程序可以从两个位置注销用户：

- 客户端应用程序可以在本地注销用户，这不会影响用户在使用同一CAS服务器的其他应用程序中的登录状态
- 客户端应用程序还可以让用户从CAS服务器注销–这将导致用户从连接到同一CAS服务器的所有其他客户端应用程序注销。

我们将首先在客户端应用程序上进行注销，然后将其扩展到在CAS服务器上进行单次注销。

为了清楚地了解幕后的情况，我们将创建一个logout()方法来处理本地注销。成功后，它会将我们重定向到一个带有单次注销链接的页面：

```java
@GetMapping("/logout")
public String logout(
  HttpServletRequest request, 
  HttpServletResponse response, 
  SecurityContextLogoutHandler logoutHandler) {
    Authentication auth = SecurityContextHolder
      .getContext().getAuthentication();
    logoutHandler.logout(request, response, auth );
    new CookieClearingLogoutHandler(
      AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY)
      .logout(request, response, auth);
    return "auth/logout";
}
```

在单次注销过程中，CAS服务器将首先使用户的票证过期，然后向所有注册的客户端应用发送异步请求。接收到该信号的每个客户端应用程序将执行本地注销。因此，一旦实现注销的目标，就会导致到处注销。

话虽如此，让我们将一些bean配置添加到我们的客户端应用程序中。具体而言，在CasSecuredApplication中：

```java
@Bean
public SecurityContextLogoutHandler securityContextLogoutHandler() {
    return new SecurityContextLogoutHandler();
}

@Bean
public LogoutFilter logoutFilter() {
    LogoutFilter logoutFilter = new LogoutFilter("https://localhost:8443/logout",
      securityContextLogoutHandler());
    logoutFilter.setFilterProcessesUrl("/logout/cas");
    return logoutFilter;
}

@Bean
public SingleSignOutFilter singleSignOutFilter() {
    SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
    singleSignOutFilter.setCasServerUrlPrefix("https://localhost:8443");
    singleSignOutFilter.setLogoutCallbackPath("/exit/cas");
    singleSignOutFilter.setIgnoreInitConfiguration(true);
    return singleSignOutFilter;
}
```

logoutFilter将拦截到`/logout/cas`的请求，并将应用程序重定向到cas服务器。SingleSignOutFilter将拦截来自CAS服务器的请求，并执行本地注销。

## 将CAS服务器连接到数据库

我们可以将CAS服务器配置为从MySQL数据库读取凭据。我们将使用在本地机器上运行的MySQL服务器的测试数据库。让我们更新cas-server/src/main/resources/etc/cas/config/cas.properties：

```properties
cas.authn.accept.users=

cas.authn.jdbc.query[0].sql=SELECT * FROM users WHERE email = ?
cas.authn.jdbc.query[0].url=
  jdbc:mysql://127.0.0.1:3306/test?
  useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
cas.authn.jdbc.query[0].dialect=org.hibernate.dialect.MySQLDialect
cas.authn.jdbc.query[0].user=root
cas.authn.jdbc.query[0].password=root
cas.authn.jdbc.query[0].ddlAuto=none
cas.authn.jdbc.query[0].driverClass=com.mysql.cj.jdbc.Driver
cas.authn.jdbc.query[0].fieldPassword=password
cas.authn.jdbc.query[0].passwordEncoder.type=NONE
```

我们将cas.authn.accept.users设置为空白。这将停用CAS服务器对静态用户存储库的使用。

根据上面的SQL，用户的凭据存储在用户表中。电子邮件列表示用户的主体（用户名）。

请确保检查[支持的数据库、可用驱动程序和方言列表](https://apereo.github.io/cas/6.1.x/installation/JDBC-Drivers.html)。我们还将密码编码器类型设置为NONE。也可以使用其他[加密机制](https://apereo.github.io/cas/6.1.x/configuration/Configuration-Properties-Common.html#password-encoding)及其特殊属性。

请注意，CAS服务器数据库中的主体必须与客户端应用程序中的主体相同。

让我们更新CasAuthenticationProvider，使其具有与CAS服务器相同的用户名：

```java
@Bean
public CasAuthenticationProvider casAuthenticationProvider() {
    CasAuthenticationProvider provider = new CasAuthenticationProvider();
    provider.setServiceProperties(serviceProperties());
    provider.setTicketValidator(ticketValidator());
    provider.setUserDetailsService(
      s -> new User("test@test.com", "Mellon", true, true, true, true,
      AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
    provider.setKey("CAS_PROVIDER_LOCALHOST_8900");
    return provider;
}
```

CasAuthenticationProvider不使用密码进行身份验证。尽管如此，其用户名必须与CAS服务器的用户名匹配，才能成功进行身份验证。CAS服务器要求MySQL服务器在端口3306的本地主机上运行。用户名和密码应为root。

再次重新启动CAS服务器和Spring Boot应用程序。然后使用新凭据进行身份验证。

## 代码说明

该模块由2个子模块组成：

1. “cas-server”-它需要JDK11，并使用Gradle War Overlay样式来简化设置和部署。

2. “cas-secured-app”-一个基于Maven的Springboot应用程序

## 结论

我们已经研究了如何将CAS SSO与Spring安全性和许多涉及的配置文件一起使用。CAS SSO还有许多其他方面是可配置的。从主题和协议类型到身份验证策略。

这些和其他都在[文档](https://apereo.github.io/cas/6.1.x/configuration/Configuration-Properties-Common.html)中。

### Relevant Articles

- [x] [CAS SSO With Spring Security](https://www.baeldung.com/spring-security-cas-sso)
- [ ] [Code Analysis with SonarQube](https://www.baeldung.com/sonar-qube)
