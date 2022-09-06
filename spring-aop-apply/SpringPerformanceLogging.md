# Spring 性能日志记录

在本教程中，我们将研究 Spring Framework 为性能监控提供的几个基本选项。

## 编码

### PerformanceMonitorInterceptor

一个简单的解决方案来获得我们方法执行时间的基本监控功能，我们可以利用 Spring AOP（面向方面编程）中的 PerformanceMonitorInterceptor 类。

Spring AOP 允许在应用程序中定义横切关注点，即拦截一个或多个方法的执行的代码，以添加额外的功能。

PerformanceMonitorInterceptor 类是一个拦截器，可以与任何自定义方法关联，同时执行。 此类使用 StopWatch 实例来确定方法运行的开始和结束时间。

让我们创建一个简单的 performancemonitor.Person 类和一个 performancemonitor.PersonService 类，其中包含我们将监控的两个方法 getFullName()、getAge() 。

为了使用 Spring 监控拦截器，我们需要定义一个切入点 pointcut 和顾问 advisor ：参见 performancemonitor.AopConfiguration 。

- 切入点包含一个表达式，用于标识我们想要被拦截的方法——在我们的例子中是 PersonService 类的 getFullName() 方法。
- 在配置了 performanceMonitorInterceptor() bean 之后，我们需要将拦截器与切入点关联起来。这是通过顾问实现的，如上面的示例所示。
- 最后，@EnableAspectJAutoProxy 注解为我们的 bean 启用了 AspectJ 支持。简单地说，AspectJ 是一个库，它通过方便的注解（如@Pointcut）使 Spring AOP 的使用变得更容易。

创建配置后，我们需要将拦截器类的日志级别设置为 TRACE（set the log level of the interceptor class to TRACE），因为这是它记录消息的级别。

例如，使用 Jog4j，我们可以通过 log4j.properties 文件来实现：

`log4j.logger.org.springframework.aop.interceptor.PerformanceMonitorInterceptor=TRACE, stdout`

对于 getAge() 方法的每次执行，我们都会在控制台日志中看到 TRACE 消息：

```log
2017-01-08 19:19:25 TRACE 
  PersonService:66 - StopWatch 
  'com.baeldung.performancemonitor.PersonService.getFullName': 
  running time (millis) = 10
```

## 自定义性能监控拦截器

如果我们想更好地控制性能监控的完成方式，我们可以实现我们自己的自定义拦截器。

为此，让我们扩展 AbstractMonitoringInterceptor 类并重写 invokeUnderTrace() 方法来记录方法的开始、结束和持续时间，并在方法执行持续时间超过 10 毫秒时发出警告：

参见 performancemonitor.MyPerformanceMonitorInterceptor 。

需要遵循与上一节中的将自定义拦截器关联到一个或多个方法的相同步骤。

让我们为 PersonService 的 getAge() 方法定义一个切入点，并将其与我们创建的拦截器相关联：

```java
@Pointcut("execution(public int com.baeldung.performancemonitor.PersonService.getAge(..))")
public void myMonitor() { }
    
@Bean
public MyPerformanceMonitorInterceptor myPerformanceMonitorInterceptor() {
    return new MyPerformanceMonitorInterceptor(true);
}
    
@Bean
public Advisor myPerformanceMonitorAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("com.baeldung.performancemonitor.AopConfiguration.myMonitor()");
    return new DefaultPointcutAdvisor(pointcut, myPerformanceMonitorInterceptor());
}
```

参见 performancemonitor.AopConfiguration 。

让我们将自定义拦截器的日志级别设置为 INFO：

`log4j.logger.com.baeldung.performancemonitor.MyPerformanceMonitorInterceptor=INFO, stdout`

getAge() 方法的执行产生了以下输出：

```log
2017-01-08 19:19:25 INFO PersonService:26 - 
  Method com.baeldung.performancemonitor.PersonService.getAge 
  execution started at:Sun Jan 08 19:19:25 EET 2017
2017-01-08 19:19:25 INFO PersonService:33 - 
  Method com.baeldung.performancemonitor.PersonService.getAge execution lasted:50 ms
2017-01-08 19:19:25 INFO PersonService:34 - 
  Method com.baeldung.performancemonitor.PersonService.getAge 
  execution ended at:Sun Jan 08 19:19:25 EET 2017
2017-01-08 19:19:25 WARN PersonService:37 - 
  Method execution longer than 10 ms!
```
