# Spring Static Resources

This module contains articles about static resources with Spring

## 使用Spring MVC的可缓存静态资产

本文主要介绍在使用Spring Boot和Spring MVC提供静态资产（如Javascript和CSS文件）时的缓存。

我们还将讨论 "完美缓存 "的概念，主要是确保当文件被更新时，旧版本不会从缓存中被错误地提供。

1. 缓存静态资产

    为了使静态资产可以被缓存，我们需要配置其相应的资源处理程序。

    下面是一个如何做的简单例子--将响应中的Cache-Control头设置为max-age=31536000，这将导致浏览器使用该文件的缓存版本一年之久。

    ```java
    @EnableWebMvc
    public class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/js/**") 
                    .addResourceLocations("/js/") 
                    .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        }
    }
    ```

    我们之所以为缓存有效期设置这么长的时间段，是因为我们希望客户端使用缓存的文件版本，直到该文件被更新，而根据RFC对Cache-Control头的规定，365天是我们可以使用的最长期限。

    于是，当客户端第一次请求foo.js时，他将通过网络收到整个文件（本例中为37字节），状态码为200 OK。响应中会有以下头信息来控制缓存行为。

    `Cache-Control: max-age=31536000`

    这就指示浏览器以一年的过期时间缓存该文件，其结果是下面的响应。

    `status Code: 200 OK`

    当客户端第二次请求同一文件时，浏览器不会再向服务器发出请求。相反，它将直接从其缓存中提供该文件，并避免网络往返，因此页面的加载速度会快很多。

    `status Code: 200 OK (from cache)`

    Chrome浏览器用户在测试时需要注意，因为如果你按屏幕上的刷新按钮或按F5键刷新页面，Chrome就不会使用缓存。你需要在地址栏上按回车键来观察缓存行为。更多相关信息请点击这里。

    1. Spring Boot

        要在Spring Boot中定制Cache-Control头文件，我们可以使用[spring.resources.cache.cachecontrol](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web)属性命名空间下的属性。例如，要将最大年龄改为一年，我们可以在application.properties中添加以下内容。

        `spring.resources.cache.cachecontrol.max-age=365d`

        这适用于由Spring Boot提供的[所有静态资源](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration.java#L307)。因此，如果我们只是想对请求的一个子集应用缓存策略，我们应该使用普通的Spring MVC方法。

        除了max-age之外，也可以用类似的配置属性来定制其他的[Cache-Control](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control)参数，如no-store或no-cache。

2. 静态资产的版本管理

    使用缓存为静态资产提供服务可以使页面的加载速度非常快，但它有一个重要的注意事项。当你更新文件时，客户端不会得到该文件的最新版本，因为它没有向服务器检查该文件是否是最新的，而只是从浏览器缓存中提供该文件。

    以下是我们需要做的，以使浏览器只在文件被更新时才从服务器上获取文件。

    - 在一个带有版本的URL下提供文件。例如，foo.js应该在/js/foo-46944c7e3a9bd20cc30fdc085cae46f2.js下提供。
    - 用新的URL来更新文件的链接
    - 每当文件被更新时，更新URL的版本部分。例如，当foo.js被更新时，它现在应该在/js/foo-a3d8d7780349a12d739799e9aa7d2623.js下提供。

    当文件被更新时，客户端将从服务器请求该文件，因为该页面将有一个指向不同URL的链接，所以浏览器不会使用其缓存。如果一个文件没有被更新，它的版本（因此它的URL）将不会改变，客户端将继续使用该文件的缓存。

    通常情况下，我们需要手动完成所有这些工作，但Spring支持这些开箱即用，包括计算每个文件的哈希值并将其追加到URL中。让我们看看如何配置我们的Spring应用程序来为我们做这些事。

    1. MVC在一个带有版本的URL下提供服务

        我们需要为路径添加一个VersionResourceResolver，以便在URL中使用更新的版本字符串来提供该路径下的文件。

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/js/**")
                    .addResourceLocations("/js/")
                    .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                    .resourceChain(false)
                    .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
        }
        ```

        这里我们使用一个内容版本策略。/js文件夹中的每个文件都将在一个URL下提供，该URL有一个从其内容计算出来的版本。这就是所谓的指纹识别。例如，foo.js现在将在URL /js/foo-46944c7e3a9bd20cc30fdc085cae46f2.js下提供。

        有了这个配置，当客户端对<http://localhost:8080/js/46944c7e3a9bd20cc30fdc085cae46f2.js> 的请求。

        `curl -i http://localhost:8080/js/foo-46944c7e3a9bd20cc30fdc085cae46f2.js`

        服务器会用一个Cache-Control头来响应，告诉客户端浏览器将该文件缓存一年。

        ```log
        HTTP/1.1 200 OK
        Server: Apache-Coyote/1.1
        Last-Modified: Tue, 09 Aug 2016 06:43:26 GMT
        Cache-Control: max-age=31536000
        ```

    2. Spring Boot

        要在Spring Boot中启用同样的基于内容的版本管理，我们只需在[spring.resources.chain.strategy.content](https://github.com/spring-projects/spring-boot/blob/bb568c5bffcf70169245d749f3642bfd9dd33143/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration.java#L532)属性命名空间下使用一些配置。例如，我们可以通过添加以下配置来实现与之前相同的结果。

        ```properties
        spring.resources.chain.strategy.content.enabled=true
        spring.resources.chain.strategy.content.paths=/**
        ```

        与Java配置类似，这可以为所有与/**路径模式匹配的资产启用基于内容的版本控制。

    3. 用新的URL更新链接

        在我们将版本插入到URL中之前，我们可以使用一个简单的脚本标签来导入foo.js。

        `<script type="text/javascript" src="/js/foo.js">`

        现在，我们在一个带有版本的URL下提供相同的文件，我们需要在页面上反映它。

        `<script type="text/javascript" src="<em>/js/foo-46944c7e3a9bd20cc30fdc085cae46f2.js</em>">`

        处理所有这些长路径会变得很乏味。Spring为这个问题提供了一个更好的解决方案。我们可以使用ResourceUrlEncodingFilter和JSTL的url标签来重写带版本的链接的URL。

        ResourceURLEncodingFilter可以像往常一样在web.xml下注册。

        ```xml
        <filter>
            <filter-name>resourceUrlEncodingFilter</filter-name>
            <filter-class>
                org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
            </filter-class>
        </filter>
        <filter-mapping>
            <filter-name>resourceUrlEncodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
        ```

        在我们使用url标签之前，JSTL核心标签库需要被导入我们的JSP页面。

        `<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>`

        然后，我们可以使用url标签来导入foo.js，如下所示。

        `<script type="text/javascript" src="<c:url value="/js/foo.js" />">`

        当这个JSP页面被渲染时，文件的URL被正确改写，以包含其中的版本。

        `<script type="text/javascript" src="/js/foo-46944c7e3a9bd20cc30fdc085cae46f2.js">`

    4. 更新URL的版本部分

        每当一个文件被更新时，它的版本会被再次计算，并且文件会在一个包含新版本的URL下提供。我们不需要为此做任何额外的工作，VersionResourceResolver为我们处理这个问题。

3. 修复CSS链接

    CSS文件可以通过使用@import指令导入其他CSS文件。例如，myCss.css文件导入另一个.css文件。

    `@import "another.css";`

    这通常会给版本化的静态资产带来问题，因为浏览器会对another.css文件进行请求，但该文件是在一个版本化的路径下提供的，例如another-9556ab93ae179f87b178cfad96a6ab72.css。

    为了解决这个问题并向正确的路径发出请求，我们需要在资源处理程序配置中引入CssLinkResourceTransformer。

    ```java
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "classpath:/other-resources/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());
    }
    ```

    这修改了myCss.css的内容，并将导入语句换成了以下内容。

    `@import "another-9556ab93ae179f87b178cfad96a6ab72.css";`

4. 结论

    利用HTTP缓存是对网站性能的巨大提升，但在使用缓存时要避免提供陈旧的资源可能会很麻烦。

    在这篇文章中，我们实现了一个很好的策略，在用Spring MVC提供静态资产时使用HTTP缓存，并在文件更新时破坏缓存。

## 用Maven对JS和CSS资产进行最小化处理

本文介绍了如何在构建步骤中对Javascript和CSS资产进行最小化，并将生成的文件提供给Spring MVC。

我们将使用[YUI Compressor](https://yui.github.io/yuicompressor/)作为底层最小化库，并使用[YUI Compressor Maven插件](https://davidb.github.io/yuicompressor-maven-plugin/)将其集成到构建流程中。

1. Maven插件配置

    首先，我们需要声明我们将在pom.xml文件中使用压缩器插件并执行压缩目标。这将压缩src/main/webapp下的所有.js和.css文件，这样foo.js将被压缩为foo-min.js，myCss.css将被压缩为myCss-min.css。

    `net.alchim31.maven.yuicompressor-maven-plugin`

    我们的 src/main/webapp 目录包含以下文件一些静态资源。

    ```log
    js/
    ├── foo.js
    ├── jquery-1.11.1.min.js
    resources/
    └── myCss.css
    ```

    现在当我们执行mvn clean package时，我们将在生成的WAR文件中拥有以下文件。

    ```log
    js/
    ├── foo.js
    ├── foo-min.js
    ├── jquery-1.11.1.min.js
    ├── jquery-1.11.1.min-min.js
    resources/
    ├── myCss.css
    └── myCss-min.css
    ```

2. 保持文件名不变

    在这个阶段，当我们执行mvn clean package时，foo-min.js和myCss-min.css被插件创建。由于我们在引用文件时最初使用了foo.js和myCss.css，我们的页面仍将使用原始的非minified文件，因为minified文件的名称与原始文件不同。

    为了防止同时出现foo.js/foo-min.js和myCss.css/myCss-min.css，并在不改变文件名的情况下将其最小化，我们需要用nosuffix选项对插件进行配置，如下所示。

    `<nosuffix>true</nosuffix>`

    现在当我们执行mvn clean package时，我们将在生成的WAR文件中拥有以下文件。

    ```log
    js/
    ├── foo.js
    ├── jquery-1.11.1.min.js
    resources/
    └── myCss.css
    ```

3. WAR插件配置

    保持文件名不变有一个副作用。它使WAR插件用原始文件覆盖已减化的foo.js和myCss.css文件，所以我们在最终输出中没有文件的减化版本。 foo.js文件在减化前包含以下几行。

    ```js
    function testing() {
        alert("Testing");
    }
    ```

    当我们检查生成的WAR文件中foo.js文件的内容时，我们看到它有原始的内容，而不是减化后的内容。为了解决这个问题，我们需要为压缩器插件指定一个webappDirectory，并在WAR插件配置中引用它。

    `<webappDirectory>${project.build.directory}/min</webappDirectory>`

    在这里，我们已经指定了min目录作为最小化文件的输出目录，并配置了WAR插件，使其包括在最终输出中。

    `<resource><directory>${project.build.directory}/min</directory></resource>`

    现在我们在生成的WAR文件中看到了已被粉碎的文件，其原始文件名为foo.js和myCss.css。我们可以检查foo.js，看看它现在有以下的最小化内容。

    `function testing(){alert("Testing")};`

4. 排除已经最小化的文件

    第三方的Javascript和CSS库可能有最小化的版本可供下载。如果你碰巧在你的项目中使用了其中的一个，你不需要再次处理它们。

    包括已经最小化的文件会在构建项目时产生警告信息。

    例如，jquery-1.11.1.min.js是一个已经最小化的Javascript文件，它在构建过程中会产生类似以下的警告信息。

    ```log
    [WARNING] .../src/main/webapp/js/jquery-1.11.1.min.js [-1:-1]:
    Using 'eval' is not recommended. Moreover, using 'eval' reduces the level of compression!
    execScript||function(b){a. ---> eval <--- .call(a,b);})
    [WARNING] ...jquery-1.11.1.min.js:line -1:column -1:
    Using 'eval' is not recommended. Moreover, using 'eval' reduces the level of compression!
    execScript||function(b){a. ---> eval <--- .call(a,b);})
    ```

    要把已经被粉碎的文件排除在进程之外，请用排除选项配置压缩器插件，如下所示。

    `<exclude>**/*.min.js</exclude>`

    这将排除所有文件名以min.js结尾的目录下的所有文件。现在执行mvn clean package不会产生警告信息，而且构建时也不会尝试对已经被粉碎的文件进行粉碎。

5. 总结

    在本文中，我们介绍了一种将Javascript和CSS文件的最小化整合到Maven工作流中的好方法。要在Spring MVC应用中提供这些静态资产，请参阅我们的Spring服务静态资源一文。

## 用Spring服务静态资源

本教程探讨了如何使用XML和Java配置为Spring提供静态资源。

进一步阅读:

- WebJars简介
  关于在Spring中使用WebJars的快速实用指南。
  [阅读更多](https://www.baeldung.com/maven-webjars)

1. 使用Spring Boot

    Spring Boot预设了一个[ResourceHttpRequestHandler](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/resource/ResourceHttpRequestHandler.java)的实现，以方便为静态资源提供服务。

    默认情况下，该处理程序会从classpath上的任何/static、/public、/resources和/META-INF/resources目录中提供静态内容。由于 src/main/resources 通常默认在 classpath 上，我们可以把这些目录中的任何一个放在那里。

    例如，如果我们把about.html文件放在classpath中的/static目录内，那么我们就可以通过<http://localhost:8080/about.html>，访问该文件。同样，我们也可以通过在其他提到的目录中添加该文件来达到同样的效果。

    1. 自定义路径模式

        默认情况下，Spring Boot在请求的根目录下提供所有的静态内容，即/**。虽然这似乎是一个很好的默认配置，但我们可以通过[spring.mvc.static-path-pattern](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/WebMvcProperties.java#L105)配置属性改变它。

        例如，如果我们想通过http://localhost:8080/content/about.html 来访问同一个文件，我们可以在我们的application.properties中这样声明：

        `spring.mvc.static-path-pattern=/content/**`

        在WebFlux环境中，我们应该使用[spring.webflux.static-path-pattern](https://github.com/spring-projects/spring-boot/blob/c71ed407e43e561333719573d63464ae9db388c1/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/reactive/WebFluxProperties.java#L38)属性。

    2. 定制目录

        与路径模式类似，我们可以通过[spring.web.resources.static-locations](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/WebProperties.java#L86)配置属性改变默认资源位置。该属性可以接受多个逗号分隔的资源位置。

        `spring.web.resources.static-locations=classpath:/files/,classpath:/static-files`

        这里我们从classpath里面的/files和/static-files目录提供静态内容。此外，Spring Boot可以从classpath之外提供静态文件。

        `spring.web.resources.static-locations=file:/opt/files`

        这里我们使用文件[资源签名](https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/core.html#resources-resourceloader)，file:/，来提供本地磁盘上的文件。

2. XML配置

    如果我们需要使用基于XML的配置，我们可以很好地利用`mvc:resources`元素，以特定的公共URL模式指向资源的位置。

    例如，通过在应用程序根文件夹下的“/resources/”目录中搜索，以下行将为所有以公共URL模式（如“/resources/**”）传入的资源请求提供服务：

    `<mvc:resources mapping="/resources/**" location="/resources/" />`

    现在我们可以访问如下HTML页面中的CSS文件：

    ```html
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html>
    <head>
        <link href="<c:url value="/resources/myCss.css" />" rel="stylesheet">
        <title>Home</title>
    </head>
    <body>
        <h1>Hello world!</h1>
    </body>
    </html>
    ```

3. ResourceHttpRequestHandler

    Spring 3.1 引入了ResourceHandlerRegistry来配置ResourceHttpRequestHandlers，以从类路径、WAR或文件系统提供静态资源。我们可以在web上下文配置类中以编程方式配置ResourceHandlerRegistry。

    1. 服务WAR中存储的资源

        为了说明这一点，我们将使用与之前相同的URL指向myCs.css，但现在实际的文件将位于WAR的webapp/resources文件夹中，在部署Spring 3.1+应用程序时，应该在该文件夹中放置静态资源：

        ```java
        @Configuration
        @EnableWebMvc
        public class MvcConfig implements WebMvcConfigurer {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
            }
        }
        ```

        让我们分析一下这个例子。首先，我们通过定义资源处理程序来配置面向外部的URI路径(external-facing URI)。然后我们将面向外部的URI路径内部映射到资源实际所在的物理路径。

        当然，我们可以使用这个简单但灵活的API定义多个资源处理程序。

        现在，html页面中的以下行将为我们获取webapp/resources目录中的myCs.css资源：

        `<link href="<c:url value="/resources/myCss.css" />" rel="stylesheet">`

    2. 服务存储在文件系统中的资源

        让我们假设，每当请求匹配/files/**模式的公共URL时，我们都希望为存储在/opt/files/目录中的资源提供服务。我们只需配置URL模式并将其映射到磁盘上的特定位置：

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
            .addResourceHandler("/files/**")
            .addResourceLocations("file:/opt/files/");
        }
        ```

        对于Windows用户，此示例中传递给addResourceLocations的参数为“file:///C:/opt/files/“.

        配置资源位置后，我们可以使用home.html中的映射URL模式加载存储在文件系统中的图像：

        ```html
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>
        <head>
            <link href="<c:url value="/resources/myCss.css" />" rel="stylesheet">
            <title>Home</title>
        </head>
        <body>
            <h1>Hello world!</h1>
            <img alt="image"  src="<c:url value="files/myImage.png" />">
        </body>
        </html>
        ```

    3. 为资源配置多个位置

        如果我们想在多个位置查找资源，该怎么办？

        我们可以使用addResourceLocations方法包含多个位置。将按顺序搜索位置列表，直到找到资源：

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/","classpath:/other-resources/");
        }
        ```

        以下curl请求将显示存储在应用程序的webapp/resources或类路径中的其他资源文件夹中的Hello.html页面：

        `curl -i http://localhost:8080/handling-spring-static-resources/resources/Hello.html`

4. 新的资源解析器

    Spring 4.1 通过新的ResourcesResolver，提供了不同类型的资源解析器，可用于在加载静态资源时优化浏览器性能。这些解析器可以链接并缓存在浏览器中，以优化请求处理。

    1. PathResourceResolver

        这是最简单的解析器，其目的是查找给定公共URL模式的资源。事实上，如果我们不向ResourceChainRegistry添加ResourceResolver，这就是默认的解析器。

        我们来看一个示例：

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/","/other-resources/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
        }
        ```

        注意事项：

        - 我们将资源链中的PathResourceResolver注册为其中唯一的ResourceResolver。我们可以参考第3.3节。我们可以参考第3.3节。了解如何链接多个ResourceResolver。
        - 服务的资源将在浏览器中缓存3600秒。
        - 链最终使用方法resourceChain(true)进行配置。

        现在查看HTML代码，该代码与PathResourceResolver一起在webapp/resources或webapp/other-resources文件夹中查找foo.js脚本：

        `<script type="text/javascript" src="<c:url value="/resources/foo.js" />">`

    2. 编码资源解析器

        此解析器尝试根据Accept Encoding请求标头值查找编码资源。

        例如，我们可能需要通过使用gzip内容编码服务静态资源的压缩版本来优化带宽。

        要配置EncodedResourceResolver，我们需要在ResourceChain中配置它，就像我们配置PathResourceResolver一样：

        ```java
        registry
            .addResourceHandler("/other-files/**")
            .addResourceLocations("file:/Users/Me/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new EncodedResourceResolver());
        ```

        默认情况下，EncodedResourceResolver配置为支持br和gzip编码。

        因此，以下curl请求将获取位于Users/Me/目录中文件系统中Home.html文件的压缩版本：

        `curl -H  "Accept-Encoding:gzip" http://localhost:8080/handling-spring-static-resources/other-files/Hello.html`

        注意我们是如何将标头的“Accept Encoding”值设置为gzip的。这很重要，因为只有当gzip内容对响应有效时，这个特定的解析器才会生效。

        最后，请注意，与之前一样，压缩版本将在浏览器中缓存的时间段内保持可用，在本例中为3600秒。

    3. 链接资源解析器

        为了优化资源查找，ResourceResolver可以将资源的处理委托给其他解析器。唯一不能委托给链的解析器是PathResourceResolver，我们应该将其添加到链的末尾。

        事实上，如果resourceChain未设置为true，那么默认情况下将仅使用PathResourceResolver来服务资源。在这里，如果GzipResourceResolver失败，我们将链接PathResourceResolver以解析资源：

        ```java
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
            .addResourceHandler("/js/**")
            .addResourceLocations("/js/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new GzipResourceResolver())
            .addResolver(new PathResourceResolver());
        }
        ```

        现在我们已经将/js/**模式添加到ResourceHandler中，让我们包含foo.js资源，该资源位于home.html页面的webapp/js/目录中：

        ```html
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>
        <head>
            <link href="<c:url value="/resources/bootstrap.css" />" rel="stylesheet" />
            <script type="text/javascript"  src="<c:url value="/js/foo.js" />"></script>
            <title>Home</title>
        </head>
        <body>
            <h1>This is Home!</h1>
            <img alt="bunny hop image"  src="<c:url value="files/myImage.png" />" />
            <input type = "button" value="Click to Test Js File" onclick = "testing();" />
        </body>
        </html>
        ```

        值得一提的是，从Spring Framework 5.1开始，GzipResourceResolver已经被弃用，取而代之的是EncodedResourceResolver。因此，我们应该避免在将来使用它。

5. 其他安全配置

    如果使用SpringSecurity，允许访问静态资源是很重要的。我们需要添加访问资源URL的相应权限：

    ```xml
    <intercept-url pattern="/files/**" access="permitAll" />
    <intercept-url pattern="/other-files/**/" access="permitAll" />
    <intercept-url pattern="/resources/**" access="permitAll" />
    <intercept-url pattern="/js/**" access="permitAll" />
    ```

6. 结论

    在本文中，我们介绍了Spring应用程序服务静态资源的各种方式。

    基于XML的资源配置是一个遗留选项，如果我们还不能遵循Java配置路线，我们可以使用它。

    Spring 3.1。通过其ResourceHandlerRegistry对象提供了一个基本的编程替代方案。

    最后，Spring 4.1 附带的新的现成ResourceResolver和ResourceChainRegistry对象。提供资源加载优化功能，如缓存和资源处理程序链接，以提高静态资源的服务效率。

    此外，本项目中还提供了与Spring Boot相关的[源代码](https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-mvc-2)。

## 在Spring中把资源加载成一个字符串

在本教程中，我们将探讨将包含文本的资源内容作为字符串注入Spring Bean的各种方法。

我们将研究如何定位资源并读取其内容。

此外，我们还将演示如何在多个Bean中共享加载的资源。我们将通过使用与[依赖性注入有关的注解](https://www.baeldung.com/spring-annotations-resource-inject-autowire)来展示这一点，尽管同样可以通过使用[基于XML的注入](https://www.baeldung.com/spring-xml-injection)和在XML属性文件中声明bean来实现。

1. 使用资源

    我们可以通过使用[资源](https://www.baeldung.com/spring-classpath-file-access)接口来简化资源文件的定位。Spring使用资源加载器帮助我们找到并读取资源，该加载器根据提供的路径决定选择哪种资源实现。资源实际上是一种访问资源内容的方式，而不是内容本身。

    让我们看看[获取classpath上资源的Resource实例](https://www.baeldung.com/spring-classpath-file-access)的一些方法。

    1. 使用资源加载器

        如果我们喜欢使用懒惰加载，我们可以使用ResourceLoader这个类。

        ```java
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:resource.txt");
        ```

        我们也可以用@Autowired将ResourceLoader注入我们的Bean中。

        ```java
        @Autowired
        private ResourceLoader resourceLoader;
        ```

        2.2. 使用@Value

        我们可以用@Value将资源直接注入Spring Bean中。

        ```java
        @Value("classpath:resource.txt")
        private Resource resource;
        ```

2. 从资源到字符串的转换

    一旦我们有了对资源的访问权，我们就需要能够把它读成一个字符串。让我们创建一个带有静态方法asString的ResourceReader实用类来为我们做这个。

    首先，我们必须获得一个InputStream。

    `InputStream inputStream = resource.getInputStream();`

    我们的下一步是把这个InputStream转换为一个字符串。我们可以使用Spring自己的FileCopyUtils#copyToString方法。

    ```java
    public class ResourceReader {
        public static String asString(Resource resource) {
            try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        // more utility methods
    }
    ```

    还有[很多其他的实现方法](https://www.baeldung.com/convert-input-stream-to-string)，例如，使用Spring的StreamUtils类的copyToString。

    让我们再创建一个实用方法readFileToString，它将为一个路径检索资源，并调用asString方法将其转换为一个字符串。

    ```java
    public static String readFileToString(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }
    ```

3. 添加一个配置类

    如果每个Bean都必须单独注入资源字符串，那么就有可能出现代码重复和Bean拥有自己单独的字符串副本而导致更多内存的使用。

    我们可以通过在加载应用上下文时将资源的内容注入一个或多个Spring Bean来实现一个更整洁的解决方案。通过这种方式，我们可以从需要使用该内容的各个Bean那里隐藏读取资源的实现细节。

    ```java
    @Configuration
    public class LoadResourceConfig {
        // Bean Declarations
    }
    ```

    1. 使用一个持有资源字符串的Bean

        让我们在一个@Configuration类中声明Bean来持有资源内容。

        ```java
        @Bean
        public String resourceString() {
            return ResourceReader.readFileToString("resource.txt");
        }
        ```

        现在让我们通过添加[@Autowired](https://www.baeldung.com/spring-autowire)注解将注册的Bean注入到字段中。

        ```java
        public class LoadResourceAsStringIntegrationTest {
            private static final String EXPECTED_RESOURCE_VALUE = "...";  // The string value of the file content
            @Autowired
            @Qualifier("resourceString")
            private String resourceString;
            @Test
            public void givenUsingResourceStringBean_whenConvertingAResourceToAString_thenCorrect() {
                assertEquals(EXPECTED_RESOURCE_VALUE, resourceString);
            }
        }
        ```

        在这种情况下，我们使用@Qualifier注解和Bean的名字，因为我们可能需要注入相同类型的多个字段--String。

        我们应该注意到，限定符中使用的Bean名称来自配置类中创建Bean的方法名称。

4. 使用SpEL

    最后，让我们看看如何使用Spring表达式语言来描述将资源文件直接加载到我们类中的一个字段所需的代码。

    让我们使用@Value注解，将文件内容注入字段resourceStringUsingSpel中。

    ```java
    public class LoadResourceAsStringIntegrationTest {
        private static final String EXPECTED_RESOURCE_VALUE = "..."; // The string value of the file content
        @Value(
        "#{T(com.baeldung.loadresourceasstring.ResourceReader).readFileToString('classpath:resource.txt')}"
        )
        private String resourceStringUsingSpel;
        @Test
        public void givenUsingSpel_whenConvertingAResourceToAString_thenCorrect() {
            assertEquals(EXPECTED_RESOURCE_VALUE, resourceStringUsingSpel);
        }
    }
    ```

    这里我们调用了ResourceReader#readFileToString，通过使用 "classpath:" 来描述文件的位置 - 在我们的@Value注解中使用了一个 "classpath:"的预设路径。

    为了减少SpEL中的代码量，我们在类ResourceReader中创建了一个辅助方法，它使用Apache Commons FileUtils来访问所提供的路径中的文件。

    ```java
    public class ResourceReader {
        public static String readFileToString(String path) throws IOException {
            return FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
        }
    }
    ```

5. 总结

    在本教程中，我们已经回顾了一些将资源转换为字符串的方法。

    首先，我们看到了如何产生一个资源来访问文件，以及如何从资源读到字符串。

    接下来，我们还展示了如何隐藏资源加载实现，并通过在@Configuration中创建合格的Bean，让字符串内容在Bean之间共享，允许字符串被自动连接。

    最后，我们使用了SpEL，它提供了一个紧凑而直接的解决方案，尽管它需要一个自定义的辅助函数来阻止它变得过于复杂。

## Relevant Articles

- [x] [Cachable Static Assets with Spring MVC](https://www.baeldung.com/cachable-static-assets-with-spring-mvc)
- [x] [Minification of JS and CSS Assets with Maven](https://www.baeldung.com/maven-minification-of-js-and-css-assets)
- [x] [Serve Static Resources with Spring](https://www.baeldung.com/spring-mvc-static-resources)
- [x] [Load a Resource as a String in Spring](https://www.baeldung.com/spring-load-resource-as-string)
