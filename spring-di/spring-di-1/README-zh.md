# Spring Dependency Injection

This module contains articles about dependency injection with Spring

## Spring中基于XML的注入

1. 简介

    在这个基础教程中，我们将学习如何用Spring框架进行简单的基于XML的bean配置。

2. 概述

    让我们先在pom.xml中添加Spring的库依赖性。

    `<groupId>org.springframework</groupId><artifactId>spring-context</artifactId>`

    最新版本的Spring依赖性可以在这里找到。

3. 依赖性注入--概述

    [依赖性注入](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)是一种技术，对象的依赖性由外部容器提供。

    比方说，我们有一个应用类，它依赖于一个实际处理业务逻辑的服务。

    参见 di.spring/IndexApp.java

    现在我们说IService是一个接口。

    参见 di.spring/IService.java

    这个接口可以有多种实现。

    让我们快速看一下一个潜在的实现。

    参见 di.spring/IndexService.java

    在这里，IndexApp 是一个高层组件，它依赖于名为 IService 的低层组件。

    实质上，我们正在将IndexApp与IService的特定实现解耦，IService可以根据各种因素而变化。

4. 依赖性注入--在行动中

    让我们看看我们如何注入一个依赖关系。

    1. 使用属性

        让我们看看如何使用基于XML的配置将依赖关系连接起来。

        ```xml
        <bean 
        id="indexService" 
        class="com.baeldung.di.spring.IndexService" />
            
        <bean 
        id="indexApp" 
        class="com.baeldung.di.spring.IndexApp" >
            <property name="service" ref="indexService" />
        </bean>    
        ```

        可以看出，我们正在创建一个IndexService的实例，并给它分配了一个id。默认情况下，这个bean是一个单例。

        此外，我们还创建了一个IndexApp的实例；在这个Bean中，我们使用setter方法注入其他Bean。

    2. 使用构造器

        我们可以使用构造函数来注入依赖关系，而不是通过setter方法来注入Bean。

        ```xml
        <bean 
        id="indexApp" 
        class="com.baeldung.di.spring.IndexApp">
            <constructor-arg ref="indexService" />
        </bean>    
        ```

    3. 使用静态工厂

        我们也可以注入一个由工厂返回的bean。让我们创建一个简单的工厂，根据提供的数字返回一个IService的实例。

        参见 di.spring/StaticServiceFactory.java

        现在让我们看看如何使用上述实现，使用基于XML的配置将一个bean注入到IndexApp中。

        ```xml
        <bean id="messageService"
        class="com.baeldung.di.spring.StaticServiceFactory"
        factory-method="getService">
            <constructor-arg value="1" />
        </bean>   

        <bean id="indexApp" class="com.baeldung.di.spring.IndexApp">
            <property name="service" ref="messageService" />
        </bean>
        ```

        在上面的例子中，我们使用工厂方法调用静态的getService方法来创建一个id为messageService的bean，并注入到IndexApp中。

    4. 使用工厂方法

        让我们考虑一个实例工厂，根据提供的数字返回一个IService的实例。这一次，这个方法不是静态的。

        参见 di.spring/InstanceServiceFactory.java

        现在让我们看看如何使用上述实现，用 XML 配置将一个 Bean 注入 IndexApp。

        ```xml
        <bean id="indexServiceFactory" 
        class="com.baeldung.di.spring.InstanceServiceFactory" />
        <bean id="messageService"
        class="com.baeldung.di.spring.InstanceServiceFactory"
        factory-method="getService" factory-bean="indexServiceFactory">
            <constructor-arg value="1" />
        </bean>  
        <bean id="indexApp" class="com.baeldung.di.spring.IndexApp">
            <property name="service" ref="messageService" />
        </bean>
        ```

        在上面的例子中，我们在InstanceServiceFactory的一个实例上使用factory-method调用getService方法来创建一个id为messageService的bean，并将其注入IndexApp中。

5. 测试

    这就是我们如何访问配置好的bean。

    ```java
    @Test
    public void whenGetBeans_returnsBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("...");
        IndexApp indexApp = applicationContext.getBean("indexApp", IndexApp.class);
        assertNotNull(indexApp);
    }
    ```

6. 总结

    在这个快速教程中，我们举例说明了如何使用Spring框架的基于XML的配置来注入依赖关系。

## Relevant Articles

- [The Spring @Qualifier Annotation](https://www.baeldung.com/spring-qualifier-annotation)
- [Spring Autowiring of Generic Types](https://www.baeldung.com/spring-autowire-generics)
- [Guice vs Spring – Dependency Injection](https://www.baeldung.com/guice-spring-dependency-injection)
- [Injecting Prototype Beans into a Singleton Instance in Spring](https://www.baeldung.com/spring-inject-prototype-bean-into-singleton)
- [Controlling Bean Creation Order with @DependsOn Annotation](https://www.baeldung.com/spring-depends-on)
- [Unsatisfied Dependency in Spring](https://www.baeldung.com/spring-unsatisfied-dependency)
- [x] [XML-Based Injection in Spring](https://www.baeldung.com/spring-xml-injection)
- More articles: [[next -->]](../spring-di-2/README-zh.md)

## Code

这些例子的实现可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-di)项目中找到--这是一个基于Maven的项目，所以应该很容易导入并按原样运行。
