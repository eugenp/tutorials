# java 反射

## 在运行时更改注释参数

注释 Annotation ，一种可以添加到 Java 代码中的元数据形式。 这些注释可以在编译时处理并嵌入到类文件中，或者可以在运行时使用反射保留和访问。

Java 实现使用两个数据字段来存储注解数据：注解 annotations、声明注解 declaredAnnotations。 这两者之间的区别：首先存储来自父类的注释，然后存储仅用于当前类。

在本文中，我们将讨论如何使用反射在运行时更改注解值。 我们将在此示例中使用类级别的注释。

Java 允许使用现有的注释创建新的注释。 在最简单的形式中，注解表示为 @ 符号后跟注解名称： `@Override` 。

让我们创建自己的注解 Greeter：

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Greeter {    
    public String greet() default ""; 
}
```

现在，我们将创建一个使用类级别注解的 Java 类 Greetings：

```java
@Greeter(greet="Good morning")
public class Greetings {}
```

现在，我们将使用反射访问注释值。 Java 类 Class 提供了一个方法 getAnnotation 来访问一个类的注解：

```java
Greeter greetings = Greetings.class.getAnnotation(Greeter.class);
System.out.println("Hello there, " + greetings.greet() + " !!");
```

更改注释

Java 类 Class 维护一个用于管理注解的映射——注解类作为键，注解对象作为值：

`Map<Class<? extends Annotation>, Annotation> map;`

我们将更新此 Map 以在运行时更改注释。 访问此映射的方法在各种 JDK 实现中有所不同。

Java 8 实现将注释信息存储在类 AnnotationData 中。 我们可以使用 annotationData 方法访问这个对象。 我们将 annotationData 方法的可访问性设置为 true，因为它是一个私有方法：

```java
Method method = Class.class.getDeclaredMethod(ANNOTATION_METHOD, null);
method.setAccessible(true);
```

现在，我们可以访问注释字段。 由于该字段也是一个私有字段，我们将可访问性设置为 true：

```java
Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
annotations.setAccessible(true);
```

该字段具有注释缓存映射，其中存储注释类和值对象。 让我们改变一下：

```java
Map<Class<? extends Annotation>, Annotation> map = annotations.get(annotationData); 
map.put(targetAnnotation, targetValue);
```

应用

让我们看这个例子：

```java
Greeter greetings = Greetings.class.getAnnotation(Greeter.class);
System.err.println("Hello there, " + greetings.greet() + " !!");
```

这将是问候“早上好”，因为这是我们提供给注释的值。
现在，我们将再创建一个 Greeter 类型的对象，其值为“晚安”：

`Greeter targetValue = new DynamicGreeter("Good evening");`

让我们用新值更新注释Map：

`alterAnnotationValueJDK8(Greetings.class, Greeter.class, targetValue);`

让我们再次检查问候值：

```java
greetings = Greetings.class.getAnnotation(Greeter.class);
System.err.println("Hello there, " + greetings.greet() + " !!");
```

它会打招呼“晚上好”。

## Java 中的动态代理

### 介绍

这篇文章是关于 Java 的[动态代理](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html)——它是我们在语言中可用的主要代理机制之一。

简单地说，代理是通过自己的设施（通常是真实方法）传递函数调用的前端或包装器——可能会添加一些功能。

Simply put, proxies are fronts or wrappers that pass function invocation through their own facilities (usually onto real methods) – potentially adding some functionality.

动态代理允许使用一种方法的单个类为具有任意数量方法的任意类的多个方法调用提供服务。动态代理可以被认为是一种外观，但它可以伪装成任何接口的实现。在幕后，它将所有方法调用路由到单个处理程序——invoke() 方法。

Dynamic proxies allow one single class with one single method to service multiple method calls to arbitrary classes with an arbitrary number of methods. A dynamic proxy can be thought of as a kind of Facade, but one that can pretend to be an implementation of any interface. Under the cover, **it routes all method invocations to a single handler** – the invoke() method.

虽然它不是用于日常编程任务的工具，但动态代理对于框架编写者来说非常有用。它也可以用于那些直到运行时才知道具体类实现的情况。

此功能内置在标准 JDK 中，因此不需要额外的依赖项。

### 编码

1.调用处理程序

让我们构建一个简单的代理，它实际上不做任何事情，除了打印请求调用的方法并返回一个硬编码的数字。

首先，我们需要创建 java.lang.reflect.InvocationHandler 的子类型： dynamicproxy.DynamicInvocationHandler

在这里，我们定义了一个简单的代理，它记录调用了哪个方法并返回 42。

2.创建代理实例

我们刚刚定义的调用处理程序所服务的代理实例是通过对 java.lang.reflect.Proxy 类的工厂方法调用创建的：

```java
Map proxyInstance = (Map) Proxy.newProxyInstance(
  DynamicProxyTest.class.getClassLoader(), 
  new Class[] { Map.class }, 
  new DynamicInvocationHandler());
