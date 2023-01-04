# 拥有多个源代码目录的Maven项目

我们有时需要在一个Java项目中使用多个源代码目录。一个常见的例子是，当有自动生成的类被放在不同的目录中时。

在这篇短文中，我们将展示如何设置Maven，使其与其他源码目录一起工作。

1. 添加另一个源目录

    假设我们已经创建了一个Maven项目，让我们在src/main文件夹下添加一个名为another-src的新源目录。

    之后，让我们在这个文件夹中创建一个简单的Java类：Foo.java。

    现在让我们在 src/main/java 目录中创建另一个类，该类使用我们刚刚创建的 Foo 类。

    MultipleSrcFolders.java

    如果我们尝试用Maven编译这个项目，会得到一个编译错误，因为项目中没有包含Foo类。

    ```.log
    [ERROR] .../MultipleSrcFolders.java:[6,9] cannot find symbol
    [ERROR]   symbol:   variable Foo
    [ERROR]   location: class com.baeldung.maven.plugins.MultipleSrcFolders
    ```

2. 使用生成器帮助插件

在Maven中，我们可以使用Builder Helper插件来添加更多的源代码目录。这个插件让我们以不同的方式定制构建生命周期。

它的目标之一是add-sources，目的是在生成-sources阶段向项目添加更多src目录。

我们可以在我们的项目中使用它，把它添加到我们的pom.xml中。

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>add-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>src/main/another-src</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

如果我们现在编译我们的项目，构建成功了。

## Relevant Articles

- [x] [Maven Project with Multiple Source Directories](https://www.baeldung.com/maven-project-multiple-src-directories)