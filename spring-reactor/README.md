# 介绍 Project Reactor Bus

在这篇快速文章中，我们将通过设置一个反应式、事件驱动应用程序的真实场景来介绍[reactor-bus](https://projectreactor.io/docs/core/snapshot/reference/)。

> 注意：reactor-bus 项目已在 Reactor 3.x 中被删除。[存档的 reactor-bus 仓库](https://github.com/reactor-attic/reactor-bus#reactor-bus)

1. Reactor 项目的基础知识

   1. 为什么是 Reactor？

      现代应用程序需要处理大量的并发请求和处理大量的数据。标准的、阻塞的代码已经不足以满足这些要求。

      [反应式](https://en.wikipedia.org/wiki/Reactor_pattern)设计模式是一种基于事件的架构方法，用于异步处理来自单个或多个服务处理程序的大量并发服务请求。

      Project Reactor 就是基于这种模式，它有一个明确而雄心勃勃的目标，即在 JVM 上构建非阻塞的、反应式的应用程序。

   2. 示例场景

      在我们开始之前，这里有几个有趣的场景，利用反应式架构风格是有意义的，只是为了了解我们可能应用它的地方。

      像亚马逊这样的大型在线购物平台的通知服务
      银行部门的巨大交易处理服务
      股票交易业务，股票价格同时变化

2. Maven 依赖性

   让我们开始使用 Project Reactor Bus，在 pom.xml 中添加以下依赖项。

   io.projectreactor.reactor-bus.2.0.8.RELEASE

   我们可以在[Maven 中心](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22io.projectreactor%22)查看 reactor-bus 的最新版本。

3. 构建一个演示应用程序

   为了更好地理解基于反应器的方法的好处，让我们看一个实际的例子。

   我们将建立一个简单的应用程序，负责向一个在线购物平台的用户发送通知。例如，如果一个用户下了一个新的订单，那么这个应用程序就会通过电子邮件或短信发送订单确认。

   一个典型的同步实现自然会受到电子邮件或短信服务的吞吐量的限制。因此，流量高峰，如节假日，通常会有问题。

   使用反应式方法，我们可以将我们的系统设计得更加灵活，并能更好地适应外部系统（如网关服务器）可能出现的故障或超时。

   让我们来看看这个应用--从更传统的方面开始，到更反应式的结构。

   1. 简单的 POJO

      首先，让我们创建一个 POJO 类来表示通知数据。参见 NotificationData.java

   2. 服务层

      现在让我们来定义一个简单的服务层。参见 NotificationService.java

      和实现，模拟一个长期运行的操作。NotificationServiceimpl.java

      请注意，为了说明通过短信或电子邮件网关发送消息的真实场景，我们故意在 initiateNotification 方法中引入了 Thread.sleep(5000)的 5 秒延迟。

      因此，当一个线程击中服务时，它将被阻断五秒钟。

   3. 消费者

      现在让我们跳到我们的应用程序的更多反应性方面，并实现一个消费者--然后我们将其映射到反应器事件总线上。NotificationConsumer.java

      我们可以看到，我们创建的消费者实现了`Consumer<T>`接口。主要的逻辑位于接受方法中。

      这也是我们在典型的[Spring 监听器](https://www.baeldung.com/spring-events#listener)实现中可以见到的类似方法。

   4. 控制器

      最后，现在我们已经能够消费事件了，让我们也来生成它们。

      我们将在一个简单的控制器中完成这个任务。NotificationController.java

      这一点不言自明--我们在这里通过事件总线发出事件。

      例如，如果客户点击参数值为 10 的 URL，那么 10 个事件将通过事件总线被发送。

   5. Java 配置

      现在让我们把所有东西放在一起，创建一个简单的 Spring Boot 应用程序。

      首先，我们需要配置 EventBus 和环境 Bean。参见 Config.java

      在我们的案例中，我们是用环境中可用的默认线程池来实例化 EventBus 的。

      另外，我们也可以使用一个自定义的 Dispatcher 实例。

      ```java
      EventBus evBus = EventBus.create(
      env,
      Environment.newDispatcher(
          REACTOR_CAPACITY,REACTOR_CONSUMERS_COUNT,
          DispatcherType.THREAD_POOL_EXECUTOR));
      ```

      现在，我们准备创建一个主程序代码。参见 NotificationApplication.java

      在我们的运行方法中，我们正在注册 notificationConsumer，以便在通知匹配给定的选择器时被触发。

      注意我们是如何使用$属性的静态导入来创建一个选择器对象的。

   6. 运行
      执行程序 NotificationApplication.java
      访问 <http://localhost:8080/startNotification/10>

4. 测试该应用程序

   现在让我们创建一个测试，看看我们的 NotificationApplication 的运行情况。

   ERROR: Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test

   处理：确保测试类在测试包 test 下的包名和类路径 java 下的包名一致导致，或指定执行 NotificationApplication 类。

   ```java
   @RunWith(SpringRunner.class)
   @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   public class NotificationApplicationIntegrationTest {

       @LocalServerPort
       private int port;

       @Test
       public void givenAppStarted_whenNotificationTasksSubmitted_thenProcessed() {
           RestTemplate restTemplate = new RestTemplate();
           restTemplate.getForObject("http://localhost:" + port + "/startNotification/10", String.class);
       }
   }
   ```

   正如我们所看到的，一旦请求被执行，所有 10 个任务就会立即被提交，而不会产生任何阻塞。而一旦提交，通知事件就会被并行处理。

   ```log
   Notification 0: notification task submitted successfully
   Notification 1: notification task submitted successfully
   Notification 2: notification task submitted successfully
   Notification 3: notification task submitted successfully
   Notification 4: notification task submitted successfully
   Notification 5: notification task submitted successfully
   Notification 6: notification task submitted successfully
   Notification 7: notification task submitted successfully
   Notification 8: notification task submitted successfully
   Notification 9: notification task submitted successfully
   Notification service started for Notification ID: 1
   Notification service started for Notification ID: 2
   Notification service started for Notification ID: 3
   Notification service started for Notification ID: 0
   Notification service ended for Notification ID: 1
   Notification service ended for Notification ID: 0
   Notification service started for Notification ID: 4
   Notification service ended for Notification ID: 3
   Notification service ended for Notification ID: 2
   Notification service started for Notification ID: 6
   Notification service started for Notification ID: 5
   Notification service started for Notification ID: 7
   Notification service ended for Notification ID: 4
   Notification service started for Notification ID: 8
   Notification service ended for Notification ID: 6
   Notification service ended for Notification ID: 5
   Notification service started for Notification ID: 9
   Notification service ended for Notification ID: 7
   Notification service ended for Notification ID: 8
   Notification service ended for Notification ID: 9
   ```

   重要的是要记住，在我们的方案中，没有必要以任何特定的顺序处理这些事件。

## Relevant articles

- [x] [Introduction to Project Reactor Bus](https://www.baeldung.com/reactor-bus)