```

`proxyInstance.put("hello", "world");`

正如预期的那样，在日志文件中会打印出有关调用 put() 方法的消息。

通过 Lambda 表达式的调用处理程序

由于 InvocationHandler 是一个函数式接口，因此可以使用 lambda 表达式内联定义处理程序：

```java
Map proxyInstance = (Map) Proxy.newProxyInstance(
  DynamicProxyTest.class.getClassLoader(), 
  new Class[] { Map.class }, 
  (proxy, method, methodArgs) -> {
    if (method.getName().equals("get")) {
        return 42;
    } else {
        throw new UnsupportedOperationException(
          "Unsupported method: " + method.getName());
    }
});
```

在这里，我们定义了一个处理程序，它为所有 get 操作返回 42，并为其他所有操作抛出 UnsupportedOperationException。

它以完全相同的方式调用：

```java
(int) proxyInstance.get("hello"); // 42
proxyInstance.put("hello", "world"); // exception
```

定时动态代理示例

让我们来看看动态代理的一种潜在的真实世界场景。

假设我们想记录我们的函数执行需要多长时间。 为此，我们首先定义一个能够包装“真实”对象、跟踪时间信息和反射调用的处理程序：dynamicproxy.TimingDynamicInvocationHandler

随后，此代理可用于各种对象类型：

```java
Map mapProxyInstance = (Map) Proxy.newProxyInstance(
  DynamicProxyTest.class.getClassLoader(), new Class[] { Map.class }, 
  new TimingDynamicInvocationHandler(new HashMap<>()));

mapProxyInstance.put("hello", "world");

CharSequence csProxyInstance = (CharSequence) Proxy.newProxyInstance(
  DynamicProxyTest.class.getClassLoader(), 
  new Class[] { CharSequence.class }, 
  new TimingDynamicInvocationHandler("Hello World"));

