# AspectJ

本文是对 AspectJ 的快速实用的介绍，有关详细信息，您可以查看 [AspectJ主页](https://eclipse.org/aspectj/)。

- [创建](#aspect-创建)
- [编织](#aspect-编织)
- [注释](#aspect-注释)
- [示例](#aspectj-示例)

首先，我们将展示如何启用面向方面的编程，然后我们将关注编译时、编译后和加载时编织之间的区别。

[AspectJ](https://eclipse.org/aspectj/) 使用 Java 编程语言的扩展来实现关注点和横切关注点的编织。

AspectJ 根据其用途提供不同的库。 我们可以在 Maven 中央存储库中的 org.aspectj 组下找到 Maven 依赖项。

在本文中，我们重点关注使用编译时、编译后和加载时 Weavers 创建方面和 Weaver 所需的依赖项。

AspectJ Runtime

- 运行 AspectJ 程序时，类路径应包含类和方面以及 AspectJ 运行时库 aspectjrt.jar：

  ```xml
  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.8.9</version>
  </dependency>
  ```

AspectJWeaver

- 除了 AspectJ 运行时依赖项之外，我们还需要包含 aspectjweaver.jar 以在加载时向 Java 类引入建议：

  ```xml
  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.9</version>
  </dependency>
  ```

这种依赖关系在 [Maven Central](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22org.aspectj%22%20AND%20a%3A%22aspectjrt%22) 上可用。

## Aspect 创建

AspectJ 提供了 AOP 的实现，具有三个核心概念：

- 加入点 Join Point
- 切入点 Pointcut
- 建议 Advice

我们将通过创建一个简单的程序来验证用户帐户余额来演示这些概念。

首先，让我们创建一个具有给定余额和提款方法的 aspectj.Account.java。

我们将创建一个 aspectj.AccountAspect.aj 文件来记录帐户信息并验证帐户余额（注意 AspectJ 文件以“.aj”文件扩展名结尾）。

我们在withdraw 方法中添加了一个切入点，并创建了三个引用定义的切入点的建议。

为了理解以下内容，我们引入以下定义：

- 方面 Aspect：跨多个对象的关注点的模块化。 每个方面都侧重于特定的横切功能
- 连接点 Join point：脚本执行过程中的一个点，例如方法执行或属性访问
- 建议 Advice：方面在特定连接点采取的行动
- 切入点 Pointcut ：匹配连接点 Join point 的正则表达式。 通知 Advice 与切入点表 Pointcut 达式相关联，并在与切入点 Pointcut 匹配的任何连接点 Join point 处运行

有关这些概念及其特定语义的更多详细信息，我们可能需要查看以下 [链接](https://eclipse.org/aspectj/doc/next/progguide/semantics-pointcuts.html)。

## Aspect 编织

接下来，我们需要将这些方面编织 weave 到我们的代码中。 以下部分介绍了三种不同类型的编织：AspectJ 中的 编译时编织 compile-time weaving、编译后编织 post-compile weaving 和 加载时编织 load-time weaving。

### 编译时编织

最简单的编织方法是编译时编织。 当我们拥有切面的源代码和我们正在使用切面的代码时，AspectJ 编译器将从源代码编译并生成一个编织类文件作为输出。 之后，在执行您的代码时，编织过程输出类作为普通 Java 类加载到 JVM 中。

我们可以下载 AspectJ 开发工具，因为它包含一个捆绑的 AspectJ 编译器。 AJDT 最重要的特性之一是横切关注点的可视化工具，它有助于调试切入点规范。 甚至在部署代码之前，我们就可以将组合效果可视化。

我们使用 Mojo 的 AspectJ Maven 插件将 AspectJ 方面编织到使用 AspectJ 编译器的类中。

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.7</version>
    <configuration>
        <complianceLevel>1.8</complianceLevel>
        <source>1.8</source>
        <target>1.8</target>
        <showWeaveInfo>true</showWeaveInfo>
        <verbose>true</verbose>
        <Xlint>ignore</Xlint>
        <encoding>UTF-8 </encoding>
    </configuration>
    <executions>
        <execution>
            <goals>
                <!-- use this goal to weave all your main classes -->
                <goal>compile</goal>
                <!-- use this goal to weave all your test classes -->
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

有关 AspectJ 编译器的选项参考的更多详细信息，我们可能需要查看以下 [链接](https://www.mojohaus.org/aspectj-maven-plugin/ajc_reference/standard_opts.html) 。

让我们为 Account 类添加一些测试用例：aspectj.AccountTest.java

当我们运行测试用例时，控制台中显示的以下文本意味着我们成功编织了源代码:

```log
[INFO] Join point 'method-call
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20)
advised by around advice from 'com.baeldung.aspectj.AccountAspect'
(AccountAspect.class:18(from AccountAspect.aj))

[INFO] Join point 'method-call
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type 
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20) 
advised by before advice from 'com.baeldung.aspectj.AccountAspect' 
(AccountAspect.class:13(from AccountAspect.aj))

[INFO] Join point 'method-call
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type 
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20) 
advised by after advice from 'com.baeldung.aspectj.AccountAspect'
(AccountAspect.class:26(from AccountAspect.aj))

2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
-  Balance before withdrawal: 20
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
-  Withdraw ammout: 5
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
- Balance after withdrawal : 15
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
-  Balance before withdrawal: 20
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
-  Withdraw ammout: 100
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
- Withdrawal Rejected!
2016-11-15 22:53:51 [main] INFO  com.baeldung.aspectj.AccountAspect 
- Balance after withdrawal : 20
```

### 编译后编织

编译后编织（有时也称为二进制编织）用于编织现有的类文件和 JAR 文件。 与编译时编织一样，用于编织的方面可以是源形式或二进制形式，并且本身可以由方面编织。

为了使用 Mojo 的 AspectJ Maven 插件来做到这一点，我们需要设置我们想要在插件配置中编织的所有 JAR 文件：

```xml
<configuration>
    <weaveDependencies>
        <weaveDependency>  
            <groupId>org.agroup</groupId>
            <artifactId>to-weave</artifactId>
        </weaveDependency>
        <weaveDependency>
            <groupId>org.anothergroup</groupId>
            <artifactId>gen</artifactId>
        </weaveDependency>
    </weaveDependencies>
</configuration>
```

包含要编织的类的 JAR 文件必须在 Maven 项目中列为 `<dependencies/>` 并在 AspectJ Maven 插件的 `<configuration>` 中列为 `<weaveDependencies/>`。

### 加载时编织

加载时编织只是延迟到类加载器加载类文件并将类定义给 JVM 的二进制编织。

为了支持这一点，需要一个或多个“编织类加载器”。 这些要么由运行时环境显式提供，要么使用“编织代理”启用。

启用加载时编织

可以使用 AspectJ 代理启用 AspectJ 加载时编织，该代理可以参与类加载过程并在 VM 中定义任何类型之前编织任何类型。 我们为 JVM -javaagent:pathto/aspectjweaver.jar 指定 javaagent 选项或使用 Maven 插件来配置 javaagent ：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <argLine>
            -javaagent:"${settings.localRepository}"/org/aspectj/
            aspectjweaver/${aspectj.version}/
            aspectjweaver-${aspectj.version}.jar
        </argLine>
        <useSystemClassLoader>true</useSystemClassLoader>
        <forkMode>always</forkMode>
    </configuration>
</plugin>
```

### 配置编织器

AspectJ 的加载时编织代理是通过使用 aop.xml 文件来配置的。 它在 META-INF 目录中的类路径上查找一个或多个 aop.xml 文件并聚合内容以确定编织器配置。

aop.xml 文件包含两个关键部分：

- 方面：定义织工的一个或多个方面，并控制在织造过程中要使用的方面。 aspect 元素可以可选地包含一个或多个 include 和 exclude 元素（默认情况下，所有定义的方面都用于编织）
- Weaver：为编织器定义编织器选项并指定应该编织的类型集。 如果未指定包含元素，则编织器可见的所有类型都将被编织

让我们为编织器配置一个方面：

```xml
<aspectj>
    <aspects>
        <aspect name="com.baeldung.aspectj.AccountAspect"/>
        <weaver options="-verbose -showWeaveInfo">
            <include within="com.baeldung.aspectj.*"/>
        </weaver>
    </aspects>
</aspectj>
```

可以看到，我们已经配置了一个aspect指向AccountAspect，只有com.baeldung.aspectj包中的源代码会被AspectJ编织。

## Aspect 注释

除了熟悉的基于 AspectJ 代码的方面声明风格外，AspectJ 5 还支持基于注释的方面声明风格。 我们将支持这种开发风格的注释集非正式地称为“@AspectJ”注释。

让我们创建一个注解：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
    public boolean isLocked() default false; 
}
```

我们使用@Secured 注解来启用或禁用一个方法：

```java
public class SecuredMethod {
    @Secured(isLocked = true)
    public void lockedMethod() {
    }
    @Secured(isLocked = false)
    public void unlockedMethod() {
    }
}
```

接下来，我们使用 AspectJ annotation-style 添加一个切面，并根据 @Secured annotation 的属性检查权限：

```java
@Aspect
public class SecuredMethodAspect {
    @Pointcut("@annotation(secured)")
    public void callAt(Secured secured) {
    }
    @Around("callAt(secured)")
    public Object around(ProceedingJoinPoint pjp, 
      Secured secured) throws Throwable {
        return secured.isLocked() ? null : pjp.proceed();
    }
}
```

有关 AspectJ 注释样式的更多详细信息，我们可以查看以下 [链接](https://eclipse.org/aspectj/doc/released/adk15notebook/annotations-aspectmembers.html) 。

接下来，我们使用加载时编织器编织我们的类和方面，并将 aop.xml 放在 META-INF 文件夹下，见 main/resources/META-INF/aop.xml。

最后，我们添加单元测试并检查结果：见 aspectj.SecuredMethodUnitTest.java 中的 testMethod()

当我们运行测试用例时，我们可能会检查控制台输出来验证我们是否成功地在源代码中编织了我们的切面和类：

```log
[INFO] Join point 'method-call
(void com.baeldung.aspectj.SecuredMethod.unlockedMethod())'
in Type 'com.baeldung.aspectj.test.SecuredMethodTest'
(SecuredMethodTest.java:11)
advised by around advice from 'com.baeldung.aspectj.SecuredMethodAspect'
(SecuredMethodAspect.class(from SecuredMethodAspect.java))

2016-11-15 22:53:51 [main] INFO com.baeldung.aspectj.SecuredMethod 
- unlockedMethod
2016-11-15 22:53:51 [main] INFO c.b.aspectj.SecuredMethodAspect - 
public void com.baeldung.aspectj.SecuredMethod.lockedMethod() is locked
```

## AspectJ 中的 Joinpoint 与 ProceedingJoinPoint

了解 AspectJ 中的 Joinpoint 和 ProceedingJoinPoint 之间的区别。

通过简短的解释和代码示例对其进行介绍。

1.JoinPoint

JoinPoint 是一个 AspectJ 接口，它提供对给定连接点可用状态的反射访问，例如方法参数、返回值或抛出的异常。它还提供有关方法本身的所有静态信息。

我们可以将它与@Before、@After、@AfterThrowing 和@AfterReturning 建议一起使用。这些切入点将分别在方法执行前、执行后、返回值后、或仅在抛出异常后、或仅在方法返回值后启动。

为了更好地理解，让我们看一个基本的例子。首先，我们需要声明一个切入点。我们将定义为 ArticleService 类中 getArticleList() 的每次执行：

```java
@Pointcut("execution(* com.baeldung.ArticleService.getArticleList(..))")
public void articleListPointcut(){ }
```

接下来，我们可以定义通知。在我们的示例中，我们将使用 @Before：

```java
@Before("articleListPointcut()")
public void beforeAdvice(JoinPoint joinPoint) {
    log.info(
      "Method {} executed with {} arguments",
      joinPoint.getStaticPart().getSignature(),
      joinPoint.getArgs()
    );
}
```

在上面的示例中，我们使用 @Before 建议来记录方法执行及其参数。一个类似的用例是记录我们代码中发生的异常：

```java
@AfterThrowing(
  pointcut = "articleListPointcut()",
  throwing = "e"
)
public void logExceptions(JoinPoint jp, Exception e) {
    log.error(e.getMessage(), e);
}
```

通过使用@AfterThrowing 建议，我们确保仅在发生异常时才进行日志记录。

2.ProceedingJoinPoint

ProceedingJoinPoint 是 JoinPoint 的扩展，它公开了附加的 proceed() 方法。调用时，代码执行会跳转到下一个通知或目标方法。它使我们能够控制代码流(It gives us the power to control the code flow)并决定是否继续进行进一步的调用。

可以只使用 @Around 建议，它围绕整个方法调用：

```java
@Around("articleListPointcut()")
public Object aroundAdvice(ProceedingJoinPoint pjp) {
    Object articles = cache.get(pjp.getArgs());
    if (articles == null) {
        articles = pjp.proceed(pjp.getArgs());
    }
    return articles;
}
```

在上面的示例中，我们说明了 @Around 建议最流行的用法之一。只有当缓存没有返回结果时，才会调用实际的方法。这正是 [Spring Cache Annotations](https://www.baeldung.com/spring-cache-tutorial) 的工作方式。

我们还可以使用 ProceedingJoinPoint 和 @Around 通知来重试操作以防出现任何异常：

```java
@Around("articleListPointcut()")
public Object aroundAdvice(ProceedingJoinPoint pjp) {
    try {
        return pjp.proceed(pjp.getArgs());
    } catch (Throwable) {
        log.error(e.getMessage(), e);
        log.info("Retrying operation");
        return pjp.proceed(pjp.getArgs());
    }
}
```

例如，此解决方案可用于在网络中断的情况下重试 HTTP 调用。

## AspectJ 示例

1.概述

在本教程中，我们将在调用配置类的方法时使用 AspectJ 编写跟踪日志输出。通过使用 AOP 建议来编写跟踪日志输出，我们将逻辑封装到单个编译单元中。

我们的示例扩展了 Intro to AspectJ 中提供的信息。

2.跟踪日志注解

我们将使用注释来配置类，以便可以跟踪它们的方法调用。使用注释为我们提供了一种简单的机制，可以将跟踪日志输出添加到新代码中，而无需直接添加日志语句。

让我们创建注释：aspectj.classmethodadvice.Trace.java

3.创造我们的方面

我们将创建一个切面来定义我们的切入点，以匹配我们关心的连接点和包含要执行的逻辑的环绕通知。

我们的方面看起来与此类似：aspectj.classmethodadvice.TracingAspect.aj

```java
...
    pointcut traceAnnotatedClasses(): within(@Trace *) && execution(* *(..));
    Object around() : traceAnnotatedClasses() {
        String signature = thisJoinPoint.getSignature().toShortString();
        LOG.trace("Entering " + signature);
        try {
            return proceed();
        } finally {
            LOG.trace("Exiting " + signature);
        }
    }
...
```

在我们的方面，我们定义了一个名为 *traceAnnotatedClasses* 的切入点 *pointcut* ，以匹配使用 *Trace* 注释注释的类中方法的执行。通过定义和命名切入点，我们可以像在类中的方法一样重用它(By defining and naming a pointcut, we can reuse it as we would a method in a class.)。我们将使用这个命名的切入点来围绕 *around* 建议配置我们的切入点。

我们的环绕通知将代替与我们的切入点匹配的任何连接点执行(Our around advice will execute in place of any join point matched by our pointcut and will return an Object)，并将返回一个对象。通过拥有 *Object* 返回类型，我们可以解释具有任何返回类型的建议方法，甚至是 *void*。

我们检索匹配连接点的签名以创建签名的短字符串表示，以将上下文添加到我们的跟踪消息中。结果，我们的日志输出将包含类的名称和执行的方法，这为我们提供了一些所需的上下文。

在我们的跟踪输出调用之间，我们调用了一个名为 *proceed* 的方法。此方法可用于环绕通知，以便继续执行匹配的连接点(This method is available for around advice in order to continue the execution of the matched join point)。返回类型将是 *Object*，因为我们无法在编译时知道返回类型。我们将在将最终跟踪输出发送到日志后将此值发送回调用方。

我们将 proceed() 调用包装在 try/finally 块中，以确保写入退出消息(We wrap the proceed() call in a try/finally block to ensure the exit message is written)。如果我们想跟踪抛出的异常，我们可以添加 after() 建议，以便在抛出异常时写入日志消息： *aspectj.classmethodadvice.TracingAspect.aj.after()*

4.注释我们的代码

现在我们需要启用我们的跟踪。让我们创建一个简单的类并使用我们的自定义注释激活跟踪日志记录：aspectj.classmethodadvice.MyTracedService.java

使用 Trace 注释，我们类中的方法将匹配我们定义的切入点。当这些方法执行时，跟踪消息将被写入日志。

运行调用这些方法的代码后，我们的日志输出应包含类似以下内容：

```log
22:37:58.867 [main] TRACE c.b.a.c.TracingAspect - Entering MyTracedService.performSomeAdditionalLogic()
22:37:58.868 [main] INFO  c.b.a.c.MyTracedService - Inside performSomeAdditionalLogic...
22:37:58.868 [main] TRACE c.b.a.c.TracingAspect - Exiting MyTracedService.performSomeAdditionalLogic()
22:37:58.869 [main] TRACE c.b.a.c.TracingAspect - Entering MyTracedService.performSomeLogic()
22:37:58.869 [main] INFO  c.b.a.c.MyTracedService - Inside performSomeLogic...
22:37:58.869 [main] TRACE c.b.a.c.TracingAspect - Exiting MyTracedService.performSomeLogic()
```

5.结论

在本文中，我们使用 AspectJ 来拦截类的所有方法，并在类上使用一个注解。这样做可以让我们快速将跟踪日志功能添加到新代码中。

我们还将跟踪日志输出逻辑整合到单个编译单元中，以提高我们在应用程序发展时修改跟踪日志输出的能力。

## Relevant articles

- [x] [Intro to AspectJ](https://www.baeldung.com/aspectj)
- [x] [Advise Methods on Annotated Classes With AspectJ](https://www.baeldung.com/aspectj-advise-methods)
- [x] [Joinpoint vs. ProceedingJoinPoint in AspectJ](https://www.baeldung.com/aspectj-joinpoint-proceedingjoinpoint)
