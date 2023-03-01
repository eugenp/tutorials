# 使用Spring的反转控制和依赖注入介绍

1. 概述

    在本教程中，我们将介绍IoC（反转控制）和DI（依赖注入）的概念，并看看这些概念是如何在Spring框架中实现的。

    [Wiring in Spring: @Autowired, @Resource and @Inject](https://www.baeldung.com/spring-annotations-resource-inject-autowire)

    本文将比较和对比与依赖注入有关的注解的使用，即@Resource、@Inject和@Autowired注解。

    [@Component vs @Repository and @Service in Spring](https://www.baeldung.com/spring-component-repository-service)

    了解@Component、@Repository和@Service注解之间的区别以及何时使用它们。

2. 什么是反转控制？

    反转控制是软件工程中的一项原则，它将对象或程序的部分控制权转移到容器或框架中。我们最常在面向对象编程的背景下使用它。

    与传统编程相比，在传统编程中，我们的自定义代码会调用一个库，而IoC使一个框架能够控制程序的流程，并调用我们的自定义代码。为了实现这一点，框架使用了内建有额外行为的抽象概念。如果我们想添加自己的行为，我们需要扩展框架的类或插入自己的类。

    这种架构的优点是。

    - 将一个任务的执行与它的实现相分离
    - 使得在不同的实现之间切换更加容易
    - 程序的模块化程度更高
    - 通过隔离组件或模拟其依赖关系，使程序的测试更加容易，并允许组件通过合同进行通信。

    我们可以通过各种机制来实现反转控制，比如。策略设计模式(Strategy design pattern)、服务定位模式(Service Locator pattern)、工厂模式(Factory pattern)和依赖注入（Dependency Injection DI）。

    我们接下来要看的是DI。

3. 什么是依赖性注入？

    依赖注入是一种我们可以用来实现IoC的模式，其中被倒置的控制是设置一个对象的依赖关系。

    将对象与其他对象连接起来，或将对象 "注入" 到其他对象中，是由汇编者而不是由对象本身完成的。

    下面是我们在传统编程中如何创建一个对象的依赖关系。

    ```java
    public class Store {
        private Item item;
        public Store() {
            item = new ItemImpl1();    
        }
    }
    ```

    在上面的例子中，我们需要在Store类本身中实例化一个Item接口的实现。

    通过使用DI，我们可以重写这个例子，而不必指定我们想要的Item的实现。

    ```java
    public class Store {
        private Item item;
        public Store(Item item) {
            this.item = item;
        }
    }
    ```

    在接下来的章节中，我们将看看我们如何通过元数据来提供Item的实现。

    IoC和DI都是简单的概念，但它们对我们构造系统的方式有很深的影响，所以非常值得全面了解。

4. Spring IoC容器

    IoC容器是实现IoC的框架的一个共同特征。

    在Spring框架中，接口ApplicationContext代表IoC容器。Spring容器负责实例化、配置和组装被称为Bean的对象，并管理其生命周期。

    Spring框架提供了ApplicationContext接口的几种实现。用于独立应用的ClassPathXmlApplicationContext和FileSystemXmlApplicationContext，以及用于Web应用的WebApplicationContext。

    为了组装Bean，容器使用配置元数据，它可以是XML配置或注解的形式。

    下面是手动实例化容器的一种方法。

    ```java
    ApplicationContext context
    = new ClassPathXmlApplicationContext("applicationContext.xml");
    ```

    为了设置上面例子中的项目属性，我们可以使用元数据。然后容器将读取这个元数据，并在运行时使用它来组装Bean。

    Spring中的依赖注入可以通过构造函数、设置器或字段来完成。

5. Constructor-Based 依赖性注入

    在[基于构造函数的依赖注入](https://www.baeldung.com/constructor-injection-in-spring)的情况下，容器将调用一个构造函数，其参数分别代表我们想要设置的依赖关系。

    Spring主要通过类型来解决每个参数，然后是属性的名称，以及用于消除歧义的索引。让我们来看看Bean的配置和它的依赖关系是如何使用注解的。

    ```java
    @Configuration
    public class AppConfig {
        @Bean
        public Item item1() {
            return new ItemImpl1();
        }
        @Bean
        public Store store() {
            return new Store(item1());
        }
    }
    ```

    @Configuration注解表明该类是Bean定义的来源。我们也可以将其添加到多个配置类中。

    我们在一个方法上使用@Bean注解来定义一个Bean。如果我们没有指定一个自定义的名字，那么Bean的名字将默认为方法的名字。

    对于使用默认单例作用域的Bean，Spring首先检查该Bean的缓存实例是否已经存在，如果不存在则只创建一个新的实例。如果我们使用的是原型作用域，容器会在每次方法调用时返回一个新的Bean实例。

    另一种创建Bean配置的方法是通过XML配置。

    ```xml
    <bean id="item1" class="org.baeldung.store.ItemImpl1" /> 
    <bean id="store" class="org.baeldung.store.Store"> 
        <constructor-arg type="ItemImpl1" index="0" name="item" ref="item1" /> 
    </bean>
    ```

6. Setter-Based 依赖注入

    对于基于setter的DI，容器将在调用无参数的构造函数或无参数的静态工厂方法来实例化Bean后调用我们类的setter方法。让我们使用注解来创建这个配置。

    ```java
    @Bean
    public Store store() {
        Store store = new Store();
        store.setItem(item1());
        return store;
    }
    ```

    我们也可以使用XML来实现对Bean的相同配置。

    ```xml
    <bean id="store" class="org.baeldung.store.Store">
        <property name="item" ref="item1" />
    </bean>
    ```

    我们可以为同一个bean结合基于构造函数和基于setter的注入类型。Spring文档建议对强制性的依赖关系使用基于构造函数的注入，而对可选的依赖关系使用基于setter的注入。

7. Field-Based 依赖注入

    在基于字段的DI中，我们可以通过用@Autowired注解来注入依赖关系。

    ```java
    public class Store {
        @Autowired
        private Item item; 
    }
    ```

    在构造Store对象时，如果没有构造函数或setter方法来注入Item bean，容器将使用反射来将Item注入Store。

    我们也可以使用[XML配置](https://www.baeldung.com/spring-xml-injection)来实现这一点。

    这种方法可能看起来更简单、更干净，但我们不推荐使用，因为它有一些缺点，比如：

    - 这种方法使用反射来注入依赖关系，这比基于构造器或基于设置器的注入成本更高。
    - 使用这种方法真的很容易不断添加多个依赖关系。如果我们使用构造函数注入，有多个参数会让我们认为这个类做了不止一件事，这就会违反单一责任原则。

    关于@Autowired注解的更多信息可以在[Wiring In Spring](https://www.baeldung.com/spring-annotations-resource-inject-autowire)文章中找到。

8. 自动连接依赖关系(Autowiring Dependencies)

    [Wiring](https://www.baeldung.com/spring-annotations-resource-inject-autowire)允许Spring容器通过检查已定义的Bean来自动解决协作Bean之间的依赖关系。

    有四种模式可以使用XML配置来自动连接Bean：

    - no：默认值--这意味着不对Bean使用autowiring，我们必须明确命名依赖关系。
    - byName：根据属性的名称进行autowiring，因此Spring将寻找与需要设置的属性名称相同的Bean。
    - byType：类似于byName的autowiring，只是基于属性的类型。这意味着Spring将寻找一个与要设置的属性类型相同的Bean。如果有多个该类型的Bean，框架会抛出一个异常。
    - constructor：autowiring是基于构造器参数完成的，这意味着Spring将寻找与构造器参数类型相同的bean。

    例如，让我们把上面定义的item1 Bean按类型自动连接到store Bean中。

    ```java
    @Bean(autowire = Autowire.BY_TYPE)
    public class Store {
        private Item item;
        public setItem(Item item){
            this.item = item;    
        }
    }
    ```

    我们也可以使用@Autowired注解来注入Bean，以便按类型进行自动布线。

    见上 Field-Based 依赖注入

    如果有多个相同类型的Bean，我们可以使用@Qualifier注解来引用一个Bean的名字。

    ```java
    public class Store {
        @Autowired
        @Qualifier("item1")
        private Item item;
    }
    ```

    现在让我们通过XML配置按类型自动连接Bean。

    `<bean id="store" class="org.baeldung.store.Store" autowire="byType"> </bean>`

    接下来，让我们通过XML将一个名为item的bean按名称注入到store Bean的item属性中。

    ```xml
    <bean id="item" class="org.baeldung.store.ItemImpl1" />
    <bean id="store" class="org.baeldung.store.Store" autowire="byName"></bean>
    ```

    我们也可以通过构造函数参数或设置器明确定义依赖关系来覆盖autowiring。

9. Lazy Initialized Beans

    默认情况下，容器会在初始化过程中创建并配置所有的单子Bean。为了避免这种情况，我们可以在Bean配置上使用值为true的lazy-init属性。

    `<bean id="item1" class="org.baeldung.store.ItemImpl1" lazy-init="true" />`

    因此，item1 Bean只有在第一次被请求时才会被初始化，而不是在启动时。这样做的好处是初始化时间更快，但代价是我们在请求bean之后才会发现任何配置错误，而这可能是在应用程序已经运行了几个小时甚至几天之后。

10. 结论

    在这篇文章中，我们介绍了控制反转和依赖注入的概念，并以Spring框架为例进行了说明。

    我们可以在Martin Fowler的文章中阅读更多关于这些概念的内容。

    - [控制反转的容器和依赖注入模式](http://martinfowler.com/articles/injection.html)
    - [反转控制](http://martinfowler.com/bliki/InversionOfControl.html)

    此外，我们还可以在[Spring框架参考文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-dependencies)中了解到IoC和DI的Spring实现。

## Relevant Articles

- [x] [Intro to Inversion of Control and Dependency Injection with Spring](https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring)
