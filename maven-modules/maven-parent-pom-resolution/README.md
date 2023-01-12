# parent pom resolution

## 理解Maven的 "相对路径 "标签对父级POM的影响

在本教程中，我们将学习Maven的父级POM解析。首先，我们将了解其默认行为。然后，我们将讨论定制它的可能性。

1. 默认的父POM解析

    如果我们想指定一个父POM，我们可以通过命名groupId、artifactId和version，即所谓的GAV坐标来实现。Maven不会先通过在仓库中搜索来解析父POM。我们可以在Maven模型文档中找到细节，并总结出该行为。

    如果父文件夹中存在一个pom.xml文件，并且该文件有匹配的GAV坐标，那么它就被归类为项目的父POM。
    如果没有，Maven就会恢复到资源库中。
    在管理多模块项目时，将一个Maven项目放入另一个项目是最佳做法。例如，我们有一个聚合器项目，其GAV坐标如下。

    aggregator/pom.xml

    然后我们可以把模块放到子文件夹中，并把聚合器作为父类引用。

    module1引用aggregator

    见module1/pom.xml中的`<parent>GAV</parent>`部分。

    不需要将聚合器POM安装到存储库中。甚至不需要在聚合器POM中声明模块1。但我们必须注意，这只适用于项目的本地签出（例如在构建项目时）。如果该项目是作为Maven资源库的依赖关系解决的，那么父POM也应该在资源库中可用。

    我们必须确保聚合器的POM有匹配的GAV坐标。否则，我们会得到一个构建错误。

    ```log
    [ERROR]     Non-resolvable parent POM for com.baeldung.maven-parent-pom-resolution:module1:1.0.0-SNAPSHOT:
    Could not find artifact com.baeldung.maven-parent-pom-resolution:aggregator:pom:1.0-SNAPSHOT
    and 'parent.relativePath' points at wrong local POM @ line 7, column 13
    ```

2. 自定义父POM的位置

    如果父POM不在父文件夹中，我们需要使用relativePath标签来引用其位置。例如，如果我们有第二个模块，应该从模块1继承设置，而不是从聚合器继承，我们必须命名兄弟姐妹文件夹。

    module2->module1->aggregator

    module2/pom.xml中的`<parent><relativePath></relativePath></parent>`部分。

    当然，我们应该只使用每个环境中都有的相对路径（大多是同一Git仓库内的路径），以确保我们构建的可移植性。

3. 禁用本地文件解析

    为了跳过本地文件搜索，直接搜索Maven仓库中的父POM，我们需要明确地将relativePath设为空值。

    ```xml
    <parent>
        <groupId>com.baeldung</groupId>
        <artifactId>external-project</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath/>
    </parent>
    ```

    ![外部](pic/external.svg)

    每当我们从[Spring Boot](https://robintegg.com/2019/01/20/why-does-spring-initializr-set-the-parent-pom-relativepath-to-empty.html)等外部项目继承时，这应该是一种最佳做法。

4. 集成开发环境

    有趣的是，IntelliJ IDEA（当前版本：2021.1.3）有一个Maven插件，在父级POM解析方面与外部Maven运行系统不同。与[Maven的POM模式](https://maven.apache.org/xsd/maven-4.0.0.xsd)不同，它是这样解释relativePath标签的。

    > […] Maven looks for the parent pom first in the reactor of currently building projects […]

    这意味着，对于IDE内部的解析，只要父项目被注册为IntelliJ Maven项目，父POM的位置并不重要。这对简单开发项目而不明确构建项目可能有帮助（如果它们不在同一个Git仓库的范围内）。但如果我们试图用外部Maven运行时来构建项目，就会失败。

5. 总结

    在这篇文章中，我们了解到Maven不会首先通过搜索Maven仓库来解决父POM。而是在本地搜索，在继承外部项目时，我们必须明确停用这一行为。此外，IDE可能会额外解析到工作区的项目，这可能会导致我们在使用外部Maven运行时出现错误。

## Relevant Articles

- [x] [Understanding Maven’s “relativePath” Tag for a Parent POM](https://www.baeldung.com/maven-relativepath)
- [ ] [How to Disable a Maven Plugin Defined in a Parent POM](https://www.baeldung.com/maven-disable-parent-pom-plugin)
