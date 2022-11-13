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

        我们可以使用一个普通的表单来提交一个文件到指定的端点。

        发布服务后，上传的文件将在'CATALINA_HOME/bin/uploads'中获得，而直接运行工程则上传到 spring-mvc-basics/uploads ，注意 uploads 目录需要手动创建。

        运行：`curl -H "Content-Type:multipart/form-data" -X POST -F "file=@/Users/wangkan/Downloads/Program_Execution.jpg" -F 'info={"":""}' http://localhost:8080/spring-mvc-basics/upload` 测试文件上传。

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

## Spring的@Controller和@RestController注解

在这个简短的教程中，我们将讨论Spring MVC中@Controller和@RestController注解的区别。

我们可以对传统的Spring控制器使用第一个注解，而且它成为框架的一部分已经有很长一段时间了。

Spring 4.0引入了@RestController注解，以简化RESTful Web服务的创建。这是一个方便的注解，它结合了@Controller和@ResponseBody，这样就不需要用@ResponseBody注解来注释控制器类的每个请求处理方法。

进一步阅读：

- [Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)
- [Spring的@RequestParam注解](https://www.baeldung.com/spring-request-param)

1. Spring MVC @Controller

    我们可以用@Controller注解来注释经典的控制器。这只是@Component类的一个特殊化，它允许我们通过classpath扫描自动检测实现类。

    我们通常将@Controller与@RequestMapping注解结合使用，用于请求处理方法。

    让我们来看看Spring MVC控制器的一个简单例子。

    ```java
    @Controller
    @RequestMapping("books")
    public class SimpleBookController {

        @GetMapping("/{id}", produces = "application/json")
        public @ResponseBody Book getBook(@PathVariable int id) {
            return findBookById(id);
        }

        private Book findBookById(int id) {
            // ...
        }
    }
    ```

    我们用@ResponseBody注释了请求处理方法。这个注解使得返回对象能够自动序列化到HttpResponse中。

2. Spring MVC @RestController

    @RestController是控制器的一个专门版本。它包括@Controller和@ResponseBody注解，因此，简化了控制器的实现。

    ```java
    @RestController
    @RequestMapping("books-rest")
    public class SimpleBookRestController {
        
        @GetMapping("/{id}", produces = "application/json")
        public Book getBook(@PathVariable int id) {
            return findBookById(id);
        }

        private Book findBookById(int id) {
            // ...
        }
    }
    ```

    该控制器使用了@RestController注解；因此，不需要@ResponseBody。

    控制器类的每个请求处理方法都自动将返回对象序列化为HttpResponse。

## Spring MVC中ViewResolver指南

所有MVC框架都提供了一种处理视图的方法。

Spring通过视图解析器实现这一点，它使您能够在浏览器中渲染模型，而无需将实现与特定的视图技术绑定。

ViewResolver将视图名称映射到实际视图。

Spring框架附带了很多视图解析器，例如InternalResourceViewResolver、BeanNameViewResolver等。

这是一个简单的教程，演示如何设置最常见的视图解析器以及如何在同一配置中使用多个ViewResolver。

1. Spring Web配置

    让我们从web配置开始；我们将用@EnableWebMvc、@Configuration和@ComponentScan对其进行注释：

    ```java
    @EnableWebMvc
    @Configuration
    @ComponentScan("com.baeldung.web")
    public class WebConfig implements WebMvcConfigurer {
        // All web configuration will go here
    }
    ```

    在这里，我们将在配置中设置视图解析器。

2. 添加InternalResourceViewResolver

    此ViewResolver允许我们为视图名称设置前缀或后缀等属性，以生成最终视图页面URL：

    ```java
    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        return bean;
    }
    ```

    为了简化示例，我们不需要控制器来处理请求。

    我们只需要一个简单的jsp页面，放在配置中定义的/WEB-INF/view文件夹中：simple.jsp

3. 添加BeanNameViewResolver

    这是ViewResovler的一个实现，它将视图名称解释为当前应用程序上下文中的bean名称。每个这样的视图都可以在XML或Java配置中定义为bean。

    首先，我们将BeanNameViewResolver添加到以前的配置中：

    ```java
    @Bean
    public BeanNameViewResolver beanNameViewResolver(){
        return new BeanNameViewResolver();
    }
    ```

    一旦定义了ViewResolver，我们需要定义View类型的bean，以便DispatcherServlet可以执行它来呈现视图：

    ```java
    @Bean
    public View sample() {
        return new JstlView("/WEB-INF/view/sample.jsp");
    }
    ```

    下面是控制器类中对应的处理程序方法：

    ```java
    @GetMapping("/sample")
    public String showForm() {
        return "sample";
    }
    ```

    从控制器方法中，视图名称返回为“sample”，这意味着该处理程序方法中的视图解析为带有 `/WEB-INF/view/sample.jsp` URL的JstlView类。

4. 链接ViewResolver并定义订单优先级

    Spring MVC还支持多视图解析器。

    这允许您在某些情况下覆盖特定视图。我们可以通过向配置中添加多个解析器来简单地链接视图解析器。

    完成后，我们需要为这些解析器定义一个顺序。order属性用于定义链中调用的顺序。顺序属性（最大顺序号）越高，视图解析器在链中的位置越晚。

    要定义顺序，我们可以在视图解析器的配置中添加以下代码行：

    `bean.setOrder(0);`

    注意顺序优先级，因为InternalResourceViewResolver应该具有更高的顺序，因为它旨在表示一个非常明确的映射。如果其他解析器的顺序更高，则可能永远不会调用InternalResourceViewResolver。

5. 使用SpringBoot

    使用Spring Boot时，WebMvcAutoConfiguration会在应用程序上下文中自动配置InternalResourceViewResolver和BeanNameViewResolverbean。

    此外，为模板引擎添加相应的启动程序，会减少我们必须进行的手动配置。

    例如，通过向pom添加[spring-boot-starter-thymeleaf](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf)依赖项。xml，启用Thymeleaf，无需额外配置：

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
        <version>${spring-boot-starter-thymeleaf.version}</version>
    </dependency>
    ```

    这个启动器依赖项在我们的应用程序上下文中使用名称ThymeleafViewResolver配置ThymeleafViewResolver bean。我们可以通过提供一个同名的bean来覆盖自动配置的ThymeleafViewResolver。

    Thymeleaf视图解析器通过用前缀和后缀包围视图名称来工作。前缀和后缀的默认值为‘classpath:/templates/’和‘.html’。

    Spring Boot还提供了一个选项，可以通过设置Spring.thmeleaf来更改前缀和后缀的默认值，spring.thymeleaf.prefix前缀属性和spring.thymeleaf.suffix后缀属性。

    同样，我们有[groovy-templates](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-groovy-templates)、[freemarker](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-freemarker)和[mustache](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mustache)模板引擎的启动依赖项，我们可以使用Spring Boot来获得相应的视图解析器的自动配置。

    DispatcherServlet使用它在应用程序上下文中发现的所有视图解析器，并尝试每一个，直到得到一个结果，因此，如果我们计划添加我们自己的视图解析器，这些视图解析器的排序就变得非常重要。

## Spring Handler 映射指南

在Spring MVC中，DispatcherServlet充当前台控制器--接收所有传入的HTTP请求并处理它们。

简单地说，处理过程是在处理程序映射的帮助下将请求传递给相关组件。

HandlerMapping是一个接口，定义了请求和处理程序对象之间的映射。虽然Spring MVC框架提供了一些现成的实现，但开发者可以通过实现该接口来提供自定义的映射策略。

本文讨论了Spring MVC提供的一些实现，即BeanNameUrlHandlerMapping、SimpleUrlHandlerMapping、ControllerClassNameHandlerMapping，它们的配置，以及它们之间的区别。

1. BeanNameUrlHandlerMapping

    BeanNameUrlHandlerMapping是默认的HandlerMapping实现。BeanNameUrlHandlerMapping将请求URL映射到同名的Bean上。

    这种特殊的映射支持直接的名称匹配，也支持使用"*"模式进行模式匹配。

    例如，一个传入的URL"/foo"映射到一个叫做"/foo"的bean。模式匹配的一个例子是将对"/foo*"的请求映射到名称以"/foo"开头的bean，如"/foo2/"或"/fooOne/"。

    让我们在这里配置这个例子，并注册一个bean控制器，处理对"/beanNameUrl"的请求，见 BeanNameUrlHandlerMappingConfig.java 。

    ```java
    @Configuration
    public class BeanNameUrlHandlerMappingConfig {
        @Bean
        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
            return new BeanNameUrlHandlerMapping();
        }

        @Bean("/beanNameUrl")
        public WelcomeController welcome() {
            return new WelcomeController();
        }
    }
    ```

    这是与上述基于Java的配置相对应的XML，见 BeanNameUrlHandlerMappingConfig.xml 。

    ```xml
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" />
    <bean name="/beanNameUrl" class="com.baeldung.WelcomeController" />
    ```

    需要注意的是，在这两个配置中，不需要为BeanNameUrlHandlerMapping定义一个bean，因为它是由Spring MVC提供的。移除这个Bean定义不会导致任何问题，请求仍然会被映射到他们注册的处理程序Bean。

    现在，所有对"/beanNameUrl"的请求都将由DispatcherServlet转发到 "WelcomeController"。WelcomeController返回一个名为 "welcome"的视图。

    下面的代码测试了这个配置并确保返回正确的视图名称。

    ```java
    public class BeanNameMappingConfigTest {

        @Test
        public void whenBeanNameMapping_thenMappedOK() {
            mockMvc.perform(get("/beanNameUrl"))
            .andExpect(status().isOk())
            .andExpect(view().name("welcome"));
        }
    }
    ```

2. SimpleUrlHandlerMapping

    SimpleUrlHandlerMapping是最灵活的HandlerMapping实现。它允许在Bean实例和URL之间或Bean名称和URL之间进行直接和声明式的映射。

    让我们把请求"/simpleUrlWelcome"和"/*/simpleUrlWelcome"映射到"welcome"Bean，见 com.baeldung.config.SimpleUrlHandlerMappingConfig.java 。

    或者，这里有同等的XML配置，见 resources.SimpleUrlHandlerMappingConfig.xml。

    需要注意的是，在XML配置中，`<value>`标签之间的映射必须以java.util.Properties类所接受的形式进行，它应该遵循这样的语法：path= Handler_Bean_Name。

    URL通常应该有一个前导斜杠，然而，如果路径不是以斜杠开头，Spring MVC会自动添加它。

    在XML中配置上述例子的另一种方法是使用 "props"属性而不是 "value"。Props有一个 "prop"标签列表，每个标签都定义了一个映射，其中 "key "指的是映射的URL，标签的值是bean的名字。

    ```xml
    <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <prop key="/simpleUrlWelcome">welcome</prop>
                <prop key="/*/simpleUrlWelcome">welcome</prop>
            </props>
        </property>
    </bean>
    ```

    下面的测试案例确保对"/simpleUrlWelcome"的请求由 "WelcomeController"处理，该控制器返回一个名为 "welcome"的视图。

    ```java
    public class SimpleUrlMappingConfigTest {
        // ...

        @Test
        public void whenSimpleUrlMapping_thenMappedOK() {
            mockMvc.perform(get("/simpleUrlWelcome"))
            .andExpect(status().isOk())
            .andExpect(view().name("welcome"));
        }
    }
    ```

3. ControllerClassNameHandlerMapping (在Spring 5中被删除)

    ControllerClassNameHandlerMapping将URL映射到注册的控制器Bean（或用@Controller注解标注的控制器），该控制器具有相同的名称，或以相同的名称开始。

    在许多情况下，特别是对于处理单一请求类型的简单控制器实现来说，这可能更方便。Spring MVC使用的惯例是使用类的名字，去掉 "Controller "的后缀，然后将名字改为小写，并将其作为映射返回，前面加一个"/"。

    例如，"WelcomeController "将作为映射返回到"/welcome*"，即任何以 "欢迎 "开头的URL。

    让我们来配置ControllerClassNameHandlerMapping。

    请注意，ControllerClassNameHandlerMapping从Spring 4.3开始被弃用，转而采用注解驱动的处理方法。

    另一个重要的注意点是，控制器的名字将总是以小写返回（减去 "Controller"后缀）。因此，如果我们有一个名为 "WelcomeBaeldungController"的控制器，它将只处理对"/welcomebaeldung"的请求，而不是对"/welcomeBaeldung"。

    在下面的Java配置和XML配置中，我们定义了ControllerClassNameHandlerMapping Bean，并为我们要用来处理请求的控制器注册了bean。我们还注册了一个 "WelcomeController"类型的Bean，这个Bean将处理所有以"/welcome"开头的请求。

4. 配置优先级

    Spring MVC框架允许同时实现一个以上的HandlerMapping接口。

    让我们创建一个配置并注册两个控制器，都映射到URL"/welcome"，只是使用不同的映射并返回不同的视图名称。

    参见 com.baeldung.config.HandlerMappingDefaultConfig.java。

    由于没有明确注册处理程序映射，将使用默认的BeanNameHandlerMapping。让我们通过测试来断言这一行为。

    ```java
    @Test
    public void whenConfiguringPriorities_thenMappedOK() {
        mockMvc.perform(get("/welcome"))
        .andExpect(status().isOk())
        .andExpect(view().name("bean-name-handler-mapping"));
    }
    ```

    如果我们明确地注册一个不同的处理程序映射器，默认的映射器将被覆盖。然而，有趣的是，当两个映射器被显式注册后会发生什么。

    参见 com.baeldung.config.HandlerMappingPrioritiesConfig.java。

    为了获得对使用映射的控制权，使用setOrder(int order)方法来设置优先级。这个方法需要一个int参数，数值越低意味着优先级越高。

    在XML配置中，你可以通过使用一个名为"order"的属性来配置优先级。

    ```xml
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping">
        <property name="order" value="2" />
    </bean>
    ```

    让我们通过下面的beanNameUrlHandlerMapping.setOrder(1)和simpleUrlHandlerMapping.setOrder(0)向处理程序映射豆添加顺序属性。顺序属性的低值反映了更高的优先权。让我们用测试来断言新的行为。

    ```java
    @Test
    public void whenConfiguringPriorities_thenMappedOK() {
        mockMvc.perform(get("/welcome"))
        .andExpect(status().isOk())
        .andExpect(view().name("simple-url-handler-mapping"));
    }
    ```

    当测试上述配置时，你会看到对"/welcome"的请求将由SimpleUrlHandlerMapping Bean处理，它调用SimpleUrlHandlerController并返回simple-url-handler-mapping视图。我们可以通过相应地调整order属性的值，轻松地配置BeanNameHandlerMapping，使其优先处理。

## Spring MVC内容协商

这篇文章描述了如何在Spring MVC项目中实现内容协商。

一般来说，有三种选择来确定请求的媒体类型。

- (已废弃）在请求中使用URL后缀（扩展名）（例如.xml/.json）。
- 在请求中使用URL参数(如?format=json)
- 在请求中使用接受头

默认情况下，这是Spring内容协商管理器将尝试使用这三种策略的顺序。如果这些都没有启用，我们可以指定回退到一个默认的内容类型。

1. 内容协商策略

    让我们从必要的依赖性开始--我们正在使用JSON和XML表示法，所以在这篇文章中，我们将使用Jackson来表示JSON。

    ```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.10.2</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.10.2</version>
    </dependency>
    ```

    对于XML支持，我们可以使用JAXB、XStream或较新的Jackson-XML支持。

    由于我们已经在前面关于[HttpMessageConverters](https://www.baeldung.com/spring-httpmessageconverter-rest)的文章中解释了Accept头的使用，所以让我们深入关注前两种策略。

2. URL后缀策略

    在Spring Boot 2.6.x版本中，针对注册的Spring MVC处理程序映射匹配请求路径的默认策略已经从AntPathMatcher变为PathPatternParser。

    由于PathPatternParser不支持后缀模式匹配，在使用这个策略之前，我们首先需要使用传统的路径匹配器。

    我们可以在application.properties文件中添加spring.mvc.pathmatch.match-strategy，将默认值调回AntPathMatcher。

    默认情况下，该策略是禁用的，我们需要在application.properties中设置spring.mvc.pathmatch.use-suffix-pattern为true来启用它。

    ```properties
    spring.mvc.pathmatch.use-suffix-pattern=true
    spring.mvc.pathmatch.matching-strategy=ant-path-matcher
    ```

    一旦启用，框架可以从URL中检查路径扩展，以确定输出内容类型。

    在进行配置之前，让我们快速看一下一个例子。在一个典型的Spring控制器中，我们有以下简单的API方法实现。

    ```java
    @RequestMapping(
    value = "/employee/{id}", 
    produces = { "application/json", "application/xml" }, 
    method = RequestMethod.GET)
    public @ResponseBody Employee getEmployeeById(@PathVariable long id) {
        return employeeMap.get(id);
    }
    ```

    让我们通过利用JSON扩展指定资源的媒体类型来调用它。

    `curl http://localhost:8080/spring-mvc-basics/employee/10.json`

    下面是我们使用JSON扩展可能得到的结果。

    ```json
    {
        "id": 10,
        "name": "Test Employee",
        "contactNumber": "999-999-9999"
    }
    ```

    下面是用XML表示的请求-响应的样子。

    `curl http://localhost:8080/spring-mvc-basics/employee/10.xml`

    响应主体。

    ```xml
    <employee>
        <contactNumber>999-999-9999</contactNumber>
        <id>10</id>
        <name>Test Employee</name>
    </employee>
    ```

    现在，如果我们不使用任何扩展或使用未配置的扩展，将返回默认的内容类型。

    `curl http://localhost:8080/spring-mvc-basics/employee/10`

    现在让我们来看看如何设置这个策略--同时使用Java和XML配置。

    1. Java配置

        ```java
        // com.baeldung.spring.web.config.WebConfig.java
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.favorPathExtension(true)。
            favorParameter(false).
            ignoreAcceptHeader(true)。
            useJaf(false)。
            defaultContentType（MediaType.APPLICATION_JSON）。
        }
        ```

        让我们来看看细节。

        首先，我们要启用路径扩展策略。值得一提的是，从[Spring Framework 5.2.4](https://github.com/spring-projects/spring-framework/issues/24179)开始，favorPathExtension(boolean)方法已被弃用，以便不鼓励使用路径扩展来进行内容协商。

        然后，我们将禁用URL参数策略和接受头策略--因为我们只想依靠路径扩展的方式来确定内容的类型。

        然后，我们要关闭Java激活框架；如果传入的请求不符合我们配置的任何策略，JAF可以作为一种回退机制来选择输出格式。我们关闭它是因为我们要将JSON配置为默认的内容类型。请注意，从Spring Framework 5开始，useJaf()方法已被废弃。

        最后--我们要把JSON设置为默认的。这意味着如果两个策略都不匹配，所有传入的请求将被映射到提供JSON的控制器方法。

    2. XML配置

        我们也来看看同样的配置，只是使用XML。

        ```xml
        <bean id="contentNegotiationManager" 
        class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
            <property name="favorPathExtension" value="true" />
            <property name="favorParameter" value="false"/>
            <property name="ignoreAcceptHeader" value="true" />
            <property name="defaultContentType" value="application/json" />
            <property name="useJaf" value="false" />
        </bean>
        ```

3. URL参数策略

    我们在上一节中已经使用了路径扩展--现在让我们设置Spring MVC来利用路径参数。

    我们可以通过将 favorParameter 属性的值设置为 true 来启用这一策略。

    让我们快速看一下这将如何与我们之前的例子一起工作。

    `curl http://localhost:8080/spring-mvc-basics/employee/10?mediaType=json`

    如果我们使用XML参数，输出将是XML形式的。

    `curl http://localhost:8080/spring-mvc-basics/employee/10?mediaType=xml`

    现在我们来做配置--同样，先用Java，然后用XML。

    1. Java配置

        ```java
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.favorPathExtension(false).
            favorParameter(true).
            parameterName("mediaType").
            ignoreAcceptHeader(true).
            useJaf(false).
            defaultContentType(MediaType.APPLICATION_JSON).
            mediaType("xml", MediaType.APPLICATION_XML). 
            mediaType("json", MediaType.APPLICATION_JSON); 
        }
        ```

        让我们阅读一下这个配置。

        首先，当然，路径扩展和接受头的策略被禁用（以及JAF）。

        其余的配置都是一样的。

    2. XML配置

        ```xml
        <bean id="contentNegotiationManager" 
        class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
            <property name="favorPathExtension" value="false" />
            <property name="favorParameter" value="true"/>
            <property name="parameterName" value="mediaType"/>
            <property name="ignoreAcceptHeader" value="true" />
            <property name="defaultContentType" value="application/json" />
            <property name="useJaf" value="false" />

            <property name="mediaTypes">
                <map>
                    <entry key="json" value="application/json" />
                    <entry key="xml" value="application/xml" />
                </map>
            </property>
        </bean>
        ```

        此外，我们可以同时启用两种策略（扩展和参数）：

        ```java
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.favorPathExtension(true).
            favorParameter(true).
            parameterName("mediaType").
            ignoreAcceptHeader(true).
            useJaf(false).
            defaultContentType(MediaType.APPLICATION_JSON).
            mediaType("xml", MediaType.APPLICATION_XML). 
            mediaType("json", MediaType.APPLICATION_JSON); 
        }
        ```

        在这种情况下，Spring将首先查找路径扩展，如果路径扩展不存在，则将查找路径参数。如果在输入请求中这两个都不可用，那么将返回默认的内容类型。

4. 接受Header策略

    如果启用了Accept Header，SpringMVC将在传入请求中查找其值，以确定表示类型。

    我们必须将ignoreAcceptHeader的值设置为false以启用此方法，并且我们禁用了其他两个策略，以便我们知道我们只依赖Accept头。

    Java配置：

    ```java
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
        favorParameter(false).
        parameterName("mediaType").
        ignoreAcceptHeader(false).
        useJaf(false).
        defaultContentType(MediaType.APPLICATION_JSON).
        mediaType("xml", MediaType.APPLICATION_XML). 
        mediaType("json", MediaType.APPLICATION_JSON); 
    }
    ```

    XML配置：

    ```xml
    <bean id="contentNegotiationManager" 
    class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
        <property name="favorPathExtension" value="true" />
        <property name="favorParameter" value="false"/>
        <property name="parameterName" value="mediaType"/>
        <property name="ignoreAcceptHeader" value="false" />
        <property name="defaultContentType" value="application/json" />
        <property name="useJaf" value="false" />

        <property name="mediaTypes">
            <map>
                <entry key="json" value="application/json" />
                <entry key="xml" value="application/xml" />
            </map>
        </property>
    </bean>
    ```

    最后，我们需要通过将内容协商管理器插入到整体配置中来打开它：

    `<mvc:annotation-driven content-negotiation-manager="contentNegotiationManager" />`

## Spring @RequestMapping 新的捷径注解

Spring 4.3引入了一些非常酷的方法级组成的注解，以便在典型的Spring MVC项目中顺利处理@RequestMapping。

1. 新注解

    通常情况下，如果我们想用传统的@RequestMapping注解来实现URL处理程序，那么它应该是这样的。

    `@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)`

    新的方法使得它可以简单地缩短为。

    `@GetMapping("/get/{id})`

    Spring目前支持五种内置注解，用于处理不同类型的HTTP请求方法，包括GET、POST、PUT、DELETE和PATCH。这些注解是

    - @GetMapping
    - @PostMapping
    - @PutMapping
    - @DeleteMapping
    - @PatchMapping

    从命名规则中我们可以看到，每个注解都是为了处理各自传入的请求方法类型，例如，@GetMapping用于处理GET类型的请求方法，@PostMapping用于处理POST类型的请求方法，等等。

2. 它是如何工作的

    上述所有的注解都已经在内部用@RequestMapping和方法元素中的相应值进行了注解。

    例如，如果我们看一下@GetMapping注解的源代码，我们可以看到它已经用RequestMethod.GET注解了，具体方式如下。

    ```java
    @Target({ java.lang.annotation.ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @RequestMapping(method = { RequestMethod.GET })
    public @interface GetMapping {
        // abstract codes
    }
    ```

    所有其他的注解都是以同样的方式创建的，即@PostMapping是用RequestMethod.POST注解的，@PutMapping是用RequestMethod.PUT注解的，等等。

    注释的完整源代码可[在此](https://github.com/spring-projects/spring-framework/tree/master/spring-web/src/main/java/org/springframework/web/bind/annotation)获得。

3. 实施

    让我们尝试使用这些注解来构建一个快速的REST应用程序。

    请注意，由于我们将使用Maven来构建项目，并使用Spring MVC来创建我们的应用程序，因此我们需要在pom.xml中添加必要的依赖项。

    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.4.RELEASE</version>
    </dependency>
    ```

    现在，我们需要创建一个控制器来映射传入的请求URL。在这个控制器中，我们将逐一使用所有这些注解。

    参见：RequestMappingShortcutsController.java。

    需要注意的几点。

    - 我们使用了必要的注解来处理适当的带有URI的传入HTTP方法。例如，@GetMapping来处理"/get"URI，@PostMapping来处理"/post"URI等等。
    - 由于我们正在制作一个基于REST的应用程序，我们将返回一个带有200响应代码的常量字符串（每个请求类型都是唯一的）来简化应用程序。在这种情况下，我们使用了Spring的@ResponseBody注解。
    - 如果我们必须处理任何URL路径变量，我们可以用更少的方式来处理，就像以前使用@RequestMapping一样。

4. 测试应用程序

    为了测试该应用程序，我们需要使用JUnit创建几个测试案例。我们将使用SpringJUnit4ClassRunner来启动测试类。我们将创建五个不同的测试用例来测试每个注解和我们在控制器中声明的每个处理器。

    参见：RequestMapingShortcutsIntegrationTest.java

    另外，我们也可以随时使用任何普通的REST客户端，例如PostMan、RESTClient等，来测试我们的应用程序。在这种情况下，我们在使用其余的客户端时，需要稍微注意选择正确的HTTP方法类型。否则，它将抛出405错误状态。

## Relevant Articles

- [x] [Spring MVC Tutorial](https://www.baeldung.com/spring-mvc-tutorial)
- [x] [An Intro to the Spring DispatcherServlet](https://www.baeldung.com/spring-dispatcherservlet)
- [x] [The Spring @Controller and @RestController Annotations](https://www.baeldung.com/spring-controller-vs-restcontroller)
- [x] [A Guide to the ViewResolver in Spring MVC](https://www.baeldung.com/spring-mvc-view-resolver-tutorial)
- [x] [Guide to Spring Handler Mappings](https://www.baeldung.com/spring-handler-mappings)
- [x] [Spring MVC Content Negotiation](https://www.baeldung.com/spring-mvc-content-negotiation-json-xml)
- [x] [Spring @RequestMapping New Shortcut Annotations](https://www.baeldung.com/spring-new-requestmapping-shortcuts)

More articles: [[more -->]](../spring-mvc-basics-2/README.md)
