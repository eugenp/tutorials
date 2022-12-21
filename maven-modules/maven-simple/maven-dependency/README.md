# Maven dependencyManagement vs. dependencies Tags

1. 概述

    在本教程中，我们将回顾两个重要的[Maven](https://www.baeldung.com/maven-guide)标签--dependencyManagement和dependencies。

    这些功能对多模块项目特别有用。

    我们将回顾这两个标签的异同，还将看看开发者在使用它们时常犯的一些错误，这些错误会造成混乱。

2. 使用方法

    一般来说，当我们在依赖标签中定义我们的依赖时，我们使用dependencyManagement标签来避免重复版本和范围标签。通过这种方式，所需的依赖被声明在一个中央 POM 文件中。

    1. 依赖管理 dependencyManagement

        这个标签由一个依赖性标签dependencies组成，它本身可能包含多个依赖性标签dependency。每个依赖关系应该至少有三个主要标签：groupId、artifactId和version。让我们看一个例子。

        ```xml
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.apache.commons</groupId>
                    <artifactId>commons-lang3</artifactId>
                    <version>3.12.0</version>
                </dependency>
            </dependencies>
        </dependencyManagement>
        ```

        上面的代码只是声明了新的工件commons-lang3，但它并没有真正将其添加到项目的依赖资源列表中。

    2. 依赖性

        这个标签包含一个依赖性dependency标签的列表。每个依赖关系应该至少有两个主要标签，即groupId和artifactId。

        ```xml
        <dependencies>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>3.12.0</version>
            </dependency>
        </dependencies>
        ```

        如果我们之前在POM文件中使用了 dependencyManagement 标签，那么版本和范围标签可以被隐式继承。

        ```xml
        <dependencies>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
            </dependency>
        </dependencies>
        ```

3. 相似性

    这两个标签都是为了声明一些第三方或子模块的依赖性。它们相互补充。

    事实上，我们通常在依赖性标签之前定义一次dependencyManagement标签。这样做是为了在POM文件中声明依赖关系。这个声明只是一个公告，它并没有真正将依赖关系添加到项目中。

    让我们看一个添加JUnit库依赖关系的例子。

    ```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.13.2</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```

    我们可以看到，在上面的代码中，有一个dependencyManagement标签，它本身包含另一个dependencies标签。

    现在，让我们看看代码的另一面，它将实际的依赖关系添加到项目中。

    ```xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
    </dependencies>
    ```

    所以，当前的标签与之前的标签非常相似。它们都会定义一个依赖关系的列表。当然，也有一些小的区别，我们很快就会介绍。

    同样的groupId和artifactId标签在两个代码片段中都有重复，它们之间存在着有意义的关联。它们都指的是同一个工件。

    可以看到，在我们后来的依赖性标签中没有任何版本标签存在，但它是有效的语法，而且可以被解析和编译，没有任何问题，这是因为已经由dependencyManagement声明了版本。

4. 差异

    结构上的差异

    - 正如我们前面所讲，这两个标签的主要结构差异是继承的逻辑。我们在 dependencyManagement 标记中定义版本，然后我们可以使用提到的版本，而不需要在下一个依赖关系标记中指定它。

    行为上的差异

    - dependencyManagement 只是一个声明，它并没有真正添加一个依赖关系。本节中声明的依赖关系必须在以后由依赖关系标签使用。只是依赖关系标签导致了真正的依赖关系的发生。
    - 在上面的例子中，dependencyManagement 标签不会将 junit 库添加到任何范围中。它只是对未来的依赖性标签的一个声明。

5. 现实世界的例子

    几乎所有基于Maven的开源项目都使用这种机制。

    让我们看看Maven项目本身的一个例子。我们看到Hamcrest-core的依赖，它存在于Maven项目中。它首先在dependencyManagement标签中声明，然后被主依赖项标签导入。

    ```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.hamcrest</groupId>
                <artifactId>hamcrest-core</artifactId>
                <version>2.2</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    ```

6. 常见用例

    这个功能的一个非常常见的用例是一个多模块的项目。

    想象一下，我们有一个由不同模块组成的大项目。每个模块都有自己的依赖关系，每个开发者都可能使用不同的版本来处理所使用的依赖关系。那么，这可能会导致不同工件版本的网状结构，这也会导致困难和难以解决的冲突。

    这个问题的简单解决方案肯定是在根POM文件（通常称为 "父"）中使用dependencyManagement标签，然后在子POM文件（子模块）甚至是父模块本身（如果适用）中使用依赖关系。

    如果我们只有一个模块，使用这个功能是否有意义呢？尽管这在多模块环境中非常有用，但即使在单模块项目中，也可以把它作为一个最佳实践规则来遵守。这有助于项目的可读性，也使得它可以扩展到一个多模块项目。

7. 常见错误

一个常见的错误是仅仅在dependencyManagement部分定义了一个依赖关系，而没有把它包括在dependencies标签中。在这种情况下，我们会遇到编译或运行时错误，这取决于提到的范围。

然后假设我们要在一个子模块源文件中使用这个库。

由于缺少库，这段代码将无法编译。编译器抱怨说有一个错误。

```log
[ERROR] Failed to execute goal compile (default-compile) on project sample-module: Compilation failure
[ERROR] ~/sample-module/src/main/java/com/baeldung/Main.java:[3,32] package org.apache.commons.lang3 does not exist
```

为了避免这个错误，只要在子模块的POM文件中加入下面的依赖关系标签就可以了。

- [x] [Maven dependencyManagement vs. dependencies Tags](https://www.baeldung.com/maven-dependencymanagement-vs-dependencies-tags)
