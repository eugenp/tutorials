# Spring Core

This module contains articles about core Spring functionality

## Guide to Spring @Autowired

1. 概述

    从Spring2.5开始，框架引入了注释驱动的依赖注入。此功能的主要注释是@Autowired。它允许Spring解析协作bean并将其注入到我们的bean中(resolve and inject collaborating beans into our bean.)。

    进一步阅读：

    - [x] [Spring组件扫描](https://www.baeldung.com/spring-component-scanning)
    - [x] [使用Spring反转控制和依赖注入简介](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)

    在本教程中，我们将首先了解如何启用自动连接以及自动连接bean的各种方法。之后，我们将讨论使用@Qualifier注释解决bean冲突，以及潜在的异常场景。

2. 启用@Autowired Annotations

    Spring框架支持自动依赖注入。换句话说，通过在Spring配置文件中声明所有bean依赖项，Spring容器可以自动连接协作bean之间的关系。这称为Spring bean自动装配(autowiring)。

    要在我们的应用程序中使用基于Java的配置，让我们启用注释驱动注入来加载Spring配置：

    autowire.sample/AppConfig.java

    或者，[`<context:annotation-config>`](https://www.baeldung.com/spring-contextannotation-contextcomponentscan#:~:text=The%20%3Ccontext%3Aannotation%2Dconfig,annotation%2Dconfig%3E%20can%20resolve.) 注释主要用于激活Spring XML文件中的依赖注入注释。

    此外，SpringBoot引入了[@SpringBootApplication](https://www.baeldung.com/spring-boot-annotations#spring-boot-application)注释。此单个注释等效于使用@Configuration、@EnableAutoConfiguration和@ComponentScan。

    让我们在应用程序的主类中使用此注释：

    ```java
    @SpringBootApplication
    public class App {
        public static void main(String[] args) {
            SpringApplication.run(App.class, args);
        }
    }
    ```

    因此，当我们运行这个SpringBoot应用程序时，它将自动扫描当前包及其子包中的组件。因此，它将在Spring的Application Context中注册它们，并允许我们使用@Autowired注入bean。

3. 使用@Autowired

    启用注释注入后，我们可以对properties属性、setter和constructors构造函数使用自动连接。

    1. @Autowired 属性

        让我们看看如何使用@Autowired注释属性。这消除了对getter和setter的需要。

        首先，让我们定义一个fooFormatterbean：

        ```java
        @Component("fooFormatter")
        public class FooFormatter {
            public String format() {
                return "foo";
            }
        }
        ```

        然后，我们将在字段定义上使用@Autowired将这个bean注入FooService bean：

        ```java
        @Component
        public class FooService {  
            @Autowired
            private FooFormatter fooFormatter;
        }
        ```

        因此，Spring在创建FooService时注入fooFormatter。

    2. @Autowired on Setters

        现在，让我们尝试在setter方法上添加@Autowired注释。

        在以下示例中，创建FooService时，将使用FooFormatter实例调用setter方法：

        ```java
        public class FooService {
            private FooFormatter fooFormatter;
            @Autowired
            public void setFormatter(FooFormatter fooFormatter) {
                this.fooFormatter = fooFormatter;
            }
        }
        ```

    3. @Autowired 构造函数

        最后，让我们在构造函数上使用@Autowired。

        我们将看到，Spring将FooFormatter的实例作为参数注入FooService构造函数：

        ```java
        public class FooService {
            private FooFormatter fooFormatter;
            @Autowired
            public FooService(FooFormatter fooFormatter) {
                this.fooFormatter = fooFormatter;
            }
        }
        ```

4. @Autowired和Optional依赖项

    构建bean时，@Autowired依赖项应该可用。否则，如果Spring无法解析用于连接的bean，它将抛出异常。

    ```java
    public class FooService {
        @Autowired
        private FooDAO dataAccessor; 
    }
    ```

    因此，它会阻止Spring容器成功启动，但表单除外：

    run App.java 报错：

    ```log
    Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: 
    No qualifying bean of type [com.autowire.sample.FooDAO] found for dependency: 
    expected at least 1 bean which qualifies as autowire candidate for this dependency. 
    Dependency annotations: 
    {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    ```

    为了解决这个问题，我们需要声明一个required类型的bean：

    ```java
    public class FooService {
        @Autowired(required = false)
        private FooDAO dataAccessor; 
    }
    ```

5. Autowire 消除歧义

    默认情况下，Spring按类型解析@Autowired条目。如果容器中有多个相同类型的bean可用，框架将抛出致命异常。

    为了解决这个冲突，我们需要明确告诉Spring要注入哪个bean。

    1. Autowiring by @Qualifier

        例如，让我们看看如何使用[@Qualifier](https://www.baeldung.com/spring-qualifier-annotation)注释来指示所需的bean。

        首先，我们将定义2个格式化程序类型的bean：

        ```java
        @Component("fooFormatter")
        public class FooFormatter implements Formatter {
            public String format() {
                return "foo";
            }
        }
        @Component("barFormatter")
        public class BarFormatter implements Formatter {
            public String format() {
                return "bar";
            }
        }
        ```

        现在，让我们尝试将Formatterbean注入FooService类：

        ```java
        public class FooService {
            @Autowired
            private Formatter formatter;
        }
        ```

        在我们的示例中，Spring容器有两个具体的Formatter实现。因此，Spring在构造FooService时将抛出 NoUniqueBeanDefinitionException 异常：

        ```log
        Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
        No qualifying bean of type [com.autowire.sample.Formatter] is defined: 
        expected single matching bean but found 2: barFormatter,fooFormatter
        ```

        我们可以通过使用@Qualifier注释缩小实现范围来避免这种情况：

        ```java
        public class FooService {
            @Autowired
            @Qualifier("fooFormatter")
            private Formatter formatter;
        }
        ```

        当存在同一类型的多个bean时，最好使用@Qualifier来避免歧义。

        请注意，@Qualifier注释的值与FooFormatter实现的@Component注释中声明的名称匹配。

    2. 通过自定义限定符自动布线

        Spring还允许我们创建自己的自定义@Qualifier注释。为此，我们应该为@Qualifier注释提供定义：

        参见：autowire.sample/FormatterType.java

        然后，我们可以在各种实现中使用FormatterType来指定自定义值：

        参见：autowire.sample/FooFormatter.java

        参见：autowire.sample/BarFormatter.java

        最后，我们的自定义限定符注释可以用于自动注入：

        ```java
        @Component
        public class FooService {  
            @Autowired
            @FormatterType("Foo")
            private Formatter formatter;
        }
        ```

        @Target meta-annotation 元注释中指定的值限制了限定符的应用位置，在我们的示例中，限定符是字段、方法、类型和参数(FIELD, METHOD, TYPE, PARAMETER)。

    3. 按名称自动注入

        Spring使用bean的名称作为默认限定符值。它将检查容器，并查找一个名称与属性完全相同的bean，以自动连接它。

        因此，在我们的示例中，Spring将fooFormatter属性名与FooFormatster实现相匹配。因此，它在构造FooService时注入特定的实现：

        ```java
        public class FooService {
            @Autowired 
            private Formatter fooFormatter; 
        }
        ```

6. 结论

    在这篇文章中，我们讨论了自动布线和使用它的不同方法。我们还研究了解决两种常见的自动布线异常的方法，它们是由缺失的Bean或不明确的Bean注入引起的。

7. 自动注入问题

    Factory method injection should be used in "@Configuration" classes (java:S3305)

    当使用@Autowired时，在实例化类时需要解决依赖关系，这可能会导致bean的早期初始化，或者导致上下文查找不应该查找bean的位置。为了避免这个棘手的问题并优化上下文加载的方式，应该尽可能晚地请求依赖项。这意味着对仅在单个@Bean方法中使用的依赖项，应该使用参数注入而不是字段注入。

    例外情况：该规则忽略了应用程序中其他方法直接调用的方法中使用的字段（与Spring框架自动调用相反），这样直接调用方就不必自己提供依赖项。

    Fields used in methods that are called directly by other methods in the application (as opposed to being invoked automatically by the Spring framework) are ignored by this rule so that direct callers don’t have to provide the dependencies themselves.

    - [ ] 示例？如一些未重写的父类方法直接调用？。

## Spring Bean Scopes 快速指南

1. 概述

    在这个快速教程中，我们将了解Spring框架中不同类型的bean作用域。

    一个Bean的作用域定义了该Bean的生命周期和在我们使用它的情况下的可见性。

    Spring框架的最新版本定义了6种类型的作用域。

    - singleton
    - prototype
    - request
    - session
    - application
    - websocket

    最后提到的四个作用域，即request、session、application和websocket，只有在Web-aware应用程序中才可用。

    进一步阅读。

    [什么是Spring Bean？](https://www.baeldung.com/spring-bean)

    关于什么是Spring Bean的快速而实用的解释。

    [Spring Bean注解](https://www.baeldung.com/spring-bean-annotations)

    了解如何以及何时使用标准Spring Bean注释--@Component、@Repository、@Service和@Controller。

2. Singleton范围

    当我们用singleton scope定义Bean时，容器会创建该Bean的一个实例；所有对该Bean名称的请求都会返回相同的对象，该对象被缓存起来。对该对象的任何修改都将反映在对该 bean 的所有引用中。如果没有指定其他作用域，这个作用域是默认值。

    让我们创建一个Person实体来示范作用域的概念。

    参见 scopes/Person.java

    之后，我们通过使用@Scope注解将Bean定义为单子范围。

    ```java
    @Bean
    @Scope("singleton")
    public Person personSingleton() {
        return new Person();
    }
    ```

    我们还可以用一个常量来代替字符串值，方法如下。

    `@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)`

    现在我们可以继续写一个测试，表明引用同一bean的两个对象会有相同的值，即使其中只有一个对象改变了它们的状态，因为它们都引用了同一个bean实例。

    ```java
    private static final String NAME = "John Smith";

    @Test
    public void givenSingletonScope_whenSetName_thenEqualNames() {
        ApplicationContext applicationContext = 
        new ClassPathXmlApplicationContext("scopes.xml");

        Person personSingletonA = (Person) applicationContext.getBean("personSingleton");
        Person personSingletonB = (Person) applicationContext.getBean("personSingleton");

        personSingletonA.setName(NAME);
        Assert.assertEquals(NAME, personSingletonB.getName());

        ((AbstractApplicationContext) applicationContext).close();
    }
    ```

    这个例子中的scopes.xml文件应该包含所用Bean的xml定义。

    `<bean id="personSingleton" class="org.baeldung.scopes.Person" scope="singleton"/>`

3. Prototype作用域

    具有原型作用域的Bean将在每次从容器中请求它时返回一个不同的实例。它是通过在Bean定义中的@Scope注解中设置值prototype来定义的。

    ```java
    @Bean
    @Scope("prototype")
    public Person personPrototype() {
        return new Person();
    }
    ```

    我们也可以像对单子作用域那样使用一个常量。

    `@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)`

    现在我们将写一个与之前类似的测试，显示两个用原型作用域请求相同Bean名称的对象。它们将有不同的状态，因为它们不再是指同一个Bean实例。

    ```java
    private static final String NAME = "John Smith";
    private static final String NAME_OTHER = "Anna Jones";

    @Test
    public void givenPrototypeScope_whenSetNames_thenDifferentNames() {
        ApplicationContext applicationContext = 
        new ClassPathXmlApplicationContext("scopes.xml");

        Person personPrototypeA = (Person) applicationContext.getBean("personPrototype");
        Person personPrototypeB = (Person) applicationContext.getBean("personPrototype");

        personPrototypeA.setName(NAME);
        personPrototypeB.setName(NAME_OTHER);

        Assert.assertEquals(NAME, personPrototypeA.getName());
        Assert.assertEquals(NAME_OTHER, personPrototypeB.getName());

        ((AbstractApplicationContext) applicationContext).close();
    }
    ```

    scopes.xml文件与上一节介绍的文件类似，同时为带有原型作用域的bean添加xml定义。

    `<bean id="personPrototype" class="org.baeldung.scopes.Person" scope="prototype"/>`

4. 网络感知作用域

    如前所述，有四个额外的作用域只有在网络感知(Web Aware)的应用程序上下文中可用。我们在实践中较少使用这些。

    request作用域为单个HTTP请求创建一个bean实例，而session作用域为一个HTTP会话创建一个bean实例。

    application scope为ServletContext的生命周期创建bean实例，而websocket scope为特定的WebSocket会话创建bean实例。

    让我们创建一个用于实例化bean的类。

    参见 scopes/HelloMessageGenerator.java

    - [ ] ScopesControllerTest未实现?

    1. Request范围

        我们可以使用@Scope注解来定义带有请求范围的Bean。

        ```java
        @Bean
        @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
        public HelloMessageGenerator requestScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        proxyMode属性是必要的，因为在Web应用上下文实例化的时刻，并没有活动的请求。Spring创建了一个代理，作为依赖关系注入，并在请求中需要目标Bean时将其实例化。

        我们也可以使用一个@RequestScope组成的注解，作为上述定义的快捷方式。

        ```java
        @Bean
        @RequestScope
        public HelloMessageGenerator requestScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        接下来我们可以定义一个控制器，它有一个对requestScopedBean的注入引用。我们需要两次访问相同的请求，以便测试网络的特定作用域。

        如果我们在每次运行请求时显示消息，我们可以看到值被重置为null，即使后来在方法中被改变。这是因为每次请求都会返回一个不同的bean实例。

        ```java
        @Controller
        public class ScopesController {
            @Resource(name = "requestScopedBean")
            HelloMessageGenerator requestScopedBean;

            @RequestMapping("/scopes/request")
            public String getRequestScopeMessage(final Model model) {
                model.addAttribute("previousMessage", requestScopedBean.getMessage());
                requestScopedBean.setMessage("Good morning!");
                model.addAttribute("currentMessage", requestScopedBean.getMessage());
                return "scopesExample";
            }
        }
        ```

    2. Session作用域

        我们可以用类似的方式来定义具有会话作用域的Bean。

        ```java
        @Bean
        @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
        public HelloMessageGenerator sessionScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        还有一个专门的组成注释，我们可以用它来简化Bean的定义。

        ```java
        @Bean
        @SessionScope
        public HelloMessageGenerator sessionScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        接下来，我们定义一个控制器，引用sessionScopedBean。同样，我们需要运行两个请求，以显示消息字段的值对会话是相同的。

        在这种情况下，当请求第一次发出时，消息值为空。然而，一旦它被改变，这个值就会在随后的请求中被保留下来，因为整个会话都会返回同一个bean的实例。

        ```java
        @Controller
        public class ScopesController {
            @Resource(name = "sessionScopedBean")
            HelloMessageGenerator sessionScopedBean;

            @RequestMapping("/scopes/session")
            public String getSessionScopeMessage(final Model model) {
                model.addAttribute("previousMessage", sessionScopedBean.getMessage());
                sessionScopedBean.setMessage("Good afternoon!");
                model.addAttribute("currentMessage", sessionScopedBean.getMessage());
                return "scopesExample";
            }
        }
        ```

    3. 应用程序作用域

        Application范围为ServletContext的生命周期创建Bean实例。

        这与单子作用域类似，但在Bean的作用域方面有一个非常重要的区别。

        当Bean是应用作用域时，同一个Bean实例会被运行在同一个ServletContext中的多个基于Servlet的应用程序共享，而单例作用域的Bean只作用于一个应用程序上下文。

        让我们用应用范围来创建Bean。

        ```java
        @Bean
        @Scope(
        value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
        public HelloMessageGenerator applicationScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        类似于请求和会话的作用域，我们可以使用一个更短的版本。

        ```java
        @Bean
        @ApplicationScope
        public HelloMessageGenerator applicationScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        现在让我们创建一个控制器来引用这个Bean。

        ```java
        @Controller
        public class ScopesController {
            @Resource(name = "applicationScopedBean")
            HelloMessageGenerator applicationScopedBean;

            @RequestMapping("/scopes/application")
            public String getApplicationScopeMessage(final Model model) {
                model.addAttribute("previousMessage", applicationScopedBean.getMessage());
                applicationScopedBean.setMessage("Good afternoon!");
                model.addAttribute("currentMessage", applicationScopedBean.getMessage());
                return "scopesExample";
            }
        }
        ```

        在这种情况下，一旦在applicationScopedBean中设置，该值信息将被保留给所有后续的请求、会话，甚至是访问该Bean的不同servlet应用程序，只要它在同一个ServletContext中运行。

    4. WebSocket作用域

        最后，让我们用websocket范围创建Bean。

        ```java
        @Bean
        @Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public HelloMessageGenerator websocketScopedBean() {
            return new HelloMessageGenerator();
        }
        ```

        首次访问时，WebSocket范围内的Bean被存储在WebSocket会话属性中。然后，只要在整个WebSocket会话期间访问该Bean，就会返回该Bean的同一实例。

        我们也可以说，它表现出单例行为，但只限于WebSocket会话。

5. 总结

    在这篇文章中，我们讨论了Spring提供的不同Bean作用域以及它们的预期用途。

## @Order in Spring

1. 概述

    在本教程中，我们将学习Spring的[@Order](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/Order.html)注解。@Order注解定义了被注解的组件或bean的排序顺序。

    它有一个可选的值参数，决定了组件的顺序；默认值是Ordered.LOWEST_PRECEDENCE。这标志着该组件在所有其他排序的组件中具有最低的优先级。

    同样地，Ordered.HIGHEST_PRECEDENCE的值可以用来覆盖组件中的最高优先级。

2. 何时使用@Order

    在Spring 4.0之前，@Order注解只用于AspectJ的执行顺序。这意味着最高顺序的建议将首先运行。

    从Spring 4.0开始，它支持对注入到集合中的组件进行排序。因此，Spring会根据顺序值来注入相同类型的自动连接的Bean。

    让我们通过一个简单的例子来探讨一下。

3. 如何使用@Order

    首先，让我们用相关的接口和类来设置我们的项目。

    1. 接口的创建

        让我们创建Rating接口，决定产品的等级。

        参见 order/Rating.java

    2. 组件的创建

        最后，让我们创建三个组件，定义一些产品的评级。

        参见 order/Excellent.java

        参见 order/Good.java

        参见 order/Averaged.java

        请注意，Average类的优先级是最低的，因为它的值被覆盖了。

4. 测试我们的例子

    到目前为止，我们已经创建了所有需要的组件和接口来测试@Order注解。现在，让我们来测试一下，以确认它是否如预期那样工作。

    参见 order/RatingRetrieverUnitTest.java

5. 总结

    在这篇快速文章中，我们已经了解了@Order注解。我们可以在各种用例中找到@Order的应用--在这些用例中，自动连接的组件的顺序很重要。一个例子是Spring的请求过滤器。

    由于它对注入优先级的影响，它似乎也会影响单子的启动顺序。但相反，依赖关系和@DependsOn声明决定了单子的启动顺序。

## Spring @Primary Annotation

1. 概述

    在这个快速教程中，我们将讨论Spring的@Primary注解，它是在框架的3.0版本中引入的。

    简单地说，当有多个相同类型的Bean时，我们使用@Primary来给一个Bean更高的优先权。

    让我们来详细描述一下这个问题。

2. 为什么需要@Primary？

    在某些情况下，我们需要注册超过一个相同类型的Bean。

    在这个例子中，我们有JohnEmployee()和TonyEmployee()的Employee类型的Bean。

    ```java
    @Configuration
    public class Config {

        @Bean
        public Employee JohnEmployee() {
            return new Employee("John");
        }

        @Bean
        public Employee TonyEmployee() {
            return new Employee("Tony");
        }
    }
    ```

    如果我们试图运行该应用程序，Spring会抛出NoUniqueBeanDefinitionException。

    为了访问具有相同类型的Bean，我们通常使用@Qualifier("beanName")注解。

    我们在注入点和@Autowired一起应用它。在我们的案例中，我们在**配置阶段**(configuration phase)选择了Bean，所以@Qualifier不能在这里应用。我们可以通过下面的[链接](#guide-to-spring-autowired)了解更多关于@Qualifier注解的信息。

    为了解决这个问题，Spring提供了@Primary注解。

3. 使用@Primary与@Bean

    让我们看一下配置类。

    参见 primary/Config.java

    我们用@Primary标记TonyEmployee()bean。Spring将优先注入TonyEmployee()bean而不是JohnEmployee()。

    现在，让我们启动应用程序上下文并从中获取Employee Bean。

    ```java
    AnnotationConfigApplicationContext context
    = new AnnotationConfigApplicationContext(Config.class);

    Employee employee = context.getBean(Employee.class);
    System.out.println(employee);
    ```

    在我们运行该应用程序后。

    `Employee{name='Tony'}`

    从输出结果中，我们可以看到TonyEmployee()实例在自动布线时有一个优先权。

4. 使用@Primary与@Component

    我们可以直接在bean上使用@Primary。让我们来看看下面的情况。

    参见 primary/Manager.java

    我们有一个Manager接口和两个子类豆，DepartmentManager。

    参见 primary/DepartmentManager.java

    还有GeneralManager bean。

    参见 primary/GeneralManager.java

    它们都覆盖了Manager接口的getManagerName()。另外，请注意，我们用@Primary来标记GeneralManager Bean。

    这一次，@Primary只有在我们启用组件扫描时才有意义。

    ```java
    @Configuration
    @ComponentScan(basePackages="org.baeldung.primary")
    public class Config {
    }
    ```

    让我们创建一个服务来使用依赖性注入，同时找到合适的Bean。

    参见 primary/ManagerService.java

    在这里，DepartmentManager和GeneralManager都有资格进行自动连接。

    由于我们用@Primary标记了GeneralManager Bean，它将被选择用于依赖性注入。

    ```java
    ManagerService service = context.getBean(ManagerService.class);
    Manager manager = service.getManager();
    System.out.println(manager.getManagerName())。
    ```

    输出结果是 "General manager"。

5. 总结

    在这篇文章中，我们了解了Spring的@Primary注解。通过代码示例，我们展示了@Primary的必要性和使用情况。

## Relevant Articles

- [x] [Guide to Spring @Autowired](http://www.baeldung.com/spring-autowire)
- [Spring Profiles](http://www.baeldung.com/spring-profiles)
- [x] [Quick Guide to Spring Bean Scopes](http://www.baeldung.com/spring-bean-scopes)
- [x] [@Order in Spring](http://www.baeldung.com/spring-order)
- [x] [Spring @Primary Annotation](http://www.baeldung.com/spring-primary)
- [Spring Events](https://www.baeldung.com/spring-events)
- [Spring Null-Safety Annotations](https://www.baeldung.com/spring-null-safety-annotations)
- [<-- prev](../spring-core/README.md) More articles [next -->](../spring-core-3/README.md)

## Code

本文的源代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-core-2)项目中找到。
