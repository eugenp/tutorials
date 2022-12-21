# Maven配置文件指南

1. 概述

    Maven配置文件可用于创建定制化的构建配置，如针对某一级别的测试粒度或特定部署环境。

    在本教程中，我们将学习如何使用Maven配置文件。

2. 一个基本例子

    通常，当我们运行mvn package时，单元测试也会被执行。但如果我们想快速打包工件并运行它，看看它是否工作，该怎么办？

    首先，我们创建一个no-test profile，将maven.test.skip属性设为true。

    ```xml
    <profile>
        <id>no-tests</id>
        <properties>
            <maven.test.skip>true</maven.test.skip>
        </properties>
    </profile>
    ```

    接下来，我们通过运行mvn package -Pno-tests命令来执行该配置文件。现在工件已经创建，测试被跳过。在这种情况下，mvn package -Dmaven.test.skip命令会更简单。

    然而，这只是对Maven配置文件的一个介绍。让我们来看看一些更复杂的设置。

3. 声明配置文件

    在上一节中，我们看到了如何创建一个配置文件。我们可以通过给它们唯一的ID来配置任意多的配置文件。

    假设我们想创建一个配置文件，只运行我们的集成测试，另一个用于一组突变测试。

    我们首先要在pom.xml文件中为每个配置文件指定一个id。

    ```xml
    <profiles>
        <profile>
            <id>integration-tests</id>
        </profile>
        <profile>
            <id>mutation-tests</id>
        </profile>
    </profiles>
    ```

    在每个配置文件元素中，我们可以配置许多元素，如依赖性、插件、资源、finalName。

    因此，对于上面的例子，我们可以为集成-测试和突变-测试分别添加插件和它们的依赖项。

    将测试分离到配置文件中，可以使默认的构建速度更快，例如，让它只关注单元测试。

    1. 配置文件范围

        现在，我们只是把这些配置文件放在pom.xml文件中，它只为我们的项目声明。

        但是，在Maven 3中，我们实际上可以将配置文件添加到三个位置中的任何一个。

        - 项目专用配置文件放在项目的pom.xml文件中
        - 用户专用配置文件放在用户的settings.xml文件中
        - 全局配置文件放在全局settings.xml文件中。

        我们尽可能地在pom.xml中配置配置文件。原因是我们希望在开发机和构建机上都能使用配置文件。使用settings.xml比较麻烦，而且容易出错，因为我们必须自己在不同的构建环境中分发。

4. 激活配置文件

    在我们创建一个或多个配置文件后，我们可以开始使用它们，或者换句话说，激活Active它们。

    1. 查看哪些配置文件是活跃的

        让我们使用help:active-profiles目标来查看哪些配置文件在我们的默认构建中是激活的。

        `mvn help:active-profiles`

        实际上，由于我们还没有激活任何东西，我们得到的是。

        `The following profiles are active:`

        我们马上就会激活它们。但很快，查看激活内容的另一种方法是在pom.xml中加入maven-help-plugin，将active-profiles目标与编译阶段联系起来。

        ```xml
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-help-plugin</artifactId>
                    <version>3.2.0</version>
                    <executions>
                        <execution>
                            <id>show-profiles</id>
                            <phase>compile</phase>
                            <goals>
                                <goal>active-profiles</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
        ```

        现在，让我们开始使用它们吧! 我们将看一下几个不同的方法。

    2. 使用-P

        实际上，我们在一开始已经看到了一种方法，那就是我们可以用-P参数激活配置文件。

        因此，让我们从启用集成-测试配置文件开始。

        `mvn package -P integration-tests`

        如果我们用maven-help-plugin或mvn help:active-profiles -P integration-tests命令验证激活的配置文件，我们会得到以下结果。

        ```log
        The following profiles are active:
        - integration-tests
        ```

        如果我们想同时激活多个配置文件，我们使用逗号分隔的配置文件列表。

        `mvn package -P integration-tests,mutation-tests`

    3. 默认激活

        如果我们总是想执行一个配置文件，我们可以让一个配置文件默认激活。

        ```xml
        <profile>
            <id>integration-tests</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        ```

        然后，我们可以在不指定配置文件的情况下运行mvn package，我们可以验证集成测试配置文件是激活的。

        然而，如果我们运行Maven命令并启用另一个配置文件，那么activeByDefault配置文件将被跳过。因此，当我们运行mvn package -P mutation-tests时，只有mutation-tests配置文件是激活的。

        当我们以其他方式激活时，activeByDefault配置文件也会被跳过，我们将在接下来的章节中看到。

    4. 基于一个属性Property

        我们可以在命令行上激活配置文件。然而，有时如果自动激活它们会更方便。例如，我们可以基于一个-D系统属性。

        ```xml
        <profile>
            <id>active-on-property-environment</id>
            <activation>
                <property>
                    <name>environment</name>
                </property>
            </activation>
        </profile>
        ```

        我们现在用`mvn package -Denvironment`命令来激活该配置文件。

        如果一个属性不存在，也可以激活一个配置文件。

        ```xml
        <property>
            <name>!environment</name>
        </property>
        ```

        或者，如果属性有一个特定的值，我们可以激活配置文件。

        ```xml
        <property>
            <name>environment</name>
            <value>test</value>
        </property>
        ```

        我们现在可以用`mvn package -Denvironment=test`来运行该配置文件。

        最后，如果属性的值不是指定的值，我们可以激活该配置文件。

        ```xml
        <property>
            <name>environment</name>
            <value>!test</value>
        </property>
        ```

    5. 基于JDK的版本

        另一个选项是根据机器上运行的JDK来启用配置文件。在本例中，我们希望在JDK版本以11开头时启用配置文件。

        ```xml
        <profile>
            <id>active-on-jdk-11</id>
            <activation>
                <jdk>11</jdk>
            </activation>
        </profile>
        ```

        我们也可以为JDK版本使用范围，如[Maven版本范围语法](https://www.baeldung.com/maven-dependency-latest-version)中所述。

    6. 基于操作系统

        另外，我们也可以根据一些操作系统信息来激活配置文件。

        如果我们不确定，可以先使用`mvn enforcer:display-info`命令，在我的机器上有如下输出。

        ```log
        Maven Version: 3.5.4
        JDK Version: 11.0.2 normalized as: 11.0.2
        OS Info: Arch: amd64 Family: windows Name: windows 10 Version: 10.0
        ```

        之后，我们可以配置一个只在Windows 10上激活的配置文件。

        ```xml
        <profile>
            <id>active-on-windows-10</id>
            <activation>
                <os>
                    <name>windows 10</name>
                    <family>Windows</family>
                    <arch>amd64</arch>
                    <version>10.0</version>
                </os>
            </activation>
        </profile>
        ```

    7. 基于一个文件

        另一个选择是在文件存在或丢失的情况下运行一个配置文件。

        因此，让我们创建一个测试配置文件，只在testreport.html还不存在时执行。

        ```xml
        <activation>
            <file>
                <missing>target/testreport.html</missing>
            </file>
        </activation>
        ```

5. 停用一个配置文件

    要禁用一个配置文件，我们可以使用'！'或'-'。

    因此，为了禁用active-on-jdk-11配置文件，我们执行`mvn compile -P -active-on-jdk-11`命令。

6. 总结

    在这篇文章中，我们看到了如何使用Maven配置文件，因此我们可以创建不同的构建配置。

    这些配置文件有助于在我们需要时执行特定的构建元素。这优化了我们的构建过程，有助于更快地反馈给开发者。

## Relevant Articles

- [x] [Guide to Maven Profiles](https://www.baeldung.com/maven-profiles)
