# Spring Cloud Stream简介

1. 概述

    Spring Cloud Stream是一个建立在Spring Boot和Spring Integration之上的框架，有助于创建事件驱动或消息驱动的微服务。

    在这篇文章中，我们将通过一些简单的例子介绍Spring Cloud Stream的概念和结构。

2. Maven的依赖性

    为了开始工作，我们需要将[Spring Cloud Starter Stream with the broker RabbitMQ](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-starter-stream-rabbit%22)的Maven依赖项作为消息传递中间件添加到我们的pom.xml中。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        <version>3.1.3</version>
    </dependency>
    ```

    我们还将从Maven中心添加模块依赖，以启用JUnit支持。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-stream-test-support</artifactId>
        <version>3.1.3</version>
        <scope>test</scope>
    </dependency>
    ```

3. 主要概念

    微服务架构遵循 "[smart endpoints and dumb pipes](https://martinfowler.com/articles/microservices.html#SmartEndpointsAndDumbPipes)" 原则。端点之间的通信由RabbitMQ或Apache Kafka等消息传递中间件方驱动。服务通过这些端点或通道发布领域事件进行通信。

    让我们走过构成Spring Cloud Stream框架的概念，以及我们在构建消息驱动服务时必须注意的基本范式。

    1. 构造

        让我们看看Spring Cloud Stream中的一个简单服务，它监听输入绑定并向输出绑定发送响应。

        ```java
        @SpringBootApplication
        @EnableBinding(Processor.class)
        public class MyLoggerServiceApplication {
            public static void main(String[] args) {
                SpringApplication.run(MyLoggerServiceApplication.class, args);
            }

            @StreamListener(Processor.INPUT)
            @SendTo(Processor.OUTPUT)
            public LogMessage enrichLogMessage(LogMessage log) {
                return new LogMessage(String.format("[1]: %s", log.getMessage()));
            }
        }
        ```

        注解@EnableBinding将应用程序配置为绑定接口处理器中定义的通道INPUT和OUTPUT。这两个通道都是绑定的，可以配置为使用具体的消息传递中间件或绑定器。

        让我们看一下所有这些概念的定义：

        - Bindings 绑定--一个接口的集合，声明性地识别输入和输出通道
        - Binder 绑定器--消息传递中间件的实现，如Kafka或RabbitMQ
        - Channel 通道--代表消息传递中间件和应用程序之间的通信管道
        - StreamListeners - Bean中的消息处理方法，在MessageConverter完成中间件特定事件和域对象类型/POJO之间的序列化/反序列化后，这些方法将被自动调用到来自通道的消息上。
        - Message Schemas 消息模式--用于消息的序列化和反序列化，这些模式可以静态地从某个位置读取或动态地加载，支持领域对象类型的演变

    2. 通信模式

        指定给目的地的消息是由发布-订阅消息模式交付的。发布者将消息归类为主题，每个主题由一个名称标识。订阅者表达对一个或多个主题的兴趣。中间件对消息进行过滤，将那些感兴趣的主题传递给订阅者。

        现在，订阅者可以被分组。消费者组是一组订阅者或消费者，由一个组的ID识别，在这个组内，来自一个主题或主题的分区的消息以负载平衡的方式被传递。

