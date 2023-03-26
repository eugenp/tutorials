# Apache Maven指南

## Maven Basics

- [Apache Maven教程](maven-simple/README.md)
- [Apache Maven标准目录布局](/README-zh.md#apache-maven标准目录布局)
- [Maven本地仓库在哪里](#maven本地仓库在哪里)
- [Maven目标和阶段](#maven目标和阶段)
- [Maven发布到Nexus](#maven发布到nexus)
- [Maven部署到Nexus](#maven部署到nexus)
- [用Maven安装本地jar](#用maven安装本地jar)
- [Maven Wrapper快速指南](../spring-boot-modules/spring-boot-artifacts/README.md)

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
- [参考pom.xml中的环境变量](#参考pomxml中的环境变量)
- 在Eclipse中为Maven构建的JDK配置
- [JAVA_HOME应指向JDK而非JRE](#maven-错误-java_home-应指向-jdk-而不是-jre)
- [Maven中的settings.xml文件](#maven中的settingsxml文件)
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

### Maven 错误 "JAVA_HOME 应指向 JDK 而不是 JRE"

1. 简介

    在本教程中，我们将讨论Maven在配置错误时抛出的一个异常。JAVA_HOME应指向JDK，而非JRE。

    Maven是构建代码的强大工具。我们将深入了解这个错误发生的原因，并将看到如何解决这个问题。

2. JAVA_HOME问题

    安装Maven后，我们必须设置JAVA_HOME环境变量，以便该工具知道在哪里找到要执行的JDK命令。Maven的目标是针对项目的源代码运行适当的Java命令。

    例如，最常见的情况是通过执行javac命令来编译代码。

    如果JAVA_HOME没有指向有效的JDK安装，Maven会在每次执行时抛出一个错误。

    ```shell
    mvn -version
    # Output... 
    The JAVA_HOME environment variable is not defined correctly
    This environment variable is needed to run this program
    NB: JAVA_HOME should point to a JDK, not a JRE
    ```

3. JDK或JRE

    Maven是如何验证JAVA_HOME路径的？

    在运行任何目标之前，Maven会检查JAVA_HOME指定的路径中是否存在java命令，或者通过询问操作系统是否有默认的JDK安装。如果找不到该可执行文件，Maven就会以错误结束。

    以下是Linux下的mvn可执行文件检查（Apache Maven v3.5.4）。

    ```shell
    if [ -z "$JAVA_HOME" ] ; then
        JAVACMD=`which java`
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
     
    if [ ! -x "$JAVACMD" ] ; then
        echo "The JAVA_HOME environment variable is not defined correctly" >&2
        echo "This environment variable is needed to run this program" >&2
        echo "NB: JAVA_HOME should point to a JDK not a JRE" >&2
        exit 1
    fi
    ```

    这个检查乍看之下似乎很合理，但我们必须考虑到 JDK 和 JRE 都有一个 bin 文件夹，并且都包含一个可执行的 java 文件。

    因此，有可能将JAVA_HOME配置为指向JRE的安装，从而隐藏了这个特殊的错误，并在以后的阶段造成问题。虽然JRE的主要目的是运行Java代码，但JDK也可以进行编译和调试，以及其他重要的区别。

    由于这个原因，mvn编译命令会失败--正如我们将在下一小节看到的。

    1. 由于JRE而不是JDK导致的编译失败

        像通常的默认设置一样，如果我们有一个 "standard" 配置，它们会非常有帮助。

        例如，如果我们在Ubuntu 18.04系统上安装了Java 11，但没有设置JAVA_HOME环境变量，Maven还是会很高兴地找到我们的JDK，并将其用于不同的目标，包括编译。

        但如果我们设法在系统上设置了一个非标准的配置（更不用说弄得一团糟），Maven的帮助性就不再足够了。它甚至会产生误导。

        让我们假设我们在Ubuntu 18.04上有以下配置。

        - JDK 11
        - JRE 8
        - JAVA_HOME设置为JRE 8的安装路径
        如果我们做基本的检查。

        `mvn --version`

        我们会得到这样一个有意义的输出。

        ```log
        Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T18:41:47Z)
        Maven home: /opt/apache-maven-3.6.0
        Java version: 1.8.0_191, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
        Default locale: en, platform encoding: UTF-8
        OS name: "linux", version: "4.15.0-42-generic", arch: "amd64", family: "unix"
        ```

        让我们看看如果我们尝试编译一个项目会发生什么。

        `mvn clean compile`

        现在，我们得到一个错误。

        ```log
        [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.7.0:compile (default-compile) on project my-app: Compilation failure
        [ERROR] No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
        ```

    2. 在项目层面上修复编译错误

        与Maven的许多其他情况一样，建议在全系统范围内进行有意义的设置--在本例中，我们将按照第5节所述改变JAVA_HOME变量的值，使其指向JDK而不是JRE。

        然而，如果我们由于某种原因不能设置默认值，我们仍然可以在项目层面上覆盖这些设置。让我们看看如何做到这一点。

        首先，我们打开项目的pom.xml，进入build/pluginManagement/plugins部分，看一下maven-compiler-plugin的条目。

        ```xml
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.7.0</version>
        </plugin>
        ```

        然后，我们给它添加一个配置，让它使用一个自定义的可执行文件，并跳过搜索JAVA_HOME/bin目录中的javac。

        ```xml
        <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.7.0</version>
            <configuration>
                <fork>true</fork>
                <executable>/usr/lib/jvm/java-11-openjdk-amd64/bin/javac</executable>
            </configuration>
        </plugin>
        ```

        在Unix系统上，这个可执行文件应该是一个有足够权限的脚本。在Windows系统上，它应该是一个.exe文件。

        接下来，我们将尝试再次编译该项目。

        现在，构建--包括使用maven-compiler-plugin进行的编译阶段--已经成功。

4. 检查JAVA_HOME配置

    检查JAVA_HOME是否指向一个实际的JDK非常简单。我们可以在终端中打印其内容，也可以运行下面的一个shell命令。

    1. 在 Linux 上检查 JAVA_HOME

        只需打开一个终端并输入

        `> $JAVA_HOME/bin/javac -version`

        如果 JAVA_HOME 指向一个 JDK，输出结果应该是这样的

        `> javac 1.X.0_XX`

        如果 JAVA_HOME 没有指向某个 JDK，操作系统将抛出一条错误信息。

        `> bash: /bin/javac: No such file or directory`

    2. 在 Windows 上检查 JAVA_HOME

        打开一个命令提示符，并输入

        `%JAVA_HOME%\bin\javac -version`

        如果 JAVA_HOME 指向一个 JDK，输出结果应该是这样的。

        `> javac 1.X.0_XX`

        如果 JAVA_HOME 没有指向某个 JDK，操作系统将抛出一条错误信息。

        `> The system cannot find the path specified.`

5. 如何解决这个问题

    首先，我们需要知道在哪里可以找到我们的 JDK。

    - 如果我们使用软件包安装程序安装了我们的 JDK 分发版，我们应该能够使用操作系统搜索工具找到该路径
    - 如果是可移植的发行版，让我们检查一下我们提取它的文件夹。
    一旦我们知道了JDK的路径，我们就可以设置JAVA_HOME环境变量，使用[适合我们特定操作系统的结果](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux)。

6. 总结

    在这个简短的教程中，我们讨论了 "JAVA_HOME应指向JDK而非JRE" 的Maven错误，并检查了其根本原因。

    最后，我们讨论了如何检查你的JAVA_HOME环境变量，以及如何确保它指向JDK。

### Maven中的settings.xml文件

1. 概述

    使用Maven时，我们将大部分项目的特定配置放在pom.xml中。

    Maven提供了一个设置文件settings.xml，它允许我们指定它将使用哪些本地和远程仓库。我们还可以用它来存储我们不希望在源代码中出现的设置，如凭证。

    在本教程中，我们将学习如何使用settings.xml。我们将看一下代理、镜像和配置文件。我们还将讨论如何确定适用于我们项目的当前设置。

    [Maven属性的默认值](https://www.baeldung.com/maven-properties-defaults)

    在这篇短文中，我们将了解如何配置Maven属性默认值，以及如何使用它们。

    [Maven中的附加源码目录](https://www.baeldung.com/maven-add-src-directories)

    了解如何在Maven中配置额外的源码目录。

    [Maven打包类型](https://www.baeldung.com/maven-packaging-types)

    在这篇文章中，我们将探讨Maven中的不同打包类型。

2. 配置

    settings.xml文件可以配置[Maven](https://www.baeldung.com/maven)的安装。它类似于pom.xml文件，但可以全局或按用户定义。

    ```xml
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
        <localRepository/>
        <interactiveMode/>
        <offline/>
        <pluginGroups/>
        <servers/>
        <mirrors/>
        <proxies/>
        <profiles/>
        <activeProfiles/>
    </settings>
    ```

    1. 简单的值

        一些顶层的配置元素包含简单的值。

        ```xml
        <settings>
            <localRepository>${user.home}/.m2/repository</localRepository>
            <interactiveMode>true</interactiveMode>
            <offline>false</offline>
        </settings>
        ```

        localRepository 元素指向系统的本地版本库的路径。[本地存储库](https://www.baeldung.com/maven-local-repository)是我们项目的所有依赖关系被缓存的地方。默认是使用用户的主目录。然而，我们可以改变它，让所有登录的用户从一个共同的本地资源库构建。

        interactiveMode标志定义了我们是否允许Maven与用户互动，征求用户的意见。该标志的默认值为 "true"。

        离线标志决定了构建系统是否可以在离线模式下运行。默认为false；但在构建服务器无法连接到远程仓库的情况下，我们可以将其切换为true。

    2. 插件组

        pluginGroups元素包含一个指定groupId的子元素列表。groupId是创建特定Maven工件的组织的唯一标识符。

        ```xml
        <settings>
            <pluginGroups>
                <pluginGroup>org.apache.tomcat.maven</pluginGroup>
            </pluginGroups>
        </settings>
        ```

        当使用一个没有在命令行中提供groupId的插件时，Maven会搜索插件组的列表。该列表默认包含org.apache.maven.plugins和org.codehaus.mojo组。

        上面定义的settings.xml文件允许我们执行截短的Tomcat插件命令。

        mvn tomcat7:help
        mvn tomcat7:deploy
        mvn tomcat7:run

    3. 代理

        我们可以为Maven的部分或全部HTTP请求配置一个代理。代理元素允许一个子代理元素的列表，但每次只能有一个代理处于活动状态。

        ```xml
        <settings>
            <proxies>
                <proxy>
                    <id>baeldung-proxy</id>
                    <active>true</active>
                    <protocol>http</protocol>
                    <host>baeldung.proxy.com</host>
                    <port>8080</port>
                    <username>demo-user</username>
                    <password>dummy-password</password>
                    <nonProxyHosts>*.baeldung.com|*.apache.org</nonProxyHosts>
                </proxy>
            </proxies>
        </settings>
        ```

        我们通过active标志来定义当前活动的代理。然后通过nonProxyHosts元素，我们指定哪些主机不被代理。使用的分隔符取决于具体的代理服务器。最常见的定界符是管道和逗号。

    4. 镜像

        Repositories可以在项目的pom.xml里面声明。这意味着共享项目代码的开发者可以得到正确的版本库设置。

        我们可以在以下情况下使用镜像，即我们想为某个部分定义另一个镜像。

        在我们想为某个特定的版本库定义一个替代镜像的情况下，我们可以使用镜像。这将覆盖pom.xml中的内容。

        例如，我们可以通过镜像所有版本库的请求来强制Maven使用一个版本库。

        ```xml
        <settings>
            <mirrors>
                <mirror>
                    <id>internal-baeldung-repository</id>
                    <name>Baeldung Internal Repo</name>
                    <url>https://baeldung.com/repo/maven2/</url>
                    <mirrorOf>*</mirrorOf>
                </mirror>
            </mirrors>
        </settings>
        ```

        我们可以为一个给定的版本库只定义一个镜像，Maven会选择第一个匹配的。通常情况下，我们应该使用通过CDN分发到世界各地的官方版本库。

    5. 服务器

        在项目pom.xml中定义版本库是个好做法。然而，我们不应该把安全设置，如证书，放入我们的源代码库与pom.xml。相反，我们在settings.xml文件中定义这些安全信息。

        ```xml
        <settings>
            <servers>
                <server>
                    <id>internal-baeldung-repository</id>
                    <username>demo-user</username>
                    <password>dummy-password</password>
                    <privateKey>${user.home}/.ssh/bael_key</privateKey>
                    <passphrase>dummy-passphrase</passphrase>
                    <filePermissions>664</filePermissions>
                    <directoryPermissions>775</directoryPermissions>
                    <configuration></configuration>
                </server>
            </servers>
        </settings>
        ```

        我们应该注意，settings.xml中服务器的ID需要与pom.xml中提到的版本库的ID元素相匹配。XML还允许我们使用占位符来从环境变量中提取凭证。

3. 配置文件

    profiles元素使我们能够创建多个profile子元素，以其ID子元素来区分。settings.xml中的profile元素是pom.xml中相同元素的一个截断版本。

    它只能包含四个子元素：Activation、repositories、pluginRepositories 和 properties。这些元素将构建系统作为一个整体进行配置，而不是任何特定的项目。

    值得注意的是，settings.xml中的激活配置文件的值将覆盖pom.xml或profiles.xml文件中的任何相等的配置文件值。配置文件是通过ID来匹配的。

    1. 激活

        我们可以使用配置文件只在特定情况下修改某些值。我们可以使用激活元素来指定这些情况。因此，当所有指定的标准被满足时，配置文件的激活就会发生。

        ```xml
        <settings>
            <profiles>
                <profile>
                    <id>baeldung-test</id>
                    <activation>
                        <activeByDefault>false</activeByDefault>
                        <jdk>1.8</jdk>
                        <os>
                            <name>Windows 10</name>
                            <family>Windows</family>
                            <arch>amd64</arch>
                            <version>10.0</version>
                        </os>
                        <property>
                            <name>mavenVersion</name>
                            <value>3.0.7</value>
                        </property>
                        <file>
                            <exists>${basedir}/activation-file.properties</exists>
                            <missing>${basedir}/deactivation-file.properties</missing>
                        </file>
                    </activation>
                </profile>
            </profiles>
        </settings>
        ```

        有四种可能的激活器，并非所有的激活器都需要被指定：

        - jdk：根据指定的JDK版本激活（支持范围）。
        - os：根据操作系统属性激活
        - property：如果Maven检测到一个特定的属性值，则激活配置文件。
        - file：如果给定的文件名存在或丢失，则激活配置文件。
        为了检查哪个配置文件会激活某个构建，我们可以使用Maven帮助插件。

        `mvn help:active-profiles`

        输出结果将显示当前某个项目的活跃配置文件。

        ```log
        [INFO] --- maven-help-plugin:3.2.0:active-profiles (default-cli) @ core-java-streams-3 ---
        [INFO]
        Active Profiles for Project 'com.baeldung.core-java-modules:core-java-streams-3:jar:0.1.0-SNAPSHOT':
        The following profiles are active:
        - baeldung-test (source: com.baeldung.core-java-modules:core-java-streams-3:0.1.0-SNAPSHOT)
        ```

    2. 属性

        Maven属性可以被认为是某一数值的命名占位符。这些值可以在pom.xml文件中用${property_name}符号来访问。

        ```xml
        <settings>
            <profiles>
                <profile>
                    <id>baeldung-test</id>
                    <properties>
                        <user.project.folder>${user.home}/baeldung-tutorials</user.project.folder>
                    </properties>
                </profile>
            </profiles>
        </settings>
        ```

        在 pom.xml 文件中，有四种不同类型的属性可用。

        - 使用 env 前缀的属性会返回一个环境变量值，例如 ${env.PATH}。
        - 使用 project 前缀的属性会返回 pom.xml 的项目元素中设置的属性值，例如 ${project.version}。
        - 使用settings前缀的属性会返回settings.xml中相应元素的值，例如${settings.localRepository}。
        - 我们可以直接引用Java中通过System.getProperties方法获得的所有属性，例如${java.home}。
        - 我们可以使用不含前缀的属性元素中设置的属性，比如${junit.version}。

    3. 资源库

        远程仓库包含工件的集合，Maven用它来填充我们的本地仓库。对于特定的工件，可能需要不同的远程仓库。Maven会搜索活动配置文件下启用的资源库。

        ```xml
        <settings>
            <profiles>
                <profile>
                    <id>adobe-public</id>
                    <repositories>
                        <repository>
                            <id>adobe-public-releases</id>
                            <name>Adobe Public Repository</name>
                            <url>https://repo.adobe.com/nexus/content/groups/public</url>
                            <releases>
                                <enabled>true</enabled>
                                <updatePolicy>never</updatePolicy>
                            </releases>
                            <snapshots>
                                <enabled>false</enabled>
                            </snapshots>
                        </repository>
                    </repositories>
                </profile>
            </profiles>
        </settings>
        ```

        我们可以使用存储库元素，只启用特定存储库的工件的发布或快照版本。

    4. 插件存储库

        Maven有两种标准类型的工件(artifacts)，即依赖性(dependencies)和插件(plugins)。由于Maven插件是一种特殊类型的工件，我们可以将插件仓库与其他仓库分开。

        ```xml
        <settings>
            <profiles>
                <profile>
                    <id>adobe-public</id>
                    <pluginRepositories>
                    <pluginRepository>
                        <id>adobe-public-releases</id>
                        <name>Adobe Public Repository</name>
                        <url>https://repo.adobe.com/nexus/content/groups/public</url>
                        <releases>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                        <snapshots>
                            <enabled>false</enabled>
                        </snapshots>
                        </pluginRepository>
                    </pluginRepositories>
                </profile>
            </profiles>
        </settings>
        ```

        值得注意的是，pluginRepositories元素的结构与repositories元素非常相似。

    5. 活动配置文件

        activeProfiles元素包含引用特定配置文件ID的子元素。Maven会自动激活这里引用的任何配置文件。

        ```xml
        <settings>
            <activeProfiles>
                <activeProfile>baeldung-test</activeProfile>
                <activeProfile>adobe-public</activeProfile>
            </activeProfiles>
        </settings>
        ```

        在这个例子中，每一次调用mvn都是在命令行中加入`-P baeldung-test,adobe-public`的情况下运行。

4. 设置级别

    settings.xml文件通常可以在几个地方找到。

    - Mavens主目录下的全局设置：${maven.home}/conf/settings.xml
    - 用户主目录下的用户设置：${user.home}/.m2/settings.xml
    如果两个文件都存在，它们的内容会被合并。来自用户设置的配置优先考虑。

    1. 确定文件位置

        为了确定全局和用户设置的位置，我们可以使用调试标志运行Maven，在输出中搜索 "settings"。

        ```shell
        $ mvn -X clean | grep "settings"
        [DEBUG] Reading global settings from C:\Program Files (x86)\Apache\apache-maven-3.6.3\bin\..\conf\settings.xml
        [DEBUG] Reading user settings from C:\Users\Baeldung\.m2\settings.xml
        ```

    2. 确定有效设置

        我们可以使用Maven的帮助插件来找出全局和用户设置的组合内容。

        `mvn help:effective-settings`

        这是以XML格式描述的设置。

        ```xml
        <settings>
            <localRepository>C:\Users\Baeldung\.m2\repository</localRepository>
            <pluginGroups>
                <pluginGroup>org.apache.tomcat.maven</pluginGroup>
                <pluginGroup>org.apache.maven.plugins</pluginGroup>
                <pluginGroup>org.codehaus.mojo</pluginGroup>
            </pluginGroups>
        </settings>
        ```

    3. 覆盖默认位置

        Maven还允许我们通过命令行覆盖全局和用户设置的位置。

        `$ mvn clean --settings c:\user\user-settings.xml --global-settings c:\user\global-settings.xml`

        我们也可以使用同一命令的较短的-s版本。

        `$ mvn clean --s c:\user\user-settings.xml --gs c:\user\global-settings.xml`

5. 总结

    在本文中，我们探讨了Maven的settings.xml文件中可用的配置。

    我们学习了如何配置代理、存储库和配置文件。接下来，我们研究了全局和用户设置文件的区别，以及如何确定哪些文件正在使用。

    最后，我们研究了如何确定所使用的有效设置，以及如何覆盖默认文件位置。

## Maven项目设置

- [Maven配置文件指南](maven-simple/maven-profiles/README.md)
- [使用Maven的多模块项目](maven-simple/parent-project/README.md)
- [使用Maven的多版本JAR文件](../core-java-modules/core-java-9-new-features/README.md)
- [带有Java模块的多模块Maven应用](multimodulemavenproject/README.md)
- [具有多个源代码目录的Maven项目](maven-multi-source/README.md)
- [如何用Maven创建一个可执行的JAR文件](../core-java-modules/core-java-jar/README.md#如何用maven创建一个可执行的jar)
- [用Maven对JS和CSS资产进行最小化处理](../spring-static-resources/README.md#用maven对js和css资产进行最小化处理)
- [Maven打包类型](/README-zh.md#maven打包类型)
- [了解Maven的父POM "相对路径" 标签](maven-parent-pom-resolution/README.md)

## Maven依赖性

- Maven依赖范围
- 查找未使用的Maven依赖项
- 如何解决Maven中工件的版本冲突问题
- 在Maven中使用最新版本的依赖关系
- 处理Maven无效的LOC头错误
- Maven中的可选依赖性
- 用Maven强制更新版本库
- Maven依赖性管理与依赖性标签

## 核心Maven插件

- Maven资源插件
- Maven编译器插件
- Maven验证器插件
- Maven Surefire插件的快速指南
- Maven失效保护插件
- Maven安装插件快速指南
- Maven部署插件
- Maven网站插件
- Maven清洁插件

## Maven Spring

- Maven和Spring
- 使用Maven的Spring
- Spring Maven Repositories
- 使用Maven的Spring安全
- 使用Spring Boot的多模块项目
- 使用自定义父类的Spring Boot依赖性管理
- 使用Maven的Spring BOM
- spring-boot:repackage和Maven包的区别

## Maven测试

- Maven测试
- 用Maven跳过测试
- 用Maven进行集成测试
- 用Maven并行运行JUnit测试
- 用Maven构建Jar并忽略测试结果
- JaCoCo介绍
- 使用Maven Cargo插件进行集成测试
- 用Maven运行单个测试或方法

## 其他Maven插件

- 其他Maven插件
- 如何创建一个Maven插件
- Maven Enforcer 插件
- Animal Sniffer Maven插件简介
- 用Maven复制文件
- 在Maven中生成一个WAR文件

## Relevant Articles

- [x] [Apache Maven Guide](https://www.baeldung.com/maven-guide)
- [x] [Apache Maven Tutorial](https://www.baeldung.com/maven)
- [x] [Where is the Maven Local Repository?](https://www.baeldung.com/maven-local-repository)
- [x] [Maven Goals and Phases](https://www.baeldung.com/maven-goals-phases)
- [x] [Maven Deploy to Nexus](https://www.baeldung.com/maven-deploy-nexus)
- [x] [Maven Release to Nexus](https://www.baeldung.com/maven-release-nexus)
- [x] [Install local jar with Maven](https://www.baeldung.com/install-local-jar-with-maven)
- [x] [Setting the Java Version in Maven](https://www.baeldung.com/maven-java-version)
- [x] [Refer to Environment Variables in pom.xml](https://www.baeldung.com/maven-env-variables)
- [x] [The settings.xml File in Maven](https://www.baeldung.com/maven-settings-xml)
- [x] [Maven Error “JAVA_HOME should point to a JDK not a JRE”](https://www.baeldung.com/maven-java-home-jdk-jre)
