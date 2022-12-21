# Maven中的插件管理

1. 概述

    Apache Maven是一个强大的工具，它使用[插件](https://www.baeldung.com/maven#introduction-10)来自动执行一个Java项目中的所有构建和报告任务。

    然而，在构建过程中，很可能会有多个这样的插件使用不同的版本和配置，特别是在一个[多模块项目](https://www.baeldung.com/maven-multi-module)中。这可能会导致复杂的POM文件中出现冗余或重复的插件工件，以及散落在各个子项目中的配置问题。

    在本文中，我们将看到如何使用Maven的插件管理机制来处理此类问题，并在整个项目中有效维护插件。

2. 插件配置

    Maven有两种类型的插件。

    - 构建--在构建过程中执行。例如Clean、Install和Surefire插件。这些应在POM的构建部分进行配置。
    - 报告--在网站生成过程中执行，产生各种项目报告。例子包括Javadoc和Checkstyle插件。这些都是在项目POM的报告部分配置的。
    Maven插件提供了执行和管理项目构建所需的所有有用功能。

    例如，我们可以在POM中声明Jar插件。

    ```xml
    <build>
        ....
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                ....
            </plugin>
        ....
        </plugins>
    </build>
    ```

    在这里，我们在构建部分包含了这个插件，以增加将我们的项目编译成一个jar的能力。

3. 插件管理

    除了插件之外，我们还可以在POM的pluginManagement部分声明插件。这里面包含的插件元素与我们之前看到的大致相同。然而，通过在pluginManagement部分添加插件，它对这个POM和所有继承的子POM都可用。

    这意味着任何子POM将继承插件的执行，只需在其插件部分引用该插件即可。我们所需要做的就是添加相关的groupId和artifactId，而不需要重复配置或管理版本。

    与[依赖性管理](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#dependency-management)机制类似，这在多模块项目中特别有用，因为它提供了一个管理插件版本和任何相关配置的中心位置。

4. 例子

    让我们开始创建一个简单的多模块项目，有两个子模块。我们将在父级POM中包含Build Helper Plugin，它包含几个小目标来协助构建生命周期。在我们的例子中，我们将使用它来复制一些额外的资源到子项目的项目输出中。

    1. 父级POM配置

        首先，我们将插件添加到父POM的pluginManagement部分。

        plugin-management/pom.xml

        这将插件的add-resource目标与默认POM生命周期中的generate-resources阶段绑定。我们还指定了包含额外资源的 src/resources 目录。插件的执行将根据需要把这些资源复制到项目输出的目标位置。

        接下来，让我们运行maven命令，确保配置有效，构建成功。

        `$ mvn clean test`

        运行这个命令后，目标位置还没有包含预期的资源。

    2. 子POM配置

        现在，让我们从子POM中引用这个插件(build-helper-maven-plugin)。

        submodule-1/pom.xml

        与依赖性管理类似，我们不声明版本或任何插件配置。相反，子项目从父POM中的声明继承这些值。

        最后，让我们再次运行构建，看看输出。

        ```log
        [INFO] --- build-helper-maven-plugin:3.3.0:add-resource (add-resource) @ submodule-1 ---
        [INFO] 
        [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ submodule-1 ---
        [INFO] Using 'UTF-8' encoding to copy filtered resources.
        ```

5. 核心插件

    有一些Maven[核心插件](https://www.baeldung.com/core-maven-plugins)是作为构建生命周期的一部分，默认使用的。例如，清洁插件和编译器插件不需要明确声明。

    不过，我们可以在POM中的pluginManagement元素中明确声明和配置这些插件。主要的区别是，核心插件的配置会自动生效，不需要在子项目中进行任何引用。

    让我们通过在熟悉的pluginManagement部分添加编译器插件来试试。

    `plugin-management/pom.xml: maven-compiler-plugin`

    在这里，我们已经锁定了插件的版本，并将其配置为使用Java 8来构建项目。然而，在任何子项目中都不需要额外的插件声明。构建框架默认会激活这个配置。因此，这种配置意味着构建时必须使用Java 8来编译这个项目的所有模块。

    总的来说，对于多模块项目中需要的任何插件，明确声明配置并锁定版本可能是一个好的做法。因此，不同的子项目可以从父POM中只继承所需的插件配置，并根据需要应用它们。

    这消除了重复的声明，简化了POM文件，并提高了构建的可重复性。

## Relevant Articles

- [x] [Plugin Management in Maven](https://www.baeldung.com/maven-plugin-management)
