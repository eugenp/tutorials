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

## Relevant Articles

- [Injecting Spring Beans into Unmanaged Objects](https://www.baeldung.com/spring-inject-bean-into-unmanaged-objects)
- [x] [Injecting a Value in a Static Field in Spring](https://www.baeldung.com/spring-inject-static-field)
- [Spring – Injecting Collections](https://www.baeldung.com/spring-injecting-collections)
- [Wiring in Spring: @Autowired, @Resource and @Inject](https://www.baeldung.com/spring-annotations-resource-inject-autowire)
- [Injecting Spring Beans into Unmanaged Objects](https://www.baeldung.com/spring-inject-bean-into-unmanaged-objects)
- [Constructor Dependency Injection in Spring](https://www.baeldung.com/constructor-injection-in-spring)
- [Circular Dependencies in Spring](https://www.baeldung.com/circular-dependencies-in-spring)
- More articles: [[<-- prev]](../spring-di)[[more -->]](../spring-di-3)
