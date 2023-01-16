# 使用Maven的多模块项目

1. 概述

    在本教程中，我们将学习如何用Maven构建一个多模块项目。

    首先，我们将讨论什么是多模块项目，并看看采用这种方法的好处。然后，我们将建立我们的示例项目。

2. Maven的多模块项目

    多模块项目是由一个管理一组子模块的聚合器POM构建的。在大多数情况下，聚合器位于项目的根目录下，必须有pom类型的包装。

    子模块是普通的Maven项目，它们可以单独或通过聚合器POM构建。

    通过聚合器POM构建项目，每个项目的打包类型与pom不同，就会产生一个已构建的归档文件。

3. 使用多模块的好处

    使用这种方法的显著优势是，我们可能会减少重复。

    假设我们有一个由几个模块组成的应用程序，一个前端模块和一个后端模块。现在想象一下，我们对它们进行工作并改变功能，这对它们都有影响。在这种情况下，如果没有专门的构建工具，我们将不得不分别构建这两个组件，或者写一个脚本来编译代码，运行测试，并显示结果。然后，当我们在项目中得到更多的模块后，管理和维护就会变得更加困难。

    在现实世界中，项目可能需要某些Maven插件，以便在[构建生命周期](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)中执行各种操作，共享依赖关系和配置文件，并包含其他[BOM项目](https://www.baeldung.com/spring-maven-bom)。

    因此，在利用多模块时，我们可以用一条命令构建应用程序的模块，如果顺序重要，Maven会帮我们算出来。我们还可以与其他模块共享大量的配置。

4. Parent POM

    Maven支持继承的方式，每个pom.xml文件都有隐含的父POM。它被称为Super POM，可以位于Maven二进制文件中。这两个文件被Maven合并，形成有效POM。

    我们可以创建自己的pom.xml文件，将其作为父项目使用。然后，我们可以在其中加入所有依赖性配置，并将其设为子模块的父项目，这样子模块就会从它那里继承。

    除了继承，Maven还提供了聚合的概念。利用这一功能的父POM被称为聚合(aggregation)POM。基本上，这种POM在其pom.xml文件中明确声明了其模块。

5. Submodules 子模块

    子模块，或称子项目，是继承自父POM的普通Maven项目。我们已经知道，继承让我们与子模块共享配置和依赖关系。然而，如果我们想一次性构建或发布我们的项目，我们必须在父POM中明确声明我们的子模块。最终，我们的父POM将是父，也是聚合POM。

6. 构建应用程序

    现在我们了解了Maven的子模块和层次结构，让我们构建一个示例应用程序来展示它们。我们将使用Maven的命令行界面来生成我们的项目。

    这个应用将由三个模块组成，分别代表。

    - 我们领域的核心部分
    - 一个提供一些REST API的网络服务
    - 一个包含面向用户的某种网络资产的web应用

    由于我们将专注于Maven，这些服务的实现将保持未定义。

    1. 生成父级POM

        首先，我们来创建一个父项目。

        `mvn archetype:generate -DgroupId=com.baeldung -DartifactId=parent-project`

        一旦父项目被生成，我们必须打开位于父项目目录下的pom.xml文件，将包装添加为pom。

        `<packaging>pom</packaging>`

        通过设置包装为pom类型，我们宣布该项目将作为一个父级或聚合器；它不会产生进一步的工件。

        现在，由于我们的聚合器已经完成，我们可以生成我们的子模块。

        然而，我们需要注意的是，这是所有要共享的配置的位置，这些配置最终将在子模块中重新使用。在其他方面，我们可以在这里使用 dependencyManagement 或 pluginManagement。

    2. 创建子模块

        由于我们的父级POM被命名为parent-project，我们需要确保我们在父级的目录中并运行生成命令。

        ```bash
        cd parent-project
        mvn archetype:generate -DgroupId=com.baeldung -DartifactId=core
        mvn archetype:generate -DgroupId=com.baeldung -DartifactId=service
        mvn archetype:generate -DgroupId=com.baeldung -DartifactId=webapp
        ```

        注意使用的命令。它与我们用于父类的命令相同。问题是，这些模块是普通的Maven项目，但Maven识别出它们是嵌套的。当我们把目录改为父项目时，它发现父项目有pom类型的包装，因此会相应地修改pom.xml文件。

        在父项目的pom.xml中，它将在模块部分添加所有子模块。

        `parent-project/pom.xml <modules>`

        并在各个子模块的pom.xml中，将父项目添加到父部分中。

        ```xml
        <parent>
            <artifactId>parent-project</artifactId>
            <groupId>com.baeldung</groupId>
            <version>1.0-SNAPSHOT</version>
        </parent>
        ```

        接下来，Maven将成功生成这三个子模块。

        值得注意的是，子模块只能有一个父模块。不过，我们可以导入很多BOM。关于BOM文件的更多细节可以在[本文](https://www.baeldung.com/spring-maven-bom)中找到。

    3. 构建项目

        现在我们可以一次性构建所有三个模块。在父类的项目目录下，我们将运行

        `mvn package`

        这将构建所有的模块。我们应该看到该命令的以下输出。

        ```log
        [INFO] Scanning for projects...
        [INFO] ------------------------------------------------------------------------
        [INFO] Reactor Build Order:
        [INFO] parent-project                                                     [pom]
        [INFO] core                                                               [jar]
        [INFO] service                                                            [jar]
        [INFO] webapp                                                             [war]
        ...
        [INFO] Reactor Summary for parent-project 1.0-SNAPSHOT:
        [INFO] parent-project ..................................... SUCCESS [  0.272 s]
        [INFO] core ............................................... SUCCESS [  2.043 s]
        [INFO] service ............................................ SUCCESS [  0.627 s]
        [INFO] webapp ............................................. SUCCESS [  0.572 s]
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        ```

        Reactor列出了parent-project，但由于它是pom类型的，所以被排除在外，构建的结果是所有其他模块的三个独立的.jar文件。在这种情况下，构建发生在其中的三个。

        此外，Maven Reactor会分析我们的项目并按照适当的顺序进行构建。因此，如果我们的webapp模块依赖于服务模块，Maven会先构建服务，然后再构建webapp。

    4. 在父项目中启用依赖性管理

        依赖管理是一种集中管理多模块父项目及其子项目的依赖信息的机制。

        当你有一组项目或模块继承了一个共同的父项目，你可以把所有需要的依赖信息放在共同的pom.xml文件中。这将简化对子POMs中工件的引用。

        让我们看一下父类的pom.xml样本。

        `parent-project/pom.xml <dependencyManagement>`

        通过在父类中声明spring-core的版本，所有依赖spring-core的子模块可以只使用groupId和artifactId来声明依赖关系，并且版本将被继承。

        此外，你可以在父模块的pom.xml中为依赖性管理提供排除项，这样特定的库就不会被子模块所继承了。

        ```xml
        <exclusions>
            <exclusion>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
            </exclusion>
        </exclusions>
        ```

        最后，如果一个子模块需要使用一个不同版本的受管依赖，你可以在子模块的 pom.xml 文件中覆盖受管版本。

        ```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.30.RELEASE</version>
        </dependency>
        ```

        请注意，虽然子模块继承自其父项目，但父项目不一定有它聚合的任何模块。另一方面，一个父项目也可以聚合那些不继承它的项目。

        关于继承和聚合的更多信息，请[参考本文档](https://maven.apache.org/pom.html#Aggregation)。

    5. 更新子模块和建立一个项目

        我们可以改变每个子模块的打包类型。例如，让我们通过更新pom.xml文件将webapp模块的包装改为WAR。

        `webapp/pom.xml <packaging>`

        现在我们可以使用mvn clean install命令来测试我们项目的构建。Maven日志的输出应该与此类似。

7. 总结

    在这篇文章中，我们讨论了使用Maven多模块的好处。我们还区分了常规Maven的父POM和聚合POM。最后，我们探讨了如何设置一个简单的多模块来开始使用。

    Maven是一个伟大的工具，但它本身就很复杂。如果我们想了解更多关于Maven的细节，可以看看[Sonatype Maven参考](https://books.sonatype.com/mvnref-book/reference/index.html)或Apache Maven指南。如果我们想了解Maven的多模块设置的高级用法，可以看看[Spring Boot项目是如何利用其用法的](https://github.com/spring-projects/spring-boot)。

## Relevant Articles

- [x] [Multi-Module Project with Maven](https://www.baeldung.com/maven-multi-module)
