# Spring AOP

- [简介](#简介)
- [advice类型](#spring-advice-类型)
- [Pointcut表达式](#spring-pointcut-表达式)
- [实现自定义注解](#实现自定义注解)
- [获得建议方法信息](#获得建议方法信息)

## 简介

在本教程中，我们将介绍 Spring 的 AOP（Aspect Oriented Programming），并学习如何在实际场景中使用这个强大的工具。

AOP 是一种编程范式（代表面向方面的编程），旨在通过允许分离横切关注点来增加模块化。它通过在不修改代码本身的情况下向现有代码添加额外的行为来实现这一点(it is a way for adding behavior to existing code without modifying that code)。相反，我们可以分别声明新代码和新行为。

在使用 Spring AOP 进行开发时，也可以利用 [AspectJ](/AspectJ.md) 注解，但在本文中，我们将重点关注基于 XML 的核心 Spring AOP 配置。

Spring 的 [AOP](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop) 框架帮助我们实现这些横切关注点。

添加 Maven 依赖项：

- 在 pom.xml 中添加 Spring's AOP library 依赖: org.springframework.boot:spring-boot-starter-aop

### AOP 概念和术语

让我们简要回顾一下 AOP 特有的概念和术语：

#### 业务对象 Business Object

业务对象是具有正常业务逻辑的正常类。 让我们看一个简单的业务对象示例，logger.SampleAdder

> 注意 这个类是一个普通的有业务逻辑的类，没有任何Spring相关的注解。

#### 方面 Aspect

方面是跨多个类的关注点的模块化。 统一日志记录可以是这种横切关注点的一个例子。

如何定义一个简单的 Aspect，参见 logger.AdderAfterReturnAspect

> 注意 这个类也是一个标准类，没有任何 Spring 注释。

#### 连接点 Joinpoint

Joinpoint 是程序执行过程中的一个点，例如方法的执行或异常的处理。

在 Spring AOP 中，一个 JoinPoint 总是代表一个方法执行。

在 logger.AdderAfterReturnAspect 中，定义了一个简单的 Java 类，该类具有一个名为 `afterReturn` 的方法，该方法接受一个 Object 类型的参数并记录该值。

#### 切入点 Pointcut

切入点是一个谓词 predicate ，有助于匹配要由 Aspect 在特定 JoinPoint 应用的 Advice。

我们经常将 Advice 与一个 Pointcut 表达式相关联，它在任何与 Pointcut 匹配的 Joinpoint 处运行。

#### 建议 Advice

建议 Advice 是方面在特定连接点处采取的行动。 不同类型的建议包括“around”、“before”和“after”。

在 Spring 中，Advice 被建模为一个拦截器 interceptor ，在 Joinpoint 周围维护一个拦截器链。

### 连接业务对象和方面

Wiring Business Object and Aspect

如何将业务对象连接到具有返回后通知的方面？即将 Aspect 连接到业务对象。

How we can wire a Business Object to an Aspect with an After-Returning advice.

下面是放置在 `<beans>` 标签中的标准 Spring 配置中的配置摘录：(resources/logger/springAop-applicationContext.xml)

```xml
<bean id="sampleAdder" class="org.baeldung.logger.SampleAdder" />
<bean id="doAfterReturningAspect" 
  class="org.baeldung.logger.AdderAfterReturnAspect" />
<aop:config>
    <aop:aspect id="aspects" ref="doAfterReturningAspect">
       <aop:pointcut id="pointCutAfterReturning" expression=
         "execution(* org.baeldung.logger.SampleAdder+.*(..))"/>
       <aop:after-returning method="afterReturn"
         returning="returnValue" pointcut-ref="pointCutAfterReturning"/>
    </aop:aspect>
</aop:config>
```

如所见，我们定义了一个名为 `simpleAdder` 的简单 bean，它表示一个业务对象的实例。此外，我们创建了一个名为 `doAfterReturningAspect` 的 Aspect 实例。

> 当然，XML 不是唯一的选择，也完全支持通过 [AspectJ注释](/AspectJ.md#aspect-注释) 。

配置一览：

- 可以使用标签 aop:config 来定义 AOP 相关的配置。在 config 标签中，我们定义了代表一个方面的类。然后我们给它一个“doAfterReturningAspect”的引用，这是我们创建的一个方面 bean。
- 接下来使用切入点标签定义一个切入点。上面示例中使用的切入点是 `execution(* org.baeldung.logger.SampleAdder+.*(..))`，这意味着在 SampleAdder 类中接受任意数量的参数并返回任意值类型的任何方法上应用通知。
- 然后定义想要应用的通知。在上面的示例中，应用了返回后的建议。我们在 Aspect AdderAfterReturnAspect 中通过执行我们使用属性方法定义的 `afterReturn` 方法来定义它。
- Aspect 中的这个建议采用 Object 类型的一个参数。该参数使我们有机会在目标方法调用之前和/或之后采取行动。在这种情况下，我们只记录方法的返回值。

> Spring AOP 使用基于注释的配置支持多种类型的建议，参见 resources/com.baeldung.logger/springAop-applicationContext.xml 。

## Spring Advice 类型

可以在 Spring 中创建的不同类型的 AOP Advice 。

Advice 是方面在特定连接点采取的行动。 不同类型的建议包括“around”、“before”和“after” Advice。

方面的主要目的是支持横切关注点，例如日志记录、分析、缓存和事务管理。

### 启用 Advice

使用 Spring，您可以使用 AspectJ 注释声明建议，但您必须首先将 `@EnableAspectJAutoProxy` 注释应用于您的配置类，这将支持处理标记有 AspectJ 的 @Aspect 注释的组件。

在 Spring Boot 项目中，我们不必显式使用 `@EnableAspectJAutoProxy`。 如果 Aspect 或 Advice 在类路径上，则有一个专用的 [AopAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/aop/AopAutoConfiguration.html) 启用 Spring 的 AOP 支持。

#### Before Advice

顾名思义，这个建议是在连接点之前执行的。 除非抛出异常，否则它不会阻止它建议的方法的继续执行。

考虑以下方面，它在调用之前简单地记录方法名称：pointcutadvice.LoggingAspect.java

```java
@Component
@Aspect
...
    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {};

    @Before("repositoryMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("Before " + methodName);
    }
...
```

`logMethodCall` 建议将在 `repositoryMethods` 切入点定义的任何存储库方法之前执行。

#### After Advice

使用 `@After` 注释声明的 After 通知在匹配的方法执行后执行，无论是否引发异常。

在某些方面，它类似于 finally 块。 如果您需要仅在正常执行后触发通知，则应使用 `@AfterReturning` 注释声明的返回通知。 如果您希望仅在目标方法抛出异常时触发您的通知，您应该使用抛出通知，通过使用 `@AfterThrowing` 注释声明。

假设我们希望在创建 Foo 的新实例时通知某些应用程序组件。 我们可以从 FooDao 发布一个事件，但这违反了单一责任原则。

相反，我们可以通过定义以下方面来实现这一点：pointcutadvice.PublishingAspect.java

```java
...
    @Pointcut("repositoryMethods() && firstLongParamMethods()")
    public void entityCreationMethods() {
    }

    @AfterReturning(value = "entityCreationMethods()", returning = "entity")
    public void logMethodCall(JoinPoint jp, Object entity) throws Throwable {
        eventPublisher.publishEvent(new FooCreationEvent(entity));
    }
...
```

请注意，首先，通过使用 `@AfterReturning` 注解，我们可以访问目标方法的返回值。 其次，通过声明 JoinPoint 类型的参数，我们可以访问目标方法调用的参数。

接下来创建一个监听器，它将简单地记录[事件](https://www.baeldung.com/spring-events)： pointcutadvice.events.FooCreationEventListener.java

#### Around Advice

环绕建议围绕一个连接点，例如方法调用。

这是最有力的建议。 环绕通知可以在方法调用之前和之后执行自定义行为。 它还负责选择是继续到连接点还是通过提供自己的返回值或抛出异常来缩短建议的方法执行。

为了演示它的使用，假设我们要测量方法执行时间。 让我们为此创建一个方面：pointcutadvice.PerformanceAspect.java

当执行与 repositoryClassMethods 切入点匹配的任何连接点时，将触发此建议。

该建议采用 ProceedingJointPoint 类型的一个参数。 该参数使我们有机会在目标方法调用之前采取行动。 在这种情况下，我们只需保存方法开始时间。

其次，通知返回类型是 Object，因为目标方法可以返回任何类型的结果。 如果目标方法为 void，则返回 null。 在目标方法调用之后，我们可以测量时间，记录下来，并将方法的结果值返回给调用者。

## Spring Pointcut 表达式

连接点是程序执行的一个步骤，例如方法的执行或异常的处理。 在 Spring AOP 中，一个连接点总是代表一个方法执行。 切入点是匹配连接点的谓词，切入点表达式语言是一种以编程方式描述切入点的方式。

### 用法

切入点表达式可以显示为 `@Pointcut` 注释的值：

```java
@Pointcut("within(@org.springframework.stereotype.Repository *)")
public void repositoryClassMethods() {}
```

方法声明称为切入点签名。 它提供了一个名称，通知注释可以使用该名称来引用该切入点：

```java
@Around("repositoryClassMethods()")
public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
    ...
}
```

切入点表达式也可以作为 aop:pointcut 标签的表达式属性的值出现：

```xml
<aop:config>
    <aop:pointcut id="anyDaoMethod" 
      expression="@target(org.springframework.stereotype.Repository)"/>
</aop:config>
```

### 切入点指示符

切入点表达式以切入点指示符 (Pointcut Designators, PCD) 开头，这是一个告诉 Spring AOP 匹配什么的关键字。 有几个切入点指示符，例如方法的执行、类型、方法参数或注释。

execution

- 主要的 Spring PCD 是执行，它匹配方法执行连接点：
  `@Pointcut("execution(public String com.baeldung.pointcutadvice.dao.FooDao.findById(Long))")`
- 此示例切入点将与 FooDao 类的 findById 方法的执行完全匹配。 这可行，但它不是很灵活。 假设我们想要匹配 FooDao 类的所有方法，这些方法可能具有不同的签名、返回类型和参数。 为此，我们可以使用通配符：
  `@Pointcut("execution(* com.baeldung.pointcutadvice.dao.FooDao.*(..))")`
  这里，第一个通配符匹配任何返回值，第二个匹配任何方法名称，并且 (..) 模式匹配任意数量的参数（零个或多个）。

within

- 实现与上一节相同结果的另一种方法是使用 inside PCD，它将匹配限制为某些类型的连接点：
  `@Pointcut("within(com.baeldung.pointcutadvice.dao.FooDao)")`
- 我们还可以匹配 com.baeldung 包或子包中的任何类型：
  `@Pointcut("within(com.baeldung..*)")`

this and target

- 这将匹配限制到 bean 引用是给定类型的实例的连接点，而 target 限制匹配到目标对象是给定类型的实例的连接点。前者在 Spring AOP 创建基于 [CGLIB](https://www.baeldung.com/cglib) 的代理时工作，后者在创建基于 JDK 的代理时使用。假设目标类实现了一个接口：
  `public class FooDao implements BarDao { ... }`
- 在这种情况下，Spring AOP 将使用基于 JDK 的代理，我们应该使用目标 PCD，因为代理对象将是 Proxy 类的实例并实现 BarDao 接口：
  `@Pointcut("target(com.baeldung.pointcutadvice.dao.BarDao)")`
- 另一方面，如果 FooDao 没有实现任何接口，或者 proxyTargetClass 属性设置为 true，那么代理对象将是 FooDao 的子类，我们可以使用这个 PCD：
  `@Pointcut("this(com.baeldung.pointcutadvice.dao.FooDao)")`

args

- 我们可以使用这个 PCD 来匹配特定的方法参数：  
  `@Pointcut("execution(* *..find*(Long))")`
  这个切入点匹配任何以 find 开头并且只有一个 Long 类型参数的方法。
- 如果我们想匹配具有任意数量参数的方法，但仍然具有 Long 类型的第一个参数，我们可以使用以下表达式：  
  `@Pointcut("execution(* *..find*(Long,..))")`

@target

- @target PCD（不要与上述 target PCD 混淆）将匹配限制为执行对象的类具有给定类型的注释(`@Repository`)的连接点：
  `@Pointcut("@target(org.springframework.stereotype.Repository)")`

@args

- 此 PCD 将匹配限制为连接点，其中传递的实际参数的运行时类型具有给定类型的注释。
  
  假设我们要跟踪所有接受带有 @Entity 注释的 bean 的方法：

  ```java
  @Pointcut("@args(com.baeldung.pointcutadvice.annotations.Entity)")
  public void methodsAcceptingEntities() {}
  ```

  要访问参数，我们应该为通知提供一个 JoinPoint 参数：

  ```java
  @Before("methodsAcceptingEntities()")
  public void logMethodAcceptionEntityAnnotatedBean(JoinPoint jp) {
    logger.info("Accepting beans with @Entity annotation: " + jp.getArgs()[0]);
  }
  ```

@within

- 此 PCD 将匹配限制为具有给定注释的类型内的连接点：
  `@Pointcut("@within(org.springframework.stereotype.Repository)")`
  等效于：
  `@Pointcut("within(@org.springframework.stereotype.Repository *)")`

@annotation

- 此 PCD 将匹配限制为连接点的主题具有给定注释的连接点。 例如，我们可以创建一个@Loggable 注解：
  
  ```java
  @Pointcut("@annotation(com.baeldung.pointcutadvice.annotations.Loggable)")
  public void loggableMethods() {}
  ```

  然后我们可以记录由该注释标记的方法的执行：

  ```java
  @Before("loggableMethods()")
  public void logMethod(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
    logger.info("Executing method: " + methodName);
  }
  ```

### 组合切入点表达式

切入点表达式可以使用 &&、|| 和 ! 运算符：

```java
@Pointcut("@target(org.springframework.stereotype.Repository)")
public void repositoryMethods() {}

@Pointcut("execution(* *..create*(Long,..))")
public void firstLongParamMethods() {}

@Pointcut("repositoryMethods() && firstLongParamMethods()")
public void entityCreationMethods() {}
```

## 实现自定义注解

我们将使用 Spring 中的 AOP 支持来实现自定义 AOP 注释。我们可以将其应用于 Spring bean 以在运行时向它们注入额外的行为。

我们将实现的 AOP 类型是注解驱动的。如果我们使用过 Spring [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html) 注解，我们可能已经对此很熟悉了：

```java
@Transactional
public void orderGoods(Order order) {
   // A series of database calls to be performed in a transaction
}
```

这里的关键是非侵入性(The key here is non-invasiveness.)。通过使用注释元数据，我们的核心业务逻辑不会被我们的事务代码污染。这使得推理、重构和隔离测试变得更容易。

有时，开发 Spring 应用程序的人可以将其视为“Spring Magic”，而无需详细考虑它是如何工作的。实际上，发生的事情并不是特别复杂。但是，一旦我们完成了本文中的步骤，我们将能够创建自己的自定义注解，以便理解和利用 AOP。

首先，让我们添加我们的 Maven 依赖项。对于这个例子，我们将使用 Spring Boot，因为它的约定优于配置的方法让我们能够尽快启动并运行：spring-boot-starter-parent、spring-boot-starter-aop

请注意，我们已经包含了 AOP 启动器，它引入了我们开始实现方面所需的库。

1.创建自定义注解

我们要创建的注释将用于记录方法执行所需的时间。让我们创建我们的注解：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {}
```

尽管实现相对简单，但值得注意的是这两个元注释的用途。

[@Target](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/annotation/Target.html) 注解告诉我们注解适用的地方。这里我们使用的是 ElementType.Method，这意味着它只适用于方法。如果我们尝试在其他任何地方使用注解，那么我们的代码将无法编译。这种行为是有道理的，因为我们的注释将用于记录方法执行时间。

[@Retention](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/annotation/Retention.html) 只是说明注解在运行时是否对 JVM 可用。默认情况下它不是，所以 Spring AOP 将无法看到注解。这就是它被重新配置的原因。

2.创建自定义方面

现在我们有了注释，让我们创建我们的方面。这只是将封装我们的横切关注点的模块，在我们的例子中是方法执行时间日志记录。它只是一个类，用 [@Aspect](https://www.eclipse.org/aspectj/doc/released/aspectj5rt-api/org/aspectj/lang/annotation/Aspect.html?is-external=true) 注释：

```java
@Aspect
@Component
public class ExampleAspect {}
```

我们还包含了 [@Component](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Component.html) 注释，因为我们的类也需要是一个 Spring bean 才能被检测到。本质上，这是我们将实现我们希望自定义注解注入的逻辑的类。

3.创建切入点和建议

现在，让我们创建切入点和建议。这将是一个存在于我们方面的带注释的方法：

```java
@Around("@annotation(LogExecutionTime)")
public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    return joinPoint.proceed();
}
```

从技术上讲，这并没有改变任何东西的行为，但是仍然有很多事情需要分析。

首先，我们用 [@Around](https://www.eclipse.org/aspectj/doc/released/aspectj5rt-api/org/aspectj/lang/annotation/Around.html?is-external=true) 注释了我们的方法。这是我们的建议，围绕建议意味着我们在方法执行之前和之后添加额外的代码。还有其他类型的建议，例如之前和之后，但它们将超出本文的范围。

接下来，我们的 @Around 注释有一个切入点参数。我们的切入点只是说，“将这个建议应用于任何带有 @LogExecutionTime 注释的方法。” 还有很多其他类型的切入点，但如果作用域，它们将再次被排除在外。

logExecutionTime() 方法本身就是我们的建议 Advice。有一个参数，[ProceedingJoinPoint](https://www.eclipse.org/aspectj/doc/next/runtime-api/org/aspectj/lang/ProceedingJoinPoint.html)。在例子中，这将是一个使用 @LogExecutionTime 注释的执行方法。

最后，当我们的注解方法最终被调用时，会发生的是我们的通知将首先被调用。然后由我们的建议决定下一步该做什么。在我们的例子中，我们的建议除了调用 `proceed()` 之外什么都不做，它只是调用原始的带注释的方法。

4.记录我们的执行时间

现在我们已经有了我们的骨架，我们需要做的就是在我们的建议中添加一些额外的逻辑。除了调用原始方法之外，这将是记录执行时间的内容。让我们将这个额外的行为添加到我们的建议中：

```java
@Around("@annotation(LogExecutionTime)")
public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;
    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
    return proceed;
}
```

同样，我们在这里没有做任何特别复杂的事情。我们刚刚记录了当前时间，执行了方法，然后将花费的时间打印到控制台。我们还记录了方法签名，它是为使用连接点实例而提供的。如果我们愿意，我们还可以访问其他信息，例如方法参数。

现在，让我们尝试用 @LogExecutionTime 注释一个方法，然后执行它来看看会发生什么。请注意，这必须是 Spring Bean 才能正常工作：

```java
@LogExecutionTime
public void serve() throws InterruptedException {
    Thread.sleep(2000);
}
```

执行后，我们应该会看到控制台记录了以下内容：

`void org.baeldung.Service.serve() executed in 2030ms`

## 获得建议方法信息

展示如何使用 Spring AOP 方面获取有关方法签名、参数和注释的所有信息。
我们通过定义切入点、将信息打印到控制台并检查运行测试的结果来做到这一点。

### 创建切入点注释

让我们创建一个 AccountOperation 注释。为了澄清，我们将使用它作为我们方面的切入点：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountOperation {
    String operation();
}
```

请注意，创建注释对于定义切入点不是必需的。也就是说，我们可以使用Spring AOP提供的切入点定义语言来定义其他切入点类型，比如类中的某些方法、以某个前缀开头的方法等。

### 创建示例服务

账户类

让我们创建一个具有 accountNumber 和 balance 属性的 methodinfo.Account POJO。我们将在我们的服务方法中使用它作为方法参数。

服务类

现在让我们创建带有两个方法的 methodinfo.BankAccountService 类，我们用 @AccountOperation 注释进行注释，这样我们就可以在我们的切面中获取方法的信息。请注意，withdraw 方法抛出一个检查异常 WithdrawLimitException 来演示我们如何获取有关方法抛出的异常的信息。

另外，请注意 getBalance 方法没有 AccountOperation 注释，因此它不会被切面拦截。

### 定义方面

让我们创建一个 methodinfo.BankAccountAspect 从我们的 BankAccountService 中调用的相关方法中获取所有必要的信息。

请注意，我们将切入点定义为注解，因此由于 BankAccountService 中的 getBalance 方法没有使用 AccountOperation 进行注解，切面不会拦截它。

> 注意 BankAccountAspect 编辑过都要重新加包，不然测试时会报错： `java.lang.NoSuchMethodError: com.baeldung.methodinfo.BankAccountAspect.aspectOf()Lcom/baeldung/methodinfo/BankAccountAspect`
  
#### 获取有关方法签名的信息

为了能够获取我们的方法签名信息，我们需要从 JoinPoint 对象中检索 MethodSignature：

```java
MethodSignature signature = (MethodSignature) joinPoint.getSignature();

System.out.println("full method description: " + signature.getMethod());
System.out.println("method name: " + signature.getMethod().getName());
System.out.println("declaring type: " + signature.getDeclaringType());
```

现在让我们调用服务的 withdraw() 方法：

```java
@Test
void withdraw() {
    bankAccountService.withdraw(account, 500.0);
    assertTrue(account.getBalance() == 1500.0);
}
```

运行withdraw() 测试后，我们现在可以在控制台上看到以下结果：

```log
full method description: public void com.baeldung.method.info.BankAccountService.withdraw(com.baeldung.methodinfo.Account,java.lang.Double) throws com.baeldung.methodinfo.WithdrawLimitException
method name: withdraw
declaring type: class com.baeldung.methodinfo.BankAccountService
```

#### 获取有关参数的信息

要检索有关方法参数的信息，我们可以使用 MethodSignature 对象：

```java
System.out.println("Method args names:");
Arrays.stream(signature.getParameterNames()).forEach(s -> System.out.println("arg name: " + s));

System.out.println("Method args types:");
Arrays.stream(signature.getParameterTypes()).forEach(s -> System.out.println("arg type: " + s));

System.out.println("Method args values:");
Arrays.stream(joinPoint.getArgs()).forEach(o -> System.out.println("arg value: " + o.toString()));
```

让我们通过调用 BankAccountService 中的 deposit 方法来试试这个：

```java
@Test
void deposit() {
    bankAccountService.deposit(account, 500.0);
    assertTrue(account.getBalance() == 2500.0);
}
```

这是我们在控制台上看到的：

```log
Method args names:
arg name: account
arg name: amount
Method args types:
arg type: class com.baeldung.methodinfo.Account
arg type: class java.lang.Double
Method args values:
arg value: Account{accountNumber='12345', balance=2000.0}
arg value: 500.0
```

#### 获取有关方法注释的信息

我们可以通过 Method 类的 getAnnotation() 方法获取注解的信息：

```java
Method method = signature.getMethod();
AccountOperation accountOperation = method.getAnnotation(AccountOperation.class);
System.out.println("Account operation annotation: " + accountOperation);
System.out.println("Account operation value: " + accountOperation.operation());
```

现在让我们重新运行我们的 withdraw() 测试并检查我们得到了什么：

```log
Account operation annotation: @com.baeldung.methodinfo.AccountOperation(operation=withdraw)
Account operation value: withdraw
```

#### 获取附加信息

可以获得一些关于方法的额外信息，比如它们的返回类型、它们的修饰符以及它们抛出的异常（如果有的话）：

```java
System.out.println("returning type: " + signature.getReturnType());
System.out.println("method modifier: " + Modifier.toString(signature.getModifiers()));
Arrays.stream(signature.getExceptionTypes())
  .forEach(aClass -> System.out.println("exception type: " + aClass));
```

现在让我们创建一个新的测试 withdrawWhenLimitReached ，使 withdraw() 方法超过其定义的取款限制：

```java
@Test 
void withdrawWhenLimitReached() { 
    Assertions.assertThatExceptionOfType(WithdrawLimitException.class)
      .isThrownBy(() -> bankAccountService.withdraw(account, 600.0)); 
    assertTrue(account.getBalance() == 2000.0); 
}
```

现在让我们检查控制台输出：

```log
....
returning type: void
method modifier: public
exception type: class com.baeldung.methodinfo.WithdrawLimitException
```

我们的最后一个测试将有助于演示 getBalance() 方法。 正如我们之前所说，它不会被切面拦截，因为方法声明中没有 AccountOperation 注解：

```java
@Test
void getBalance() {
    bankAccountService.getBalance();
}
```

运行此测试时，控制台中没有输出，正如我们预期的那样。

## Relevant articles

- [x] [Implementing a Custom Spring AOP Annotation](https://www.baeldung.com/spring-aop-annotation)
- [x] [Introduction to Spring AOP](https://www.baeldung.com/spring-aop)
- [x] [Introduction to Pointcut Expressions in Spring](https://www.baeldung.com/spring-aop-pointcut-tutorial)
- [x] [Introduction to Advice Types in Spring](https://www.baeldung.com/spring-aop-advice-tutorial)
- [x] [Get Advised Method Info in Spring AOP](https://www.baeldung.com/spring-aop-get-advised-method-info)
