# Spring Boot Dependency Inject

This module contains articles about dependency injection with Spring Boot

## Spring组件扫描

1.概述

在本教程中，我们将介绍Spring中的组件扫描。在使用Spring时，我们可以对类进行注释，以便将它们变成Springbean。此外，我们可以告诉Spring在哪里搜索这些带注释的类，因为在这个特定的运行中，并非所有的类都必须成为bean。

当然，组件扫描有一些默认设置，但我们也可以自定义搜索包。

首先，让我们看看默认设置。

进一步阅读：

- [ ] [Spring Bean注释](https://www.baeldung.com/spring-bean-annotations)
- [ ] [在Spring Boot中创建自定义自动配置](https://www.baeldung.com/spring-boot-custom-auto-configuration)

2.@ComponentScan无参数

2.1.在Spring应用程序中使用@ComponentScan

在Spring中，我们使用@ComponentScan注释和@Configuration注释来指定要扫描的包，不带参数的@ComponentScan告诉Spring扫描当前包及其所有子包。

假设我们在 com.baeldung.componentscan.springapp 软件包中有以下@Configuration：

```java
@Configuration
@ComponentScan
public class SpringComponentScanApp {
    private static ApplicationContext applicationContext;

    @Bean
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }

    public static void main(String[] args) {
        applicationContext = 
          new AnnotationConfigApplicationContext(SpringComponentScanApp.class);

        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }
}
```

此外，我们在com.baeldung.componentscan.springapp.animals package中有Cat和Dog组件：

```java
package com.baeldung.componentscan.springapp.animals;
// ...
@Component
public class Cat {}
package com.baeldung.componentscan.springapp.animals;
// ...
@Component
public class Dog {}
```

最后，我们在com.baldeng.componentscan.springapp中有Rose组件。鲜花套餐：

```java
package com.baeldung.componentscan.springapp.flowers;
// ...
@Component
public class Rose {}
```

main（）方法的输出将包含com.baldeng.componentscan的所有bean。springapp包及其子包：

springComponentScanApp
cat
dog
rose
exampleBean

注意，主应用程序类也是一个bean，因为它用@Configuration注释，它是一个@Component。

我们还应该注意，主应用程序类和配置类不一定相同。如果它们不同，那么我们将主应用程序类放在哪里并不重要。只有配置类的位置很重要，因为组件扫描默认从其包开始。

最后，请注意，在我们的示例中，@ComponentScan等效于：

`@ComponentScan(basePackages = "com.baeldung.componentscan.springapp")`

basePackages参数是用于扫描的包或包数组。

2.2.在Spring Boot应用程序中使用@ComponentScan

SpringBoot的诀窍在于，许多事情都是隐式发生的。我们使用@SpringBootApplication注释，但它是三个注释的组合：

@Configuration
@EnableAutoConfiguration
@ComponentScan

让我们在com.baldeng.componentscan.springbootapp包中创建一个类似的结构，这一次的主要应用将是：

```java
package com.baeldung.componentscan.springbootapp;
// ...
@SpringBootApplication
public class SpringBootComponentScanApp {
    private static ApplicationContext applicationContext;

    @Bean
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootComponentScanApp.class, args);
        checkBeansPresence(
          "cat", "dog", "rose", "exampleBean", "springBootComponentScanApp");

    }

    private static void checkBeansPresence(String... beans) {
        for (String beanName : beans) {
            System.out.println("Is " + beanName + " in ApplicationContext: " + 
              applicationContext.containsBean(beanName));
        }
    }
}
```

所有其他包和类都保持不变，我们只需将它们复制到附近的com.baeldung.componentscan.springbootapp包。

SpringBoot扫描包的方式与前面的示例类似。让我们检查一下输出：

Is cat in ApplicationContext: true
Is dog in ApplicationContext: true
Is rose in ApplicationContext: true
Is exampleBean in ApplicationContext: true
Is springBootComponentScanApp in ApplicationContext: true

我们只是在第二个示例中检查bean是否存在（而不是打印出所有bean），原因是输出太大。

这是因为隐式@EnableAutoConfiguration注释，它使SpringBoot依赖pom.xml中的依赖项自动创建许多bean。

3.带参数的@ComponentScan

现在，让我们自定义扫描路径。例如，假设我们要排除Rose bean。

3.1.@ComponentScan特定软件包

我们可以用几种不同的方法来做到这一点。首先，我们可以更改基本包：

```java
@ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals")
@Configuration
public class SpringComponentScanApp {
   // ...
}
```

现在输出将是：

springComponentScanApp
cat
dog
exampleBean

让我们看看这背后的原因：

- springComponentScanApp是作为参数传递给AnnotationConfigApplicationContext的配置创建的
- exampleBean是配置内配置的bean
- cat和dog位于指定的com.baeldung.componentscan.springapp.animals包中

上面列出的所有定制也适用于Spring Boot。我们可以将@ComponentScan与@SpringBootApplication结合使用，结果将是相同的：

```java
@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.componentscan.springbootapp.animals")
```

3.2.@ComponentScan多包

Spring提供了一种方便的方法来指定多个包名称。为此，我们需要使用字符串数组。

数组的每个字符串表示一个包名称：

`@ComponentScan(basePackages = {"com.baeldung.componentscan.springapp.animals", "com.baeldung.componentscan.springapp.flowers"})`

或者，自4.1.1春季以来，我们可以使用逗号、分号或空格分隔包裹列表：

```java
@ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals;com.baeldung.componentscan.springapp.flowers")
@ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals,com.baeldung.componentscan.springapp.flowers")
@ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals com.baeldung.componentscan.springapp.flowers")
```

3.3.@ComponentScan（排除）

另一种方法是使用过滤器，指定要排除的类的模式：

```java
@ComponentScan(excludeFilters = 
  @ComponentScan.Filter(type=FilterType.REGEX,
    pattern="com\\.baeldung\\.componentscan\\.springapp\\.flowers\\..*"))
```

我们还可以选择不同的过滤器类型，因为注释支持多种灵活的选项来过滤扫描的类：

```java
@ComponentScan(excludeFilters = 
  @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Rose.class))
```

4.默认包

我们应该避免将@Configuration类放在[默认包](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html)中（即根本不指定包）。如果这样做，Spring会扫描类路径中所有jar中的所有类，这会导致错误，应用程序可能无法启动。

5.TEST

**ERROR**: `Caused by: org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'cat' for bean class [com.baeldung.componentscan.springbootapp.animals.Cat] conflicts with existing, non-compatible bean definition of same name and class [com.baeldung.componentscan.springapp.animals.Cat]`

说明：运行 SpringBootDiApplication.java 报错，未限定扫描范围，`@ComponentScan(basePackages = "com.baeldung.componentscan.springbootapp")`。

## Spring@ComponentScan–过滤器类型

在之前的教程中，我们学习了Spring组件扫描的基础知识。

在本文中，我们将看到@ComponentScan注释提供的不同类型的过滤器选项。

默认情况下，用@Component、@Repository、@Service、@Controller注释的类注册为Spring bean。用@Component注释的自定义注释注释的类也是如此。我们可以通过使用@ComponentScan注释的includeFilters和excludeFilters参数来扩展此行为。

有五种类型的[ComponentScan.Filter](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.Filter.html)过滤器。筛选器：

- ANNOTATION
- ASSIGNABLE_TYPE
- ASPECTJ
- REGEX
- CUSTOM

我们将在下一节中详细介绍这些内容。

我们应该注意，所有这些过滤器都可以在扫描中包含或排除类。为了简化示例，我们只包含类。

### FilterType.ANNOTATION

ANNOTATION过滤器类型包括或排除用给定注释标记的组件扫描中的类。

例如，我们有一个@Animal注释：componentscan.filter.annotation.Animal.java

现在，让我们定义一个Elephant类，它使用@Animal: componentscan.filter.annotation.Elephant.java

最后，让我们使用FilterType。通知Spring扫描@Animal注释类：

```java
@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = Animal.class))
public class ComponentScanAnnotationFilterApp { }
```

正如我们所看到的，扫描仪可以很好地拾取我们的大象：filter.annotation.ComponentScanAnnotationFilterAppTest.java。

### FilterType.ASSIGNABLE_TYPE

ASSIGNABLE_TYPE在组件扫描期间过滤所有扩展类或实现指定类型接口的类。

首先，让我们声明Animal接口：

`public interface Animal { }`

再一次，让我们声明我们的Elephant类，这次实现了Animal接口：

`public class Elephant implements Animal { }`

让我们宣布我们的Cat类也实现了Animal：

`public class Cat implements Animal { }`

现在，让我们使用ASSIGNABLE_TYPE指导Spring扫描Animal实现类：

```java
@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = Animal.class))
public class ComponentScanAssignableTypeFilterApp { }
```

我们将看到猫和大象都被扫描：

```java
@Test
public void whenAssignableTypeFilterIsUsed_thenComponentScanShouldRegisterBean() {
    ApplicationContext applicationContext =
      new AnnotationConfigApplicationContext(ComponentScanAssignableTypeFilterApp.class);
    List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
      .filter(bean -> !bean.contains("org.springframework")
        && !bean.contains("componentScanAssignableTypeFilterApp"))
      .collect(Collectors.toList());
    assertThat(beans.size(), equalTo(2));
    assertThat(beans.contains("cat"), equalTo(true));
    assertThat(beans.contains("elephant"), equalTo(true));
}
```

### FilterType.REGEX

REGEX过滤器检查类名是否匹配给定的正则表达式模式。过滤器类型。REGEX检查简单类名和完全限定类名。

声明大象、猫、狮子类。这次没有实现任何接口或用任何注释进行注释：

`public class Elephant { }`
`public class Cat { }`
`public class Loin { }`

让我们使用FilterType。REGEX，指示Spring扫描与REGEX.*[nt]匹配的类。我们的正则表达式计算包含nt的所有内容：

```java
@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = ".*[nt]"))
public class ComponentScanRegexFilterApp { }
```

这次在我们的测试中，我们将看到Spring扫描的是大象，而不是狮子：

```java
@Test
public void whenRegexFilterIsUsed_thenComponentScanShouldRegisterBeanMatchingRegex() {
    ApplicationContext applicationContext =
      new AnnotationConfigApplicationContext(ComponentScanRegexFilterApp.class);
    List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
      .filter(bean -> !bean.contains("org.springframework")
        && !bean.contains("componentScanRegexFilterApp"))
      .collect(Collectors.toList());
    assertThat(beans.size(), equalTo(1));
    assertThat(beans.contains("elephant"), equalTo(true));
}
```

### FilterType.ASPECTJ

当我们想使用表达式挑选类的复杂子集时，我们需要使用FilterType ASPECTJ。

对于这个用例，我们可以重用与前一节相同的三个类。

让我们使用FilterType。ASPECTJ将指示Spring扫描与我们的ASPECTJ表达式匹配的类：

```java
@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
  pattern = "com.baeldung.componentscan.filter.aspectj.* "
  + "&& !(com.baeldung.componentscan.filter.aspectj.L* "
  + "|| com.baeldung.componentscan.filter.aspectj.C*)"))
public class ComponentScanAspectJFilterApp { }
```

虽然有点复杂，但我们这里的逻辑需要在类名中既不以“L”也不以“C”开头的bean，所以这又给我们留下了Elephants：

```java
@Test
public void whenAspectJFilterIsUsed_thenComponentScanShouldRegisterBeanMatchingAspectJCreteria() {
    ApplicationContext applicationContext =
      new AnnotationConfigApplicationContext(ComponentScanAspectJFilterApp.class);
    List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
      .filter(bean -> !bean.contains("org.springframework")
        && !bean.contains("componentScanAspectJFilterApp"))
      .collect(Collectors.toList());
    assertThat(beans.size(), equalTo(1));
    assertThat(beans.get(0), equalTo("elephant"));
}
```

### FilterType.CUSTOM

如果上述过滤器类型都不符合我们的要求，那么我们还可以创建自定义过滤器类型。例如，假设我们只想扫描名称为五个字符或更短的类。

要创建自定义过滤器，我们需要实现org.springframework.core.type.filter.TypeFilter：参见 componentscan.filter.custom.ComponentScanCustomFilter.java。

让我们使用FilterType.CUSTOM，它使用我们的自定义过滤器ComponentScanCustomFilter将Spring传递给扫描类：

```java
@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM,
  classes = ComponentScanCustomFilter.class))
public class ComponentScanCustomFilterApp { }
```

现在是时候看看我们的自定义过滤器ComponentScanCustomFilter的测试用例了：参见 componentscan.filter.custom.ComponentScanCustomFilterAppTest.java。

> **重要**：当 Cat/Elephant 等 Spring POJO 加上 @Component 后将强制扫描，导致 ComponentScanCustomFilter 不起作用。

## 如何获得所有Spring-Managed Beans？

1.概述

在本文中，我们将探索在容器中显示所有Spring管理bean的不同技术。

2.IoC容器

bean是Spring管理应用程序的基础；所有bean都驻留在IOC容器中，IOC容器负责管理它们的生命周期。

我们可以通过两种方式获取此容器中所有bean的列表：

- 使用ListableBeanFactory接口
- 使用Spring Boot Actuator执行器

3.使用ListableBeanFactory接口

ListableBeanFactory接口提供getBeanDefinitionNames（）方法，该方法返回此工厂中定义的所有bean的名称。此接口由所有预先加载其bean定义以枚举其所有bean实例的bean工厂实现。

您可以在[官方文档](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/ListableBeanFactory.html)中找到所有已知子接口及其实现类的列表。

对于这个例子，我们将使用一个SpringBoot应用程序。

首先，我们将创建一些Springbean。让我们创建一个简单的Spring Controller FooController：displayallbeans.controller.FooController.java

此控制器依赖于另一个Spring-bean FooService：displayallbeans.controller.FooService.java

请注意，我们在这里创建了两个不同的bean：

- fooController
- fooService

在执行此应用程序时，我们将使用applicationContext对象并调用其getBeanDefinitionNames()方法，该方法将返回applicationContext容器中的所有bean：displayallbeans.Application.java

这将打印applicationContext容器中的所有bean：

fooController
fooService
//other beans

注意，除了我们定义的bean之外，它还将记录此容器中的所有其他bean。为了清楚起见，我们在这里省略了它们，因为它们有很多。

4.使用Spring Boot Actuator

Spring Boot Actuator功能提供用于监控应用程序统计信息的端点。

它包括许多内置端点，包括/beans。这显示了应用程序中所有Spring托管bean的完整列表。您可以在[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints)上找到现有端点的完整列表。

现在，我们只需点击URL `http://<address>：<managementport>/beans`。如果没有指定任何单独的管理端口，我们可以使用默认服务器端口。这将返回一个JSON响应，显示Spring IoC容器中的所有bean：

```java
[
    {
        "context": "application:8080",
        "parent": null,
        "beans": [
            {
                "bean": "fooController",
                "aliases": [],
                "scope": "singleton",
                "type": "com.baeldung.displayallbeans.controller.FooController",
                "resource": "file [E:/Workspace/tutorials-master/spring-boot/target
                  /classes/com/baeldung/displayallbeans/controller/FooController.class]",
                "dependencies": [
                    "fooService"
                ]
            },
            {
                "bean": "fooService",
                "aliases": [],
                "scope": "singleton",
                "type": "com.baeldung.displayallbeans.service.FooService",
                "resource": "file [E:/Workspace/tutorials-master/spring-boot/target/
                  classes/com/baeldung/displayallbeans/service/FooService.class]",
                "dependencies": []
            },
            // ...other beans
        ]
    }
]
```

当然，这也由驻留在同一个spring容器中的许多其他bean组成，但为了清晰起见，我们在这里省略了它们。

如果您想了解更多关于Spring Boot Actuator的信息，可以前往Spring Boot Actuator主指南。

## Relevant Articles

- [x] [Spring Component Scanning](https://www.baeldung.com/spring-component-scanning)
- [x] [Spring @ComponentScan – Filter Types](https://www.baeldung.com/spring-componentscan-filter-type)
- [x] [How to Get All Spring-Managed Beans?](https://www.baeldung.com/spring-show-all-beans)
