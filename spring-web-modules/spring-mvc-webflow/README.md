# Spring MVC WebFlow

This module contains articles about Spring MVC Web Flow

1.概述

Spring Web Flow构建于SpringMVC之上，允许在Web应用程序中实现流。它用于创建引导用户通过流程或某些业务逻辑的步骤序列。

在这个快速教程中，我们将介绍一个简单的用户激活流程示例。用户将看到一个页面，单击Activate按钮继续，或单击Cancel按钮取消激活。

这里并不是假设我们已经安装了Spring MVC web应用程序。

2.设置

让我们从将Spring Web Flow依赖项添加到pom.xml开始： org.springframework.webflow.2.5.1.RELEASE 。

最新版本的SpringWebFlow可以在 [Central Maven Repository](https://central.sonatype.dev/search?q=spring-webflow) 中找到。

3.创建流

现在，让我们创建一个简单的流。如前所述，流程是指导用户完成流程的一系列步骤。目前，这只能使用基于XML的配置来完成。

流中的每个步骤都称为状态(Each step in the flow is called a state.)。

对于这个简单的例子，我们将使用视图状态。视图状态是流中呈现匹配视图的步骤。视图状态是指应用程序中的一个页面（WEB-INF/view），视图状态的id是它所指页面的名称。

我们还将使用过渡元素。转换元素用于处理在特定状态中发生的事件。

对于这个示例流，我们将设置三种视图状态——激活、成功和失败。

这个流程非常简单。起点是激活视图。如果激活事件被触发，它应该转换到成功视图。如果触发了取消事件，它应该转换到故障视图。转换元素处理视图状态中发生的按钮单击事件：

```xml
<view-state id="activation">
    <transition on="activate" to="success"/>
    <transition on="cancel" to="failure"/>
</view-state>
<view-state id="success" />
<view-state id="failure" />
```

初始激活页面，由id激活引用，位于WEB-INF/view/activation中。jsp是一个简单的页面，有两个按钮，activate和cancel。单击按钮触发转换，将用户发送到成功视图状态（WEB-INF/view/success.jsp）或失败视图状态（WEB-INF/view/failure.jsp）：

```html
<body>
    <h2>Click to activate account</h2>
    <form method="post" action="${flowExecutionUrl}">
        <input type="submit" name="_eventId_activate" value="activate" />
        <input type="submit" name="_eventId_cancel" value="cancel" />
    </form>
</body>
```

我们正在使用flowExecutionUrl访问当前流执行视图状态的上下文相关URI。

4.配置流

接下来，我们将在Web环境中配置Spring Web Flow。我们将通过设置流注册表和流生成器服务来实现这一点。

Flow Registry允许我们指定流的位置，还可以指定正在使用的Flow Builder服务。

Flow Builder Service帮助我们自定义用于构建流的服务和设置。

我们可以定制的服务之一是视图工厂创建器(view-factory-creator)。视图工厂创建器允许我们定制Spring Web Flow使用的ViewFactoryCreator。因为我们使用的是SpringMVC，所以我们可以配置Spring Web Flow，以便在Spring MVC配置中使用视图解析器(view resolver)。

下面是我们将如何为示例配置Spring Web Flow：

```java
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private WebMvcConfig webMvcConfig;

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices())
          .addFlowLocation("/WEB-INF/flows/activation-flow.xml", "activationFlow")
          .build();
    }

    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry()).build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
          .setViewFactoryCreator(mvcViewFactoryCreator())
          .setDevelopmentMode(true).build();
    }

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(
          Collections.singletonList(this.webMvcConfig.viewResolver()));
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }
}
```

> 注意：通过 Autowired webMvcConfig 实现bean的早期初始化，若是使用参数注入`mvcViewFactoryCreator(WebMvcConfig webMvcConfig)`，运行时会报 `ApplicationObjectSupport instance [org.springframework.web.servlet.view.InternalResourceViewResolver@6d2b3a55] does not run in an ApplicationContext`。

我们还可以将XML用于该配置：

```xml
<bean class="org.springframework.webflow.mvc.servlet.FlowHandlerMapping">
    <property name="flowRegistry" ref="activationFlowRegistry"/>
</bean>

<flow:flow-builder-services id="flowBuilderServices"
  view-factory-creator="mvcViewFactoryCreator"/>

<bean id="mvcViewFactoryCreator" 
  class="org.springframework.webflow.mvc.builder.MvcViewFactoryCreator">
    <property name="viewResolvers" ref="jspViewResolver"/>
</bean>

<flow:flow-registry id="activationFlowRegistry" 
  flow-builder-services="flowBuilderServices">
    <flow:flow-location id="activationFlow" path="/WEB-INF/flows/activation-flow.xml"/>
</flow:flow-registry>

<bean class="org.springframework.webflow.mvc.servlet.FlowHandlerAdapter">
    <property name="flowExecutor" ref="activationFlowExecutor"/>
</bean>
<flow:flow-executor id="activationFlowExecutor" 
  flow-registry="activationFlowRegistry"/>
```

5.浏览流程

要浏览流，请启动web应用程序并转到 <http://localhost:8080/spring-mvc-webflow/activationFlow> 。要启动应用程序，请将其部署在应用服务器上，例如Tomcat或 [Jetty](https://www.baeldung.com/deploy-to-jetty) 。

这会将我们发送到流的初始页面，即流配置中指定的激活页面

您可以单击激活按钮进入成功页面

激活成功1或单击取消按钮转到失败页面

## Relevant Articles

- [x] [Guide to Spring Web Flow](https://www.baeldung.com/spring-web-flow)
