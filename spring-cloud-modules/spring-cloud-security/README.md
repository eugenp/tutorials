# Spring Cloud Security

This module contains articles about Spring Cloud Security

## An Intro to Spring Cloud Security

1. 概述

    Spring Cloud Security模块提供了与Spring Boot应用程序中基于令牌的安全有关的功能。

    具体而言，它使基于OAuth2的SSO变得更容易--支持在资源服务器之间转发令牌，以及使用嵌入式Zuul代理配置下游认证。

    在这篇快速文章中，我们将看看如何使用Spring Boot客户端应用程序、授权服务器和作为资源服务器工作的REST API来配置这些功能。

    请注意，在这个例子中，我们只有一个使用SSO的客户端应用程序来展示云安全功能--但在一个典型的场景中，我们至少会有两个客户端应用程序来证明单点登录SSO的必要性。

2. 快速启动一个云安全应用程序

    让我们先在一个Spring Boot应用程序中配置SSO。

    首先，我们需要添加[spring-cloud-starter-oauth2](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-cloud-starter-oauth2%22)依赖项。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
        <version>2.2.2.RELEASE</version>
    </dependency>
    ```

    这也将带来spring-cloud-starter-security的依赖。

    我们可以为我们的网站配置任何社交网站作为Auth服务器，也可以使用我们自己的服务器。在我们的案例中，我们选择了后者，并配置了一个作为授权服务器的应用程序 - 它被部署在本地<http://localhost:7070/authserver>。

    我们的授权服务器使用JWT令牌。

    此外，为了让任何客户端能够检索到用户的证书，我们需要将运行在9000端口的资源服务器配置为一个能够提供这些证书的端点。

    在这里，我们已经配置了一个/user端点，它可以在<http://localhost:9000/user>。

    关于如何设置授权服务器和资源服务器的更多细节，请查看我们[之前的文章](https://www.baeldung.com/rest-api-spring-oauth2-angular)。

    我们现在可以在我们的客户端应用程序的配置类中添加注解。

    ```java
    @Configuration
    public class SiteSecurityConfigurer {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // ...   
            http.oauth2Login();    
            // ... 
        }
    }
    ```

    任何需要认证的请求都将被重定向到授权服务器。为了使其发挥作用，我们还必须定义服务器属性。

    ```yaml
    spring:
    security:
        oauth2:
        client:
            registration:
            baeldung:
                client-id: authserver
                client-secret: passwordforauthserver
                authorization-grant-type: authorization_code
                redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            provider:
            baeldung:
                token-uri: http://localhost:7070/authserver/oauth/token
                authorization-uri: http://localhost:7070/authserver/oauth/authorize
                user-info-uri: http://localhost:9000/user
    ```

    注意，我们需要在classpath里有spring-boot-starter-security，才能发现上述配置的工作。

3. 中继访问令牌

    在中继(relaying)令牌时，OAuth2客户端会将其收到的OAuth2令牌转发到发出的资源请求中。

    Spring Security公开了一个OAuth2AuthorizedClientService，这对创建RestTemplate拦截器很有用；基于此，我们可以在客户端应用程序中创建我们自己的RestTemplate。

    ```java
    @Bean
    public RestOperations restTemplate(OAuth2AuthorizedClientService clientService) {
        return new RestTemplateBuilder().interceptors((ClientHttpRequestInterceptor) 
            (httpRequest, bytes, execution) -> {
            OAuth2AuthenticationToken token = 
            OAuth2AuthenticationToken.class.cast(SecurityContextHolder.getContext()
                .getAuthentication());
            OAuth2AuthorizedClient client = 
                clientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), 
                token.getName());
                httpRequest.getHeaders()
                    .add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                        .getTokenValue());
            return execution.execute(httpRequest, bytes);
        }).build();
    }
    ```

    一旦我们配置了Bean，上下文将把访问令牌转发给所请求的服务，如果令牌过期，也将刷新它。

4. 使用RestTemplate转发OAuth令牌

    我们之前在我们的客户端应用程序中定义了一个RestTemplate类型的restOperations bean。因此，我们可以使用RestTemplate的getForObject()方法，从我们的客户端向受保护的资源服务器发送一个带有必要令牌的请求。

    首先，让我们在我们的资源服务器中定义一个需要认证的端点。

    ```java
    @GetMapping("/person")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Person personInfo(){        
        return new Person("abir", "Dhaka", "Bangladesh", 29, "Male");       
    }    
    ```

    这是一个简单的REST端点，返回一个Person对象的JSON表示。

    现在，我们可以使用getForObject()方法从客户端应用程序发送一个请求，该方法将把令牌转发给资源服务器。

    ```java
    @Autowired
    private RestOperations restOperations;

    @GetMapping("/personInfo")
    public ModelAndView person() { 
        ModelAndView mav = new ModelAndView("personinfo");
        String personResourceUrl = "http://localhost:9000/person";
        mav.addObject("person", 
        restOperations.getForObject(personResourceUrl, String.class));       
        return mav;
    }
    ```

5. 为令牌中继配置Zuul

    如果我们想把令牌中继到代理服务的下游，我们可以使用Spring Cloud Zuul嵌入式反向代理。

    首先，我们需要添加Maven依赖项，以便与Zuul一起工作。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>
    ```

    接下来，我们需要将@EnableZuulProxy注解添加到客户端应用程序的配置类中。

    ```java
    @EnableZuulProxy
    @Configuration
    public class SiteSecurityConfigurer {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // ...   
            http.oauth2Login();    
            // ... 
        }
    }
    ```

    剩下的就是把Zuul的配置属性添加到我们的application.yml文件中。

    ```yaml
    zuul:
    sensitiveHeaders: Cookie,Set-Cookie  
    routes:
        resource:
        path: /api/**
        url: http://localhost:9000
        user: 
        path: /user/**
        url: http://localhost:9000/user
    ```

    任何来到客户端应用程序的/api端点的请求将被重定向到资源服务器的URL。我们还需要提供用户凭证端点的URL。

6. 总结

    在这篇快速文章中，我们探讨了如何使用Spring Cloud Security与OAuth2和Zuul来配置安全的授权和资源服务器，以及如何使用RestTemplate和嵌入式Zuul代理在服务器之间转发OAuth2令牌。

    像往常一样，代码可以在[GitHub上](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-security)找到。

## Relevant Articles

- [x] [An Intro to Spring Cloud Security](http://www.baeldung.com/spring-cloud-security)

## Update

2023/01/31
