# Spring MVC Basics

This module contains articles about the basics of Spring MVC. Articles about more specific areas of Spring MVC have
their own module.

## Spring MVC Tutorial

1. 概述

    这是一个简单的Spring MVC教程，展示了如何设置Spring MVC项目，包括基于Java的配置和XML的配置。

    Spring MVC项目的Maven依赖项在Spring MVC依赖项一文中有详细描述。

2. 什么是Spring MVC？

    顾名思义，它是Spring框架中处理模型-视图-控制器或MVC模式的一个模块。它结合了MVC模式的所有优点和Spring的便利性。

    Spring[使用DispatcherServlet实现了MVC与前置控制器模式](https://www.baeldung.com/spring-controllers#Overview)。

    简而言之，DispatcherServlet充当主控制器，将请求路由到它们的目的地。模型只不过是我们应用程序的数据，而视图则由各种模板引擎中的任何一个来表示。

3. 使用Java配置Spring MVC

    为了通过Java配置类启用Spring MVC支持，我们只需添加@EnableWebMvc注解。

    这将设置我们对MVC项目所需的基本支持，如注册控制器和映射、类型转换器、验证支持、消息转换器和异常处理。

    如果我们想定制这个配置，我们需要实现WebMvcConfigurer接口。

    ```java
    @EnableWebMvc
    @Configuration
    public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
    ```

    在这个例子中，我们注册了一个ViewResolver bean，它将从/WEB-INF/view目录中返回.jsp视图。

    这里非常重要的是，我们可以注册视图控制器，使用ViewControllerRegistry在URL和视图名称之间创建一个直接的映射。这样一来，两者之间就不需要任何控制器了。

    如果我们想同时定义和扫描控制器类，我们可以在包含控制器的包中添加@ComponentScan注解。

    ```java
    @EnableWebMvc
    @Configuration
    @ComponentScan(basePackages = { "com.baeldung.web.controller" })
    public class WebConfig implements WebMvcConfigurer {
        // ...
    }
    ```

    为了启动一个加载该配置的应用程序，我们还需要一个初始化类。

    ```java
    public class MainWebAppInitializer implements WebApplicationInitializer {
        @Override
        public void onStartup(final ServletContext sc) throws ServletException {

            AnnotationConfigWebApplicationContext root = 
            new AnnotationConfigWebApplicationContext();
            
            root.scan("com.baeldung");
            sc.addListener(new ContextLoaderListener(root));

            ServletRegistration.Dynamic appServlet = 
            sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
            appServlet.setLoadOnStartup(1);
            appServlet.addMapping("/");
        }
    }
    ```

    > 注意，对于早于Spring 5的版本，我们必须使用 WebMvcConfigurerAdapter 类而不是接口。

4. 使用XML配置的Spring MVC

    取代上面的Java配置，我们也可以使用一个纯粹的XML配置。

    ```xml
    <context:component-scan base-package="com.baeldung.web.controller" />
    <mvc:annotation-driven />    

    <bean id="viewResolver" 
        class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/view/" />
            <property name="suffix" value=".jsp" />
        </bean>

        <mvc:view-controller path="/" view-name="index" />

    </beans>
    ```

    如果我们想使用一个纯粹的XML配置，我们还需要添加一个web.xml文件来引导应用程序。关于这种方法的更多细节，请查看我们[之前的文章](https://www.baeldung.com/spring-xml-vs-java-config)。

5. 控制器和视图

    让我们来看一个基本控制器的示例：

    ```java
    @Controller
    public class SampleController {
        @GetMapping("/sample")
        public String showForm() {
            return "sample";
        }
    }
    ```

    相应的JSP资源就是simple.jsp文件，基于JSP的视图文件位于项目的 /WEB-INF/view 文件夹下，因此只能通过Spring基础架构访问，而不能通过直接URL访问。

6. 带引导的Spring MVC

    Spring Boot是对Spring平台的一个补充，它使启动和创建独立的生产级应用程序变得非常容易。Boot并不是为了取代Spring，而是为了更快更容易地使用它。

    1. Spring Boot Starters

        新框架提供了方便的启动依赖项，这些依赖项是依赖描述符，可以为特定功能引入所有必要的技术。

        这样做的好处是，我们不再需要为每个依赖项指定一个版本，而是允许启动器为我们管理依赖项。

        开始的最快方法是添加 [spring-boot-starter-parent](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-boot-starter-parent%22) pom.xml：

        ```xml
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.7.2</version>
        </parent>
        ```

        这将负责依赖关系管理。

    2. Spring Boot Entry Point

        使用Spring Boot构建的每个应用程序只需要定义主入口点。

        这通常是一个带有main方法的Java类，用@SpringBootApplication注释。

        此注释添加了以下其他注释：

        - @Configuration将类标记为bean定义的源。
        - @EnableAutoConfiguration告诉框架根据类路径上的依赖关系自动添加bean。
        - @ComponentScan扫描与Application类或更低级别相同包中的其他配置和bean。

        使用Spring Boot，我们可以使用Thymeleaf或JSP设置前端，而不需要使用第3节中定义的ViewResolver.xml，启用Thymeleaf，无需额外配置。

        Boot应用程序的[源代码](https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-bootstrap)一如既往地在GitHub上提供。

        最后，如果您想开始使用Spring Boot，请查看我们的[参考简介](https://www.baeldung.com/spring-boot-start)。

7. 运行

    运行 Application.java，当项目在本地启动完成，可以通过<http://localhost:8080/spring-mvc-basics/sample>访问sample.jsp。

    > 注意：老代码，需要指定 JavaRuntime <maven.compiler.target>1.8</maven.compiler.target>

## 介绍Spring DispatcherServlet

简单地说，在前台控制器设计模式中，一个控制器负责将传入的HttpRequests引导到应用程序的所有其他控制器和处理程序。

Spring的DispatcherServlet实现了这种模式，因此，它负责正确协调HttpRequests到正确的处理程序。

在这篇文章中，我们将研究Spring DispatcherServlet的请求处理工作流程，以及如何实现参与该工作流程的几个接口。

1. DispatcherServlet请求处理

    从本质上讲，DispatcherServlet处理传入的HttpRequest，委托请求，并根据配置的HandlerAdapter接口来处理该请求，这些接口已经在Spring应用程序中实现，同时还有指定处理程序、控制器端点和响应对象的附带注释。

    让我们更深入地了解DispatcherServlet如何处理一个组件。

    - the WebApplicationContext associated to a DispatcherServlet under the key DispatcherServlet WEB_APPLICATION_CONTEXT_ATTRIBUTE is searched for and made available to all of the elements of the process
    - 在DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE键下与DispatcherServlet相关联的WebApplicationContext被搜索到并提供给进程中的所有元素
    - DispatcherServlet使用getHandler()找到为你的调度器配置的HandlerAdapter接口的所有实现--每个找到并配置的实现都通过handle()处理请求，并通过进程的其余部分进行处理。
    - [LocaleResolver](https://www.baeldung.com/spring-dispatcherservlet#localResolver)被选择性地绑定到请求上，以使流程中的元素能够解决地域问题。
    - [ThemeResolver](https://www.baeldung.com/spring-dispatcherservlet#themeResolver)可以选择与请求绑定，以便让元素（如视图）决定使用哪个主题。
    - 如果指定了[MultipartResolver](https://www.baeldung.com/spring-dispatcherservlet#multipartResolver)，请求将被检查为MultipartFiles--任何发现的文件都将被包装在MultipartHttpServletRequest中以便进一步处理。
    - 在WebApplicationContext中声明的[HandlerExceptionResolver](https://www.baeldung.com/spring-dispatcherservlet#handlerExceptionResolver)实现会拾取在处理请求时抛出的异常。

    你可以在[这里](https://www.baeldung.com/register-servlet)了解更多关于注册和设置DispatcherServlet的所有方法。

2. 处理程序适配器（HandlerAdapter）接口

    HandlerAdapter接口通过几个特定的接口促进了controllers、servlets、HttpRequests和HTTP路径的使用。因此，HandlerAdapter接口在DispatcherServlet请求处理工作流程的许多阶段中发挥着重要作用。

    首先，每个HandlerAdapter的实现都从你的dispatcher的getHandler()方法中被放入HandlerExecutionChain。然后，随着执行链的进行，这些实现中的每一个都会 handle() 相应 HttpServletRequest对象。

    1. 映射

        为了理解映射，我们首先需要看看如何注释控制器，因为控制器对HandlerMapping接口来说是非常重要的。

        SimpleControllerHandlerAdapter允许在没有@Controller注解的情况下明确实现一个控制器。

        RequestMappingHandlerAdapter支持带有@RequestMapping注解的方法。

        在这里我们将重点讨论@Controller注解，但也有一个有用的资源，其中有几个[使用SimpleControllerHandlerAdapter的例子](https://www.baeldung.com/spring-mvc-handler-adapters)。

        @RequestMapping注解设置了特定的端点，处理程序将在与之相关的WebApplicationContext中可用。

        让我们看一个控制器的例子，它公开并处理"/user/example"端点。

        ```java
        @Controller
        @RequestMapping("/user")
        @ResponseBody
        public class UserController {
        
            @GetMapping("/example")
            public User fetchUserExample() {
                // ...
            }
        }
        ```

        由@RequestMapping注解指定的路径通过HandlerMapping接口进行内部管理。

        URLs结构自然是相对于DispatcherServlet本身而言的--并由servlet映射决定。

        因此，如果DispatcherServlet被映射到'/'，那么所有的映射都将被该映射所覆盖。

        然而，如果servlet的映射是'/dispatcher'，那么任何@RequestMapping注释都将是相对于该根URL的。

        请记住，对于servlet映射来说，'/'和'/*'是不一样的 '/'是默认的映射，它将所有的URL暴露在调度器的职责范围内。

        对于很多较新的Spring开发者来说，'/*'是令人困惑的。它并没有指定所有具有相同URL上下文的路径都在调度器的责任范围内。相反，它覆盖并忽略了其他调度器的映射。因此，'/example'会显示为404!

        由于这个原因，除非在非常有限的情况下（比如配置过滤器），否则不应该使用'/*'。

    2. HTTP请求处理

        DispatcherServlet的核心职责是将传入的HttpRequests分派给由@Controller或@RestController注解指定的正确处理程序。

        顺便提一下，@Controller和@RestController的主要区别在于如何生成响应--@RestController默认也定义了@ResponseBody。

        关于Spring的控制器，我们有一篇更深入的文章，可以在[这里](https://www.baeldung.com/spring-controllers)找到。

    3. ViewResolver接口

        ViewResolver作为ApplicationContext对象的一个配置设置被附加到DispatcherServlet上。

        ViewResolver决定了调度器提供什么样的视图以及从哪里提供。

        下面是一个配置的例子，我们将把它放入我们的AppConfig，用于渲染JSP页面。

        ```java
        @Configuration
        @EnableWebMvc
        @ComponentScan("com.baeldung.springdispatcherservlet")
        public class AppConfig implements WebMvcConfigurer {

            @Bean
            public UrlBasedViewResolver viewResolver() {
                UrlBasedViewResolver resolver
                = new UrlBasedViewResolver();
                resolver.setPrefix("/WEB-INF/view/");
                resolver.setSuffix(".jsp");
                resolver.setViewClass(JstlView.class);
                return resolver;
            }
        }
        ```

        非常简单明了! 这里面有三个主要部分。

        - 设置前缀，它设置了默认的URL路径，以便在其中找到所设置的视图
        - 通过后缀设置的默认视图类型
        - 在解析器上设置一个视图类，允许JSTL或Tiles等技术与渲染的视图相关联。

        一个常见的问题涉及到调度器的ViewResolver和整个项目的目录结构是如何精确关联的。让我们看一下基础知识。

        下面是一个使用Spring的XML配置的InternalViewResolver的路径配置例子。

        `<property name="prefix" value="/jsp/"/>`

        在我们的例子中，我们假设我们的应用程序被托管在：<http://localhost:8080/>。

        这是本地托管的Apache Tomcat服务器的默认地址和端口。

        假设我们的应用程序被称为dispatcherexample-1.0.0，我们的JSP视图将可以从以下位置访问。

        `http://localhost:8080/dispatcherexample-1.0.0/jsp/`

        在使用Maven的普通Spring项目中，这些视图的路径是这样的。

        ```txt
        src -|
            main -|
                    java
                    resources
                    webapp -|
                            jsp
                            WEB-INF
        ```

        视图的默认位置是在WEB-INF中。在上面的片段中，为我们的InternalViewResolver指定的路径决定了 "src/main/webapp"的子目录，你的视图将在其中可用。

    4. LocaleResolver接口

        为我们的调度器定制会话、请求或cookie信息的主要方式是通过LocaleResolver接口。

        CookieLocaleResolver是一个实现，允许使用cookie配置无状态应用程序属性。让我们把它添加到AppConfig中。

        ```java
        @Bean
        public CookieLocaleResolver cookieLocaleResolverExample() {
            CookieLocaleResolver localeResolver 
                = new CookieLocaleResolver();
            localeResolver.setDefaultLocale(Locale.ENGLISH);
            localeResolver.setCookieName("locale-cookie-resolver-example");
            localeResolver.setCookieMaxAge(3600);
            return localeResolver;
        }

        @Bean 
        public LocaleResolver sessionLocaleResolver() { 
            SessionLocaleResolver localeResolver = new SessionLocaleResolver(); 
            localeResolver.setDefaultLocale(Locale.US); 
            localResolver.setDefaultTimeZone(TimeZone.getTimeZone("UTC"));
            return localeResolver; 
        }
        ```

        SessionLocaleResolver允许在一个有状态的应用程序中进行特定会话的配置。

        setDefaultLocale()方法代表了一个地理、政治或文化区域，而setDefaultTimeZone()则决定了有关应用Bean的相关TimeZone对象。

        这两个方法在上述LocaleResolver的每个实现中都可用。

    5. The ThemeResolver接口

        Spring为我们的视图提供了风格化的主题。

        让我们来看看如何配置我们的调度器来处理主题。

        首先，让我们设置所有必要的配置来寻找和使用我们的静态主题文件。我们需要为我们的ThemeSource设置一个静态资源位置，以配置实际的主题本身（主题对象包含这些文件中规定的所有配置信息）。把这个添加到AppConfig。

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**")
            .addResourceLocations("/", "/resources/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
        }

        @Bean
        public ResourceBundleThemeSource themeSource() {
            ResourceBundleThemeSource themeSource
            = new ResourceBundleThemeSource();
            themeSource.setDefaultEncoding("UTF-8");
            themeSource.setBasenamePrefix("themes.");
            return themeSource;
        }
        ```

        由DispatcherServlet管理的请求可以通过传入ThemeChangeInterceptor对象上可用的setParamName()的指定参数来修改主题。添加到AppConfig中。

        ```java
        @Bean
        public CookieThemeResolver themeResolver() {
            CookieThemeResolver resolver = new CookieThemeResolver();
            resolver.setDefaultThemeName("example");
            resolver.setCookieName("example-theme-cookie");
            return resolver;
        }

        @Bean
        public ThemeChangeInterceptor themeChangeInterceptor() {
            ThemeChangeInterceptor interceptor
                = new ThemeChangeInterceptor();
            interceptor.setParamName("theme");
            return interceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(themeChangeInterceptor());
        }
        ```

        下面的JSP标签被添加到我们的视图中，以使正确的样式出现。

        `<link rel="styleheet" href="${ctx}/<spring:theme code='styleSheet'/>" type="text/css"/>`

        下面的URL请求使用传入我们配置的ThemeChangeIntercepter的'theme'参数渲染了示例主题。

        <http://localhost:8080/spring-mvc-basics/?theme=example>

        > FIXME: 未起作用，css/example.css 文件不存在

    6. MultipartResolver接口

        一个MultipartResolver实现检查请求中的多部分，如果发现至少有一个多部分，就把它们包装在MultipartHttpServletRequest中，以便由流程中的其他元素进一步处理。添加到AppConfig中。

        ```java
        @Bean
        public CommonsMultipartResolver multipartResolver() 
        throws IOException {
            CommonsMultipartResolver resolver
            = new CommonsMultipartResolver();
            resolver.setMaxUploadSize(10000000);
            return resolver;
        }
        ```

        现在我们已经配置了我们的MultipartResolver Bean，让我们设置一个控制器 MultipartController.java 来处理MultipartFile请求。

        我们可以使用一个普通的表单来提交一个文件到指定的端点。上传的文件将在'CATALINA_HOME/bin/uploads'中获得。

    7. HandlerExceptionResolver接口

        Spring的HandlerExceptionResolver为整个Web应用、单个控制器或一组控制器提供统一的错误处理。

        为了提供应用范围内的自定义异常处理，创建一个带有@ControllerAdvice注释的类。

        ```java
        @ControllerAdvice
        public class ExampleGlobalExceptionHandler {
            @ExceptionHandler
            @ResponseBody 
            public String handleExampleException(Exception e) {
                // ...
            }
        }
        ```

        该类中任何带有@ExceptionHandler注解的方法都可以在dispatcher的责任范围内的每个控制器上使用。

        DispatcherServlet的ApplicationContext中的HandlerExceptionResolver接口的实现，只要@ExceptionHandler被用作注解，并将正确的类作为参数传入，就可以拦截该调度器职责范围内的特定控制器。

        ```java
        @Controller
        public class FooController{
            @ExceptionHandler({ CustomException1.class, CustomException2.class })
            public void handleException() {
                // ...
            }
            // ...
        }
        ```

        如果CustomException1或CustomException2发生异常，handleException()方法现在将作为我们上面例子中FooController的异常处理程序。

        这里有一篇[文章](https://www.baeldung.com/exception-handling-for-rest-with-spring)，更深入地介绍了Spring Web应用中的异常处理。

## Relevant Articles

- [x] [Spring MVC Tutorial](https://www.baeldung.com/spring-mvc-tutorial)
- [x] [An Intro to the Spring DispatcherServlet](https://www.baeldung.com/spring-dispatcherservlet)
- [The Spring @Controller and @RestController Annotations](https://www.baeldung.com/spring-controller-vs-restcontroller)
- [A Guide to the ViewResolver in Spring MVC](https://www.baeldung.com/spring-mvc-view-resolver-tutorial)
- [Guide to Spring Handler Mappings](https://www.baeldung.com/spring-handler-mappings)
- [Spring MVC Content Negotiation](https://www.baeldung.com/spring-mvc-content-negotiation-json-xml)
- [Spring @RequestMapping New Shortcut Annotations](https://www.baeldung.com/spring-new-requestmapping-shortcuts)
- More articles: [[more -->]](../spring-mvc-basics-2)
