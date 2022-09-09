# java 反射

## Java中的空类型

了解 Void 这个特殊的类，看看何时以及如何使用它，以及如何尽可能避免使用它。

虚空类型：从 JDK 1.1 开始，Java 为我们提供了 Void 类型。它的目的只是将 void 返回类型表示为一个类并包含一个 `Class<Void>` 公共值。它不可实例化，因为它唯一的构造函数是私有的。

因此，我们可以分配给 Void 变量的唯一值是 null。它可能看起来有点无用，但我们现在将看看何时以及如何使用这种类型。

### 用途

在某些情况下，使用 Void 类型可能会很有趣。

#### 反射

首先，我们可以在进行反射时使用它。实际上，任何 void 方法的返回类型都将匹配包含前面提到的 `Class<Void>` 值的 Void.TYPE 变量。

让我们想象一个简单的 reflection.voidtype.Calculator 类。

有些方法返回一个整数，有些没有返回任何东西。现在，假设我们必须通过反射检索所有不返回任何结果的方法。我们将通过使用 Void.TYPE 变量来实现这一点：

参见 reflection.voidtype.CalculatorUnitTest 。

正如我们所见，只检索了 clear() 和 print() 方法。

#### 泛型

Void 类型的另一种用法是泛型类。假设我们正在调用一个需要 Callable 参数的方法：

```java
public class Defer {
    public static <V> V defer(Callable<V> callable) throws Exception {
        return callable.call();
    }
}
```

但是，我们想要传递的 Callable 不必返回任何东西。因此，我们可以传递一个 `Callable<Void>`：

见 reflection.voidtype.DeferUnitTest givenVoidCallable_whenDiffer_thenReturnNull()

如上所示，为了从返回类型为 Void 的方法返回，我们只需返回 null。此外，我们可以使用随机类型（例如 `Callable<Integer>` ）并返回 null 或根本不返回类型（Callable），但使用 Void 清楚地表明了我们的意图。

我们也可以将此方法应用于 lambdas。事实上，我们的 Callable 可以写成 lambda。让我们想象一个需要函数的方法，但我们想使用一个不返回任何内容的函数。然后我们只需要让它返回 Void：

```java
public static <T, R> R defer(Function<T, R> function, T arg) {
    return function.apply(arg);
}
```

Test 见 reflection.voidtype.DeferUnitTest givenVoidFunction_whenDiffer_thenReturnNull()

### 如何避免使用它？

我们现在将看到如何避免这些情况。首先，让我们考虑一下带有 Callable 参数的方法。为了避免使用 `Callable<Void>`，我们可能会提供另一种采用 Runnable 参数的方法：

```java
public static void defer(Runnable runnable) {
    runnable.run();
}
```

所以，我们可以给它传递一个不返回任何值的 Runnable，从而摆脱无用的返回 null：

```java
Runnable runnable = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello!");
    }
};

Defer.defer(runnable);
```

但是，如果 Defer 类不是我们要修改的呢？然后我们可以坚持使用 `Callable<Void>` 选项，或者创建另一个采用 Runnable 的类并将调用推迟到 eflection.voidtype.Defer 类。

通过这样做，我们将繁琐的部分一劳永逸地封装在我们自己的方法中，允许未来的开发人员使用更简单的 API。

当然，Function 也可以实现同样的效果。在我们的示例中，Function 不返回任何内容，因此我们可以提供另一个采用 Consumer 的方法：

```java
public static <T> void defer(Consumer<T> consumer, T arg) {
    consumer.accept(arg);
}
```

那么，如果我们的函数不带任何参数怎么办？我们可以使用 Runnable 或创建我们自己的功能接口（如果这看起来更清晰）：

```java
public interface Action {
    void execute();
}
```

然后，我们再次重载 defer() 方法：

```java
public static void defer(Action action) {
    action.execute();
}
```

```java
Action action = () -> System.out.println("Hello!");
Defer.defer(action);
```

## Java中的方法参数反射

Java 8 中添加了方法参数反射支持。简单地说，它提供了在运行时获取参数名称的支持。

此信息最明显的用例是帮助在编辑器中实现自动完成支持。

在这个快速教程中，我们将了解如何在运行时访问构造函数和方法的参数名称——使用反射。

编译器参数

为了能够访问方法名称信息，我们必须明确选择加入。

为此，我们在编译期间指定参数选项。

对于一个 Maven 项目，我们可以在 pom.xml 中声明这个选项：

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.1</version>
  <configuration>
    <source>1.8</source>
    <target>1.8</target>
    <compilerArgument>-parameters</compilerArgument>
  </configuration>
