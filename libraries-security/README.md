# Security

This module contains articles about security libraries.

## 使用 Java 完成 SSH 连接

1.简介

SSH，也称为 Secure Shell 或 Secure Socket Shell，是一种网络协议，允许一台计算机通过不安全的网络安全地连接到另一台计算机。在本教程中，我们将展示如何使用 JSch 和 Apache MINA SSHD 库使用 Java 建立与远程 SSH 服务器的连接。

在我们的示例中，我们将首先打开 SSH 连接，然后执行一个命令，读取输出并将其写入控制台，最后关闭 SSH 连接。我们将尽可能简化示例代码。

2.JSch

JSch 是 SSH2 的 Java 实现，它允许我们连接到 SSH 服务器并使用端口转发、X11 转发和文件传输。此外，它在 BSD 风格许可下获得许可，并为我们提供了一种与 Java 建立 SSH 连接的简单方法。

首先，让我们将 [JSch Maven](https://search.maven.org/search?q=g:com.jcraft%20AND%20a:jsch) 依赖添加到我们的 pom.xml 文件中。

2.1。 执行

要使用 JSch 建立 SSH 连接，我们需要用户名、密码、主机 URL 和 SSH 端口。 默认的 SSH 端口是 22，但我们可能会将服务器配置为使用其他端口进行 SSH 连接，参见 ssh.jsch.JschDemo.java 的 listFolderStructure 方法。

正如我们在代码中看到的，我们首先创建一个客户端会话并将其配置为连接到我们的 SSH 服务器。然后，我们创建一个客户端通道，用于与我们提供通道类型的 SSH 服务器通信——在本例中为 exec，这意味着我们将向服务器传递 shell 命令。

此外，我们应该为将写入服务器响应的通道设置输出流。我们使用 channel.connect() 方法建立连接后，命令传递，接收到的响应写入控制台。

让我们看看如何使用 JSch 提供的不同配置参数：

- StrictHostKeyChecking - 它指示应用程序是否将检查是否可以在已知主机中找到主机公钥。此外，可用的参数值为 ask、yes 和 no，其中 ask 是默认值。如果我们将此属性设置为 yes，JSch 将永远不会自动将主机密钥添加到 known_hosts 文件，并且它将拒绝连接到主机密钥已更改的主机。这会强制用户手动添加所有新主机。如果我们将其设置为 no，JSch 会自动将新的主机密钥添加到已知主机列表中
- compression.s2c – 指定是否对从服务器到客户端应用程序的数据流使用压缩。可用值为 zlib 和 none，其中第二个是默认值
- compression.c2s – 指定是否对客户端-服务器方向的数据流使用压缩。可用值为 zlib 和 none，其中第二个是默认值
与服务器通信结束后关闭会话和 SFTP 通道非常重要，以避免内存泄漏。

3.Apache MINA SSHD

[Apache MINA SSHD](https://mina.apache.org/sshd-project/) 为基于Java的应用程序提供SSH支持。此库基于Apache MINA，这是一个可扩展的高性能异步IO库。

让我们添加[Apache Mina SSHD Maven](https://search.maven.org/search?q=a:apache-sshd)依赖项。

3.1.实施

让我们看看使用Apache MINA SSHD连接到SSH服务器的代码示例：参见 ssh.apachesshd.SshdDemo.java 的 listFolderStructure 方法。

当使用Apache MINA SSHD时，我们的事件序列与JSch非常相似。首先，我们使用SshClient类实例建立到SSH服务器的连接。如果我们用SshClient初始化它。setupDefaultClient（），我们将能够使用具有适合大多数用例的默认配置的实例。这包括密码、压缩、MAC、密钥交换和签名。

之后，我们将创建ClientChannel并将ByteArrayOutputStream附加到它，以便将其用作响应流。如我们所见，SSHD要求为每个操作定义超时。它还允许我们定义在使用Channel传递命令后等待服务器响应的时间。waitFor（）方法。

需要注意的是，SSHD将把完整的控制台输出写入响应流。JSch将仅使用命令执行结果来执行此操作。

关于Apache Mina SSHD的完整文档可在项目的官方[GitHub存储库](https://github.com/apache/mina-sshd/tree/master/docs)中获得。

### Relevant Articles

- [Guide to ScribeJava](https://www.baeldung.com/scribejava)
- [Guide to Passay](https://www.baeldung.com/java-passay)
- [Guide to Google Tink](https://www.baeldung.com/google-tink)
- [Introduction to BouncyCastle with Java](https://www.baeldung.com/java-bouncy-castle)
- [Intro to Jasypt](https://www.baeldung.com/jasypt)
- [Digital Signatures in Java](https://www.baeldung.com/java-digital-signature)
- [How to Read PEM File to Get Public and Private Keys](https://www.baeldung.com/java-read-pem-file-keys)
- [x] [SSH Connection With Java](https://www.baeldung.com/java-ssh-connection)
