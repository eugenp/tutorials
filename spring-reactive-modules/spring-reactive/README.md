# Spring Reactive

This module contains articles describing reactive processing in Spring.

## Reactor Core介绍

Reactor Core是一个Java 8库，实现了反应式编程模型。它建立在[Reactive Streams](http://www.reactive-streams.org)规范之上，是构建反应式应用的标准。

从非反应式Java开发的背景来看，走向反应式可能是一个相当陡峭的学习曲线。当把它与Java 8 Stream API进行比较时，这将变得更加具有挑战性，因为它们可能会被误认为是相同的高级抽象概念。

在这篇文章中，我们将试图揭开这一范式的神秘面纱。我们将通过Reactor采取小步骤，直到我们建立起如何编写反应式代码的画面，为以后系列中更高级的文章奠定基础。

1. 反应式流规范

    在看Reactor之前，我们应该先看一下Reactive Streams Specification。这是Reactor实现的内容，它为该库奠定了基础。

    本质上，Reactive Streams是一个异步流处理的规范。

    **Essentially, Reactive Streams is a specification for asynchronous stream processing.**

    换句话说，就是一个大量事件被异步产生和消费的系统。想想看，每秒有成千上万的股票更新流进入一个金融应用程序，而它必须及时地对这些更新作出反应。

    这方面的主要目标之一是解决背压(backpressure)的问题。如果我们有一个生产者向消费者发出的事件比它处理的速度快，那么最终消费者将被事件淹没，耗尽系统资源。

    背压意味着我们的消费者应该能够告诉生产者要发送多少数据，以防止这种情况，这就是规范中所规定的。

2. Maven的依赖性

    在我们开始之前，让我们添加我们的Maven依赖项。

    ```xml
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-core</artifactId>
        <version>3.4.16</version>
    </dependency>

    <dependency> 
        <groupId>ch.qos.logback</groupId> 
        <artifactId>logback-classic</artifactId> 
        <version>1.2.6</version> 
    </dependency>
    ```

    我们也要把[Logback](https://logback.qos.ch/)作为一个依赖项加入。这是因为我们将记录反应器的输出，以便更好地了解数据流。



## Relevant articles

- [Intro To Reactor Core](https://www.baeldung.com/reactor-core)
- [Debugging Reactive Streams in Java](https://www.baeldung.com/spring-debugging-reactive-streams)
- [Guide to Spring 5 WebFlux](https://www.baeldung.com/spring-webflux)
- [Introduction to the Functional Web Framework in Spring 5](https://www.baeldung.com/spring-5-functional-web)
- [Spring 5 WebClient](https://www.baeldung.com/spring-5-webclient)
- [Spring WebClient vs. RestTemplate](https://www.baeldung.com/spring-webclient-resttemplate)
- [Spring WebClient Requests with Parameters](https://www.baeldung.com/webflux-webclient-parameters)
- [Handling Errors in Spring WebFlux](https://www.baeldung.com/spring-webflux-errors)
- [Spring Security 5 for Reactive Applications](https://www.baeldung.com/spring-security-5-reactive)
- [Concurrency in Spring WebFlux](https://www.baeldung.com/spring-webflux-concurrency)