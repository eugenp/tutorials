# Apache Maven指南

## Maven Basics

- [Apache Maven教程](maven-simple/README.md)
- [Apache Maven标准目录布局](#apache-maven标准目录布局)
- [Maven本地仓库在哪里](#maven本地仓库在哪里)
- [Maven目标和阶段](#maven目标和阶段)
- [Maven发布到Nexus](#maven发布到nexus)
- [Maven部署到Nexus](#maven部署到nexus)
- [用Maven安装本地jar](#用maven安装本地jar)
- [Maven Wrapper快速指南](../spring-boot-modules/spring-boot-artifacts/README.md)

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

### Maven本地仓库在哪里？

我们将重点讨论Maven在本地存储所有本地依赖项的地方，也就是Maven本地仓库(Maven local repository)。

简单地说，当我们运行Maven构建时，项目的依赖项（jars、插件jars、其他工件）都会存储在本地，以便日后使用。

另外请记住，除了这种类型的本地仓库，Maven还支持三种类型的仓库。

- 本地Local--本地开发机器上的文件夹位置
- 中央Central--由Maven社区提供的资源库
- 远程Remote--组织拥有的自定义资源库

1. 本地仓库

    Maven的本地仓库是本地机器上的一个目录，储存了所有项目工件。

    当我们执行Maven构建时，Maven会自动将所有依赖的jars下载到本地仓库。通常情况下，这个目录被命名为.m2。

    以下是基于操作系统的默认本地资源库的位置。

    `Windows: C:\Users\<User_Name>\.m2`

    `Linux: /home/<User_Name>/.m2`

    `Mac: /Users/<user_name>/.m2`

    而对于Linux和Mac，我们可以用简短的形式来写。

    `~/.m2`

2. 在 settings.xml 中自定义本地版本库

    如果 repo 没有出现在这个默认位置，很可能是因为一些预先存在的配置。

    该配置文件位于Maven安装目录下一个名为conf的文件夹，名称为settings.xml。

    以下是决定我们丢失的本地 repo 的位置的相关配置。

    ```xml
    <settings>
        <localRepository>C:/maven_repository</localRepository>
    ```

    这基本上就是我们改变本地版本库位置的方法。当然，如果我们改变了这个位置，就不会再在默认位置找到版本库了。

    存储在早期位置的文件不会被自动移动。

3. 通过命令行传递本地版本库的位置

    除了在Maven的settings.xml中设置自定义本地仓库外，mvn命令还支持maven.repo.local属性，它允许我们将本地仓库位置作为命令行参数传递。

    `mvn -Dmaven.repo.local=/my/local/repository/path clean install`

    这样一来，我们就不必改变Maven的settings.xml。

### Maven目标和阶段

1. 概述

    在本教程中，我们将探讨不同的Maven构建生命周期及其阶段。

    我们还将讨论目标和阶段之间的核心关系。

2. Maven构建生命周期

    Maven构建遵循特定的生命周期来部署和分发目标项目。

    有三个内置的生命周期。

    - default：主要的生命周期，因为它负责项目部署
    - clean：清理项目，删除之前构建产生的所有文件。
    - site：创建项目的网站文档。

    每个生命周期由一连串的阶段组成。默认的构建生命周期由23个阶段组成，因为它是主要的构建生命周期。

    另一方面，清洁生命周期由3个阶段组成，而站点生命周期则由4个阶段组成。

3. Maven阶段

    Maven阶段代表Maven构建生命周期中的一个阶段。每个阶段都负责一项具体任务。

    以下是默认构建生命周期中最重要的几个阶段。

    - validate验证：检查构建所需的所有信息是否可用
    - compile编译：编译源代码
    - test-compile: 编译测试源代码
    - test测试：运行单元测试
    - package打包：将编译的源代码打包成可分发的格式（jar，war，...）。
    - integration-test集成测试：如果需要的话，处理并部署软件包，以运行集成测试
    - install安装：将软件包安装到本地资源库中
    - deploy部署：将软件包复制到远程存储库中

    关于每个生命周期的阶段的完整列表，请查看[Maven参考](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference)。

    各个阶段是按特定顺序执行的。这意味着，如果我们用命令运行一个特定的阶段。

    `mvn <PHASE>`

    它不仅会执行指定的阶段，还会执行前面的所有阶段。

    例如，如果我们运行部署阶段，这是默认构建生命周期的最后一个阶段，它也将执行部署阶段之前的所有阶段，也就是整个默认生命周期。

    `mvn deploy`

4. Maven目标

    每个阶段都是一个目标序列，每个目标都负责一个特定的任务。

    当我们运行一个阶段时，所有与该阶段绑定的目标都会按顺序执行。

    以下是一些阶段和与之绑定的默认目标。

    - compiler:compile - 来自编译器插件的编译目标被绑定到编译阶段。
    - compiler:testCompile 被绑定到测试-编译阶段。
    - surefire:test 被绑定到测试阶段
    - install:install 被绑定到安装阶段
    - jar:jar 和 war:war 被绑定到打包阶段。

    我们可以使用命令列出所有绑定到特定阶段的目标和它们的插件。

    `mvn help:describe -Dcmd=PHASENAME`

    例如，要列出绑定在编译阶段的所有目标，我们可以运行。

    `mvn help:describe -Dcmd=compile`

    然后我们会得到样本输出。

    ```log
    compile' is a phase corresponding to this plugin:
    org.apache.maven.plugins:maven-compiler-plugin:3.1:compile
    ```

    如上所述，这意味着编译器插件的编译目标被绑定到编译阶段。

5. Maven插件

    一个Maven插件是一组目标；但是，这些目标不一定都绑定在同一个阶段。

    例如，下面是Maven Failsafe插件的一个简单配置，它负责运行集成测试。

    ```xml
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>${maven.failsafe.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ```

    我们可以看到，Failsafe插件在这里配置了两个主要目标。

    - integration-test：运行集成测试
    - verify: verify 所有集成测试是否通过

    我们可以使用下面的命令来列出某个特定插件的所有目标。

    `mvn <PLUGIN>:help`

    例如，要列出Failsafe插件的所有目标，我们可以运行。

    `mvn failsafe:help`

    而输出结果将是：

    ```log
    This plugin has 3 goals:

    failsafe:help
    Display help information on maven-failsafe-plugin.
    Call mvn failsafe:help -Ddetail=true -Dgoal=<goal-name> to display parameter
    details.

    failsafe:integration-test
    Run integration tests using Surefire.

    failsafe:verify
    Verify integration tests ran using Surefire.
    ```

    要运行一个特定的目标而不执行其整个阶段（以及前面的阶段），我们可以使用命令。

    `mvn <PLUGIN>:<GOAL>`

    例如，要运行Failsafe插件的集成测试目标，我们需要运行。

    `mvn failsafe:integration-test`

6. 构建Maven项目

    要构建一个Maven项目，我们需要通过运行其中一个阶段来执行一个生命周期。

    `mvn deploy`

    这将执行整个默认生命周期。或者，我们也可以在安装阶段停止。

    `mvn install`

    但通常情况下，我们会在新的构建之前运行清洁生命周期，以清理项目。

    `mvn clean install`

    我们也可以只运行插件的一个特定目标。

    `mvn compiler:compile`

    注意，如果我们试图在不指定阶段或目标的情况下构建一个Maven项目，我们会得到一个错误。

    `[ERROR] No goals have been specified for this build. You must specify a valid lifecycle phase or a goal`

### Maven发布到Nexus

在这篇文章中，我们将用Maven配置发布流程--在项目的pom中以及Jenkins作业中都是如此。

1. pom中的存储库

    为了让Maven能够向Nexus版本库服务器发布，我们需要通过distributionManagement元素定义版本库信息。

    ```xml
    <distributionManagement>
        <repository>
            <id>nexus-releases</id>
            <url>http://localhost:8081/nexus/content/repositories/releases</url>
        </repository>
    </distributionManagement>
    ```

    在Nexus上，托管的Release Repository是开箱即用的，所以不需要明确创建它。

2. Maven pom中的SCM

    发布过程将与项目的源代码控制进行交互--这意味着我们首先需要在pom.xml中定义`<scm>`元素。

    ```xml
    <scm>
        <connection>scm:git:https://github.com/user/project.git</connection>
        <url>http://github.com/user/project</url>
        <developerConnection>scm:git:https://github.com/user/project.git</developerConnection>
    </scm>
    ```

    或者，使用git协议。

    ```xml
    <scm>
        <connection>scm:git:git@github.com:user/project.git</connection>
        <url>scm:git:git@github.com:user/project.git</url>
        <developerConnection>scm:git:git@github.com:user/project.git</developerConnection>
    </scm>
    ```

3. 发布插件

    发布流程使用的标准Maven插件是maven-release-plugin，该插件的配置很简单。

    ```xml
    <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-release-plugin</artifactId>
    <version>2.4.2</version>
    <configuration>
        <tagNameFormat>v@{project.version}</tagNameFormat>
        <autoVersionSubmodules>true</autoVersionSubmodules>
        <releaseProfiles>releases</releaseProfiles>
    </configuration>
    </plugin>
    ```

    这里重要的是，releaseProfiles配置实际上会在发布过程中强迫一个Maven配置文件--发布配置文件--变得活跃。

    在这个过程中，nexus-staging-maven-plugin被用来执行部署到nexus-releases Nexus仓库的工作。

    ```xml
    <profiles>
    <profile>
        <id>releases</id>
        <build>
            <plugins>
                <plugin>
                <groupId>org.sonatype.plugins</groupId>
                <artifactId>nexus-staging-maven-plugin</artifactId>
                <version>1.5.1</version>
                <executions>
                    <execution>
                        <id>default-deploy</id>
                        <phase>deploy</phase>
                        <goals>
                            <goal>deploy</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <serverId>nexus-releases</serverId>
                    <nexusUrl>http://localhost:8081/nexus/</nexusUrl>
                    <skipStaging>true</skipStaging>
                </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
    </profiles>
    ```

    该插件被配置为在没有暂存机制的情况下执行发布过程，与之前的部署过程一样（skipStaging=true）。

    而且与部署过程类似，释放到Nexus是一个安全的操作--所以我们要再次使用Out of the Box部署用户表单Nexus。

    我们还需要在全局settings.xml（%USER_HOME%/.m2/settings.xml）中配置nexus-releases服务器的凭证。

    ```xml
    <servers>
    <server>
        <id>nexus-releases</id>
        <username>deployment</username>
        <password>the_pass_for_the_deployment_user</password>
    </server>
    </servers>
    ```

    这就是完整的配置

4. 释放过程

    让我们把发布过程分成几个小而集中的步骤。当项目的当前版本是SNAPSHOT版本--比如说0.1-SNAPSHOT，我们就执行一个发布。

    1. Release:clean

        清理一个 Release 将会。

        删除发布描述符（release.properties）。
        删除任何备份的 POM 文件

    2. Release:prepare

        发布过程的下一个部分是准备发布；这将：

        - 执行一些检查--应该没有未提交的修改，项目应该不依赖于任何SNAPSHOT的依赖关系
        - 将pom文件中的项目版本改为完整的版本号（去掉SNAPSHOT的后缀）--在我们的例子中是0.1
        - 运行项目的测试套件
        - 提交并推送修改内容
        - 从这个非SNAPSHOT版本的代码中创建标签
        - 增加项目在pom中的版本 - 在我们的例子中 - 0.2-SNAPSHOT
        - 提交并推送这些变化

    3. release:perform

        发布过程的后半部分是执行发布；这将：

        - 从 SCM 签出发布标签
        - 构建并部署发布的代码
        这个过程的第二步依赖于准备步骤的输出 - Release.properties。

5. 在Jenkins上

    Jenkins可以通过两种方式之一执行发布过程--它可以使用自己的发布插件，也可以简单地通过标准的maven作业运行正确的发布步骤来执行发布。

    现有的专注于发布过程的Jenkins插件有。

    - [发布插件](https://wiki.jenkins-ci.org/display/JENKINS/Release+Plugin)
    - [M2发布插件](https://wiki.jenkins-ci.org/display/JENKINS/M2+Release+Plugin)

    然而，由于执行发布的Maven命令足够简单，我们可以简单地定义一个标准的Jenkins作业来执行该操作--不需要插件。

    因此，对于一个新的Jenkins作业（构建一个maven2/3项目），我们将定义两个字符串参数：releaseVersion=0.1和developmentVersion=0.2-SNAPSHOT。

    在构建配置部分，我们可以简单地配置以下Maven命令来运行。

    ```bash
    release:clean release:prepare release:perform 
    -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${developmentVersion}
    ```

    当运行一个参数化的作业时，Jenkins会提示用户指定这些参数的值--所以每次运行作业时，我们需要为releaseVersion和developmentVersion填写正确的值。

    另外，值得使用[工作区清理插件](https://wiki.jenkins-ci.org/display/JENKINS/Workspace+Cleanup+Plugin)，并为这次构建勾选在构建开始前删除工作区的选项。但是请记住，Release的执行步骤必须由preparestep的同一命令来运行--这是因为后者的执行步骤将使用prepare所创建的release.properties文件。这意味着我们不能让一个Jenkins作业运行prepare，而另一个运行perform。

6. 总结

    本文展示了如何在有无Jenkins的情况下实现Maven项目的发布过程。与部署类似，该过程使用nexus-staging-maven-plugin与Nexus交互，重点是一个git项目。

### Maven部署到Nexus

在上章节中，我讨论了Maven项目如何在本地安装尚未在Maven中心（或其他大型公共托管仓库）部署的第三方jar。

该方案只适用于小型项目，因为在这些项目中，安装、运行和维护一个完整的Nexus服务器可能是多余的。然而，随着项目的发展。

Nexus很快就会成为唯一真正成熟的选择，用于托管第三方工件，以及跨开发流重复使用内部工件。

本文将介绍如何用Maven将项目的工件部署到Nexus。

1. pom.xml中的Nexus要求

    为了让Maven能够部署它在构建的打包阶段创建的工件，它需要通过distributionManagement元素定义打包后的工件将被部署的仓库信息。

    ```xml
    <distributionManagement>
    <snapshotRepository>
        <id>nexus-snapshots</id>
        <url>http://localhost:8081/nexus/content/repositories/snapshots</url>
    </snapshotRepository>
    </distributionManagement>
    ```

    在Nexus上，一个托管的公共Snapshots仓库是开箱即用的，所以不需要再创建或配置什么。Nexus很容易确定其托管仓库的URL--每个仓库都会在项目pom的`<distributionManagement>`中显示要添加的确切条目，在Summary标签下。

2. 插件

    默认情况下，Maven通过maven-deploy-plugin处理部署机制--这与默认Maven生命周期的部署阶段相匹配。

    ```xml
    <plugin>
    <artifactId>maven-deploy-plugin</artifactId>
    <version>2.8.1</version>
    <executions>
        <execution>
            <id>default-deploy</id>
            <phase>deploy</phase>
            <goals>
                <goal>deploy</goal>
            </goals>
        </execution>
    </executions>
    </plugin>
    ```

    maven-deploy-plugin是处理向Nexus部署项目工件任务的一个可行选择，但它并不是为了充分利用Nexus所能提供的优势而建立。正因为如此，Sonatype建立了一个Nexus专用插件--nexus-staging-maven-plugin--实际上是为了充分利用Nexus提供的更高级的功能--如staging功能。

    虽然在简单的部署过程中，我们不需要暂存功能，但我们将继续使用这个定制的Nexus插件，因为它的目的很明确，就是为了与Nexus很好地对话。

    使用maven-deploy-plugin的唯一原因是为将来使用Nexus的替代方案留有余地，比如[Artifactory](https://www.jfrog.com/open-source/#os-arti)仓库。然而，与其他组件不同的是，Maven Repository Manager在项目的整个生命周期中实际上可能会发生变化，而Maven Repository Manager极不可能发生变化，所以不需要这种灵活性。

    因此，在部署阶段使用另一个部署插件的第一步是禁用现有的、默认的映射。

    ```xml
    <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-deploy-plugin</artifactId>
    <version>${maven-deploy-plugin.version}</version>
    <configuration>
        <skip>true</skip>
    </configuration>
    </plugin>
    ```

    现在，我们可以定义。

    ```xml
    <plugin>
    <groupId>org.sonatype.plugins</groupId>
    <artifactId>nexus-staging-maven-plugin</artifactId>
    <version>1.5.1</version>
    <executions>
        <execution>
            <id>default-deploy</id>
            <phase>deploy</phase>
            <goals>
                <goal>deploy</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <serverId>nexus</serverId>
        <nexusUrl>http://localhost:8081/nexus/</nexusUrl>
        <skipStaging>true</skipStaging>
    </configuration>
    </plugin>
    ```

    该插件的部署目标被映射到Maven构建的部署阶段。

    还要注意的是，如前所述，我们在向Nexus简单部署-SNAPSHOT工件时不需要暂存功能，所以通过`<skipStaging>`元素完全禁用。

    默认情况下，部署目标包括暂存工作流，这是对发布构建的建议。

3. 全局设置.xml

    部署到Nexus是一项安全操作--任何Nexus实例上都有一个部署用户。

    在项目的pom.xml中不能用该部署用户的凭证配置Maven，以便它能与Nexus正确互动。这是因为pom的语法不允许这样做，更何况pom可能是一个公共构件，所以不适合保存凭证信息。

    服务器的凭证必须在全局Maven setting.xml中定义。

    ```xml
    <servers>
    <server>
        <id>nexus-snapshots</id>
        <username>deployment</username>
        <password>the_pass_for_the_deployment_user</password>
    </server>
    </servers>
    ```

    服务器也可以被配置为使用基于密钥的安全，而不是原始和明文凭证。

4. 部署过程

    执行部署过程是一项简单的任务。

    `mvn clean deploy -Dmaven.test.skip=true`

    在部署工作中跳过测试是可以的，因为这个工作应该是项目部署管道的最后一个工作。

    这种部署管道的一个常见例子是连续的Jenkins作业，每个作业只有在成功完成后才会触发下一个。因此，管道中的前一个作业有责任运行项目的所有测试套件 - 在部署作业运行时，所有测试都应该通过。

    如果运行一条命令，那么测试可以在部署阶段执行前保持激活运行。

    `mvn clean deploy`

5. 总结

    这是一个简单而又高效的解决方案，可以将Maven工件部署到Nexus上。

    它也有些主观臆断--使用nexus-staging-maven-plugin代替默认的maven-deploy-plugin；禁用暂存功能等--正是这些选择使该解决方案简单实用。

    激活完整的暂存功能可能是未来文章的主题。

    最后，我们将在下一篇文章中讨论发布流程。

### 用Maven安装本地jar

1. 问题与选择

    Maven是一个非常通用的工具，其可用的公共资源库首屈一指。然而，总有一些工件要么不在任何地方托管，要么托管的仓库有风险，因为当你需要它时，它可能不会出现。

    当这种情况发生时，有几个选择。

    - 咬紧牙关，安装一个成熟的版本库管理解决方案，如Nexus
    - 尝试将工件上传到一个有信誉的公共仓库中
    - 使用maven插件在本地安装该工件

    Nexus当然是更成熟的解决方案，但也更复杂。对于使用单个jar这样简单的问题来说，配置一个实例来运行Nexus，设置Nexus本身，配置和维护Nexus，可能都是多余的。如果这种情况--托管自定义工件--是一种常见的情况，那么资源库管理器就有很大的意义。

    将工件直接上传到公共仓库或Maven中心也是一个很好的解决方案，但通常需要很长时间。此外，该库可能根本没有启用Maven，这使得这一过程更加困难，所以这不是一个能够使用工件NOW的现实解决方案。

    这就留下了第三种选择--在源码控制中添加工件，并使用maven插件--在这种情况下，[maven-install-plugin](https://maven.apache.org/plugins/maven-install-plugin/)可以在构建过程需要它之前将其安装到本地。这是迄今为止最简单、最可靠的选择。

2. 用maven-install-plugin安装本地罐子

    让我们先来看看将工件安装到本地仓库所需的全部配置。

    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-install-plugin</artifactId>
        <version>2.5.1</version>
        <configuration>
            <groupId>org.somegroup</groupId>
            <artifactId>someartifact</artifactId>
            <version>1.0</version>
            <packaging>jar</packaging>
            <file>${basedir}/dependencies/someartifact-1.0.jar</file>
            <generatePom>true</generatePom>
        </configuration>
        <executions>
            <execution>
                <id>install-jar-lib</id>
                <goals>
                    <goal>install-file</goal>
                </goals>
                <phase>validate</phase>
            </execution>
        </executions>
    </plugin>
    ```

    现在，让我们来分解和分析一下这个配置的细节。

    1. Artifact信息

        Artifact信息被定义为`<configuration>`元素的一部分。实际的语法与声明依赖关系非常相似--一个groupId、artifactId和版本元素。

        配置的下一部分需要定义工件的包装 - 这被指定为jar。

        接下来，我们需要提供实际要安装的jar文件的位置--可以是绝对文件路径，也可以是相对路径，使用Maven中的[可用属性](https://cwiki.apache.org/confluence/display/MAVEN/Maven+Properties+Guide)。本例中，${basedir}属性代表项目的根，即pom.xml文件所在的位置。这意味着someartifact-1.0.jar文件需要放置在根目录下的/dependencies/目录中。

        最后，还有其他几个[可选细节](https://maven.apache.org/plugins/maven-install-plugin/install-file-mojo.html)也可以配置。

    2. 执行

        install-file 目标的执行与标准Maven构建生命周期中的验证阶段相联系。因此，在尝试编译之前，你需要明确运行验证阶段。

        `mvn validate`

        这一步之后，标准的编译就可以工作了。

        `mvn clean install`

        一旦编译阶段执行，我们的someartifact-1.0.jar就会正确地安装在本地仓库中，就像其他可能从Maven中心本身获取的工件一样。

    3. 生成POM与提供POM

        我们是否需要为工件提供一个pom.xml文件的问题主要取决于工件本身的运行时依赖性。简单地说，如果工件在运行时依赖其他的jar，这些jar在运行时也需要出现在classpath上。对于一个简单的工件来说，这不应该是一个问题，因为它在运行时可能没有依赖关系（依赖图中的一个叶子）。

        install-file目标中的generatePom选项对于这些类型的工件来说应该足够了。

        `<generatePom>true</generatePom>`

        然而，如果工件更复杂，并且确实有非琐碎的依赖，那么，如果这些依赖还没有在classpath中，就必须添加它们。一种方法是在项目的pom文件中手动定义这些新的依赖项。一个更好的解决方案是与安装的工件一起提供一个自定义的pom.xml文件。

        ```xml
        <generatePom>false</generatePom>
        <pomFile>${basedir}/dependencies/someartifact-1.0.pom</pomFile>
        ```

        这将使Maven能够解决该自定义pom.xml中定义的神器的所有依赖关系，而不必在项目的主pom文件中手动定义它们。

3. 总结

    本文介绍了如何通过使用maven-install-plugin在本地安装未在Maven项目中托管的jar。

## Maven配置

- [在Maven中设置Java版本](#在maven中设置java版本)
- [在代理服务器后面使用Maven](maven-proxy/README.md)
- 参考pom.xml中的环境变量
- 在Eclipse中为Maven构建的JDK配置
- JAVA_HOME应指向JDK而非JRE
- Maven中的settings.xml文件
- 作为Maven属性的命令行参数

### 在Maven中设置Java版本

在这个快速教程中，我们将展示如何在Maven中设置Java版本。

在继续之前，我们可以查看Maven的默认JDK版本。运行mvn -v命令将显示Maven所运行的Java版本。

1. 使用编译器插件

    我们可以在编译器插件中指定所需的Java版本。

    1. 编译器插件

        第一个选择是在编译器插件属性中设置版本。

        ```xml
        <properties>
            <maven.compiler.target>1.8</maven.compiler.target>
            <maven.compiler.source>1.8</maven.compiler.source>
        </properties>
        ```

        Maven编译器接受该命令的-target和-source版本。如果我们想使用Java 8的语言特性，-source应设置为1.8。

        另外，为使编译后的类与JVM 1.8兼容，-target值也应是1.8。

        另外，我们也可以直接配置编译器插件。

        ```xml
        <plugins>
            <plugin>    
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
        ```

        maven-compiler-plugin还有其他配置属性，除了-source和-target版本外，我们还可以对编译过程进行更多控制。

    2. Java 9及其他

        此外，从JDK 9版本开始，我们可以使用一个新的-release命令行选项。这个新的参数将自动配置编译器，使其产生的类文件与给定平台版本的实现相链接。

        默认情况下，-source和-target选项并不能保证交叉编译。

        这意味着我们不能在旧版本的平台上运行我们的应用程序。此外，为了编译和运行旧版Java的程序，我们还需要指定-bootclasspath选项。

        为了正确地进行交叉编译，新的-release选项取代了三个标志。-source、-target和-bootclasspath。

        在转换了我们的例子之后，我们可以为插件的属性声明如下。

        ```xml
        <properties>
            <maven.compiler.release>7</maven.compiler.release>
        </properties>
        ```

        而对于从3.6版本开始的maven-compiler-plugin，我们可以这样写。

        ```xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.0</version>
            <configuration>
                <release>7</release>
            </configuration>
        </plugin>
        ```

        注意，我们可以在一个新的`<release>`属性中添加Java版本。在这个例子中，我们为Java 7编译了我们的应用程序。

        更重要的是，我们不需要在机器上安装JDK 7。Java 9已经包含了所有用于将新的语言特性与JDK 7连接起来的信息。

2. Spring Boot规范

    Spring Boot应用程序在pom.xml文件的属性标签内指定JDK版本。

    首先，我们需要添加spring-boot-starter-parent作为我们项目的父级。

    这个父级POM允许我们配置默认插件和多个属性，包括Java版本 - 默认情况下，Java版本是1.8。

    然而，我们可以通过指定java.version属性来覆盖父类的默认版本。

    ```xml
    <properties>
        <java.version>9</java.version>
    </properties>
    ```

    通过设置java.version属性，我们声明源和目标Java版本都等于1.9。

    最重要的是，我们应该记住，这个属性是Spring Boot的规范。此外，从Spring Boot 2.0开始，Java 8是最低版本。

    这意味着我们不能为旧的JDK版本使用或配置Spring Boot。

3. 总结

    这个快速教程展示了在我们的Maven项目中设置Java版本的可能方式。

    - 使用`<java.version>`只有在Spring Boot应用中才能实现。
    - 对于简单情况，maven.compiler.source和maven.compiler.target属性应该是最合适的。
    - 最后，为了对编译过程有更多控制，可以使用maven-compiler-plugin配置设置。

### 参考pom.xml中的环境变量

在本快速教程中，我们将了解如何从Maven的pom.xml中读取环境变量来定制构建过程。

要从pom.xml中引用环境变量，我们可以使用${env.VARIABLE_NAME}语法。

例如，让我们在构建过程中使用它来[外部化Java版本](https://www.baeldung.com/maven-java-version)。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>${env.JAVA_VERSION}</source>
                <target>${env.JAVA_VERSION}</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

我们应该记得通过环境变量传递Java版本信息。如果我们没有这样做，那么我们将无法构建项目。

要针对这样的构建文件运行Maven目标或阶段，我们应首先导出环境变量。比如说

```bash
export JAVA_VERSION=9
mvn clean package
```

在Windows上，我们应该使用 "set VAR=value "语法来导出环境变量。

为了在缺少JAVA_VERSION环境变量时提供一个默认值，我们可以使用Maven配置文件。

```xml
<profiles>
    <profile>
        <id>default-java</id>
        <activation>
            <property>
                <name>!env.JAVA_VERSION</name>
            </property>
        </activation>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.1</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

如上所示，我们正在创建一个配置文件，并仅在 JAVA_VERSION 环境变量缺失的情况下使其处于激活状态 - !env.JAVA_VERSION 部分。如果发生这种情况，那么这个新的插件定义将覆盖现有的插件。

## Maven项目设置

- [Maven配置文件指南](maven-simple/maven-profiles/README.md)
- [使用Maven的多模块项目](maven-simple/parent-project/README.md)
- [使用Maven的多版本JAR文件](../core-java-modules/core-java-9-new-features/README.md)
- [带有Java模块的多模块Maven应用](multimodulemavenproject/README.md)
- [具有多个源代码目录的Maven项目](maven-multi-source/README.md)
- [如何用Maven创建一个可执行的JAR文件](../core-java-modules/core-java-jar/README.md#如何用maven创建一个可执行的jar)
- [用Maven对JS和CSS资产进行最小化处理](../spring-static-resources/README.md#用maven对js和css资产进行最小化处理)
- [Maven打包类型](#maven打包类型)
- [了解Maven的父POM "相对路径 "标签](maven-parent-pom-resolution/README.md)

### Maven打包类型

打包类型是任何Maven项目的一个重要方面。它指定项目生成的工件类型。通常，构建生成jar、war、pom或其他可执行文件。

Maven提供了许多默认的打包类型，还提供了定义自定义打包类型的灵活性。

在本教程中，我们将深入了解Maven打包类型。首先，我们将查看Maven中的构建生命周期。然后，我们将讨论每种包装类型，它们代表什么，以及它们对项目生命周期的影响。最后，我们将了解如何定义自定义包装类型。

1. 默认包装类型

    Maven提供了许多默认的打包类型，包括jar、war、ear、pom、rar、ejb和maven-plugin。每种打包类型都遵循由阶段组成的构建生命周期。通常，每个阶段都是一系列目标，并执行特定任务。

    不同的包装类型在特定阶段可能有不同的目标。例如，在jar打包类型的打包阶段，执行maven-jar-plugin的jar目标。相反，对于war项目，maven-war-plugin的war目标在同一阶段执行。

    1. jar

        Java archive（或jar）是最流行的打包类型之一。具有这种打包类型的项目生成一个扩展名为.jar的压缩zip文件。它可能包括纯Java类、接口、资源和元数据文件。

        首先，让我们看看为jar构建阶段绑定的一些默认目标：

        - resources: resources
        - compiler: compile
        - resources: testResources
        - compiler: testCompile
        - surefire: test
        - jar: jar
        - install: install
        - deploy: deploy

        毫不拖延，让我们定义jar项目的包装类型：

        `<packaging>jar</packaging>`

        如果没有指定任何内容，Maven会假设包装类型是jar。

    2. war

        简单地说，web应用程序存档（或war）包含与web应用程序相关的所有文件。它可能包括Javaservlet、JSP、HTML页面、部署描述符和相关资源。总的来说，war与jar有着相同的目标绑定，但有一个例外——战争的打包阶段有一个不同的目标，那就是war。

        毫无疑问，jar和war是Java社区中最流行的打包类型。这两者之间的[详细差异](#jar和war打包的区别)。

        让我们定义web应用程序的打包类型：

        `<packaging>war</packaging>`

        其他打包类型ejb、par和rar也有类似的生命周期，但每个打包类型都有不同的打包目标。

        ejb:ejb或par:par或rar:rar

    3. ear

        企业应用程序存档（或ear）是包含J2EE应用程序的压缩文件。它由一个或多个模块组成，这些模块可以是web模块（打包为war文件）或EJB模块（打包成jar文件），也可以是两者。

        换言之，ear是jar和war的超集，需要一个应用程序服务器来运行应用程序，而war只需要一个web容器或web服务器来部署它。区分web服务器和应用程序服务器的方面，以及[Java中流行的服务器](https://www.baeldung.com/java-servers)是什么，对于Java开发人员来说都是重要的概念。

        让我们定义ear的默认目标绑定：

        - ear: generate-application-xml
        - resources: resources
        - ear: ear
        - install: install
        - deploy: deploy

        以下是我们如何定义此类项目的包装类型：

        `<packaging>ear</packaging>`

    4. pom

        在所有包装类型中，pom是最简单的一种。它有助于创建聚合器和父项目。

        聚合器或多模块项目组装来自不同来源的子模块。这些子模块是常规的Maven项目，遵循自己的构建生命周期。聚合器POM在modules元素下具有子模块的所有引用。

        父项目允许您定义POM之间的继承关系。父POM共享某些配置、插件和依赖项及其版本。父级的大多数元素都由其子级继承-例外包括artifactId、name和前提条件。

        因为没有要处理的资源，也没有要编译或测试的代码。因此，pom项目的工件会自己生成，而不是生成任何可执行文件。

        让我们定义多模块项目的打包类型：

        `<packaging>pom</packaging>`

        此类项目的生命周期最简单，只有两个步骤：安装和部署。

    5. maven-plugin

        Maven提供了各种有用的插件。然而，在某些情况下，默认插件不够。在这种情况下，该工具提供了根据项目需要创建[maven插件](https://www.baeldung.com/maven-plugin)的灵活性。

        要创建插件，请设置项目的打包类型：

        `<packaging>maven-plugin</packaging>`

        maven插件的生命周期类似于jar的生命周期，但有两个例外：

        - plugin：描述符绑定到生成资源阶段
        - plugin：addPluginArtifactMetadata添加到包阶段

        对于这种类型的项目，需要maven-plugin-api 依赖关系。

    6. ejb

        Enterprise Java Beans（或[ejb](https://www.baeldung.com/ejb-intro)）有助于创建可扩展的分布式服务器端应用程序。EJB通常提供应用程序的业务逻辑。典型的EJB体系结构由三个组件组成：Enterprise Java Beans（EJBs）、EJB container和应用程序服务器。

        现在，让我们定义EJB项目的打包类型：

        `<package>ejb</package>`

        ejb打包类型也具有与jar打包类似的生命周期，但具有不同的打包目标。这类项目的包目标是ejb:ejb。

        具有ejb打包类型的项目需要一个maven ejb插件来执行生命周期目标。Maven提供对EJB2和EJB3的支持。如果未指定版本，则使用默认版本2。

    7. rar

        Resource adapter 资源适配器（或rar）是一个存档文件，它是将资源适配器部署到应用程序服务器的有效格式。基本上，它是一个将Java应用程序连接到企业信息系统（Enterprise information system EIS）的系统级驱动程序。

        以下是资源适配器的打包类型声明：

        `<package>rar</package>`

        每个资源适配器归档文件由两部分组成：包含源代码的jar文件和充当部署描述符的ra.xml。

        同样，生命周期阶段与jar或war打包相同，只有一个例外：打包阶段执行rar目标，该目标由一个maven-rar-plugin组成，用于打包归档文件。

2. 其他包装类型

    到目前为止，我们已经研究了Maven提供的各种默认打包类型。现在，让我们假设我们希望我们的项目生成一个带有.zip扩展名的工件。在这种情况下，默认的包装类型对我们没有帮助。

    Maven还通过插件提供了更多的打包类型。借助这些插件，我们可以定义自定义打包类型及其构建生命周期。其中一些类型包括：

    - msi
    - rpm
    - tar
    - tar.bz2
    - tar.gz
    - tbz
    - zip

    要定义自定义类型，我们必须定义其包装类型及其生命周期中的阶段。为此，在src/main/resources/META-INF/plush目录下创建components.xml文件：

    ```xml
    <component>
    <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
    <role-hint>zip</role-hint>
    <implementation>org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping</implementation>
    <configuration>
        <phases>
            <process-resources>org.apache.maven.plugins:maven-resources-plugin:resources</process-resources>
            <package>com.baeldung.maven.plugins:maven-zip-plugin:zip</package>
            <install>org.apache.maven.plugins:maven-install-plugin:install</install>
            <deploy>org.apache.maven.plugins:maven-deploy-plugin:deploy</deploy>
        </phases>
    </configuration>
    </component>
    ```

    到目前为止，Maven对我们的新包装类型及其生命周期一无所知。为了使其可见，让我们在项目的pom文件中添加插件，并将扩展名设置为true：

    ```xml
    <plugins>
        <plugin>
            <groupId>com.baeldung.maven.plugins</groupId>
            <artifactId>maven-zip-plugin</artifactId>
            <extensions>true</extensions>
        </plugin>
    </plugins>
    ```

    现在，该项目将可用于扫描，系统也将查看插件和components.xml文件。

    除了所有这些类型之外，Maven还通过外部项目和插件提供了许多其他打包类型。例如，nar（native archive）、swf和swc是生成Adobe Flash和Flex内容的项目的打包类型。对于这样的项目，我们需要一个定义自定义打包的插件和一个包含该插件的存储库。

#### JAR和WAR打包的区别

在本快速教程中，我们将重点讨论Java中JAR和WAR打包之间的差异。

首先，我们将分别定义每个打包选项。之后，我们将总结他们的差异。

1. JAR包

    简单地说，JAR或Java Archive是一种包文件格式。JAR文件具有.JAR扩展名，可能包含库libraries、资源resources和元数据metadata文件。

    本质上，它是一个压缩文件，包含.class文件的压缩版本以及编译的Java库和应用程序的资源。

    例如，这里有一个简单的JAR文件结构：

    ```txt
    META-INF/
        MANIFEST.MF
    com/
        baeldung/
            MyApplication.class
    ```

    [META-INF/MANIFEST.MF](https://www.baeldung.com/java-jar-executable-manifest-main-class)文件可能包含有关存档中存储的文件的其他元数据。

    我们可以使用JAR命令或Maven等工具[创建JAR文件](https://www.baeldung.com/java-create-jar)。

2. WAR包

    WAR代表Web应用程序档案或Web应用程序资源。这些归档文件具有.war扩展名，用于打包我们可以部署在任何Servlet/JSP容器上的web应用程序。

    下面是典型WAR文件结构的布局示例：

    ```txt
    META-INF/
        MANIFEST.MF
    WEB-INF/
        web.xml
        jsp/
            helloWorld.jsp
        classes/
            static/
            templates/
            application.properties
        lib/
            // *.jar files as libs
    ```

    在内部，它有一个META-INF目录，在MANIFEST.MF中保存有关web存档的有用信息。META-INF目录是私有的，无法从外部访问。

    另一方面，它还包含包含所有静态WEB资源的WEB-INF公共目录，包括HTML页面、图像和JS文件。此外，它还包含web.xml文件、servlet类和库。

    我们可以使用与构建JAR时相同的工具和命令来构建.war存档。

3. 关键区别

那么，这两种归档类型之间的关键区别是什么？

第一个也是最明显的区别是文件扩展名。jar具有.jar扩展名，而WAR文件具有.WAR扩展名。

第二个主要区别是它们的目的和作用方式。JAR文件允许我们打包多个文件，以便将其用作库、插件或任何类型的应用程序。另一方面，WAR文件仅用于web应用程序。

档案的结构也不同。我们可以创建具有任何所需结构的JAR。相反，WAR具有预定义的结构，包含WEB-INF和META-INF目录。

最后，如果我们将JAR构建为可执行JAR而不使用其他软件，那么我们可以从命令行运行它。或者，我们可以将其用作library。相反，我们需要一个服务器来执行WAR。

## Relevant Articles

- [x] [Apache Maven Guide](https://www.baeldung.com/maven-guide)
- [x] [Apache Maven Tutorial](https://www.baeldung.com/maven)
- [x] [Apache Maven Standard Directory Layout](https://www.baeldung.com/maven-directory-structure)
- [x] [Where is the Maven Local Repository?](https://www.baeldung.com/maven-local-repository)
- [x] [Maven Goals and Phases](https://www.baeldung.com/maven-goals-phases)
- [x] [Maven Deploy to Nexus](https://www.baeldung.com/maven-deploy-nexus)
- [x] [Maven Release to Nexus](https://www.baeldung.com/maven-release-nexus)
- [x] [Install local jar with Maven](https://www.baeldung.com/install-local-jar-with-maven)
- [x] [Setting the Java Version in Maven](https://www.baeldung.com/maven-java-version)
- [x] [Maven Packaging Types](https://www.baeldung.com/maven-packaging-types)
- [x] [Differences Between JAR and WAR Packaging](https://www.baeldung.com/java-jar-war-packaging)
