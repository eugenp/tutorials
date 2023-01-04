# Core Java JAR

## 如何用Maven创建一个可执行的JAR

在本快速教程中，我们将重点介绍如何将Maven项目打包成一个可执行的Jar文件。

创建Jar文件时，我们通常希望不使用集成开发环境就能轻松运行它。为此，我们将讨论使用上述每种方法创建可执行文件的配置和利弊。

进一步阅读。

[使用Maven BOM的Spring](https://www.baeldung.com/spring-maven-bom)

1. 配置

    我们不需要任何额外的依赖来创建一个可执行的jar。我们只需要创建一个Maven Java项目，并至少有一个带有main(...)方法的类。

    在我们的例子中，我们创建了名为ExecutableMavenJar的Java类。

    我们还需要确保我们的pom.xml包含这些元素。

    ```xml
    ...
    <packaging>jar</packaging>
    ```

    这里最重要的是类型--要创建一个可执行的jar，请仔细检查配置中使用的jar类型。

    现在我们可以开始使用各种解决方案了。

    1. 手动配置

        让我们先在maven-dependency-plugin的帮助下采用手动方式。

        首先，我们要把所有需要的依赖项复制到我们指定的文件夹中。

        ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            ...
        </plugin>
        ```

        有两个重要方面需要注意。

        首先，我们指定了copy-dependencies这一目标goal，它告诉Maven将这些依赖项复制到指定的outputDirectory。在我们的例子中，我们将在项目构建目录（通常是目标文件夹）内创建一个名为libs的文件夹。

        其次，我们要创建可执行文件和classpath-aware jar，并将其与第一步中复制的依赖项链接。

        ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            ...
        </plugin>
        ```

        这其中最重要的部分是清单的配置。我们添加一个classpath`<addClasspath>`，包括所有的依赖项（文件夹libs/），并提供关于主类的信息。

        请注意，我们需要提供一个完全合格的类名`<mainClass>`，这意味着它将包括包名。

        这种方法的优点和缺点是。

        - pros 优点 - 透明的过程，我们可以指定每个步骤
        - cons 缺点 - 手工操作；依赖关系不在最终的jar中，这意味着我们的可执行jar只有在libs文件夹被访问并对jar可见时才会运行。

    2. Apache Maven汇编插件

        Apache Maven Assembly插件允许用户将项目输出连同其依赖关系、模块、网站文档和其他文件一起汇总到一个可运行的软件包。

        汇编插件的主要目标是[单一](https://maven.apache.org/plugins/maven-assembly-plugin/single-mojo.html)目标，用于创建所有汇编（所有其他目标都已废弃，将在未来的版本中删除）。

        让我们看一下pom.xml中的配置。

        ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            ...
        </plugin>
        ```

        与手动方法类似，我们需要提供主类的相关信息。不同的是，Maven汇编插件会自动将所有需要的依赖项复制到jar文件中。

        在配置代码的`<descriptorRefs>`部分，我们提供了将被添加到项目名称中的名称。

        在我们的例子中，输出将被命名为core-java-jar-with-dependencies.jar。

        - 优点 - jar文件中的依赖性，只有一个文件
        - 缺点 - 包装工件的基本控制，例如，没有对类的重定位支持

    3. Apache Maven Shade插件

        Apache Maven Shade Plugin提供了将工件打包成一个uber-jar的能力，其中包括运行项目所需的所有依赖项。此外，它还支持对一些依赖项的包进行着色--即重命名。

        让我们来看看这个配置。

        ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            ...
        </plugin>
        ```

        这个配置有三个主要部分。

        首先，`<shadedArtifactAttached>`标记了所有要打包到jar中的依赖项。

        其次，我们需要指定[转化器的实现](https://maven.apache.org/plugins/maven-shade-plugin/usage.html)；在我们的例子中我们使用了标准的转化器。

        最后，我们需要指定我们应用程序的主类。

        输出文件将被命名为 core-java-0.1.0-SNAPSHOT-shaded.jar，其中 core-java 是我们的项目名称，后面是快照版本和插件名称。

        - 优点--jar文件中的依赖关系，对包装我们的工件的高级控制，有阴影和类的重定位
        - 缺点 - 复杂的配置（尤其是当我们想使用高级功能时）。

    4. One Jar Maven插件

        创建可执行jar的另一个选择是One Jar项目。

        它提供了一个自定义的类加载器，知道如何从归档中的jar加载类和资源，而不是从文件系统中的jar加载。

        让我们看一下配置。

        ```xml
        <plugin>
            <groupId>com.jolira</groupId>
            <artifactId>onejar-maven-plugin</artifactId>
            ...
        </plugin>
        ```

        如配置中所示，我们需要指定主类，并通过使用attachToBuild = true，将所有的依赖关系附加到构建中。

        此外，我们还应该提供输出文件名。此外，Maven的目标是one-jar。请注意，One Jar是一个商业解决方案，它将使依赖的jar在运行时不扩展到文件系统中。

        - 优点 - 清晰的委托模型，允许类位于One Jar的顶层，支持外部jar并能支持Native库
        - 缺点 - 自2012年以来没有积极支持

    5. Spring Boot Maven Plugin

        最后，我们要看的最后一个解决方案是Spring Boot Maven Plugin。

        它允许打包可执行的jar或war文件，并 "就地 "运行一个应用程序。

        要使用它，我们至少需要使用Maven 3.2版本。详细说明见[这里](https://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/maven-plugin/)。

        让我们看一下配置。

        ```xml
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            ...
        </plugin>
        ```

        Spring插件和其他的插件有两个不同之处。执行的目标被称为repackage，分类器`<classifier>`被称为spring-boot。

        请注意，我们不需要有Spring Boot应用就可以使用这个插件。

        - 优点 - 依赖关系在jar文件中，我们可以在每个可访问的位置运行它，对包装我们的工件进行高级控制，可以从jar文件中排除依赖关系等，也可以包装war文件
        - 缺点 - 增加了潜在的不必要的Spring和Spring Boot相关类

    6. 使用可执行Tomcat的Web应用程序

        在最后一部分，我们想介绍一个独立的Web应用，它被打包在一个jar文件中。

        为了做到这一点，我们需要使用不同的插件，专门用于创建可执行的jar文件。

        ```xml
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.0</version>
            <executions>
                <execution>
                    <id>tomcat-run</id>
                    <goals>
                        <goal>exec-war-only</goal>
                    </goals>
                    <phase>package</phase>
                    <configuration>
                        <path>/</path>
                        <enableNaming>false</enableNaming>
                        <finalName>webapp.jar</finalName>
                        <charset>utf-8</charset>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        ```

        目标被设置为exec-war-only，配置标签中指定了我们的服务器的路径，还有其他属性，如finalName、charset等。

        为了建立一个jar，我们运行man package，这将导致在我们的目标目录中创建webapp.jar。

        要运行应用程序，我们只需在控制台中写下java -jar target/webapp.jar，并尝试在浏览器中指定localhost:8080/来测试它。

        - 优点 - 只有一个文件，易于部署和运行
        - 缺点 - 文件的大小要大得多，因为要把Tomcat的嵌入式分布打包在一个war文件中

        注意，这是该插件的最新版本，它支持Tomcat7服务器。为了避免错误，我们可以检查我们对Servlets的依赖是否已经设置了所提供的范围，否则，在可执行jar的运行时就会有冲突。

        ```xml
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <scope>provided</scope>
        </dependency>
        ```

2. 总结

    在本文中，我们介绍了用各种Maven插件创建可执行jar的多种方法。

    本教程的完整实现可以在这些GitHub项目中找到：[可执行jar](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-jar)和[可执行war](https://github.com/eugenp/tutorials/tree/master/spring-web-modules/spring-thymeleaf-5)。

    如何测试？为了将项目编译成可执行jar，请用mvn clean package命令运行Maven。

    这篇文章希望能提供一些更多的见解，并帮助你根据自己的需要找到自己喜欢的方法。

    最后一点：我们要确保我们捆绑的jar的许可证不禁止这种操作。一般来说，不会出现这种情况，但这值得考虑。

## Relevant Articles

- [x] [How to Create an Executable JAR with Maven](http://www.baeldung.com/executable-jar-with-maven)
- [Importance of Main Manifest Attribute in a Self-Executing JAR](http://www.baeldung.com/java-jar-executable-manifest-main-class)
- [Guide to Creating and Running a Jar File in Java](https://www.baeldung.com/java-create-jar)
- [Get Names of Classes Inside a JAR File](https://www.baeldung.com/jar-file-get-class-names)
- [Find All Jars Containing Given Class](https://baeldung.com/find-all-jars-containing-given-class/)
- [Creating JAR Files Programmatically](https://www.baeldung.com/jar-create-programatically)
- [Guide to Creating Jar Executables and Windows Executables from Java](https://www.baeldung.com/jar-windows-executables)
- [Get the Full Path of a JAR File From a Class](https://www.baeldung.com/java-full-path-of-jar-from-class)