4. 编程模型

    本节介绍了构建Spring Cloud Stream应用程序的基本知识。

    1. 功能测试

        测试支持是一个绑定器的实现，允许与通道进行交互并检查消息。

        让我们向上述enrichLogMessage服务发送一条消息，并检查响应中是否包含文本"[1]: "在消息的开头。

        ```java
        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration(classes = MyLoggerServiceApplication.class)
        @DirtiesContext
        public class MyLoggerApplicationTests {

            @Autowired
            private Processor pipe;

            @Autowired
            private MessageCollector messageCollector;

            @Test
            public void whenSendMessage_thenResponseShouldUpdateText() {
                pipe.input()
                .send(MessageBuilder.withPayload(new LogMessage("This is my message"))
                .build());

                Object payload = messageCollector.forChannel(pipe.output())
                .poll()
                .getPayload();

                assertEquals("[1]: This is my message", payload.toString());
            }
        }
        ```

    2. 自定义通道

        在上面的例子中，我们使用了Spring Cloud提供的处理器接口，它只有一个输入和一个输出通道。

        如果我们需要不同的东西，比如一个输入和两个输出通道，我们可以创建一个自定义处理器。

        ```java
        public interface MyProcessor {
            String INPUT = "myInput";

            @Input
            SubscribableChannel myInput();

            @Output("myOutput")
            MessageChannel anOutput();

            @Output
            MessageChannel anotherOutput();
        }
        ```

        Spring将为我们提供这个接口的正确实现。通道名称可以使用注解来设置，比如@Output("myOutput")。

        否则，Spring将使用方法名作为通道名。因此，我们有三个通道，分别叫做myInput、myOutput和anotherOutput。

        现在，让我们设想一下，如果数值小于10，我们要将消息路由到一个输出，如果数值大于或等于10，则路由到另一个输出。

        ```java
        @Autowired
        private MyProcessor processor;

        @StreamListener(MyProcessor.INPUT)
        public void routeValues(Integer val) {
            if (val < 10) {
                processor.anOutput().send(message(val));
            } else {
                processor.anotherOutput().send(message(val));
            }
        }

        private static final <T> Message<T> message(T val) {
            return MessageBuilder.withPayload(val).build();
        }
        ```

    3. 有条件调度

        使用@StreamListener注解，我们也可以使用我们用[SpEL表达式](https://www.baeldung.com/spring-expression-language)定义的任何条件来过滤我们在消费者中期待的消息。

        作为一个例子，我们可以使用条件调度作为另一种方法，将消息发送到不同的输出。

        ```java
        @Autowired
        private MyProcessor processor;

        @StreamListener(
        target = MyProcessor.INPUT, 
        condition = "payload < 10")
        public void routeValuesToAnOutput(Integer val) {
            processor.anOutput().send(message(val));
        }

        @StreamListener(
        target = MyProcessor.INPUT, 
        condition = "payload >= 10")
        public void routeValuesToAnotherOutput(Integer val) {
            processor.anotherOutput().send(message(val));
        }
        ```

        这种方法的唯一限制是，这些方法必须不返回一个值。

5. 设置

    让我们设置将处理来自 RabbitMQ 代理的消息的应用程序。

    1. 绑定器配置

        我们可以通过 META-INF/spring.binders 将我们的应用程序配置为使用默认的粘合剂实现。

        ```log
        rabbit:\
        org.springframework.cloud.stream.binder.rabbit.config.RabbitMessageChannelBinderConfiguration
        ```

        或者我们可以通过包含此依赖项将 RabbitMQ 的绑定器库添加到 classpath 中。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
            <version>1.3.0.RELEASE</version>
        </dependency>
        ```

        如果没有提供绑定器实现，Spring将在通道之间使用直接的消息通信。

    2. RabbitMQ 配置

        要配置 3.1 节中的示例以使用 RabbitMQ 绑定器，我们需要更新位于 src/main/resources 的 application.yml。

        ```yaml
        spring:
        cloud:
            stream:
            bindings:
                input:
                destination: queue.log.messages
                binder: local_rabbit
                output:
                destination: queue.pretty.log.messages
                binder: local_rabbit
            binders:
                local_rabbit:
                type: rabbit
                environment:
                    spring:
                    rabbitmq:
                        host: <host>
                        port: 5672
                        username: <username>
                        password: <password>
                        virtual-host: /
        ```

        输入绑定将使用名为queue.log.messages的交换，而输出绑定将使用交换queue.pretty.log.messages。两个绑定都将使用名为 local_rabbit 的绑定器。

        请注意，我们不需要提前创建 RabbitMQ 交换或队列。当运行应用程序时，这两个交换器将自动创建。

        为了测试应用程序，我们可以使用 RabbitMQ 管理站点来发布消息。在交易所 queue.log.messages 的 Publish Message 面板中，我们需要输入 JSON 格式的请求。

    3. 自定义消息转换

        Spring Cloud Stream允许我们为特定的内容类型应用消息转换。在上面的例子中，我们不想使用JSON格式，而是想提供纯文本。

        要做到这一点，我们要使用MessageConverter对LogMessage进行自定义转换。

        ```java
        @SpringBootApplication
        @EnableBinding(Processor.class)
        public class MyLoggerServiceApplication {
            //...

            @Bean
            public MessageConverter providesTextPlainMessageConverter() {
                return new TextPlainMessageConverter();
            }

            //...
        }
        ```

        ```java
        public class TextPlainMessageConverter extends AbstractMessageConverter {

            public TextPlainMessageConverter() {
                super(new MimeType("text", "plain"));
            }

            @Override
            protected boolean supports(Class<?> clazz) {
                return (LogMessage.class == clazz);
            }

            @Override
            protected Object convertFromInternal(Message<?> message, 
                Class<?> targetClass, Object conversionHint) {
                Object payload = message.getPayload();
                String text = payload instanceof String 
                ? (String) payload 
                : new String((byte[]) payload);
                return new LogMessage(text);
            }
        }
        ```

        应用这些变化后，回到 Publish Message 发布消息面板，如果我们把头 "contentTypes" 设置为 "text/plain"，把有效载荷设置为 "Hello World"，它应该像以前一样工作。

    4. 消费者组

        当我们的应用程序运行多个实例时，每当输入通道中有一个新的消息时，所有的订阅者都会得到通知。

        大多数时候，我们需要消息只被处理一次。Spring Cloud Stream通过消费者组实现了这种行为。

        为了启用这种行为，每个消费者绑定可以使用`spring.cloud.stream.bindings.<CHANNEL>.group`属性来指定一个组名。

        ```yaml
        spring:
        cloud:
            stream:
            bindings:
                input:
                destination: queue.log.messages
                binder: local_rabbit
                group: logMessageConsumers
                ...
        ```

6. 消息驱动的微服务

    在本节中，我们将介绍在微服务背景下运行我们的Spring Cloud Stream应用程序所需的所有功能。

    1. 扩大规模

        当多个应用程序运行时，确保数据在消费者之间正确分割是很重要的。为了做到这一点，Spring Cloud Stream提供了两个属性。

        - spring.cloud.stream.instanceCount - 运行中的应用程序的数量
        - spring.cloud.stream.instanceIndex - 当前应用程序的索引

        例如，如果我们已经部署了上述MyLoggerServiceApplication应用程序的两个实例，那么两个应用程序的属性spring.cloud.stream.instanceCount应该是2，而属性spring.cloud.stream.instanceIndex应该分别是0和1。

        如果我们使用[本文](https://www.baeldung.com/spring-cloud-data-flow-stream-processing)所述的Spring Data Flow部署Spring Cloud Stream应用程序，这些属性会自动设置。

    2. 分区

        领域事件可以是分区消息。这在我们扩展存储和提高应用性能时有帮助。

        领域事件通常有一个分区键，这样它就会和相关的消息在同一个分区中结束。

        比方说，我们想让日志消息按照消息中的第一个字母进行分区，这将是分区键，并被分成两个分区。

        一个分区是以A-M开头的日志信息，另一个分区是N-Z开头的。这可以通过两个属性进行配置。

        - spring.cloud.stream.bindings.output.producer.partitionKeyExpression - 分割有效载荷的表达式
        - spring.cloud.stream.bindings.output.producer.partitionCount - 组的数量。

        有时要分割的表达式太复杂，无法只用一行来写。对于这些情况，我们可以使用属性 spring.cloud.stream.bindings.output.producer.partitionKeyExtractorClass 编写我们的自定义分区策略。

    3. 健康指示器

        在微服务背景下，我们还需要检测服务何时停机或开始失效。Spring Cloud Stream提供了management.health.binders.enabled属性来启用绑定器的健康指标。

        当运行应用程序时，我们可以在`http://<host>:<port>/health`查询健康状态。

7. 总结

    在本教程中，我们介绍了Spring Cloud Stream的主要概念，并通过一些简单的例子展示了如何通过RabbitMQ使用它。关于Spring Cloud Stream的更多信息可以在[这里](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/)找到。

    本文的源代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-stream/spring-cloud-stream-rabbit)上找到。

## Relevant Articles

- [x] [Introduction to Spring Cloud Stream](http://www.baeldung.com/spring-cloud-stream)