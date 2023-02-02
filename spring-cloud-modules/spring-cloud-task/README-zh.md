# Spring Cloud Task介绍

1. 概述

    Spring Cloud Task的目标是为Spring Boot应用程序提供创建短期微服务的功能。

    在Spring Cloud Task中，我们可以灵活地动态运行任何任务，按需分配资源，并在任务完成后检索结果。

    任务是Spring Cloud Data Flow中的一个新的基元，允许用户将几乎任何Spring Boot应用程序作为一个短暂的任务执行。

2. 开发一个简单的任务应用程序

    1. 添加相关的依赖关系

        首先，我们可以用spring-cloud-task-dependencies添加依赖性管理部分。

        ```xml
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-task-dependencies</artifactId>
                    <version>2.2.3.RELEASE</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
        ```

        这个依赖管理通过导入范围来管理依赖的版本。

        我们需要添加以下依赖关系。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-task</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-task-core</artifactId>
        </dependency>
        ```

        这是到spring-cloud-task-core的Maven中心的链接。

        现在，为了启动我们的Spring Boot应用程序，我们需要spring-boot-starter和相关的父类。

        我们将使用Spring Data JPA作为ORM工具，所以我们也需要添加它的依赖性。

        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.6.1</version>
        </dependency>
        ```

        使用Spring Data JPA引导一个简单的Spring Boot应用程序的细节在[这里](https://www.baeldung.com/spring-boot-start)可以找到。

        我们可以在Maven中心查看最新版本的spring-boot-starter-parent。

    2. @EnableTask注解

        为了启动Spring Cloud Task的功能，我们需要添加@EnableTask注解。

        ```java
        @SpringBootApplication
        @EnableTask
        public class TaskDemo {
            // ...
        }
        ```

        注解将SimpleTaskConfiguration类带入图片中，它反过来注册了任务存储库及其基础设施。默认情况下，一个内存地图被用来存储任务存储库的状态。

        TaskRepository的主要信息在TaskExecution类中被建模。这个类的字段是taskName, startTime, endTime, exitMessage。exitMessage存储了退出时的可用信息。

        如果退出是由应用程序的任何事件的失败引起的，完整的异常堆栈跟踪将被存储在这里。

        Spring Boot提供了一个ExitCodeExceptionMapper接口，该接口将未捕获的异常映射为退出代码，允许进行仔细调试。云任务将这些信息存储在数据源中，以便将来分析。

    3. 为TaskRepository配置一个数据源

        一旦任务结束，存储TaskRepository的内存地图就会消失，我们会失去与任务事件相关的数据。为了存储在一个永久的存储器中，我们将使用MySQL作为Spring Data JPA的数据源。

        数据源在application.yml文件中进行了配置。为了配置Spring Cloud Task使用所提供的数据源作为TaskRepository的存储，我们需要创建一个扩展DefaultTaskConfigurer的类。

        现在，我们可以将配置好的数据源作为构造函数参数发送到超类的构造函数中。

        ```java
        @Autowired
        private DataSource dataSource;

        public class HelloWorldTaskConfigurer extends DefaultTaskConfigurer{
            public HelloWorldTaskConfigurer(DataSource dataSource){
                super(dataSource);
            }
        }
        ```

        为了使上述配置生效，我们需要用@Autowired注解来注解DataSource的一个实例，并将该实例作为上面定义的HelloWorldTaskConfigurer Bean的构造参数注入。

        ```java
        @Bean
        public HelloWorldTaskConfigurer getTaskConfigurer() {
            return new HelloWorldTaskConfigurer(dataSource);
        }
        ```

        这样就完成了将任务仓库存储到MySQL数据库的配置。

    4. 执行

        在Spring Boot中，我们可以在应用程序完成启动之前执行任何任务。我们可以使用ApplicationRunner或CommandLineRunner接口来创建一个简单的任务。

        我们需要实现这些接口的运行方法，并将实现类声明为一个bean。

        ```java
        @Component
        public static class HelloWorldApplicationRunner 
        implements ApplicationRunner {
        
            @Override
            public void run(ApplicationArguments arg0) throws Exception {
                System.out.println("Hello World from Spring Cloud Task!");
            }
        }
        ```

        现在，如果我们运行我们的应用程序，我们应该得到我们的任务产生必要的输出，并在我们的MySQL数据库中创建必要的表，记录任务的事件数据。

3. Spring Cloud任务的生命周期

    一开始，我们在TaskRepository中创建一个条目。这表明所有的Bean已经准备好在应用程序中使用，Runner接口的运行方法已经准备好被执行。

    当运行方法执行完毕或ApplicationContext事件失败时，TaskRepository将被更新为另一个条目。

    在任务的生命周期中，我们可以通过TaskExecutionListener接口来注册监听器。我们需要一个实现该接口的类，它有三个方法--onTaskEnd、onTaksFailed和onTaskStartup，在任务的各个事件中触发。

    我们需要在TaskDemo类中声明这个实现类的bean。

    ```java
    @Bean
    public TaskListener taskListener() {
        return new TaskListener();
    }
    ```

4. 与Spring Batch集成

    我们可以将Spring Batch作业作为一个任务来执行，并使用Spring Cloud Task记录作业执行的事件。为了启用这一功能，我们需要添加与Boot和Cloud有关的Batch依赖项。

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-batch</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-task-batch</artifactId>
    </dependency>
    ```

    以下是spring-cloud-task-batch的Maven中心的链接。

    要将一项工作配置为任务，我们需要在JobConfiguration类中注册Job Bean。

    ```java
    @Bean
    public Job job2() {
        return jobBuilderFactory.get("job2")
        .start(stepBuilderFactory.get("job2step1")
        .tasklet(new Tasklet(){
            @Override
            public RepeatStatus execute(
                StepContribution contribution,
                ChunkContext chunkContext) throws Exception {
                System.out.println("This job is from Baeldung");
                    return RepeatStatus.FINISHED;
            }
        }).build()).build();
    }
    ```

    我们需要用@EnableBatchProcessing注解来装饰TaskDemo类。

    ```java
    //..Other Annotation..
    @EnableBatchProcessing
    public class TaskDemo {
        // ...
    }
    ```

    @EnableBatchProcessing注解用设置批处理作业所需的基本配置来启用Spring Batch功能。

    现在，如果我们运行应用程序，@EnableBatchProcessing注解将触发Spring批处理作业的执行，Spring Cloud Task将记录所有批处理作业的执行事件与springcloud数据库中执行的其他任务。

5. 从流中启动一个任务

    我们可以从Spring Cloud Stream触发任务。为了达到这个目的，我们有@EnableTaskLaucnher注解。一旦我们在Spring Boot应用程序中添加注解，就会有一个TaskSink可用。

    ```java
    @SpringBootApplication
    @EnableTaskLauncher
    public class StreamTaskSinkApplication {
        public static void main(String[] args) {
            SpringApplication.run(TaskSinkApplication.class, args);
        }
    }
    ```

    TaskSink从一个流中接收消息，该流包含一个包含TaskLaunchRequest作为有效载荷的GenericMessage。然后，它根据任务启动请求中提供的坐标来触发一个任务。

    为了让TaskSink发挥作用，我们需要配置一个实现了TaskLauncher接口的bean。为了测试的目的，我们在这里模拟实现。

    ```java
    @Bean
    public TaskLauncher taskLauncher() {
        return mock(TaskLauncher.class);
    }
    ```

    我们需要注意的是，只有在添加spring-cloud-deployer-local依赖关系后，TaskLauncher接口才可用。

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-deployer-local</artifactId>
        <version>2.3.1.RELEASE</version>
    </dependency>
    ```

    我们可以通过调用Sink接口的输入来测试任务是否启动。

    ```java
    public class StreamTaskSinkApplicationTests {
        @Autowired
        private Sink sink; 
        //
    }
    ```

    现在，我们创建一个TaskLaunchRequest的实例，并将其作为`GenericMessage<TaskLaunchRequest>`对象的有效载荷发送。然后我们可以调用Sink的输入通道，将GenericMessage对象保留在通道中。

6. 结论

    在本教程中，我们已经探索了Spring Cloud Task是如何执行的，以及如何配置它在数据库中记录其事件。我们还观察了Spring Batch作业是如何定义并存储在TaskRepository中的。最后，我们解释了我们如何从Spring Cloud Stream中触发任务。

    像往常一样，这些代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-task)上找到。

## Relevant Articles

- [x] [An Intro to Spring Cloud Task](http://www.baeldung.com/spring-cloud-task)
