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

## Wiring in Spring @Autowired @Resource and @Inject

1. 概述

    在这个Spring框架教程中，我们将演示如何使用与依赖注入有关的注解，即@Resource、@Inject和@Autowired注解。这些注解为类提供了一种解决依赖关系的声明性方法。

    ```java
    @Autowired 
    ArbitraryClass arbObject;
    ```

    相对于直接实例化它们（命令式的方式）。

    `ArbitraryClass arbObject = new ArbitraryClass();`

    三个注解中有两个属于Java扩展包：javax.annotation.Resource和javax.inject。@Autowired注解属于org.springframework.beans.factory.annotation包。

    这些注解中的每一个都可以通过字段注入或设置器注入来解决依赖关系。我们将使用一个简化但实用的例子，根据每个注解所采取的执行路径，来演示这三个注解之间的区别。

    这些例子将集中在如何在集成测试中使用这三种注入注解。测试所需的依赖性可以是一个任意的文件或一个任意的类。

    [Spring中的构造器依赖注入](https://www.baeldung.com/constructor-injection-in-spring)

    快速实用的介绍Spring中基于构造器的注入。

    [Spring的反转控制和依赖注入介绍](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)

    快速介绍控制反转和依赖注入的概念，然后用Spring框架做一个简单的演示。

    [在抽象类中使用@Autowired](https://www.baeldung.com/spring-autowired-abstract-class)

    了解在抽象类和具体类中使用@Autowired的区别。

2. @Resource 注解

    @Resource注解是[JSR-250](https://jcp.org/en/jsr/detail?id=250)注解集的一部分，并与Jakarta EE一起打包。该注解有以下执行路径，按优先级排列。

    - 按Name名称匹配
    - 按Type类型匹配
    - 按Qualifier限定词匹配

    这些执行路径同时适用于setter和field injection。

    1. Field注入

        我们可以通过字段注入来解决依赖关系，方法是用@Resource注解来注解一个实例变量。

        1. 通过名称匹配

            我们将使用下面的集成测试来演示逐名匹配字段注入。

            wiring.configuration.resource/FieldResourceInjectionIntegrationTest.java

            让我们浏览一下代码。在FieldResourceInjectionTest集成测试中，我们通过将Bean名称作为属性值传递给@Resource注解来解决依赖关系。

            ```java
            @Resource(name="namedFile")
            private File defaultFile;
            ```

            这个配置将使用逐名匹配的执行路径来解决依赖关系。我们必须在 ApplicationContextTestResourceNameType 应用程序上下文中定义 bean namedFile。

            请注意，Bean的id和相应的引用属性值必须匹配。

            wiring.configuration/ApplicationContextTestResourceNameTyped.java

            如果我们未能在应用上下文中定义Bean，将导致抛出org.springframework.beans.factory.NoSuchBeanDefinitionException。我们可以通过改变ApplicationContextTestResourceNameType应用上下文中传入@Bean注解的属性值，或改变FieldResourceInjectionTest集成测试中传入@Resource注解的属性值来证明。

        2. 按类型匹配

            为了演示按类型匹配的执行路径，我们只需删除 FieldResourceInjectionTest 集成测试中defaultFile属性值。

            ```java
            @Resource
            private File defaultFile;
            ```

            然后我们再次运行测试。

            测试仍然会通过，因为如果@Resource注解没有收到作为属性值的bean名称，Spring框架就会进行下一级的优先级，即逐个类型的匹配，以尝试解决依赖关系。

        3. 按限定词匹配

            为了演示按限定词匹配的执行路径，集成测试场景将被修改，以便在 ApplicationContextTestResourceQualifier 应用上下文中定义两个 Bean。

            wiring.configuration/ApplicationContextTestResourceQualifier.java

            我们将使用QualifierResourceInjectionTest集成测试来演示逐个限定词的依赖性解析。在这种情况下，一个特定的Bean依赖需要被注入到每个引用变量中。

            wiring.configuration.resource/QualifierResourceInjectionIntegrationTest.java

            当我们运行集成测试时，将抛出org.springframework.beans.factory.NoUniqueBeanDefinitionException。这将发生，因为应用程序上下文将找到两个File类型的Bean定义，并且不知道哪个Bean应该解决这个依赖关系。

            为了解决这个问题，我们需要参考QualifierResourceInjectionTest集成测试的两个属性。

            ```java
            @Resource
            private File dependency1;
            @Resource
            private File dependency2;
            ```

            我们必须添加以下@Qualifier注释。这样，代码块看起来如下。

            ```java
            @Resource
            @Qualifier("defaultFile")
            private File dependency1;
            @Resource
            @Qualifier("namedFile")
            private File dependency2;
            ```

            当我们再次运行集成测试时，它应该通过。我们的测试表明，即使我们在一个应用上下文中定义了多个Bean，我们也可以使用@Qualifier注解来清除任何混淆，允许我们将特定的依赖注入一个类中。

    2. Setter注入

        在字段上注入依赖关系时的执行路径也适用于基于setter的注入。

        1. 通过名称匹配

            唯一的区别是MethodResourceInjectionTest集成测试有一个setter方法。

            参见 wiring.configuration.resource/MethodResourceInjectionIntegrationTest.java

            我们通过注解引用变量对应的setter方法，通过setter注入解决依赖关系。然后我们将Bean类依赖的名称作为属性值传递给@Resource注解。

            ```java
            private File defaultFile;

            @Resource(name="namedFile")
            protected void setDefaultFile(File defaultFile) {
                this.defaultFile = defaultFile。
            }
            ```

            我们将在本例中重新使用 namedFile Bean 的依赖关系。豆类的名字和相应的属性值必须匹配。

            当我们运行集成测试时，它将通过。

            为了让我们验证逐名匹配的执行路径是否解决了依赖关系，我们需要将传递给@Resource注解的属性值改为我们选择的值，并再次运行测试。这一次，测试将以NoSuchBeanDefinitionException失败。

        2. 按类型匹配

            为了演示基于setter的、按类型匹配的执行，我们将使用MethodByTypeResourceTest集成测试。

            参见 wiring.configuration.resource/MethodByTypeResourceIntegrationTest.java。

            当我们运行这个测试时，它将通过。

            为了让我们验证match-by-type执行路径解决了File的依赖性，我们需要将defaultFile变量的类类型改为另一种类类型，如String。然后我们可以再次执行MethodByTypeResourceTest集成测试，这次将抛出NoSuchBeanDefinitionException。

            这个异常验证了match-by-type确实被用来解决File的依赖性。NoSuchBeanDefinitionException证实了引用变量名不需要与Bean名相匹配。相反，依赖关系的解析取决于 bean 的类类型与引用变量的类类型相匹配。

        3. 按限定词匹配

            我们将使用MethodByQualifierResourceTest集成测试来演示按限定词匹配的执行路径。

            参见 wiring.configuration.resource/MethodByQualifierResourceIntegrationTest.java

            我们的测试表明，即使我们在应用程序上下文中定义了多个特定类型的Bean实现，我们也可以使用@Qualifier注解和@Resource注解来解决一个依赖关系。

            与基于字段的依赖注入类似，如果我们在一个应用上下文中定义了多个Bean，我们必须使用@Qualifier注解来指定使用哪个Bean来解决依赖关系，否则将抛出NoUniqueBeanDefinitionException。

3. @Inject注解

    @Inject注解属于[JSR-330](https://jcp.org/en/jsr/detail?id=330)注解集合。该注解有以下执行路径，按优先级排列。

    - 按Type类型匹配
    - 通过Qualifier限定词匹配
    - 按Name名称匹配

    这些执行路径同时适用于setter和field injection。为了访问@Inject注解，我们必须将javax.inject库声明为Gradle或Maven的依赖项。

    对于Gradle来说。

    `testCompile group: 'javax.inject', name: 'javax.inject', version: '1'`

    对于Maven来说。

    ```xml
    <dependency>
        <groupId>javax.inject</groupId>
        <artifactId>javax.inject</artifactId>
        <version>1</version>
    </dependency>
    ```

    1. 字段注入

        1. 按类型匹配

            我们将修改集成测试的例子，使用另一种类型的依赖，即ArbitraryDependency类。ArbitraryDependency类的依赖只是作为一个简单的依赖，没有进一步的意义。

            参见 dependency/ArbitraryDependency.java

            下面是有关FieldInjectTest的集成测试。

            参见 wiring.configuration.inject/FieldInjectIntegrationTest.java

            与@Resource注解不同的是，@Inject注解的默认行为是通过类型来解决依赖关系。

            这意味着即使类的引用变量名称与Bean名称不同，只要Bean在应用上下文中被定义，依赖关系仍将被解释。请注意以下测试中的引用变量名是怎样的。

            ```java
            @Inject
            private ArbitraryDependency fieldInjectDependency;
            ```

            与应用上下文中配置的Bean名称不同。

            ```java
            @Bean
            public ArbitraryDependency injectDependency() {
                ArbitraryDependency injectDependency = new ArbitraryDependency();
                return injectDependency;
            }
            ```

            当我们执行测试时，我们就能解释这个依赖关系。

        2. 通过限定词匹配

            如果某个类的类型有多个实现，而某个类需要一个特定的bean，怎么办？让我们修改一下集成测试的例子，使其需要另一个依赖关系。

            在这个例子中，我们对ArbitraryDependency类进行子类化，该类在按类型匹配的例子中使用，创建AnotherArbitraryDependency类。

            参见 dependency/AnotherArbitraryDependency.java

            每个测试案例的目的是确保我们正确地将每个依赖注入到每个引用变量中。

            ```java
            @Inject
            private ArbitraryDependency defaultDependency;
            @Inject
            private ArbitraryDependency namedDependency;
            ```

            我们可以使用FieldQualifierInjectTest集成测试来演示通过限定词进行匹配。

            参见 wiring.configuration.inject/FieldQualifierInjectIntegrationTest.java

            如果我们在一个应用上下文中有一个特定类的多个实现，并且FieldQualifierInjectTest集成测试试图以下面列出的方式注入依赖关系，将抛出NoUniqueBeanDefinitionException。

            ```java
            @Inject 
            private ArbitraryDependency defaultDependency;
            @Inject 
            Private ArbitraryDependency namedDependency;
            ```

            抛出这个异常是Spring框架指出某个类有多个实现的方式，它对使用哪一个感到困惑。为了阐明这种困惑，我们可以去看FieldQualifierInjectTest集成测试。

            我们可以将所需的bean名称传递给@Qualifier注解，我们将其与@Inject注解一起使用。现在的代码块将是这样的。

            ```java
            @Inject
            @Qualifier("defaultFile")
            private ArbitraryDependency defaultDependency;
            @Inject
            @Qualifier("namedFile")
            Private ArbitraryDependency namedDependency;
            ```

            @Qualifier注解期望在接收Bean名称时进行严格匹配。我们必须确保Bean名称被正确地传递给Qualifier，否则，将抛出NoUniqueBeanDefinitionException。如果我们再次运行该测试，应该会通过。

        3. 按名称匹配

            用来演示按名称匹配的FieldByNameInjectTest集成测试与按类型匹配的执行路径相似。唯一的区别是现在我们需要一个特定的Bean，而不是一个特定的类型。在这个例子中，我们再次将ArbitraryDependency类子类化，产生YetAnotherArbitraryDependency类。

            参见 dependency/YetAnotherArbitraryDependency.java

            为了演示逐个名字的匹配执行路径，我们将使用以下集成测试。

            参见 wiring.configuration.inject/FieldByNameInjectIntegrationTest.java

            我们列出应用程序的上下文。

            参见 wiring.configuration/ApplicationContextTestInjectName

            如果我们运行集成测试，它将通过。

            为了验证我们是否通过逐个名称的匹配执行路径注入了依赖关系，我们需要将 FieldByNameInjectIntegrationTest 类中传递到 @Named 注解中的值 yetAnotherFieldInjectDependency 改为我们选择的另一个名称。当我们再次运行测试时，将抛出NoSuchBeanDefinitionException。

    2. 设置器注入

        @Inject注解的基于setter的注入与@Resource的基于setter的注入所使用的方法类似。我们不注解引用变量，而是注解相应的setter方法。基于字段的依赖注入所遵循的执行路径也适用于基于设置器的注入。

4. @Autowired注解

    @Autowired注解的行为类似于@Inject注解。唯一的区别是，@Autowired注解是Spring框架的一部分。这个注解的执行路径与@Inject注解相同，按优先级排列。

    - 按类型匹配
    - 通过限定词匹配
    - 按名称匹配

    这些执行路径同时适用于setter和field的注入。

    1. 字段注入

        1. 按类型匹配

            用于演示@Autowired逐个类型匹配执行路径的集成测试示例将与用于演示@Inject逐个类型匹配执行路径的测试相似。我们使用下面的FieldAutowiredTest集成测试来演示使用@Autowired注解的逐个类型的匹配。

            参见 wiring.configuration.autowired/FieldAutowiredIntegrationTest.java

            我们为这个集成测试列出应用上下文。

            参见 wiring.configuration/ApplicationContextTestAutowiredType.java

            我们用这个集成测试来证明，逐个类型的匹配优先于其他执行路径。注意FieldAutowiredTest集成测试引用的变量名称。

            ```java
            @Autowired
            private ArbitraryDependency fieldDependency;
            ```

            这与ApplicationContextTestAutowiredType上下文中的Bean名称不同。

            当我们运行该测试时，它应该通过。

            为了确认依赖确实是通过逐个类型的执行路径来解决的，我们需要改变fieldDependency参考变量的类型（如改为AnotherArbitraryDependency），并再次运行集成测试。这一次，FieldAutowiredTest集成测试将失败，并抛出NoSuchBeanDefinitionException。这就验证了我们使用了match-by-type来解决这个依赖关系。

        2. 通过限定词匹配

            如果我们面临的情况是，我们在应用上下文中定义了多个Bean的实现，该怎么办？

            参见 wiring.configuration/ApplicationContextTestAutowiredQualifier.java

            如果我们执行以下FieldQualifierAutowiredTest集成测试，将抛出NoUniqueBeanDefinitionException。

            参见 wiring.configuration.autowired/FieldQualifierAutowiredIntegrationTest.java

            这个异常是由于应用上下文中定义的两个Bean引起的歧义。Spring框架不知道哪个Bean依赖应该被自动连接到哪个引用变量。

            ```java
            @Autowired
            private FieldDependency fieldDependency1;
            @Autowired
            private FieldDependency fieldDependency2;
            ```

            我们可以通过在测试代码中添加@Qualifier注解来解决这个问题。代码块看起来如下。

            ```java
            @Autowired
            @Qualifier("autowiredFieldDependency")
            private FieldDependency fieldDependency1;
            @Autowired
            @Qualifier("anotherAutowiredFieldDependency")
            private FieldDependency fieldDependency2;
            ```

            当我们再次运行该测试时，它将通过。

        3. 按名称匹配

            我们将使用相同的集成测试场景来演示逐名匹配的执行路径，使用@Autowired注解来注入一个字段依赖。当按名称自动匹配依赖时，@ComponentScan 注解必须与应用程序上下文一起使用，即 ApplicationContextTestAutowiredName。

            参见 wiring.configuration/ApplicationContextTestAutowiredName.java

            我们使用@ComponentScan注解来搜索包中已经被@Component注解的Java类。例如，在应用上下文中，com.baeldung.dependency包将被扫描，以寻找已被@Component注解的类。在这种情况下，Spring框架必须检测到ArbitraryDependency类，它有@Component注解。

            传递到@Component注解中的属性值autowiredFieldDependency告诉Spring框架，ArbitraryDependency类是一个名为autowiredFieldDependency的组件。为了让@Autowired注解按名称解析依赖关系，组件名称必须与FieldAutowiredNameTest集成测试中定义的字段名称一致。

            `private ArbitraryDependency autowiredFieldDependency;`

            参见 wiring.configuration.autowired/FieldAutowiredNameIntegrationTest.java

            当我们运行FieldAutowiredNameTest集成测试时，它将通过。

            但我们怎么知道@Autowired注解真的调用了逐名匹配的执行路径？我们可以将引用变量autowiredFieldDependency的名称改为我们选择的另一个名称，然后再次运行测试。

            这一次，测试将失败，并抛出一个NoUniqueBeanDefinitionException。类似的检查是将@Component属性值autowiredFieldDependency改为我们选择的另一个值，然后再次运行测试。一个NoUniqueBeanDefinitionException也将被抛出。

            这个异常证明，如果我们使用了一个不正确的Bean名称，就不会找到有效的Bean。这就是我们如何知道逐个名称的匹配执行路径被调用的原因。

    2. 设置器注入

        针对 @Autowired 注解的基于设置器(Setter)的注入与针对 @Resource 的基于设置器的注入所演示的方法类似。我们没有用@Inject注解来注解引用变量，而是注解了相应的设置器。基于字段的依赖注入所遵循的执行路径也适用于基于设置器的注入。

5. 应用这些注解

    这就提出了一个问题：在什么情况下应该使用哪个注解。对这些问题的回答取决于有关应用程序所面临的设计方案，以及开发者希望如何根据每个注解的默认执行路径来利用多态性(Polymorphism)。

    1. 通过多态性在整个应用中使用单例

        Application-Wide Use of Singletons Through Polymorphism

        如果设计是这样的：应用程序的行为是基于接口或抽象类的实现，并且这些行为在整个应用程序中被使用，那么我们可以使用@Inject或@Autowired注解。

        这种方法的好处是，当我们升级应用程序，或者为了修复一个错误而应用一个补丁时，类可以被替换，而对整个应用程序的行为产生最小的负面影响。在这种情况下，主要的默认执行路径是按*类型*匹配。

    2. 通过多态性进行细粒度（Fine-Grained Application Behavior）的应用行为配置

        如果设计是这样的：应用程序有复杂的行为，每个行为都基于不同的接口/抽象类，并且每个实现的用法在整个应用程序中都是不同的，那么我们可以使用@Resource注解。在这种情况下，主要的默认执行路径是按*名称*匹配。

    3. 依赖注入应该只由Jakarta EE平台来处理

        如果在设计上要求所有的依赖都由Jakarta EE平台而不是Spring来注入，那么就需要在@Resource注解和@Inject注解之间做出选择。我们应该根据需要哪种默认执行路径来缩小这两个注解的最终决定。

    4. 依赖注入应该只由Spring框架处理

        如果任务是由Spring框架来处理所有的依赖关系，那么唯一的选择就是@Autowired注解。

    5. 讨论总结

        下面的表格总结了我们的讨论。

        | 场景                          | @Resource | @Inject | @Autowired |
        |------------------------------|-----------|---------|------------|
        | 通过多态性在整个应用中使用单例    | ✗         | ✔       | ✔          |
        | 通过多态性进行细粒度的应用行为配置 | ✔         | ✗       | ✗          |
        | 依赖注入应仅由Jakarta EE平台处理 | ✔         | ✔       | ✗          |
        | 依赖注入应仅由Spring框架处理     | ✗         | ✗       | ✔          |

6. 结论

    在这篇文章中，我们旨在对每个注释的行为提供更深入的了解。了解每个注解的行为方式将有助于更好地进行整体应用的设计和维护。

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
- [x] [Wiring in Spring: @Autowired, @Resource and @Inject](https://www.baeldung.com/spring-annotations-resource-inject-autowire)
- [Injecting Spring Beans into Unmanaged Objects](https://www.baeldung.com/spring-inject-bean-into-unmanaged-objects)
- [x] [Constructor Dependency Injection in Spring](https://www.baeldung.com/constructor-injection-in-spring)
- [Circular Dependencies in Spring](https://www.baeldung.com/circular-dependencies-in-spring)
- More articles: [[<-- prev]](../spring-di-1) [[more -->]](../spring-di-3)

## Code

本文的完整实现可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-di-2)上找到。
