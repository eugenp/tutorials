# Spring Dependency Injection

## 在Spring的静态字段中注入一个值

在本教程中，我们将看到如何从Java[属性文件](https://www.baeldung.com/java-properties)中向Spring的静态字段注入一个值。

1. 问题

    首先，让我们设想一下，我们在属性文件中设置一个属性。

    `name = Inject a value to a static field`

    之后，我们想把它的值注入到一个实例变量中。

    这通常可以通过在一个实例字段上使用[@Value](https://www.baeldung.com/spring-value-annotation)注解来实现。

    ```java
    @Value("${name}")
    private String name;
    ```

    然而，当我们试图将其应用于一个静态字段时，我们会发现它仍然是空的。

    ```java
    @Value("${name}")
    private static String NAME_NULL;
    ```

    这是因为Spring不支持静态字段的@Value。

    现在，说实话，我们的代码处于这样一个奇怪的位置，我们应该首先考虑重构。但是，让我们看看如何使其发挥作用。

2. 解决方案

    首先，让我们声明我们要注入的静态变量NAME_STATIC。

    之后，我们将创建一个setter方法，叫做setNameStatic，并使用@Value注解来注解它。

    staticvalue.injection/PropertyController.java

    让我们试着理解一下上面发生的事情。

    首先，PropertyController是一个RestController，正在被Spring初始化。

    之后，Spring搜索了Value注解的字段和方法。

    当Spring找到@Value注解时，它使用[依赖注入](https://www.baeldung.com/spring-dependency-injection)来填充特定的值。然而，它没有把值交给实例变量，而是交给了隐式setter。然后，这个setter处理我们的NAME_STATIC值的填充。

    ```warn
    Instance methods should not write to "static" fields (java:S2696)
    CODE_SMELL  Code Smell  CRITICAL  Critical
    从非静态方法中正确地更新静态字段是很棘手的，如果有多个类实例和/或多个线程参与，很容易导致错误。理想情况下，静态字段只能从同步的静态方法中更新。
    ```

    改为：`public static void setNameStatic(String name) {}`

    ```log
    main] f.a.AutowiredAnnotationBeanPostProcessor : Autowired annotation is not supported on static fields: private static java.lang.String com.baeldung.staticvalue.injection.PropertyController.nameNull    
    main] f.a.AutowiredAnnotationBeanPostProcessor : Autowired annotation is not supported on static methods: public static void com.baeldung.staticvalue.injection.PropertyController.setNameStatic(java.lang.String)
    ```

3. 总结

    在这个简短的教程中，我们看了如何将属性文件中的一个值注入静态变量中，但这是一种不合理的实现！

    只有当我们尝试重构失败时，这才是一条我们可以考虑的途径。

## Spring中的构造函数依赖性注入

1. 简介

    可以说，现代软件设计中最重要的开发原则之一是依赖注入（DI），它很自然地从另一个至关重要的原则中产生。模块化。

    这个快速教程将探讨Spring中一种特殊的DI技术，即基于构造函数的依赖注入，简单地说，就是在实例化时将所需的组件传递给类。

    为了开始学习，我们需要在pom.xml中导入spring-text依赖。

    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.2.8.RELEASE</version>
    </dependency>
    ```

    然后我们需要设置一个配置文件。这个文件可以是一个POJO，也可以是一个XML文件，根据偏好而定。

    进一步阅读。

    [Intro to Inversion of Control and Dependency Injection with Spring](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)

    快速介绍控制反转和依赖注入的概念，然后用Spring框架做一个简单的演示。

    [Top Spring Framework Interview Questions](https://www.baeldung.com/spring-interview-questions)

    迅速讨论求职面试中可能出现的关于Spring框架的常见问题。

    [Wiring in Spring: @Autowired, @Resource and @Inject](https://www.baeldung.com/spring-annotations-resource-inject-autowire)

    本文将比较和对比与依赖注入有关的注解，即@Resource、@Inject和@Autowired注解的使用。

2. 基于注解的配置(Annotation Based Configuration)

    Java配置文件看起来类似于带有一些额外注解的Java对象。

    constructordi/Config.java

    这里我们使用注解来通知Spring运行时，这个类提供了Bean定义（@Bean注解），并且包com.baeldung.spring需要执行上下文扫描，以获取更多的Bean。接下来，我们定义一个Car类。

    ```java
    @Component
    public class Car {
        @Autowired
        public Car(Engine engine, Transmission transmission) {
            this.engine = engine;
            this.transmission = transmission;
        }
    }
    ```

    Spring在进行包扫描时将遇到我们的汽车类，并将通过调用@Autowired注解的构造函数来初始化其实例。

    通过调用配置类的@Bean注解方法，我们将获得引擎和变速器的实例。最后，我们需要使用我们的POJO配置来引导一个ApplicationContext。

    ```java
    ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    Car car = context.getBean(Car.class);
    ```

3. 隐式构造函数注入(Implicit Constructor Injection)

    从Spring 4.3开始，只有一个构造函数的类可以省略@Autowired注解。这是一个很好的小便利和模板删除。

    除此之外，从4.3开始，我们还可以在@Configuration注解的类中利用基于构造函数的注入。此外，如果这样的类只有一个构造函数，我们也可以省略@Autowired注解。

4. 基于XML的配置(XML Based Configuration)

    用基于构造函数的依赖注入来配置Spring运行时的另一种方法是使用XML配置文件。

    resources/constructordi.xml

    请注意，constructor-arg可以接受一个字面值或对另一个bean的引用，而且可以提供一个可选的显式索引和类型。我们可以使用Type和index属性来解决模棱两可的问题（例如，如果一个构造函数接受相同类型的多个参数）。

    > name属性也可以用于xml到java变量的匹配，但这样你的代码在编译时必须打开调试标志。

    在这种情况下，我们需要使用ClassPathXmlApplicationContext来引导我们的Spring应用上下文。

    ```java
    ApplicationContext context = new ClassPathXmlApplicationContext("baeldung.xml");
    Car car = context.getBean(Car.class);
    ```

5. 优点和缺点(Pros and Cons)

    构造函数注入与字段注入相比有几个优点。

    第一个优点是可测试性。假设我们要对一个使用字段注入的Spring Bean进行单元测试。

    ```java
    public class UserService {
        // Field-Based
        @Autowired 
        private UserRepository userRepository;
    }
    ```

    在构建UserService实例的过程中，我们不能初始化userRepository的状态。实现这一目标的唯一方法是[通过反射API](https://www.baeldung.com/java-reflection)，这完全破坏了封装。而且，与简单的构造器调用相比，所产生的代码将不太安全。

    此外，通过字段注入，我们不能执行类级的不变性，所以有可能出现UserService实例没有正确初始化userRepository的情况。因此，我们可能会遇到随机的NullPointerExceptions在这里和那里。另外，有了构造函数注入，构建不可变的组件就更容易了。

    此外，从OOP的角度来看，使用构造函数来创建对象实例更加自然。

    另一方面，构造函数注入的主要缺点是它的冗长性，特别是当一个bean有少量的依赖关系时。有时，这可能是一种变相的祝福，因为我们可能会更努力地保持最小的依赖关系数量。

6. 结语

    这篇简短的文章展示了使用Spring框架使用基于构造函数的依赖注入的两种不同方式的基础知识。

## Relevant Articles

- [Injecting Spring Beans into Unmanaged Objects](https://www.baeldung.com/spring-inject-bean-into-unmanaged-objects)
- [x] [Injecting a Value in a Static Field in Spring](https://www.baeldung.com/spring-inject-static-field)
- [Spring – Injecting Collections](https://www.baeldung.com/spring-injecting-collections)
- [Wiring in Spring: @Autowired, @Resource and @Inject](https://www.baeldung.com/spring-annotations-resource-inject-autowire)
- [Injecting Spring Beans into Unmanaged Objects](https://www.baeldung.com/spring-inject-bean-into-unmanaged-objects)
- [x] [Constructor Dependency Injection in Spring](https://www.baeldung.com/constructor-injection-in-spring)
- [Circular Dependencies in Spring](https://www.baeldung.com/circular-dependencies-in-spring)
- More articles: [[<-- prev]](../spring-di-1) [[more -->]](../spring-di-3)

## Code

本文的完整实现可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-di-2)上找到。
