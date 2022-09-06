# Java 什么时候抛出 UndeclaredThrowableException？

在本教程中，我们将了解导致 Java 抛出 UndeclaredThrowableException 异常实例的原因。

## 理论

### UndeclaredThrowableException

从理论上讲，当我们尝试抛出未声明的检查异常时，Java 会抛出 UndeclaredThrowableException 的实例。也就是说，我们没有在 throws 子句中声明检查的异常，但我们在方法体中抛出了该异常。

有人可能会争辩说这是不可能的，因为 Java 编译器会通过编译错误来阻止这一点。例如，如果我们尝试编译：

```java
public void undeclared() {
    throw new IOException();
}
```

Java 编译器失败并显示以下消息：

`java: unreported exception java.io.IOException; must be caught or declared to be thrown`

即使在编译时可能不会抛出未声明的检查异常，但在运行时仍然有可能。例如，让我们考虑一个运行时代理拦截一个不抛出任何异常的方法：

```java
public void save(Object data) {
    // omitted
}
```

如果代理本身抛出检查异常，从调用者的角度来看，save 方法会抛出检查异常。调用者可能对该代理一无所知，并且会将此异常归咎于保存。

在这种情况下，Java 会将实际检查的异常包装在 UndeclaredThrowableException 中，并抛出。值得一提的是，UndeclaredThrowableException 本身就是一个未经检查的异常。

现在我们对该理论有了足够的了解，让我们看一些现实世界的例子。

### Java Dynamic Proxy 动态代理

作为我们的第一个示例，让我们为 java.util.List 接口创建一个运行时代理 ([runtime proxy](https://www.baeldung.com/java-dynamic-proxies)) 并拦截其方法调用。首先，我们应该实现调用处理程序 [InvocationHandler](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/reflect/InvocationHandler.html) 接口并将额外的逻辑放在那里：

```java
public class ExceptionalInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("size".equals(method.getName())) {
            throw new SomeCheckedException("Always fails");
        }           
        throw new RuntimeException();
    }
}

public class SomeCheckedException extends Exception {
    public SomeCheckedException(String message) {
        super(message);
    }
}
```

如果代理方法是 size，则此代理将引发检查异常。否则，它将引发未经检查的异常。

让我们看看 Java 如何处理这两种情况。首先，我们将调用 List.size() 方法：

```java
ClassLoader classLoader = getClass().getClassLoader();
InvocationHandler invocationHandler = new ExceptionalInvocationHandler();
List<String> proxy = (List<String>) Proxy.newProxyInstance(classLoader, 
  new Class[] { List.class }, invocationHandler);

assertThatThrownBy(proxy::size)
  .isInstanceOf(UndeclaredThrowableException.class)
  .hasCauseInstanceOf(SomeCheckedException.class);
```

如上图，我们为List接口创建一个代理，并在其上调用size方法。代理反过来拦截调用并抛出一个检查异常。然后，Java 将此检查的异常包装在 UndeclaredThrowableException 的实例中。(The proxy, in turn, intercepts the call and throws a checked exception. Then, Java wraps this checked exception inside an instance of UndeclaredThrowableException.) 发生这种情况是因为我们以某种方式抛出了一个已检查的异常，而没有在方法声明中声明它。

如果我们在 List 接口上调用任何其他方法：

`assertThatThrownBy(proxy::isEmpty).isInstanceOf(RuntimeException.class);`

由于代理抛出未经检查的异常，Java 让异常按原样传播。(Since the proxy throws an unchecked exception, Java lets the exception to propagate as-is.)

### Spring Aspect

当我们在 Spring Aspect 中抛出已检查异常而建议的方法没有声明它们时，也会发生同样的事情。让我们从一个注解开始： 参见 undeclared.ThrowUndeclared

现在我们将建议使用此注释注释的所有方法，定义方面 undeclared.UndeclaredAspect 。

基本上，这个建议将使所有带注释的方法都抛出一个检查异常，即使它们没有声明这样的异常。现在，让我们创建一个服务： 参见 undeclared.UndeclaredService

如果我们调用带注解的方法，Java 会抛出 UndeclaredThrowableException 异常的实例： 参见 undeclared.UndeclaredThrowableExceptionIntegrationTest

如上所示，Java 将实际异常封装为原因，并改为抛出 UndeclaredThrowableException 异常。
