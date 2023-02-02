# Alibaba Sentinel介绍

1. 概述

    顾名思义，[Sentinel](https://github.com/alibaba/Sentinel)是一个强大的微服务防护系统。它提供了流量控制(flow control)、并发限制(concurrency limiting)、断路(circuit breaking)和自适应系统保护(adaptive system protection)等功能，以保证其可靠性。它是一个开源的组件，由阿里巴巴集团积极维护。此外，它正式成为[Spring Cloud Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)的一部分。

    在本教程中，我们将看一下Sentinel的一些主要功能。此外，我们将看到一个如何使用它的例子，它的注释支持，以及它的监控仪表板。

2. 功能

    1. 流量控制

        Sentinel控制随机传入请求的速度，以避免微服务的过载。这确保我们的服务不会被激增的流量杀死。它支持各种流量整形策略。当每秒查询次数（QPS）过高时，这些策略会自动将流量调整到适当的形状。

        这些流量整形策略中的一些是。

        - Direct Rejection Mode（直接拒绝模式）- 当每秒的请求数超过设定的阈值时，它会自动拒绝进一步的请求
        - Slow Start Warm-Up Mode（慢速启动预热模式）- 如果流量突然激增，该模式确保请求数逐渐增加，直到达到上限。

    2. 断路和降级(Circuit Breaking and Downgrade)

        当一个服务同步调用另一个服务时，有可能另一个服务会因为某些原因而停机。在这种情况下，线程会被阻塞，因为它们一直在等待另一个服务的响应。这可能会导致资源耗尽，调用者服务也将无法处理进一步的请求。这就是所谓的级联效应(cascading effect)，可以使我们的整个微服务架构崩溃。

        为了防止这种情况的发生，一个断路器出现了。它将立即阻止对其他服务的所有后续调用。在超时期过后，一些请求会被通过。如果它们成功了，那么断路器就会恢复正常流动。否则，超时期又开始了。

        Sentinel使用最大并发量限制的原则来实现断路。它通过限制并发线程的数量来减少不稳定资源的影响。

        Sentinel还将不稳定的资源降级。当一个资源的响应时间过高时，对该资源的所有调用将在指定的时间窗口被拒绝。这可以防止调用变得非常慢，导致级联效应的情况。

    3. 自适应系统保护

        Sentinel在系统负载过高的情况下保护我们的服务器。它使用load1（系统负载）作为衡量标准来启动流量控制。在以下条件下，请求将被阻止。

        - 当前系统负载（load1）> 阈值（highestSystemLoad）。
        - 当前并发请求（线程数）> 估计容量（最小响应时间 * 最大QPS (`min response time * max QPS`))
3. 如何使用

    1. 添加Maven依赖项

        在我们的Maven项目中，我们需要在pom.xml中添加sentinel-core依赖项。

        ```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-core</artifactId>
            <version>1.8.0</version>
        </dependency>
        ```

    2. 定义资源

        让我们使用Sentinel API在一个try-catch块中定义我们的资源和相应的业务逻辑。

        ```java
        try (Entry entry = SphU.entry("HelloWorld")) {
            // Our business logic here.
            System.out.println("hello world");
        } catch (BlockException e) {
            // Handle rejected request.
        }
        ```

        这个资源名称为 "HelloWorld "的try-catch块，是我们业务逻辑的入口，由Sentinel负责保护。

    3. 定义流控制规则

        这些规则控制流向我们的资源，如阈值计数或控制行为--例如，直接拒绝或缓慢启动。让我们使用FlowRuleManager.loadRules()来配置流量规则。

        ```java
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RESOURCE_NAME);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);
        ```

        这个规则定义了我们的资源 "RESOURCE_NAME" 每秒最多可以响应一个请求。

    4. 定义降级规则

        使用降级规则，我们可以配置断路器的阈值请求数、恢复超时，以及其他设置。
        让我们使用DegradeRuleManager.loadRules()来配置降级规则。

        ```java
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(RESOURCE_NAME);
        rule.setCount(10);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
        ```

        这条规则规定，当我们的资源RESOURCE_NAME不能满足10个请求（阈值计数）时，电路将中断。所有对该资源的后续请求将被Sentinel阻断10秒（时间窗口）。

    5. 定义系统保护规则

        使用系统保护规则，我们可以配置并确保自适应的系统保护（load1的阈值，平均响应时间，并发线程数）。让我们使用SystemRuleManager.loadRules()方法来配置系统规则。

        ```java
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
        ```

        这条规则规定，对于我们的系统，最高系统负载是每秒10个请求。如果当前负载超过这个阈值，所有进一步的请求将被阻止。

4. 注释支持

    Sentinel还为定义资源提供了面向方面的注解支持。

    首先，我们要为sentinel-annotation-aspectj添加Maven依赖项。

    ```xml
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-annotation-aspectj</artifactId>
        <version>1.8.0</version>
    </dependency>
    ```

    然后，我们在配置类中添加@Configuration，将sentinel方面注册为Spring bean。

    ```java
    @Configuration
    public class SentinelAspectConfiguration {
        @Bean
        public SentinelResourceAspect sentinelResourceAspect() {
            return new SentinelResourceAspect();
        }
    }
    ```

    @SentinelResource表示资源定义。它有像value这样的属性，它定义了资源名称。属性fallback是回退方法的名称。当电路中断时，这个回退方法定义了我们程序的备用流程。让我们使用@SentinelResource注解来定义这个资源。

    ```java
    @SentinelResource(value = "resource_name", fallback = "doFallback")
    public String doSomething(long i) {
        return "Hello " + i;
    }

    public String doFallback(long i, Throwable t) {
        // Return fallback value.
        return "fallback";
    }
    ```

    这就定义了名称为resource_name的资源，以及回落方法。

5. 监控仪表板

    Sentinel还提供了一个监控仪表板。有了它，我们可以监控客户端，并动态地配置规则。我们可以实时看到我们定义的资源的传入流量的数量。

    1. 启动仪表板

        首先，我们需要下载[Sentinel Dashboard jar](https://github.com/alibaba/Sentinel/releases)。然后，我们可以使用命令来启动仪表板。

        `java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar`

        一旦仪表盘应用程序启动，我们就可以按照下面几节的步骤连接我们的应用程序。

    2. 准备我们的应用程序

        让我们把sentinel-transport-simple-http依赖项添加到我们的pom.xml中。

        ```xml
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-transport-simple-http</artifactId>
            <version>1.8.0</version>
        </dependency>
        ```

    3. 将我们的应用程序连接到仪表板

        在启动应用程序时，我们需要添加仪表板的IP地址。

        `-Dcsp.sentinel.dashboard.server=consoleIp:port`

        现在，只要有资源被调用，仪表盘就会收到来自我们应用程序的心跳声。

        我们还可以使用仪表板动态地操纵流量、降级和系统规则。

6. 总结

    在这篇文章中，我们看到了阿里巴巴Sentinel流量控制、断路器和自适应系统保护的主要功能。

    相应的例子可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-sentinel)上找到。

## Relevant Articles

- [x] [Introduction to Alibaba Sentinel](https://www.baeldung.com/java-sentinel-intro)
