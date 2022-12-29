# 带有Java模块的多模块Maven应用程序

1. 概述

    Java平台模块系统（[JPMS](https://www.baeldung.com/java-9-modularity)）为Java应用程序增加了更多的可靠性、更好的关注点分离和更强的封装性。然而，它不是一个构建工具，因此它缺乏自动管理项目依赖关系的能力。

    当然，我们可能会想，我们是否可以在模块化应用程序中使用成熟的构建工具，如Maven或Gradle。

    事实上，我们可以 在本教程中，我们将学习如何使用Java模块创建一个多模块Maven应用程序。

2. 将Maven模块封装在Java模块中

    由于模块化和依赖性管理在Java中并不是相互排斥的概念，因此我们可以将JPMS等与Maven无缝集成，从而利用两个世界的优点。

    在一个标准的多模块Maven项目中，我们将一个或多个子Maven模块放在项目的根文件夹下，并在父POM的`<modules>`部分中进行声明。

    反过来，我们编辑每个子模块的POM，通过标准的`<groupId>`、`<artifactId>`和`<version>`坐标指定其依赖关系。

    Maven中负责处理多模块项目的反应器机制负责按正确顺序构建整个项目。

    在这种情况下，我们基本上会使用相同的设计方法，但有一个细微但基本的变化：我们会把每个Maven模块包装成一个Java模块，在其中加入模块描述符文件module-info.java。

3. 母Maven模块

    为了展示模块化和依赖性管理如何协同工作，我们将建立一个基本的多模块Maven演示项目，其功能将缩小到只从持久层获取一些领域对象。

    为了保持代码简单，我们将使用普通的Map作为底层数据结构来存储领域对象。当然，我们可以很容易地进一步切换到一个完全成熟的关系型数据库。

    让我们从定义Maven的父模块开始。为了实现这一目标，我们创建一个根项目目录，例如，称为multimodulemavenproject，并在其中添加父模块的pom.xml文件。

    在父POM的定义中，有几个细节值得注意。

    首先，由于我们使用的是Java 11，我们的系统至少需要Maven 3.5.0，因为Maven从该版本开始支持Java 9及以上版本。

    而且，我们还需要至少3.8.0版的Maven编译器插件。因此，我们要确保在Maven Central上检查该插件的最新版本。

4. 子Maven模块

    注意，到此为止，父POM没有声明任何子模块。

    由于我们的演示项目将从持久层获取一些域对象，我们将创建四个子Maven模块。

    - entitymodule：将包含一个简单的域类
    - daomodule：将包含访问持久层所需的接口（一个基本的DAO合同）。
    - userdaomodule：将包括daomodule接口的实现。
    - mainappmodule：项目的入口。

    1. entitymodule的Maven模块

        现在，我们来添加第一个子Maven模块，它只包括一个基本的域类。

        在项目的根目录下，让我们创建entitymodule/src/main/java/com/baeldung/entity目录结构，添加一个User类。

        让我们包括该模块的entitymodule/pom.xml文件。

        正如我们所见，Entity模块不依赖其他模块，也不需要额外的Maven工件，因为它只包括User类。

        现在，我们需要将Maven模块封装成一个Java模块。为了实现这一目标，我们只需在entitymodule/src/main/java目录下放置以下模块描述符文件（module-info.java）。

        最后，让我们把子Maven模块添加到父POM中。

    2. daomodule的Maven模块

        让我们创建一个新的Maven模块，它将包含一个简单的接口。这便于定义一个抽象的契约，从持久层获取通用类型。

        事实上，把这个接口放在一个单独的Java模块里有一个很有说服力的理由。通过这样做，我们有了一个抽象的、高度解耦的契约，它很容易在不同的环境中重用。核心是，这是依赖反转原则的另一种实现，它产生了一种更灵活的设计。

        因此，让我们在项目的根目录下创建daomodule/src/main/java/com/baeldung/dao目录结构，并在其中添加`Dao<T>`接口。

        让我们来定义该模块的pom.xml文件。

        这个新模块也不需要其他模块或工件，所以我们只是把它包装成一个Java模块。让我们在daomodule/src/main/java目录下创建模块描述符。

        最后，让我们把该模块添加到父级POM中。

    3. userdaomodule的Maven模块

        接下来，我们来定义Maven模块，它拥有Dao接口的实现。

        在项目的根目录下，我们创建userdaomodule/src/main/java/com/baeldung/userdao目录结构，并在其中添加以下UserDao类。

        简单地说，UserDao类提供了一个基本的API，允许我们从持久化层获取用户对象。

        为了保持简单，我们使用一个Map作为持久化域对象的支持数据结构。当然，我们也可以提供一个更彻底的实现，比如说使用Hibernate的实体管理器。

        现在，我们来定义Maven模块的POM。

        在这种情况下，事情略有不同，因为userdaomodule模块需要entitymodule和daomodule模块。这就是为什么我们在pom.xml文件中把它们作为依赖项加入。

        我们仍然需要将这个Maven模块封装成一个Java模块。因此，让我们在userdaomodule/src/main/java目录下添加以下模块描述符。

        最后，我们需要将这个新模块添加到父级POM中。

        从高处看，很容易看到pom.xml文件和模块描述符扮演着不同的角色。即便如此，它们也能很好地相互补充。

        假设我们需要更新entitymodule和daomodule的Maven神器版本。我们无需改变模块描述符中的依赖关系，就能轻松做到这一点。Maven会负责为我们提供正确的工件。

        同样，我们可以通过修改模块描述符中的 "provides...with "指令来改变模块提供的服务实现。

        当我们同时使用Maven和Java模块时，我们会收获很多。前者带来了自动、集中的依赖性管理功能，而后者则提供了模块化的内在好处。

    4. mainappmodule的Maven模块

        此外，我们需要定义包含项目主类的Maven模块。

        正如我们之前所做的，让我们在根目录下创建mainappmodule/src/main/java/mainapp目录结构，并在其中添加以下Application类。

        应用程序类的main()方法非常简单。首先，它用几个用户对象填充了一个HashMap。接下来，它使用UserDao实例从地图中获取它们，然后将它们显示在控制台。

        该模块的依赖关系是不言自明的。所以，我们只需要把模块放在一个Java模块里面。因此，在mainappmodule/src/main/java目录结构下，让我们包含模块描述符。

        最后，让我们把这个模块添加到父级POM中。

        所有的Maven子模块已经就位，并被整齐地封装在Java模块中。

5. 运行应用程序

    最后，让我们在IDE中或在控制台中运行该应用程序。

    正如我们所期望的，当应用程序启动时，我们应该看到有几个用户对象被打印到控制台。

## Relevant Articles

- [x] [Multi-Module Maven Application with Java Modules](https://www.baeldung.com/maven-multi-module-project-java-jpms)
- [Importing Maven Project into Eclipse](https://www.baeldung.com/maven-import-eclipse)
