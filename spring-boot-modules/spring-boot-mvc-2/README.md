# Spring Boot MVC

This module contains articles about Spring Web MVC in Spring Boot projects.

## Spring MVC中的功能控制器

Spring 5引入了[WebFlux](https://www.baeldung.com/spring-5-functional-web)，这是一个新的框架，可以让我们使用[反应式](https://www.baeldung.com/spring-reactor)编程模型构建Web应用。

在本教程中，我们将看到如何将这种编程模型应用于Spring MVC的功能控制器。

1. Maven设置

    我们将使用Spring Boot来演示新的API。

    这个框架支持我们熟悉的基于注解的定义控制器的方法。但它也增加了一种新的特定领域语言，提供了一种定义控制器的功能方式。

    从Spring 5.2开始，[Spring Web MVC](https://www.baeldung.com/spring-mvc-tutorial)框架也将提供这种功能化方法。与WebFlux模块一样，RouterFunctions和RouterFunction是这个API的主要抽象。

    因此，让我们从导入[spring-boot-starter-web](https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-web)依赖项开始。

2. RouterFunction与@Controller

    在功能领域，网络服务被称为路由，传统的@Controller和@RequestMapping的概念被RouterFunction取代。

    为了创建我们的第一个服务，让我们采取一个基于注解的服务，看看如何将其转化为功能上的等价物。

    我们将使用一个返回产品目录中所有产品的服务的例子。

    ```java
    @RestController
    public class ProductController {

        @RequestMapping("/product")
        public List<Product> productListing() {
            return ps.findAll();
        }
    }
    ```

    现在，让我们来看看它的功能等价物。见 ProductController.java:

    `public RouterFunction<ServerResponse> productListing(ProductService ps) {}`

    1. 路由定义

        我们应该注意到，在函数式方法中，productListing()方法返回一个RouterFunction而不是响应体。这是对路由的定义，而不是对请求的执行。

        RouterFunction包括路径、请求头信息、一个处理函数，它将被用来生成响应体和响应头信息。它可以包含单个或一组Web服务。

        当我们研究嵌套路由时，我们将更详细地讨论网络服务组。

        在这个例子中，我们使用了RouterFunctions中的静态route()方法来创建一个RouterFunction。一个路由的所有请求和响应属性都可以用这个方法提供。

    2. 请求谓词

        在我们的例子中，我们在route()上使用GET()方法来指定这是一个GET请求，路径以字符串形式提供。

        当我们想指定请求的更多细节时，我们也可以使用RequestPredicate。

        例如，前面的例子中的路径也可以用RequestPredicate指定为。

        `RequestPredicates.path("/product")`

        在这里，我们使用了静态工具RequestPredicates来创建一个RequestPredicate的对象。

    3. 响应

        类似地，ServerResponse包含静态工具方法，用于创建响应对象。

        在我们的例子中，我们使用ok()将HTTP状态200添加到响应头文件中，然后使用body()来指定响应体。

        此外，ServerResponse还支持使用EntityResponse从自定义数据类型构建响应。我们还可以通过RenderingResponse使用Spring MVC的ModelAndView。

    4. 注册路由

        接下来，让我们使用@Bean注解来注册这个路由，将其添加到应用上下文中。

        ```java
        @SpringBootApplication
        public class SpringBootMvcFnApplication {
            @Bean
            RouterFunction<ServerResponse> productListing(ProductController pc, ProductService ps) {
                return pc.productListing(ps);
            }
        }
        ```

        现在，让我们用函数式方法实现一些我们在开发Web服务时遇到的常见用例。

3. 嵌套路由

    在一个应用程序中拥有一堆Web服务，并且根据功能或实体将它们划分为逻辑组，这是很常见的。例如，我们可能希望所有与产品有关的服务，都以./product开始。

    让我们在现有的/product路径上添加另一个路径，以通过它的名字找到一个产品。

    ```java
    public RouterFunction<ServerResponse> productSearch(ProductService ps) {
        return route().nest(RequestPredicates.path("/product"), builder -> {
            builder.GET("/name/{name}", req -> ok().body(ps.findByName(req.pathVariable("name"))));
        }).build();
    }
    ```

    在传统的方法中，我们会通过向@Controller传递路径来实现这一目标。然而，分组Web服务的功能等同于route()的nest()方法。

    在这里，我们首先提供我们想要分组的路径，也就是/product。接下来，我们使用构建器对象来添加路由，就像在前面的例子中一样。

    nest()方法负责将添加到构建器对象的路由与主RouterFunction合并。

4. 错误处理

    另一个常见的用例是要有一个自定义的错误处理机制。我们可以使用route()的onError()方法来定义一个自定义的异常处理程序。

    这等同于在基于注解的方法中使用@ExceptionHandler。但它要灵活得多，因为它可以用来为每组路由定义单独的异常处理程序。

    让我们在前面创建的产品搜索路由中添加一个异常处理程序，以处理未找到产品时抛出的一个自定义异常。

    ```java
    public RouterFunction<ServerResponse> productSearch(ProductService ps) {
        return route()...
        .onError(ProductService.ItemNotFoundException.class,
            (e, req) -> EntityResponse.fromObject(new Error(e.getMessage()))
            .status(HttpStatus.NOT_FOUND)
            .build())
        .build();
    }
    ```

    onError()方法接受Exception类对象，并期望从功能实现中得到一个ServerResponse。

    我们使用了EntityResponse，它是ServerResponse的一个子类型，在这里从自定义的数据类型Error构建一个响应对象。然后我们添加状态并使用EntityResponse.build()返回一个ServerResponse对象。

5. 过滤器

    实现认证以及管理跨领域问题（如日志和审计）的一个常见方法是使用过滤器。过滤器被用来决定是否继续或中止对请求的处理。

    让我们举个例子，我们想要一个新的路由，将一个产品添加到目录中。

    ```java
    public RouterFunction<ServerResponse> adminFunctions(ProductService ps) {
        return route().POST("/product", req -> ok().body(ps.save(req.body(Product.class))))
        .onError(IllegalArgumentException.class, 
            (e, req) -> EntityResponse.fromObject(new Error(e.getMessage()))
            .status(HttpStatus.BAD_REQUEST)
            .build())
            .build();
    }
    ```

    由于这是一个管理函数，我们还想对调用服务的用户进行认证。

    我们可以通过在route()上添加一个filter()方法来做到这一点。

    ```java
    public RouterFunction<ServerResponse> adminFunctions(ProductService ps) {
    return route().POST("/product", req -> ok().body(ps.save(req.body(Product.class))))
        .filter((req, next) -> authenticate(req) ? next.handle(req) : 
        status(HttpStatus.UNAUTHORIZED).build())
        ....;
    }
    ```

    在这里，由于filter()方法提供了请求以及next handler，我们用它来做一个简单的认证，如果成功的话，允许产品被保存，如果失败的话，则向客户端返回一个UNAUTHORIZED错误。

6. Cross-Cutting关注

    有时，我们可能想在一个请求之前、之后或周围执行一些行动。例如，我们可能想记录传入请求和传出响应的一些属性。

    让我们在每次应用程序找到匹配的传入请求时记录一条语句。我们将使用route()上的before()方法来做这个。

    ```java
    @Bean
    RouterFunction<ServerResponse> allApplicationRoutes(ProductController pc, ProductService ps) {
        return route()...
        .before(req -> {
            LOG.info("Found a route which matches " + req.uri()
                .getPath());
            return req;
        })
        .build();
    }
    ```

    同样地，我们可以在请求被处理后，使用route()上的after()方法添加一个简单的日志语句。

    ```java
    @Bean
    RouterFunction<ServerResponse> allApplicationRoutes(ProductController pc, ProductService ps) {
        return route()...
        .after((req, res) -> {
            if (res.statusCode() == HttpStatus.OK) {
                LOG.info("Finished processing request " + req.uri()
                    .getPath());
            } else {
                LOG.info("There was an error while processing request" + req.uri());
            }
            return res;
        })          
        .build();
    }
    ```

## ? 用多种MIME类型测试REST

1. 概述

    本文将重点测试具有多种媒体类型/表现形式的REST服务。

    我们将编写集成测试，能够在API支持的多种类型的代表之间切换。我们的目标是能够运行完全相同的测试，消耗完全相同的服务URI，只是要求不同的媒体类型。

2. 目标

    任何REST API都需要使用一种或多种媒体类型将其资源作为表示方法公开。客户端将设置接受头来选择它从服务中要求的表示类型。

    由于资源可以有多种表现形式，服务器将必须实现一种机制，负责选择正确的表现形式。这也被称为内容协商（Content Negotiation）。

    因此，如果客户端要求application/xml，那么它应该得到资源的XML表示。而如果它要求application/json，那么它应该得到JSON。

3. 测试基础设施

    我们将首先为marshaller定义一个简单的接口。这将是主要的抽象，允许测试在不同的媒体类型之间切换。

    见 TestMarshallerFactory.java

    让我们来看看这个。

    - 首先，这里使用了Spring 3.1中引入的新的环境抽象--关于这一点的更多信息，请查看[关于使用Spring属性的详细文章](https://www.baeldung.com/properties-with-spring)
    - 我们从环境中获取test.mime属性，并使用它来决定创建哪个marshaller--这里有一些Java 7对String语法的转换。
    - 接下来，默认的marshaller将是Jackson的marshaller，因为该属性根本没有被定义，所以它支持JSON。
    - 最后，这个BeanFactory只在测试场景中起作用，因为我们正在使用@Profile支持，也是在Spring 3.1中引入的。

    就是这样--该机制能够根据test.mime属性的值在不同的marshaller之间切换。

4. JSON 和 XML Marshallers

    接下来，我们将需要实际的marshaller实现--每种支持的媒体类型都有一个。

    对于JSON，我们将使用Jackson作为基础库。

    见 JacksonMarshaller.java

    对于XML支持，marshaller使用XStream。

    见 XStreamMarshaller.java

    请注意，这些marshaller不是Spring Bean本身。原因是它们将被TestMarshallerFactory引导到Spring上下文中；没有必要直接让它们成为组件。

5. 用JSON和XML来消费服务

    在这一点上，我们应该能够针对部署的服务运行一个完整的集成测试。使用marshaller很简单：我们将在测试中注入一个IMarshaller。

    ```java
    @ActiveProfiles({ "test" })
    public abstract class SomeRestLiveTest {

        @Autowired
        private IMarshaller marshaller;

        // tests
        ...

    }
    ```

    Spring将根据test.mime属性的值来决定要注入的确切的marshaller。

    如果我们没有为这个属性提供一个值，TestMarshallerFactory将简单地返回到默认的marshaller--JSON marshaller。

6. Maven和Jenkins

    如果Maven被设置为针对已经部署的REST服务运行集成测试，那么我们可以用以下方式运行它。

    `mvn test -Dtest.mime=xml`

    或者，如果该构建使用Maven生命周期中的集成测试阶段。

    `mvn integration-test -Dtest.mime=xml`

    关于如何设置Maven构建以运行集成测试的更多细节，请参见《[用Maven进行集成测试](https://www.baeldung.com/integration-testing-with-the-maven-cargo-plugin)》。

    使用Jenkins，我们必须用配置作业。

    `This build is parametrized`

    并添加字符串参数：test.mime=xml。

    一个常见的Jenkins配置是让作业针对部署的服务运行同一组集成测试--一个用XML表示，另一个用JSON表示。

## ? 用Postman集合测试Web APIs

1. 简介

    为了彻底测试一个网络API，我们需要某种网络客户端来访问API的端点。Postman是一个独立的工具，它通过从服务外部发出HTTP请求来测试网络API。

    当使用Postman时，我们不需要仅仅为了测试而编写任何HTTP客户端的基础结构代码。相反，我们创建称为集合的测试套件，让Postman与我们的API进行交互。

    在本教程中，我们将看到如何创建一个可以测试REST API的Postman集合。

2. 设置

    在我们开始使用我们的集合之前，我们需要设置好环境。

    安装Postman

    - Postman可用于Linux、Mac和Windows。该工具可以从[Postman网站](https://www.getpostman.com/downloads/)下载和安装。

    运行服务器

    - Postman需要一个实时的HTTP服务器来处理其请求。在本教程中，我们将使用一个以前的Baeldung项目，spring-boot-rest，它可以在 spring-boot-modules/spring-boot-mvc-2 内找到。

    - 正如我们从标题所猜测的，spring-boot-rest是一个Spring Boot应用。我们用Maven目标安装来构建该应用。建好后，我们用自定义Maven目标`spring-boot:run`启动服务器。

      - 为了验证服务器是否在运行，我们可以在浏览器中点击这个URL。

        `http://localhost:8080/spring-boot-rest/auth/foos`

      - 该服务使用一个内存数据库。服务器停止时，所有记录都会被清除。

3. 创建一个Postman集合

    Postman中的一个集合是一系列的HTTP请求。Postman保存了请求的每一个方面，包括头文件和消息体。因此，我们可以按顺序运行这些请求，作为半自动的测试。

    让我们从创建一个新的集合开始。我们可以点击新建按钮上的下拉箭头，选择集合。

    postman new menu

    - 当出现CREATE A NEW COLLECTION对话框时，我们可以将我们的集合命名为 "foo API test"。最后，我们点击创建按钮，看到我们的新集合出现在左边的列表中。

    创建的集合

    - 一旦我们的集合被创建，我们可以将光标悬停在它上面，显示出两个菜单按钮。箭头按钮打开一个向右拉动的面板，提供对集合运行器的访问。反之，省略号按钮打开一个下拉菜单，包含对集合的一些操作。

4. 添加一个POST请求

    1. 创建一个新的请求

        现在我们有一个空的集合，让我们添加一个点击我们的API的请求。具体来说，让我们向URI /auth/foos发送一个POST消息。要做到这一点，我们打开集合上的省略号菜单，选择添加请求。

        当SAVE REQUEST对话框出现时，让我们提供一个描述性的名字，比如 "add foo"。然后，点击按钮Save to foo API test。

        一旦请求被创建，我们可以看到，我们的集合显示了一个请求。然而，如果我们的集合还没有被扩展，那么我们还不能看到这个请求。在这种情况下，我们可以点击集合来扩展它。

        现在，我们应该看到新的请求列在我们的集合下。我们可以观察到，这个新的请求，默认情况下，是一个HTTP GET，这不是我们想要的。我们将在下一节中解决这个问题。

    2. 编辑请求

        要编辑这个请求，让我们点击它，从而将其加载到请求编辑器标签中。

        尽管请求编辑器有许多选项，但我们现在只需要其中的几个。

        首先，让我们使用下拉菜单，将方法从GET改为POST。

        其次，我们需要一个URL。在方法下拉框的右边有一个请求URL的文本框。所以，让我们现在就输入它。

        `http://localhost:8080/spring-boot-rest/auth/foos`

        最后一步是提供一个信息体。在URL地址下面是一排标签头。我们将点击 "Body" 标签头，进入正文编辑器。

        在Body标签中，就在文本区的上方，有一排单选按钮和一个下拉菜单。这些控制请求的格式和内容类型。

        我们的服务接受JSON数据，所以我们选择raw单选按钮。在右边的下拉菜单中，我们应用JSON（application/json）内容类型。

        一旦编码和内容类型被设置，我们就把我们的JSON内容添加到文本区。

        ```json
        {
            "name": "Transformers"
        }
        ```

        最后，让我们确保按Ctrl-S或点击 "Save" 按钮来保存我们的修改。保存按钮位于发送按钮的右边。一旦我们保存，我们可以在左边的列表中看到请求已经被更新为POST。

5. 运行请求

    1. 运行一个单一的请求

        要运行一个单一的请求，我们只要点击URL地址右边的Send按钮。一旦我们点击发送，响应面板将在请求面板的下面打开。可能需要向下滚动才能看到它。

        让我们检查一下我们的结果。具体来说，在标题栏中，我们看到我们的请求成功了，状态为201创建。此外，响应体显示我们的变形金刚记录收到的id是1。

    2. 使用集合运行器

        与发送按钮相比，集合运行器可以执行整个集合。

        当我们点击运行按钮时，集合运行器在一个新窗口中打开。因为我们是从我们的集合中启动它的，所以运行器已经初始化到我们的集合中了。

        集合运行器提供了影响测试运行的选项，但我们在这个练习中不需要它们。让我们直接进入底部的Run foo API测试按钮并点击它。

        当我们运行集合时，视图变为运行结果。在这个视图中，我们看到一个测试的列表，其中绿色标记为成功，红色标记为失败。

        尽管我们的请求已经发出，但运行器显示有零个测试通过，零个测试失败。这是因为我们还没有向我们的请求添加测试。

6. 测试响应

    1. 向请求中添加测试

        要创建一个测试，让我们回到请求编辑面板，在那里我们建立了我们的POST方法。我们点击位于URL下面的Tests标签。当我们这样做时，测试面板就会出现。

        在测试面板中，我们编写JavaScript，当从服务器收到响应时，这些JavaScript将被执行。

        Postman提供了内置变量，提供对请求和响应的访问。此外，可以使用require()语法导入一些JavaScript库。

        在本教程中，有太多的脚本功能可以涵盖。然而，官方的Postman文档是关于这个主题的优秀资源。

        让我们继续在我们的请求中添加三个测试。

        ```js
        pm.test("success status", () => pm.response.to.be.success );
        pm.test("name is correct", () => 
        pm.expect(pm.response.json().name).to.equal("Transformers"));
        pm.test("id was assigned", () => 
        pm.expect(pm.response.json().id).to.be.not.null );
        ```

        我们可以看到，这些测试利用了Postman提供的全局pm模块。特别是，这些测试使用了pm.test(), pm.expect(), 和 pm.response。

        pm.test()函数接受一个标签和一个断言函数，如 expect()。我们使用pm.expect()来断言响应JSON内容的条件。

        pm.response对象提供了对从服务器返回的响应的各种属性和操作的访问。可用的属性包括响应状态和JSON内容，等等。

        像往常一样，我们用Ctrl-S或保存按钮保存我们的修改。

    2. 运行测试

        现在我们有了我们的测试，让我们再次运行这个请求。按下 "Send" 按钮，在响应面板的 "Test Results" 标签中显示结果。

        同样，集合运行器现在也显示我们的测试结果。具体来说，左上方的摘要显示了最新的通过和失败的总数。摘要下面是一个列表，显示每个测试的状态。

    3. 查看Postman控制台

        Postman控制台是一个创建和调试脚本的有用工具。我们可以在View菜单下找到控制台，项目名称为Show Postman Console。启动后，控制台会在一个新窗口中打开。

        当控制台打开时，它会记录所有的HTTP请求和响应。此外，当脚本使用console.log()时，Postman Console会显示这些信息。

7. 创建一个请求序列

    到目前为止，我们已经关注了一个单一的HTTP请求。现在，让我们看看我们可以对多个请求做什么。通过将一系列的请求串联起来，我们可以模拟和测试一个客户机-服务器工作流程。

    在本节中，让我们应用我们所学到的知识来创建一个请求序列。具体来说，我们将在我们已经创建的POST请求之后再添加三个请求来执行。这些将是一个GET，一个DELETE，最后是另一个GET。

    1. 捕获变量中的响应值

        在我们创建新的请求之前，让我们对现有的POST请求做一个修改。因为我们不知道服务器会给每个foo实例分配哪个id，我们可以用一个变量来捕获服务器返回的id。

        为了捕获这个id，我们将在POST请求的测试脚本的结尾处再添加一行。

        global: `pm.globals.set("id", pm.response.json().id);`

        collection: `pm.collectionVariables.set("id", pm.response.json().id);`

        pm.globals.set()函数接收一个值并将其分配给一个临时变量(全局)。在本例中，我们要创建一个id变量来存储我们对象的id值。一旦设置好，我们就可以在以后的请求中访问这个变量。

    2. 添加一个GET请求

        现在，使用前几节的技术，让我们在POST请求之后添加一个GET请求。

        通过这个GET请求，我们将检索POST请求所创建的同一个foo实例。让我们把这个GET请求命名为 "get a foo"。

        这个GET请求的URL是。

        `http://localhost:8080/spring-boot-rest/auth/foos/{{id}}`

        在这个URL中，我们引用的是之前在POST请求中设置的id变量。因此，GET请求应该检索到POST所创建的同一个实例。

        当变量出现在脚本之外时，要用双括号语法`{{id}}`来引用。

        由于GET请求没有正文，让我们直接进入测试标签。因为测试是相似的，我们可以复制POST请求的测试，然后做一些修改。

        首先，我们不需要再次设置id变量，所以我们不复制这一行。

        第二，我们知道这次要期待哪个id，所以我们来验证这个id。我们可以用id变量来做这件事。

        ```js
        pm.test("success status", () => pm.response.to.be.success );
        pm.test("name is correct", () => 
        pm.expect(pm.response.json().name).to.equal("Transformers"));
        pm.test("id is correct", () => 
        pm.expect(pm.response.json().id).to.equal(pm.variables.get("id")) );
        ```

        由于双括号语法不是有效的JavaScript，我们使用pm.variables.get()函数来访问id变量。

        最后，让我们像以前一样保存这些修改。

    3. 添加一个DELETE请求

        接下来，我们将添加一个DELETE请求，将foo对象从服务器上删除。

        我们将在GET之后添加一个新的请求，并将其方法设置为DELETE。我们可以把这个请求命名为 "delete a foo"。

        删除的URL和GET的URL是一样的。

        `http://localhost:8082/spring-boot-rest/auth/foos/{{id}}`

        响应不会有一个可以测试的主体，但我们可以测试响应代码。因此，DELETE请求将只有一个测试。

        `pm.test("success status", () => pm.response.to.be.success );`

    4. ? 验证DELETE

        最后，让我们再添加一份GET请求的副本，以验证DELETE是否真的有效。这一次，让我们复制我们的第一个GET请求，而不是从头开始创建一个请求。

        要复制一个请求，我们在请求上点击右键来显示下拉菜单。然后，我们选择Duplicate。

        复制的请求将在其名字后面加上Copy这个词。让我们把它重命名为 "verify delete" 以避免混淆。重命名选项可以通过右键点击请求来实现。

        默认情况下，重复的请求会紧随原始请求之后出现。因此，我们需要把它拖到DELETE请求的下面。

        最后一步是修改测试。然而，在这之前，让我们借机看看一个失败的测试。

        我们已经复制了GET请求并把它移到了DELETE的后面，但我们还没有更新测试。因为DELETE请求应该已经删除了该对象，所以测试应该失败。

        让我们确保保存我们所有的请求，然后在集合运行器中点击重试。正如预期的那样，我们的测试已经失败了。

        现在，我们的短暂迂回已经完成，让我们来修复测试。

        通过回顾失败的测试，我们可以看到服务器的响应状态是500。因此，我们要在我们的测试中改变这个状态。

        此外，通过在Postman Console中查看失败的响应，我们了解到该响应包括一个原因属性。此外，cause属性包含字符串 "No value present"。我们也可以对此进行测试。

        ```js
        pm.test("status is 500", () => pm.response.to.have.status(500) );
        pm.test("no value present", () => 
        pm.expect(pm.response.json().cause).to.equal("No value present"));
        ```

    5. 运行完整的集合

        现在我们已经添加了所有的请求，让我们在集合运行器中运行整个集合。

        如果一切都按计划进行，我们应该有九个成功的测试。

8. 导出和导入集合

    虽然Postman将我们的集合存储在一个私有的、本地的位置，但我们可能想分享这个集合。为了做到这一点，我们将集合导出到一个JSON文件。

    导出命令在集合的省略号菜单中可用。当提示我们选择JSON文件的版本时，让我们选择最新的推荐版本。

    在我们选择了文件版本后，Postman会提示我们为导出的集合提供一个文件名和位置。例如，我们可以选择GitHub项目中的一个文件夹。

    要导入一个先前导出的集合，我们使用导入按钮。我们可以在Postman主窗口的工具条上找到它。当Postman提示文件的位置时，我们可以导航到我们希望导入的JSON文件。

    值得注意的是，Postman并不跟踪导出的文件。因此，在我们重新导入集合之前，Postman不会显示外部变化。

9. 总结

    在这篇文章中，我们已经使用Postman为REST API创建了半自动化的测试。虽然本文是对Postman的基本功能的介绍，但我们几乎没有触及其功能的表面。[Postman的在线文档](https://learning.getpostman.com/docs/postman/launching_postman/installation_and_updates/)是深入探索的宝贵资源。

## Spring Boot消费和产生JSON

在本教程中，我们将演示如何使用Spring Boot构建一个REST服务来消费和生产JSON内容。

我们还将看看我们如何轻松地采用RESTful HTTP语义。

For simplicity, we won't include a [persistence layer](https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa), but [Spring Data](https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa) also makes this easy to add.

1. REST服务

    在Spring Boot中编写JSON REST服务很简单，因为当Jackson在classpath上时，这是它的默认意见。

    ```java
    @RestController
    @RequestMapping("/students")
    public class StudentController {

        @Autowired
        private StudentService service;

        @GetMapping("/{id}")
        public Student read(@PathVariable String id) {
            return service.find(id);
        }
    ...
    ```

    通过用[@RestController](https://www.baeldung.com/spring-controller-vs-restcontroller)注解我们的StudentController，我们已经告诉Spring Boot将读取方法的返回类型写入响应体中。由于我们在类的层面上也有一个@RequestMapping，所以对于我们添加的更多公共方法也是如此。

    虽然简单，但这种方法缺乏HTTP语义。例如，如果我们没有找到要求的学生，会发生什么？我们可能不希望返回200或500状态代码，而是返回404。

    让我们来看看如何对HTTP响应本身进行更多的控制，并反过来给我们的控制器添加一些典型的RESTful行为。

2. 创建

    当我们需要控制响应的其他方面时，比如状态代码，我们可以返回一个ResponseEntity。

    见 StudentController.create()

    在这里，我们要做的不仅仅是在响应中返回创建的学生。我们还将响应一个语义清晰的HTTP状态，如果创建成功，还将响应一个指向新资源的URI。

3. GET

    如前所述，如果我们想读取一个学生，如果我们找不到这个学生，返回一个404，语义上会更清楚。

    见 StudentController.create()

    在这里，我们可以清楚地看到与我们最初的read()实现的区别。

    这样一来，学生对象将被正确地映射到响应体中，并同时以适当的状态返回。

4. 更新

    更新与创建非常相似，只是它被映射为PUT而不是POST，而且URI包含我们要更新的资源的ID。

    见 StudentController.update()

5. 删除

    删除操作被映射到DELETE方法。URI也包含资源的ID。

    见 StudentController.delete()

    我们没有实现具体的错误处理，因为delete()方法实际上是通过抛出一个异常而失败的。

6. 总结

    在这篇文章中，我们学习了如何在用Spring Boot开发的典型CRUD REST服务中消费和生产JSON内容。此外，我们还演示了如何实现适当的响应状态控制和错误处理。

    为了保持简单，我们这次没有进入持久化，但[Spring Data REST](https://www.baeldung.com/spring-data-rest-intro)提供了一种快速有效的方法来构建RESTful数据服务。

## 在Postman中为每个请求添加标题

1. 概述

    在本教程中，我们将学习如何通过使用预请求脚本为Postman中的每个请求添加HTTP头信息。

2. HTTP头信息

    在深入实施之前，让我们回顾一下什么是[HTTP头](https://www.baeldung.com/spring-rest-http-headers)信息。

    在HTTP请求中，头信息是在客户端和服务器HTTP通信之间提供额外信息的字段。HTTP头有一个键值对的格式，它们可以被附加到请求和响应中。

    授权、内容类型和cookies是可以由HTTP标头提供的元数据的例子。

    比如说。

    ```txt
    Authorization: Bearer YmFyIiwiaWF0IjoxN;
    Content-Type: application/json;
    Cookie: foo=bar;
    ```

    我们将使用Postman预请求脚本功能，通过执行JavaScript代码来设置头文件。

3. 运行服务器

    使用 spring-boot-json(SpringBootStudentsApplication) 作为示范。该应用由一个控制器StudentController组成，它接受对Student java模型的CRUD操作。

    我们必须使用Maven安装命令安装所有依赖项，然后运行SpringBootStudentsApplication文件，它将在8080端口启动Tomcat服务器。

    使用Postman，我们可以通过向以下端点发送GET请求并期待JSON响应来确认服务器的运行。

    `http://localhost:8080/students/`

    现在我们验证了服务器正在运行，我们可以以编程方式向Postman发送的请求添加HTTP头信息。

4. 用预请求脚本添加头信息

    要在Postman中用预请求脚本为HTTP请求添加头信息，我们需要访问Postman的JavaScript API对象pm所提供的请求数据。

    我们可以通过调用pm.request对象对请求元数据进行操作；因此，我们可以在发送请求之前添加、修改和删除HTTP头信息。

    正如前面所讨论的，HTTP头文件有一个键值对的格式。Postman JavaScript API希望在向请求中添加头信息时能同时提供一个键和一个值。

    我们可以通过使用name: value格式的字符串来添加一个头信息。

    `pm.request.headers.add("foo: bar");`

    我们还可以通过以下方式传递一个带有key和value属性的JavaScript对象。

    ```js
    pm.request.headers.add({
    key: "foo",
    value: "bar"
    });
    ```

    然而，根据Postman文档，我们可以向头对象添加额外的属性，如id、name和disabled，这将扩展Postman JavaScript运行环境中的功能。

    现在，让我们看看这个动作。首先，我们将为一个单独的Postman请求添加一个脚本；然后，我们将为整个集合添加头信息。

    1. 单个请求

        我们可以通过使用预请求脚本来为Postman中的单个请求添加头信息。我们可以参考上一节所示的实现；然而，我们将关注第二个实现，在这个实现中，我们传递一个JavaScript对象，这样我们就可以添加扩展功能的额外属性。

        在Postman窗口的pre-request Script中，我们添加以下脚本，表明客户端期望得到一个json类型的响应。

        ```js
        pm.request.headers.add({
            key: "Accept",
            value: "application/json"
        });
        ```

        现在，我们通过点击发送按钮来发送一个GET请求。一旦请求被发送，我们必须打开Postman控制台（通常是通过点击左下角的控制台按钮），并展开我们最近的请求来查看请求头文件部分。

        在控制台中，我们可以看到接受。"application/json" 头，表明它被脚本成功地连接到GET请求中。此外，我们可以检查响应的主体和状态代码，以确认请求是否成功。

        为了进一步验证预请求脚本，我们可以添加以下头信息，并期望得到一个空的响应，同时状态码为406 Not Acceptable。

        ```js
        pm.request.headers.add({ 
            key: "Accept",
            value: "image/*" 
        });
        ```

    2. 集合

        同样地，我们可以用预请求脚本为整个集合添加HTTP头信息。

        首先，我们将创建一个学生API集合，用Postman测试我们的API端点，并确认每个请求都包含我们用预请求脚本添加的头文件。

        在Postman中，我们可以通过进入左边的Collection菜单选项来分组网络API端点。然后，我们点击加号按钮，创建一个新的集合，命名为学生API集合。

        注意，我们还向我们的集合添加了两个端点：`http://localhost:8080/students/` 和 `http://localhost:8080/students/2`。

        与单个请求类似，我们可以通过在左边的菜单上选择学生API集合，并进入Pre-request Script预请求脚本标签，将预请求脚本添加到我们的集合中。现在，我们可以添加我们的脚本。

        在运行集合之前，我们必须确保删除我们在上一节中最初添加的预请求脚本。否则，HTTP头信息将被请求脚本中指定的头信息所覆盖，而集合层面的头信息将被驳回。

        现在，我们准备运行我们的集合。点击集合栏上的运行按钮，运行器标签将自动打开。

        Runner选项卡允许我们对请求进行排序，从我们的集合中选择或取消选择请求，并指定其他设置。点击运行学生API集合来执行我们的请求。

        一旦整个集合完成，我们可以看到执行的顺序和测试结果（如果有的话）。然而，我们要确保我们的HTTP头是我们请求的一部分，我们可以通过打开Postman控制台来确认这一点。

        再一次，我们可以在控制台中展开我们请求的请求头部分，确认我们的预请求脚本添加了接受头。此外，你可以通过查看状态代码和响应体来确认请求是否成功。

## Relevant Articles

- [x] [Functional Controllers in Spring MVC](https://www.baeldung.com/spring-mvc-functional-controllers)
- [x] [Testing REST with multiple MIME types](https://www.baeldung.com/testing-rest-api-with-multiple-media-types)
- [x] [Testing Web APIs with Postman Collections](https://www.baeldung.com/postman-testing-collections)
- [x] [Spring Boot Consuming and Producing JSON](https://www.baeldung.com/spring-boot-json)
- [x] [Serve Static Resources with Spring](https://www.baeldung.com/spring-mvc-static-resources)
- [x] [Add Header to Every Request in Postman](https://www.baeldung.com/postman-add-headers-pre-request)
- More articles: [[prev -->]](../spring-boot-mvc/README.md)
