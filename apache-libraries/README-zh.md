# Apache Libraries

This module contains articles about various Apache libraries and utilities.

## Java和Zookeeper的入门

1. 概述

    [Apache ZooKeeper](https://zookeeper.apache.org/)是一个分布式协调服务，它可以简化分布式应用程序的开发。它被Apache Hadoop、HBase等[项目](https://cwiki.apache.org/confluence/display/ZOOKEEPER/PoweredBy)用于不同的用例，如领导者选举、配置管理、节点协调、服务器租赁管理等。

    ZooKeeper集群内的节点在一个共享的分层命名空间中存储他们的数据，这与标准的文件系统或树形数据结构类似。

    在这篇文章中，我们将探讨如何使用Apache Zookeeper的Java API来存储、更新和删除存储在ZooKeeper中的信息。

2. 设置

    最新版本的Apache ZooKeeper Java库可以在[这里](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.apache.zookeeper%22%20AND%20a%3A%22zookeeper%22)找到。

    ```xml
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.8.1</version>
    </dependency>
    ```

3. ZooKeeper数据模型 - ZNode

    ZooKeeper有一个分层的命名空间，很像一个分布式文件系统，它在这里存储协调数据，如状态信息、协调信息、位置信息等。这些信息被存储在不同的节点上。

    ZooKeeper树中的每个节点都被称为ZNode。

    每个ZNode都维护任何数据或ACL变化的版本号和时间戳。同时，这也使得ZooKeeper能够验证缓存并协调更新。

4. 安装

    1. 安装

        最新的ZooKeeper版本可以从[这里](https://www.apache.org/dyn/closer.cgi/zookeeper/)下载。在这之前，我们需要确保满足[这里](https://zookeeper.apache.org/doc/r3.8.1/zookeeperAdmin.html#sc_systemReq)描述的系统要求。

    2. 单机模式

        在本文中，我们将以独立模式运行ZooKeeper，因为它需要最小的配置。按照[这里](https://zookeeper.apache.org/doc/r3.8.1/zookeeperStarted.html#sc_InstallingSingleMode)的文档描述的步骤进行。

        注意：在单机模式下，没有复制，所以如果ZooKeeper进程失败，服务就会中断。

5. ZooKeeper CLI实例

    现在我们将使用ZooKeeper命令行界面（CLI）与ZooKeeper互动。

    bin/zkCli.sh -server 127.0.0.1:2181

    上述命令在本地启动一个独立的实例。现在我们来看看如何在ZooKeeper中创建一个ZNode并存储信息。

    ```log
    [zk: localhost:2181(CONNECTED) 0] create /MyFirstZNode ZNodeVal
    Created /FirstZnode
    ```

    我们刚刚在ZooKeeper分层命名空间的根部创建了一个ZNode'MyFirstZNode'，并把'ZNodeVal'写入其中。

    由于我们没有传递任何标志，创建的ZNode将是持久的。

    现在让我们发布一个'get'命令来获取与ZNode相关的数据和元数据。

    ```log
    [zk: localhost:2181(CONNECTED) 1] get /MyFirstZNode
    “ZNodeVal”
    cZxid = 0x7f
    ctime = Sun Feb 18 16:15:47 IST 2018
    mZxid = 0x7f
    mtime = Sun Feb 18 16:15:47 IST 2018
    pZxid = 0x7f
    cversion = 0
    dataVersion = 0
    aclVersion = 0
    ephemeralOwner = 0x0
    dataLength = 22
    numChildren = 0
    ```

    我们可以使用set操作来更新一个现有的ZNode的数据。

    比如说

    `set /MyFirstZNode ZNodeValUpdated`

    这将把MyFirstZNode的数据从ZNodeVal更新到ZNodeValUpdated。

6. ZooKeeper Java API实例

    现在让我们看看Zookeeper Java API，创建一个节点，更新节点并检索一些数据。

    参考：<https://zookeeper.apache.org/doc/r3.8.1/zookeeperProgrammers.html>

    1. Java包

        ZooKeeper的Java绑定主要由两个Java包组成。

        1. org.apache.zookeeper：它定义了ZooKeeper客户端库的主类，以及ZooKeeper事件类型和状态的许多静态定义
        2. org.apache.zookeeper.data：定义了与ZNodes相关的特性，如访问控制列表（ACL）、ID、统计等等。
        还有ZooKeeper的Java APIs用于服务器的实现，如org.apache.zookeeper.server、org.apache.zookeeper.server.quorum和org.apache.zookeeper.server.upgrade。

        不过，它们已经超出了本文的范围。

    2. 连接到一个 ZooKeeper 实例

        现在让我们创建ZKConnection类，它将被用来连接和断开已经运行的ZooKeeper。

        参见：zookeeper.connection/ZKConnection.java

        要使用ZooKeeper服务，应用程序必须首先实例化一个ZooKeeper类的对象，它是ZooKeeper客户端库的主类。

        在connect方法中，我们正在实例化一个ZooKeeper类的实例。此外，我们还注册了一个回调方法来处理来自ZooKeeper的WatchedEvent以接受连接，并相应地使用CountDownLatch的倒计时方法完成连接方法。

        一旦与服务器的连接建立起来，就会给客户端分配一个会话ID。为了保持会话的有效性，客户端应定期向服务器发送心跳信号。

        只要会话ID保持有效，客户端应用程序就可以调用ZooKeeper的API。

    3. 客户端操作

        现在我们将创建一个ZKManager接口，它暴露了不同的操作，如创建一个ZNode并保存一些数据，获取和更新ZNode数据。

        参见：zookeeper.manager/ZKManager.java

        现在让我们来看看上述接口的实现。

        参见：zookeeper.manager/ZKManagerImpl.java

        在上述代码中，connect和disconnect调用被委托给先前创建的ZKConnection类。我们的创建方法被用来在给定的路径上从字节数组数据创建一个ZNode。仅为演示目的，我们保持ACL完全开放。

        一旦创建，ZNode是持久的，当客户端断开连接时不会被删除。

        在我们的getZNodeData方法中，从ZooKeeper中获取ZNode数据的逻辑是非常直接的。最后，在更新方法中，我们要检查ZNode在给定路径上是否存在，如果存在，就去获取它。

        除此之外，为了更新数据，我们首先检查ZNode是否存在，并获取当前的版本。然后，我们用ZNode的路径、数据和当前版本作为参数调用setData方法。只有当传递的版本与最新版本相匹配时，ZooKeeper才会更新数据。

7. 总结

    在开发分布式应用程序时，Apache ZooKeeper作为分布式协调服务发挥了关键作用。特别是对于像存储共享配置、选举主节点等用例。

    ZooKeeper还提供了优雅的基于Java的API，可用于客户端的应用程序代码，与ZooKeeper ZNodes进行无缝通信。

    像往常一样，本教程的所有源代码都可以在[Github](https://github.com/eugenp/tutorials/tree/master/apache-libraries)上找到。

## Relevant Articles

- [Guide to Apache Avro](https://www.baeldung.com/java-apache-avro)
- [Introduction to Apache Beam](https://www.baeldung.com/apache-beam)
- [Intro to Apache BVal](https://www.baeldung.com/apache-bval)
- [Building a Microservice with Apache Meecrowave](https://www.baeldung.com/apache-meecrowave)
- [Intro to Apache OpenNLP](https://www.baeldung.com/apache-open-nlp)
- [Introduction to Apache Pulsar](https://www.baeldung.com/apache-pulsar)
- [x] [Getting Started with Java and Zookeeper](https://www.baeldung.com/java-zookeeper)
- [Introduction to Apache Curator](https://www.baeldung.com/apache-curator)
- [A Quick Guide to Apache Geode](https://www.baeldung.com/apache-geode)
- [Guide To Solr in Java With Apache SolrJ](https://www.baeldung.com/apache-solrj)
