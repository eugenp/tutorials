# Courses

Here's the new "Learn Spring" course:

**[>> LEARN SPRING - THE MASTER CLASS](https://www.baeldung.com/learn-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=ls#master-class)**

Here's the Master Class of "REST With Spring" (along with the new announced Boot 2 material):
**[>> THE REST WITH SPRING - MASTER CLASS](https://www.baeldung.com/rest-with-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=rws#master-class)**

And here's the Master Class of "Learn Spring Security":
**[>> LEARN SPRING SECURITY - MASTER CLASS](https://www.baeldung.com/learn-spring-security-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=lss#master-class)**

## Java 和 Spring 教程

该项目是**一系列小型且重点突出的教程**——每个教程都涵盖了 Java 生态系统中一个明确定义的开发领域。
其中一个重点当然是 Spring Framework - Spring、Spring Boot 和 Spring Security。
除了 Spring 之外，这里的模块还涵盖了 Java 的许多方面。

## 基于配置文件的隔离

我们正在使用 Maven 构建配置文件来隔离我们在存储库中拥有的庞大的单个项目列表。

就目前而言，绝大多数模块都需要 JDK8 才能正确构建和运行。

这些项目大致分为3个列表：first, second, heavy。

接下来，根据我们要执行的测试将它们进一步隔离。

此外，还有 2 个专用于 JDK9 及更高版本的配置文件。

因此，我们总共有 8 个配置文件：

Therefore, we have a total of 8 profiles:

| Profile                    | Includes                    | Type of test enabled |
| -------------------------- | --------------------------- | -------------------- |
| default-first              | First set of projects       | *UnitTest            |
| integration-lite-first     | First set of projects       | *IntegrationTest     |
| default-second             | Second set of projects      | *UnitTest            |
| integration-lite-second    | Second set of projects      | *IntegrationTest     |
| default-heavy              | Heavy/long running projects | *UnitTest            |
| integration-heavy          | Heavy/long running projects | *IntegrationTest     |
| default-jdk9-and-above     | JDK9 and above projects     | *UnitTest            |
| integration-jdk9-and-above | JDK9 and above projects     | *IntegrationTest     |

## 构建项目

虽然不应该经常需要一次构建整个存储库，因为我们通常只关心一个特定的模块。

但是，如果我们愿意，如果我们想构建整个存储库并仅启用单元测试，我们可以从存储库的根目录调用以下命令：

`mvn clean install -Pdefault-first,default-second,default-heavy`

或者如果我们想在启用集成测试的情况下构建整个存储库，我们可以这样做：

`mvn clean install -Pintegration-lite-first,integration-lite-second,integration-heavy`

类似地，对于 JDK9 及以上项目，命令是：

`mvn clean install -Pdefault-jdk9-and-above`

and

`mvn clean install -Pintegration-jdk9-and-above`

### 构建单个模块

要构建特定模块，请在模块目录中运行命令：`mvn clean install`。

### 运行 Spring Boot 模块

要运行 Spring Boot 模块，请在模块目录中运行命令：`mvn spring-boot:run`。

## 使用 IDE

这个 repo 包含大量的模块。
当您使用单个模块时，无需导入所有模块（或构建所有模块） - 您可以在 Eclipse 或 IntelliJ 中简单地导入该特定模块。

## 运行测试

模块中的命令 `mvn clean install` 将运行该模块中的单元测试。
对于 Spring 模块，如果存在，这也将运行 `SpringContextTest`。

要运行集成测试，请使用以下命令：

`mvn clean install -Pintegration-lite-first` or 

`mvn clean install -Pintegration-lite-second` or 

`mvn clean install -Pintegration-heavy` or

`mvn clean install -Pintegration-jdk9-and-above`

取决于我们的模块所在的列表。
