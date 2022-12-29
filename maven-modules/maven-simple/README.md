# Apache Maven教程

1. 简介

    构建一个软件项目通常包括下载依赖关系、在classpath上添加jars、将源代码编译成二进制代码、运行测试、将编译后的代码打包成可部署的工件，如JAR、WAR和ZIP文件，并将这些工件部署到应用服务器或资源库。

    [Apache Maven](https://maven.apache.org/)将这些任务自动化，最大限度地减少了人类在手动构建软件时出错的风险，并将编译和打包的工作与代码构建的工作分开。

    在本教程中，我们将探讨这个强大的工具，它使用一个中心信息--项目对象模型（POM）--来描述、构建和管理Java软件项目，该信息是用XML编写的。

2. 为什么使用Maven？

    Maven的主要特点是。

    - 项目设置简单，遵循最佳实践。Maven通过提供项目模板（名为原型），尽量避免配置。
    - 依赖性管理：它包括自动更新、下载和验证兼容性，以及报告依赖关系的关闭（也被称为反式依赖）。
    - 项目依赖和插件之间的隔离：利用Maven，项目依赖从依赖库中获取，而任何插件的依赖则从插件库中获取，从而在插件开始下载额外的依赖时减少冲突。
    - 中央资源库系统：项目的依赖性可以从本地文件系统或公共资源库（如[Maven Central](https://search.maven.org/classic/)）加载。

    为了了解如何在您的系统上安装Maven，请查看[安装教程](https://www.baeldung.com/install-maven-on-windows-linux-mac)。

3. 项目对象模型

    Maven项目的配置是通过项目对象模型（POM）完成的，由pom.xml文件表示。POM描述了项目，管理了依赖关系，并配置了用于构建软件的插件。

    POM还定义了多模块项目的模块之间的关系。让我们来看看一个典型的POM文件的基本结构。

    ```xml
    <project>
        <modelVersion>4.0.0</modelVersion>
        <groupId>com.baeldung</groupId>
        <artifactId>baeldung</artifactId>
        <packaging>jar</packaging>
        <version>1.0-SNAPSHOT</version>
        <name>com.baeldung</name>
        <url>http://maven.apache.org</url>
        <dependencies>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.8.2</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
        <build>
            <plugins>
                <plugin>
                //...
                </plugin>
            </plugins>
        </build>
    </project>
    ```

    1. Project Identifiers 项目标识符

        Maven使用一组标识符（也叫坐标 coordinates）来唯一地识别一个项目，并指定项目工件应如何打包。

        - groupId - 创建项目的公司或团体的唯一基本名称
        - artifactId - 项目的唯一名称
        - version - 项目的一个版本
        - packaging - 包装方法（如WAR/JAR/ZIP）。

        其中前三个（groupId:artifactId:version）组合起来形成唯一的标识符，是你指定项目将使用哪些版本的外部库（如JAR）的机制。

    2. Dependencies 依赖关系

        项目使用的这些外部库被称为依赖项。Maven的依赖性管理功能确保从中央仓库自动下载这些库，因此你不必在本地存储它们。

        这是Maven的一个关键功能，具有以下好处。

        - 大大减少了从远程仓库下载的次数，从而减少了存储量的使用
        - 使得检查项目的速度更快
        - 为企业内部和外部交换二进制工件提供了一个有效的平台，无需每次都从源头构建工件。

        为了声明对外部库的依赖，你需要提供该库的groupId、artifactId和版本。让我们来看看一个例子。

        ```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.16</version>
        </dependency>
        ```

        当Maven处理这些依赖项时，它会将Spring Core库下载到你的本地Maven仓库。

    3. Repositories 仓库

        Maven中的资源库用于存放不同类型的构建工件和依赖项。默认的本地资源库位于用户主目录下的.m2/repository文件夹中。

        如果本地仓库中有工件或插件，Maven会使用它。否则，就从中央仓库下载并存储在本地仓库中。默认的中央资源库是Maven Central。

        有些库（如JBoss服务器）在中央仓库中不可用，但在另一个仓库中可用。对于这些库，你需要在pom.xml文件中提供备用库的URL。

        ```xml
        <repositories>
            <repository>
                <id>JBoss repository</id>
                <url>http://repository.jboss.org/nexus/content/groups/public/</url>
            </repository>
        </repositories>
        ```

        > 注意，你可以在你的项目中使用多个存储库。

    4. Properties 属性

        自定义属性可以帮助使你的pom.xml文件更容易阅读和维护。在经典的使用案例中，你会使用自定义属性来定义项目的依赖版本。

        Maven属性是数值占位符，在pom.xml中的任何地方都可以使用${name}符号，其中name是属性。

        让我们看一个例子。

        ```xml
        <properties>
            <spring.version>5.3.16</spring.version>
        </properties>

        <dependencies>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-core</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>${spring.version}</version>
            </dependency>
        </dependencies>
        ```

        现在，如果你想把Spring升级到一个新的版本，你只需要改变`<spring.version>`属性标签里面的值，所有在`<version>`标签中使用该属性的依赖关系都会被更新。

        属性也经常被用来定义构建路径变量。

        ```xml
        <properties>
            <project.build.folder>${project.build.directory}/tmp/</project.build.folder>
        </properties>

        <plugin>
            //...
            <outputDirectory>${project.resources.build.folder}</outputDirectory>
            //...
        </plugin>
        ```

    5. 构建

        构建部分也是Maven POM的一个非常重要的部分。它提供了关于默认Maven目标、编译项目的目录以及应用程序的最终名称的信息。默认的构建部分看起来像这样。

        ```xml
        <build>
            <defaultGoal>install</defaultGoal>
            <directory>${basedir}/target</directory>
            <finalName>${artifactId}-${version}</finalName>
            <filters>
            <filter>filters/filter1.properties</filter>
            </filters>
            //...
        </build>
        ```

        被编译的工件的默认输出文件夹被命名为target，被打包的工件的最终名称由artifactId和version组成，但是你可以在任何时候改变它。

    6. 使用配置文件

        Maven的另一个重要特征是对配置文件的支持。配置文件基本上是一组配置值。通过使用配置文件，你可以为不同的环境（如生产/测试/开发）定制构建方式。

        ```xml
        <profiles>
            <profile>
                <id>production</id>
                <build>
                    <plugins>
                        <plugin>
                        //...
                        </plugin>
                    </plugins>
                </build>
            </profile>
            <profile>
                <id>development</id>
                <activation>
                    <activeByDefault>true</activeByDefault>
                </activation>
                <build>
                    <plugins>
                        <plugin>
                        //...
                        </plugin>
                    </plugins>
                </build>
            </profile>
        </profiles>
        ```

        正如你在上面的例子中看到的，默认配置文件被设置为开发。如果您想运行生产配置文件，您可以使用以下Maven命令。

        `mvn clean install -Pproduction`

4. Maven构建生命周期

    每个Maven构建都遵循一个指定的生命周期。您可以执行多个构建生命周期目标，包括编译项目代码、创建包、在本地Maven依赖库中安装归档文件。

    1. 生命周期的各个阶段

        下面列出了最重要的Maven生命周期阶段。

        - validate--检查项目的正确性
        - compile--将所提供的源代码编译成二进制构件
        - test--执行单元测试
        - package--将编译后的代码打包成一个归档文件
        - integration-test--执行额外的测试，这需要打包。
        - verify - 检查该软件包是否有效
        - install - 将包文件安装到本地Maven仓库中
        - deploy--将包文件部署到远程服务器或资源库中

    2. 插件和目标

        一个Maven插件是一个或多个目标的集合。目标是分阶段执行的，这有助于确定目标的执行顺序。

        这里有Maven官方支持的丰富的[插件列表](https://maven.apache.org/plugins/)。还有一篇关于如何使用各种插件在Baeldung上[构建可执行JAR的有趣文章](https://www.baeldung.com/executable-jar-with-maven)。

        为了更好地了解哪些目标在默认情况下会在哪些阶段运行，请看一下[默认的Maven生命周期绑定](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Built-in_Lifecycle_Bindings)。

        要通过上述任何一个阶段，我们只需调用一条命令。

        `mvn <phase>`

        例如，mvn clean install将删除之前创建的jar/war/zip文件和编译的类（clean），并执行安装新归档文件所需的所有阶段（install）。

        > 注意，插件提供的目标可以与生命周期的不同阶段相关联。

5. 你的第一个Maven项目

    本节中，我们将使用Maven的命令行功能来创建一个Java项目。

    1. 生成一个简单的Java项目

        为了建立一个简单的Java项目，让我们运行以下命令。

        ```bash
        mvn archetype:generate \
        -DgroupId=com.baeldung \
        -DartifactId=baeldung \
        -DarchetypeArtifactId=maven-archetype-quickstart \
        -DarchetypeVersion=1.4 \
        -DinteractiveMode=false
        ```

        groupId是一个参数，表示创建项目的小组或个人，通常是一个反转的公司域名。artifactId是项目中使用的基础包名称，我们使用标准原型。这里我们使用的是最新的原型版本，以确保我们的项目是以更新的、最新的结构创建的。

        由于我们没有指定版本和包装类型，这些将被设置为默认值--版本将被设置为1.0-SNAPSHOT，而包装将默认为jar。

        如果你不知道要提供哪些参数，你可以随时指定interactiveMode=true，这样Maven就会询问所有需要的参数。

        命令完成后，我们在src/main/java文件夹下有一个包含App.java类的Java项目，这只是一个简单的 "Hello World"程序。

        我们在 src/test/java 中还有一个测试类的例子。这个项目的 pom.xml 将类似于这样。

        ```xml
        <project>
            <modelVersion>4.0.0</modelVersion>
            <groupId>com.baeldung</groupId>
            <artifactId>baeldung</artifactId>
            <version>1.0-SNAPSHOT</version>
            <name>baeldung</name>
            <url>http://www.example.com</url>
            <dependencies>
                <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>4.11</version>
                    <scope>test</scope>
                </dependency>
            </dependencies>
        </project>
        ```

        正如你所看到的，JUnit的依赖性是默认提供的。

    2. 编译和打包一个项目

        下一步是编译该项目。

        `mvn compile`

        Maven将运行编译阶段所需的所有生命周期阶段，以构建项目的源代码。如果你只想运行测试阶段，你可以使用。

        `mvn test`

        现在让我们调用打包阶段，它将生成编译后的归档jar文件。

        `mvn package`

    3. 执行一个应用程序

        最后，我们要用exec-maven-plugin来执行我们的Java项目。让我们在pom.xml中配置必要的插件。

        ```xml
        <build>
            <sourceDirectory>src</sourceDirectory>
            <plugins>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.6.1</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>exec-maven-plugin</artifactId>
                    <version>3.0.0</version>
                    <configuration>
                        <mainClass>com.baeldung.java.App</mainClass>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        ```

        第一个插件[maven-compiler-plugin](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22maven-compiler-plugin%22)负责使用Java 1.8版本编译源代码。[exec-maven-plugin](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22exec-maven-plugin%22)在我们的项目中搜索mainClass。

        为了执行该应用程序，我们运行以下程序

        `mvn exec:java`

## Relevant Articles

- [x] [Apache Maven Tutorial](https://www.baeldung.com/maven)