</plugin>
```

示例类

我们将使用一个人为的带有一个名为 fullName 的属性的 Person 类来演示：reflect.Person

用法

Parameter 类在 Java 8 中是新的，并且有多种有趣的方法。如果提供了 -parameters 编译器选项，isNamePresent() 方法将返回 true。

要访问参数的名称，我们可以简单地调用 getName()：参见 reflect.MethodParamNameUnitTest 。

## 使用反射从 Java 类中检索字段

反射是计算机软件在运行时检查其结构的能力。在 Java 中，我们通过使用 Java 反射 API 来实现这一点。它允许我们在运行时检查类的元素，例如字段、方法甚至内部类。

本教程将重点介绍如何检索 Java 类的字段，包括私有和继承字段。

### 从类中检索字段

让我们首先看看如何检索一个类的字段，而不管它们的可见性如何。稍后，我们还将看到如何获取继承字段。

让我们从一个具有两个字符串字段的 reflection.Person 类的示例开始：lastName 和 firstName。前者是受保护的（稍后会有用），而后者是私有的。

我们想使用反射获取 lastName 和 firstName 字段。我们将通过使用 Class::getDeclaredFields 方法来实现这一点。顾名思义，这会以 Field 数组的形式返回一个类的所有声明字段： 参见 reflection.PersonAndEmployeeReflectionUnitTest givenPersonClass_whenGetDeclaredFields_thenTwoFields() 。

如我们所见，我们得到了 Person 类的两个字段。我们检查它们的名称和类型是否与 Person 类中的字段定义相匹配。

### 检索继承的字段

现在让我们看看如何获​​取 Java 类的继承字段。

为了说明这一点，让我们创建一个名为 reflection.Employee 的第二个类，它扩展了 Person，它有一个自己的字段： employeeId 。

#### 检索简单类层次结构中的继承字段

使用 Employee.class.getDeclaredFields() 只会返回 employeeId 字段，因为此方法不会返回在超类中声明的字段。要获得继承的字段，我们还必须获得 Person 超类的字段。

当然，我们可以在 Person 和 Employee 类上使用 getDeclaredFields() 方法，并将它们的结果合并到一个数组中。但是，如果我们不想显式指定超类怎么办？

在这种情况下，我们可以使用 Java 反射 API 的另一种方法：Class::getSuperclass。这给了我们另一个类的超类，我们不需要知道那个超类是什么。

让我们收集 Employee.class 和 Employee.class.getSuperclass() 上的 getDeclaredFields() 的结果，并将它们合并到一个数组中：

参见 test/PersonAndEmployeeReflectionUnitTest givenEmployeeClass_whenGetDeclaredFieldsOnBothClasses_thenThreeFields()

我们可以在这里看到我们已经收集了 Person 的两个字段以及 Employee 的单个字段。

但是，Person 的私有字段真的是继承字段吗？ 没那么多。 对于包私有字段也是如此。 只有公共和受保护的字段被认为是继承的。Only public and protected fields are considered inherited.

#### 过滤公共和受保护的字段

不幸的是，Java API 中没有任何方法允许我们从一个类及其超类中收集公共和受保护的字段。 Class::getFields 方法接近我们的目标，因为它返回类及其超类的所有公共字段，但不返回受保护的字段。

我们必须只获取继承字段的唯一方法是使用 getDeclaredFields() 方法，就像我们刚才所做的那样，并使用 Field::getModifiers 方法过滤其结果。这个返回一个 int 表示当前字段的修饰符。每个可能的修饰符都被分配了一个介于 2^0 和 2^7 之间的 2 的幂。

例如，public 是 2^0，static 是 2^3。因此，在公共和静态字段上调用 ​​getModifiers() 方法将返回 9。

然后，可以在此值和特定修饰符的值之间执行按位和，以查看该字段是否具有该修饰符。如果操作返回 0 以外的值，则应用修饰符，否则不应用。

我们很幸运，因为 Java 为我们提供了一个实用程序类来检查 getModifiers() 返回的值中是否存在修饰符。在示例中，让我们使用 isPublic() 和 isProtected() 方法仅收集继承的字段：

```java
List<Field> personFields = Arrays.stream(Employee.class.getSuperclass().getDeclaredFields())
  .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers()))
  .collect(Collectors.toList());

assertEquals(1, personFields.size());

assertTrue(personFields.stream().anyMatch(field ->
  field.getName().equals(LAST_NAME_FIELD)
    && field.getType().equals(String.class))
);
```

正如我们所见，结果不再带有私有字段。

### 检索深层类层次结构中的继承字段

在上面的示例中，我们处理了单个类层次结构。 如果我们有更深的类层次结构并且想要收集所有继承的字段，我们现在该怎么办？

假设我们有一个 Employee 的子类或一个 Person 的超类——那么获取整个层次结构的字段将需要检查所有超类。

我们可以通过创建一个贯穿层次结构的实用方法来实现这一点，为我们构建完整的结果：

```java
List<Field> getAllFields(Class clazz) {
    if (clazz == null) {
        return Collections.emptyList();
    }

    List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
    List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields())
      .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers()))
      .collect(Collectors.toList());
    result.addAll(filteredFields);
    return result;
}
```

此递归方法将通过类层次结构搜索公共和受保护字段，并返回在列表中找到的所有内容。

让我们通过一个新的 reflection.MonthEmployee 类的小测试来说明它，扩展 Employee 类。

这个类定义了一个新的领域——reward。给定所有层次类，我们的方法应该给我们以下字段定义：Person::lastName、Employee::employeeId 和 MonthEmployee::reward。

让我们在 MonthEmployee 上调用 getAllFields() 方法：参见 test/PersonAndEmployeeReflectionUnitTest givenMonthEmployeeClass_whenGetAllFields_thenThreeFields() 。

正如预期的那样，收集了所有公共和受保护的字段。

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
- [x] [Retrieve Fields from a Java Class Using Reflection](https://www.baeldung.com/java-reflection-class-fields)
- [x] [Method Parameter Reflection in Java](http://www.baeldung.com/java-parameter-reflection)
- [x] [Changing Annotation Parameters At Runtime](http://www.baeldung.com/java-reflection-change-annotation-params)
- [x] [Dynamic Proxies in Java](http://www.baeldung.com/java-dynamic-proxies)
- [x] [Whax Causes java.lang.reflect.InvocationTargetException?](https://www.baeldung.com/java-lang-reflect-invocationtargetexception)
- [x] [How to Get a Name of a Method Being Executed?](http://www.baeldung.com/java-name-of-executing-method)
