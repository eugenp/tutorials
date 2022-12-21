# Apache Maven指南

## Maven Basics

- [Apache Maven教程](maven-simple/README.md)
- [Apache Maven标准目录布局](#apache-maven标准目录布局)

### Apache Maven标准目录布局

Apache Maven是最受欢迎的Java项目构建工具之一。除了分散依赖关系和资源库外，促进项目间统一的目录结构也是其重要方面之一。

在这篇短文中，我们将探讨典型Maven项目的标准目录布局。

1. 目录布局

    一个典型的Maven项目有一个pom.xml文件和一个基于定义的惯例的目录结构。

    ```txt
    └───maven-project
        ├───pom.xml
        ├───README.txt
        ├───NOTICE.txt
        ├───LICENSE.txt
        └───src
            ├───main
            │   ├───java
            │   ├───resources
            │   ├───filters
            │   └───webapp
            ├───test
            │   ├───java
            │   ├───resources
            │   └───filters
            ├───it
            ├───site
            └───assembly
    ```

    默认的目录布局可以用项目描述符来覆盖，但这是不常见的，也是不鼓励的。

2. 根目录

    该目录是每个Maven项目的根目录。

    让我们仔细看看根目录下通常有哪些标准文件和子目录。

    - maven-project/pom.xml--定义Maven项目构建周期中需要的依赖和模块
    - maven-project/LICENSE.txt - 项目的许可信息
    - maven-project/README.txt - 项目概要
    - maven-project/NOTICE.txt - 关于项目中使用的第三方库的信息
    - maven-project/src/main - 包含源代码和资源，成为工件的一部分
    - maven-project/src/test - 包含所有测试代码和资源
    - maven-project/src/it - 通常保留给Maven Failsafe插件使用的集成测试
    - maven-project/src/site - 使用Maven网站插件创建的网站文档
    - maven-project/src/assembly - 用于打包二进制文件的汇编配置

3. src/main目录

    正如其名，src/main是Maven项目中最重要的目录。任何应该成为工件一部分的东西，无论是jar还是war，都应该出现在这里。

    - src/main/java - 神器的Java源代码
    - src/main/resources - 配置文件和其他文件，如i18n文件、每个环境的配置文件和XML配置。
    - src/main/webapp - 用于Web应用程序，包含JavaScript、CSS、HTML文件、视图模板和图片等资源
    - src/main/filters --包含在构建阶段向资源文件夹中的配置属性注入数值的文件。

4. src/test目录

    src/test目录是应用程序中每个组件的测试所在的地方。

    注意，这些目录或文件都不会成为工件的一部分。我们来看看它的子目录。

    - src/test/java - 用于测试的Java源代码
    - src/test/resources - 测试使用的配置文件和其他文件
    - src/test/filters - 包含在测试阶段向资源文件夹中的配置属性注入数值的文件

### Relevant Articles

- [x] [Apache Maven Guide](https://www.baeldung.com/maven-guide)
- [x] [Apache Maven Tutorial](https://www.baeldung.com/maven)
- [x] [Apache Maven Standard Directory Layout](https://www.baeldung.com/maven-directory-structure)
- [Multi-Module Project with Maven](https://www.baeldung.com/maven-multi-module)
- [Maven Packaging Types](https://www.baeldung.com/maven-packaging-types)
- [Maven Snapshot Repository vs Release Repository](https://www.baeldung.com/maven-snapshot-release-repository)
