# Security

本模块包含有关安全库的文章。

## 如何读取PEM文件以获取公钥和私钥

1.概述

在公钥加密（也称为非对称加密 [asymmetric cryptography](https://www.baeldung.com/cs/symmetric-vs-asymmetric-cryptography) ）中，加密机制依赖于两个相关的密钥，即公钥和私钥。公钥用于加密消息，而只有私钥的所有者才能解密消息。

在本教程中，我们将学习如何从PEM文件读取公钥和私钥。

首先，我们将学习有关公钥加密的一些重要概念。然后，我们将学习如何使用纯Java读取PEM文件。

最后，我们将探索 [BouncyCastle](https://www.baeldung.com/java-bouncy-castle) 库作为替代方法。

2.概念

在开始之前，让我们讨论一些关键概念。

X.509是定义公钥证书格式的标准。因此，此格式描述了公钥以及其他信息。

DER是存储数据的最流行的编码格式，如X.509证书和文件中的PKCS8私钥。这是一种二进制编码，生成的内容不能用文本编辑器查看。

PKCS8是存储私钥信息的标准语法。可以选择使用对称算法加密私钥。

该标准不仅可以处理RSA私钥，还可以处理其他算法。PKCS8私钥通常通过PEM编码格式交换。

PEM是DER证书的base-64编码机制。PEM还可以对其他类型的数据进行编码，例如公钥/私钥和证书请求。

PEM文件还包含描述编码数据类型的页眉和页脚：

-----BEGIN PUBLIC KEY-----
...Base64 encoding of the DER encoded certificate...
-----END PUBLIC KEY-----

3.使用纯Java

3.1.从文件读取PEM数据

让我们从读取PEM文件开始，并将其内容存储到字符串中：

`String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());`

3.2.从PEM字符串获取公钥

现在，我们将构建一个实用程序方法，从PEM编码的字符串中获取公钥：

-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjtGIk8SxD+OEiBpP2/T
JUAF0upwuKGMk6wH8Rwov88VvzJrVm2NCticTk5FUg+UG5r8JArrV4tJPRHQyvqK
wF4NiksuvOjv3HyIf4oaOhZjT8hDne1Bfv+cFqZJ61Gk0MjANh/T5q9vxER/7TdU
NHKpoRV+NVlKN5bEU/NQ5FQjVXicfswxh6Y6fl2PIFqT2CfjD+FkBPU1iT9qyJYH
A38IRvwNtcitFgCeZwdGPoxiPPh1WHY8VxpUVBv/2JsUtrB/rAIbGqZoxAIWvijJ
Pe9o1TY3VlOzk9ASZ1AeatvOir+iDVJ5OpKmLnzc46QgGPUsjIyo6Sje9dxpGtoG
QQIDAQAB
-----END PUBLIC KEY-----

假设我们收到一个文件作为参数：

```java
public static RSAPublicKey readPublicKey(File file) throws Exception {
    String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

    String publicKeyPEM = key
      .replace("-----BEGIN PUBLIC KEY-----", "")
      .replaceAll(System.lineSeparator(), "")
      .replace("-----END PUBLIC KEY-----", "");

    byte[] encoded = Base64.decodeBase64(publicKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    return (RSAPublicKey) keyFactory.generatePublic(keySpec);
}
```

正如我们所看到的，首先我们需要删除页眉、页脚和新行。然后，我们需要将Base64编码的字符串解码为相应的二进制格式。

接下来，我们需要将结果加载到能够处理公钥材料的密钥规范类中。在本例中，我们将使用X509EncodedKeySpec类。

最后，我们可以使用KeyFactory类从规范生成公钥对象。

3.3.从PEM字符串获取私钥

现在我们知道了如何读取公钥，读取私钥的算法非常相似。

我们将使用PKCS8格式的PEM编码私钥。让我们看看页眉和页脚是什么样子的：

-----BEGIN PRIVATE KEY-----
...Base64 encoded key...
-----END PRIVATE KEY-----

正如我们之前所了解的，我们需要一个能够处理PKCS8关键材料的类。PKCS8EncodedKeySpec类填充该角色。

让我们看看算法：

```java
public RSAPrivateKey readPrivateKey(File file) throws Exception {
    String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

    String privateKeyPEM = key
      .replace("-----BEGIN PRIVATE KEY-----", "")
      .replaceAll(System.lineSeparator(), "")
      .replace("-----END PRIVATE KEY-----", "");

    byte[] encoded = Base64.decodeBase64(privateKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
}
```

4.使用BouncyCastle图书馆

4.1.读取公钥

我们将研究BouncyCastle库，看看如何将其用作纯Java实现的替代品。

让我们获取公钥：

```java
public RSAPublicKey readPublicKey(File file) throws Exception {
    KeyFactory factory = KeyFactory.getInstance("RSA");

    try (FileReader keyReader = new FileReader(file);
      PemReader pemReader = new PemReader(keyReader)) {

        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
        return (RSAPublicKey) factory.generatePublic(pubKeySpec);
    }
}
```

在使用BouncyCastle时，我们需要注意几个重要的类：

- PemReader–将Reader作为参数并解析其内容。它删除不必要的标头，并将底层Base64 PEM数据解码为二进制格式。
- PemObject–存储PemReader生成的结果

让我们看看另一种将Java类（X509EncodedKeySpec、KeyFactory）封装到BouncyCastle自己的类（JcaPEMKeyConverter）中的方法：

```java
public RSAPublicKey readPublicKeySecondApproach(File file) throws IOException {
    try (FileReader keyReader = new FileReader(file)) {
        PEMParser pemParser = new PEMParser(keyReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
        return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
    }
}
```

4.2.读取私钥

现在我们将看到两个与上面显示的非常相似的示例。

在第一个示例中，我们只需要将X509EncodedKeySpec类替换为PKCS8EncodedKeySpec类，并返回一个RSAPrivateKey对象，而不是一个RSAPublicKey：

```java
public RSAPrivateKey readPrivateKey(File file) throws Exception {
    KeyFactory factory = KeyFactory.getInstance("RSA");

    try (FileReader keyReader = new FileReader(file);
      PemReader pemReader = new PemReader(keyReader)) {

        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
    }
}
```

现在，让我们稍微修改上一节中的第二种方法，以便读取私钥：

```java
public RSAPrivateKey readPrivateKeySecondApproach(File file) throws IOException {
    try (FileReader keyReader = new FileReader(file)) {

        PEMParser pemParser = new PEMParser(keyReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());

        return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
    }
}
```

正如我们所看到的，我们只是用PrivateKeyInfo替换了SubjectPublicKeyInfo，用RSAPrivateKey替换了RSAPublicKey。

4.3.优势

BouncyCastle库有几个优点。

一个优点是，我们不需要手动跳过或删除页眉和页脚。另一个原因是我们也不负责Base64解码。因此，我们可以用BouncyCastle编写较少出错的代码。
此外，BouncyCastle库也支持PKCS1格式。尽管PKCS1也是一种用于存储加密密钥（仅RSA密钥）的流行格式，但Java本身并不支持它。

5.结论

在本文中，我们学习了如何从PEM文件读取公钥和私钥。

首先，我们研究了关于公钥加密的几个关键概念。然后我们了解了如何使用纯Java读取公钥和私钥。

最后，我们研究了BouncyCastle库，发现它是一个很好的替代品，因为与纯Java实现相比，它提供了一些优势。

[Java](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-security-2) 和 [BouncyCastle](https://github.com/eugenp/tutorials/tree/master/libraries-security) 方法的完整源代码可以在GitHub上找到。

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

> 附：三种CHANNEL
String CHANNEL_EXEC = "exec";
String CHANNEL_SHELL = "shell";
String CHANNEL_SUBSYSTEM = "subsystem";

3.2.1 测试

ssh.ApacheMinaSshdLiveTest.java givenValidCredentials_whenConnectionIsEstablished_thenServerReturnsResponse() 方法报

`org.apache.sshd.common.SshException: DefaultAuthFuture[ssh-connection]: Failed (RuntimeException) to execute: Failed (NoSuchAlgorithmException) to load key from /Users/wangkan/.ssh/id_ed25519: Unsupported key type (ssh-ed25519) in /Users/wangkan/.ssh/id_ed25519
givenValidCredentials_whenConnectionIsEstablished_thenServerReturnsResponse(ApacheMinaSshdLiveTest.java:20)
Caused by: java.security.NoSuchAlgorithmException: Unsupported key type (ssh-ed25519) in /Users/wangkan/.ssh/id_ed25519`

说明：系统 MacOS SSH 对所有 host 启用了证书，且为 OPENSSH PRIVATE KEY， MinaSshd 不支持。

```config
Host *
  AddKeysToAgent yes
  UseKeychain yes
  IdentityFile ~/.ssh/id_ed25519
```

另：ssh.JSchLiveTest.java givenInvalidCredentials_whenConnectionAttemptIsMade_thenServerReturnsErrorResponse() 方法报
`java.lang.AssertionError: Expected exception: java.lang.Exception`

### MINA SSHD client 详细说明

<https://github.com/apache/mina-sshd/blob/master/docs/client-setup.md>

## Relevant Articles

- [Guide to ScribeJava](https://www.baeldung.com/scribejava)
- [Guide to Passay](https://www.baeldung.com/java-passay)
- [Guide to Google Tink](https://www.baeldung.com/google-tink)
- [Introduction to BouncyCastle with Java](https://www.baeldung.com/java-bouncy-castle)
- [Intro to Jasypt](https://www.baeldung.com/jasypt)
- [Digital Signatures in Java](https://www.baeldung.com/java-digital-signature)
- [x] [How to Read PEM File to Get Public and Private Keys](https://www.baeldung.com/java-read-pem-file-keys)
- [x] [SSH Connection With Java](https://www.baeldung.com/java-ssh-connection)
