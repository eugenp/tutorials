# Spring Core

This module contains articles about core Spring functionality

## Guide to Spring @Autowired

1.概述

从Spring2.5开始，框架引入了注释驱动的依赖注入。此功能的主要注释是@Autowired。它允许Spring解析协作bean并将其注入到我们的bean中(resolve and inject collaborating beans into our bean.)。

进一步阅读：

- [ ] [Spring组件扫描](https://www.baeldung.com/spring-component-scanning)
- [x] [使用Spring反转控制和依赖注入简介](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)

在本教程中，我们将首先了解如何启用自动连接以及自动连接bean的各种方法。之后，我们将讨论使用@Qualifier注释解决bean冲突，以及潜在的异常场景。

2.启用@Autowired Annotations

Spring框架支持自动依赖注入。换句话说，通过在Spring配置文件中声明所有bean依赖项，Spring容器可以自动连接协作bean之间的关系。这称为Spring bean自动装配(autowiring)。

要在我们的应用程序中使用基于Java的配置，让我们启用注释驱动注入来加载Spring配置：

```java
@Configuration
@ComponentScan("com.baeldung.autowire.sample")
public class AppConfig {}
```

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

3.使用@Autowired

启用注释注入后，我们可以对properties属性、setter和constructors构造函数使用自动连接。

3.1.@Autowired 属性

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

3.2.@Autowired on Setters

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

3.3.@Autowired 构造函数

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

4.@Autowired和Optional依赖项

构建bean时，@Autowired依赖项应该可用。否则，如果Spring无法解析用于连接的bean，它将抛出异常。

因此，它会阻止Spring容器成功启动，但表单除外：

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

5.Autowire 消除歧义

默认情况下，Spring按类型解析@Autowired条目。如果容器中有多个相同类型的bean可用，框架将抛出致命异常。

为了解决这个冲突，我们需要明确告诉Spring要注入哪个bean。

5.1.Autowiring by @Qualifier

例如，让我们看看如何使用@Qualifier注释来指示所需的bean。

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

在我们的示例中，Spring容器有两个具体的Formatter实现。因此，Spring在构造FooService时将抛出NoUniqueBeanDefinitionException异常：

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

5.2.通过自定义限定符自动布线

Spring还允许我们创建自己的自定义@Qualifier注释。为此，我们应该为@Qualifier注释提供定义：

```java
@Qualifier
@Target({
  ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatterType {  
    String value();
}
```

然后，我们可以在各种实现中使用FormatterType来指定自定义值：

```java
@FormatterType("Foo")
@Component
public class FooFormatter implements Formatter {
    public String format() {
        return "foo";
    }
}
@FormatterType("Bar")
@Component
public class BarFormatter implements Formatter {
    public String format() {
        return "bar";
    }
}
```

最后，我们的自定义限定符注释可以用于自动注入：

```java
@Component
public class FooService {  
    @Autowired
    @FormatterType("Foo")
    private Formatter formatter;
}
```

@Target meta-annotation 元注释中指定的值限制了限定符的应用位置，在我们的示例中，限定符是字段、方法、类型和参数。

5.3.按名称自动注入

Spring使用bean的名称作为默认限定符值。它将检查容器，并查找一个名称与属性完全相同的bean，以自动连接它。

因此，在我们的示例中，Spring将fooFormatter属性名与FooFormatster实现相匹配。因此，它在构造FooService时注入特定的实现：

```java
public class FooService {
 @Autowired 
private Formatter fooFormatter; 
}
```

6.自动注入问题

Factory method injection should be used in "@Configuration" classes (java:S3305)

当使用@Autowired时，在实例化类时需要解决依赖关系，这可能会导致bean的早期初始化，或者导致上下文查找不应该查找bean的位置。为了避免这个棘手的问题并优化上下文加载的方式，应该尽可能晚地请求依赖项。这意味着对仅在单个@Bean方法中使用的依赖项，应该使用参数注入而不是字段注入。

例外情况：该规则忽略了应用程序中其他方法直接调用的方法中使用的字段（与Spring框架自动调用相反），这样直接调用方就不必自己提供依赖项。

Fields used in methods that are called directly by other methods in the application (as opposed to being invoked automatically by the Spring framework) are ignored by this rule so that direct callers don’t have to provide the dependencies themselves.

- [ ] 示例？如一些未重写的父类方法直接调用？。

## Relevant Articles

- [x] [Guide to Spring @Autowired](http://www.baeldung.com/spring-autowire)
- [Spring Profiles](http://www.baeldung.com/spring-profiles)
- [Quick Guide to Spring Bean Scopes](http://www.baeldung.com/spring-bean-scopes)
- [@Order in Spring](http://www.baeldung.com/spring-order)
- [Spring @Primary Annotation](http://www.baeldung.com/spring-primary)
- [Spring Events](https://www.baeldung.com/spring-events)
- [Spring Null-Safety Annotations](https://www.baeldung.com/spring-null-safety-annotations)
- [<-- prev](../spring-core/README.md) More articles [next -->](../spring-core-3/README.md)