csProxyInstance.length()
```

在这里，我们代理了一个地图和一个字符序列（字符串）。

代理方法的调用将委托给被包装的对象并产生日志语句：

```log
Executing put finished in 19153 ns 
Executing get finished in 8891 ns 
Executing charAt finished in 11152 ns 
Executing length finished in 10087 ns
```

## 什么原因导致 java.lang.reflect.InvocationTargetException？

使用 [Java反射API](https://www.baeldung.com/java-reflection) 时，经常会遇到 java.lang.reflect.InvocationTargetException。

在本教程中，我们将通过一个简单的示例来了解它以及如何处理它，了解反射层如何包装任何底层异常。

1.InvocationTargetException 的原因

它主要发生在我们使用反射层并尝试调用本身引发底层异常的方法或构造函数时。

反射层用 InvocationTargetException 包装了方法抛出的实际异常。

让我们试着用一个例子来理解它。

我们将编写一个带有故意抛出异常的方法的类： reflection.exception.invocationtarget.InvocationTargetExample

让我们在一个简单的 JUnit 5 测试中使用反射调用上述方法： reflection.exception.invocationtarget.InvocationTargetUnitTest

在代码中，我们断言了 InvocationTargetException，它在调用方法时被抛出。这里要注意的重要一点是，实际的异常（在本例中为 ArithmeticException）被包装到 InvocationTargetException 中。

现在，为什么反射首先不抛出实际的异常？

原因在于它可以让我们了解异常发生是由于通过反射层调用方法失败还是发生在方法本身内部。

2.如何处理 InvocationTargetException？

这里实际的底层异常是 InvocationTargetException 的原因，因此我们可以使用 Throwable.getCause() 来获取有关它的更多信息。

让我们看看我们如何使用 getCause() 在上面使用的相同示例中获取实际异常：

`assertEquals(ArithmeticException.class, exception.getCause().getClass());`

我们在抛出的同一个异常对象上使用了 getCause() 方法。 我们已经断言 ArithmeticException.class 是异常的原因。

因此，一旦我们得到底层异常，我们可以重新抛出相同的异常，将其包装在一些自定义异常中，或者根据我们的要求简单地记录异常。

## 如何获取正在执行的方法的名称？

有时我们需要知道当前正在执行的 Java 方法的名称。

介绍了几种在当前执行堆栈中获取方法名称的简单方法。

### Java 9：堆栈行走 API

Java 9 引入了 [Stack-Walking API](https://www.baeldung.com/java-9-stackwalking-api) 以一种惰性且高效的方式遍历 JVM 堆栈帧。为了使用这个 API 找到当前正在执行的方法，我们可以编写一个简单的测试：

```java
public void givenJava9_whenWalkingTheStack_thenFindMethod() {
    StackWalker walker = StackWalker.getInstance();
    Optional<String> methodName = walker.walk(frames -> frames
      .findFirst()
      .map(StackWalker.StackFrame::getMethodName));

    assertTrue(methodName.isPresent());
    assertEquals("givenJava9_whenWalkingTheStack_thenFindMethod", methodName.get());
}
```

首先，我们使用 [getInstance()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/StackWalker.html#getInstance()) 工厂方法获得一个 [StackWalker](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/StackWalker.html) 实例。然后我们使用 [walk()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/StackWalker.html#walk(java.util.function.Function)) 方法从上到下遍历栈帧：

- walk() 方法可以将堆栈帧流 - `Stream<StackFrame>` - 转换为任何东西
- 给定流中的第一个元素是栈顶帧
- 栈顶帧总是代表当前正在执行的方法

因此，如果我们从流中获取第一个元素，我们就会知道当前正在执行的方法的详细信息。更具体地说，我们可以使用 [StackFrame.getMethodName()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/StackWalker.StackFrame.html#getMethodName()) 来查找方法名称。

与其他方法相比，Stack-Walking API 有几个优点：

- 无需创建虚拟匿名 (dummy anonymous) 内部类实例 — new Object().getClass() {}
- 无需创建虚拟异常 — new Throwable()
- 无需急切地捕获整个堆栈跟踪 stacktrace ，这可能会很昂贵

相反，StackWalker 只是以一种懒惰的方式逐个遍历堆栈。在这种情况下，它只获取顶部帧，而不是堆栈跟踪方法，它急切地捕获所有帧。

最重要的是，如果您使用的是 Java 9+，请使用 Stack-Walking API。

> [core-java-9-new-features](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-9-new-features) 模块中提供了相应示例。

### 兼容方法

使用 getEnclosureMethod

我们可以使用 getEnclosureMethod() API 找到正在执行的方法的名称：

```java
public void givenObject_whenGetEnclosingMethod_thenFindMethod() {
    String methodName = new Object() {}
      .getClass()
      .getEnclosingMethod()
      .getName();
       
    assertEquals("givenObject_whenGetEnclosingMethod_thenFindMethod",
      methodName);
}
```

使用 Throwable Stack Trace

使用 Throwable 堆栈跟踪为我们提供了当前正在执行的方法的堆栈跟踪：

```java
public void givenThrowable_whenGetStacktrace_thenFindMethod() {
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
 
    assertEquals(
      "givenThrowable_whenGetStacktrace_thenFindMethod",
      stackTrace[0].getMethodName());
}
```

使用线程堆栈跟踪

此外，当前线程（从 JDK 1.5 开始）的堆栈跟踪通常包括正在执行的方法的名称：

```java
public void givenCurrentThread_whenGetStackTrace_thenFindMethod() {
    StackTraceElement[] stackTrace = Thread.currentThread()
      .getStackTrace();
 
    assertEquals(
      "givenCurrentThread_whenGetStackTrace_thenFindMethod",
      stackTrace[1].getMethodName()); 
}
```

但是，我们需要记住，这种解决方案有一个明显的缺点。一些虚拟机可能会跳过一个或多个堆栈帧。虽然这并不常见，但我们应该意识到这可能会发生。

示例，参见： currentmethod.CurrentlyExecutedMethodFinderUnitTest 。

## Relevant Articles

- [ ] [Void Type in Java](https://www.baeldung.com/java-void-type)
- [ ] [Retrieve Fields from a Java Class Using Reflection](https://www.baeldung.com/java-reflection-class-fields)
- [ ] [Method Parameter Reflection in Java](http://www.baeldung.com/java-parameter-reflection)
- [x] [Changing Annotation Parameters At Runtime](http://www.baeldung.com/java-reflection-change-annotation-params)
- [x] [Dynamic Proxies in Java](http://www.baeldung.com/java-dynamic-proxies)
- [x] [Whax Causes java.lang.reflect.InvocationTargetException?](https://www.baeldung.com/java-lang-reflect-invocationtargetexception)
- [x] [How to Get a Name of a Method Being Executed?](http://www.baeldung.com/java-name-of-executing-method)
