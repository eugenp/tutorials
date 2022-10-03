# Security

本模块包含有关常用安全库介绍的文章。

环境准备：

1.设置无限强度管辖政策文件

标准Java安装在加密功能强度方面受到限制，这是因为政策禁止使用大小超过特定值的密钥，例如AES的密钥大小为128。

为了克服这个限制，我们需要配置无限制权限策略文件。

为了做到这一点，我们首先需要为 JVM 安装 [Java Cryptography Extension（JCE）Unlimited Strength Jurisdiction Policy Files](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html) （安装说明包含在下载中）。然后，我们需要将下载的 jar 压缩文件解压缩到我们选择的目录中，该目录包含两个jar文件：

local_policy.jar
US_export_policy.jar

最后，我们需要查找 {JAVA_HOME}/lib/security 文件夹，并用这里提取的文件替换现有的策略文件。

> 将 JCE JAR 文件复制到以下目录:
`<java-home>/lib/security           [Unix]`
`<java-home>\lib\security           [Windows]`

注意，在Java9中，我们不再需要下载策略文件包，设置加密。保单属性设置为无限制就足够了：

`Security.setProperty("crypto.policy", "unlimited");`

完成后，我们需要检查配置是否正常工作：

```java
int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
System.out.println("Max Key Size for AES : " + maxKeySize);
```

结果：`Max Key Size for AES : 2147483647`

根据getMaxAllowedKeyLength()方法返回的最大密钥大小，我们可以放心地说，无限强度策略文件已正确安装。

如果返回值等于128，则需要确保已将文件安装到运行代码的JVM中。

## Java中的数字签名

1.概述

在本教程中，我们将学习数字签名机制，以及如何使用Java加密体系结构（[JCA](https://docs.oracle.com/en/java/javase/11/security/java-cryptography-architecture-jca-reference-guide.html)）实现它。我们将研究KeyPair、MessageDigest、Cipher、KeyStore、Certificate和Signature JCA API。

我们将首先了解什么是数字签名，如何生成密钥对，以及如何从证书颁发机构（CA）认证公钥。之后，我们将了解如何使用低级和高级JCA API实现数字签名。

2.什么是数字签名？

2.1.数字签名定义

数字签名是一种确保：

- 完整性：消息在传输过程中未被更改
- 真实性：信息的作者确实是他们声称的人
- 不可抵赖性：消息的作者以后不能否认他们是消息的来源

2.2.发送带有数字签名的消息

从技术上讲，数字签名是消息的加密哈希（摘要、校验和）。这意味着我们从消息生成一个散列，并根据所选算法用私钥对其进行加密。

然后发送消息、加密的哈希、相应的公钥和算法。这被归类为带有数字签名的消息。

2.3.接收和检查数字签名

为了检查数字签名，消息接收方从接收到的消息生成一个新的哈希，使用公钥解密接收到的加密哈希，并对其进行比较。如果匹配，则称数字签名已验证。

我们应该注意，我们只加密消息散列，而不是消息本身。换句话说，数字签名并不试图对消息保密。我们的数字签名只能证明消息在传输过程中没有被更改。

当签名被验证时，我们确信只有私钥的所有者才能是消息的作者。

3.数字证书和公钥身份

证书是将身份与给定公钥相关联的文档。证书由称为证书颁发机构（CA）的第三方实体签署。

我们知道，如果我们用发布的公钥解密的哈希与实际的哈希匹配，那么消息就会被签名。然而，我们如何知道公钥真正来自正确的实体？这可以通过使用数字证书来解决。

数字证书包含公钥，并且本身由另一实体签名。该实体的签名本身可以由另一个实体进行验证，依此类推。我们最终得到了我们所称的证书链。每个顶级实体验证下一个实体的公钥。最顶层的实体是自签名的，这意味着他的公钥是由他自己的私钥签名的。

X.509是最常用的证书格式，它以二进制格式（DER）或文本格式（PEM）提供。JCA已经通过X509Certificate类为此提供了一个实现。

4.密钥对管理

由于数字签名使用私钥和公钥，因此我们将分别使用JCA类PrivateKey和PublicKey对消息进行签名和检查。

4.1.获取密钥对

要创建私钥和公钥的密钥对，我们将使用Java密钥工具。

让我们使用genkeypair命令生成密钥对：

```bash
keytool -genkeypair -alias senderKeyPair -keyalg RSA -keysize 2048 \
  -dname "CN=Baeldung" -validity 365 -storetype PKCS12 \
  -keystore sender_keystore.p12 -storepass changeit
```

这将为我们创建一个私钥及其对应的公钥。公钥被包装到一个X.509自签名证书中，该证书又被包装成一个单元素证书链。我们将证书链和私钥存储在密钥库文件sender_Keystore中。p12，我们可以使用KeyStore API处理它。

这里，我们使用了PKCS12密钥存储格式，因为它是Java专有JKS格式的标准和推荐格式。此外，我们应该记住密码和别名，因为我们将在下一小节加载Keystore文件时使用它们。

4.2.加载用于签名的私钥

为了签署消息，我们需要PrivateKey的实例。

使用KeyStore API和以前的KeyStore文件sender_KeyStore。p12，我们可以获取PrivateKey对象：

```java
KeyStore keyStore = KeyStore.getInstance("PKCS12");
keyStore.load(new FileInputStream("sender_keystore.p12"), "changeit");
PrivateKey privateKey = 
  (PrivateKey) keyStore.getKey("senderKeyPair", "changeit");
```

4.3.公开密钥

在发布公钥之前，我们必须首先决定是使用自签名证书还是CA签名证书。

当使用自签名证书时，我们只需要从密钥库文件导出它。我们可以使用exportcert命令执行此操作：

```bash
keytool -exportcert -alias senderKeyPair -storetype PKCS12 \
  -keystore sender_keystore.p12 -file \
  sender_certificate.cer -rfc -storepass changeit
```

否则，如果要使用CA签名的证书，则需要创建证书签名请求（CSR）。我们使用certreq命令执行此操作：

```bash
keytool -certreq -alias senderKeyPair -storetype PKCS12 \
  -keystore sender_keystore.p12 -file -rfc \
  -storepass changeit > sender_certificate.csr
```

CSR文件sender_certificate。然后将csr发送给证书颁发机构进行签名。完成后，我们将收到一个封装在X.509证书中的签名公钥，可以是二进制（DER）格式，也可以是文本（PEM）格式。这里，我们将rfc选项用于PEM格式。

我们从CA sender_certificate收到的公钥。cer，现在已由CA签署，可供客户使用。

4.4.加载公钥进行验证

接收方可以访问公钥，可以使用importcert命令将其加载到密钥库中：

```bash
keytool -importcert -alias receiverKeyPair -storetype PKCS12 \
  -keystore receiver_keystore.p12 -file \
  sender_certificate.cer -rfc -storepass changeit
```

和以前一样，使用KeyStore API，我们可以获得PublicKey实例：

```java
KeyStore keyStore = KeyStore.getInstance("PKCS12");
keyStore.load(new FileInputStream("receiver_keytore.p12"), "changeit");
Certificate certificate = keyStore.getCertificate("receiverKeyPair");
PublicKey publicKey = certificate.getPublicKey();
```

现在，我们在发送方有了一个PrivateKey实例，在接收方有了PublicKey的实例，我们可以开始签名和验证过程了。

**ERROR**: `Exception in thread "main" java.io.FileNotFoundException: sender_keystore.p12 (No such file or directory)`

- 解决：必须在 FileInputStream() 中给出文件访问路径 /path/receiver_keytore.p12。

5.具有MessageDigest和Cipher类的数字签名

正如我们所看到的，数字签名是基于哈希和加密的。

通常，我们使用带有[SHA](https://www.baeldung.com/sha-256-hashing-java)或[MD5](https://www.baeldung.com/java-md5)的MessageDigest类进行散列，使用Cipher类进行加密。

现在，让我们开始实现数字签名机制。

5.1.生成消息哈希

消息可以是字符串、文件或任何其他数据。让我们来看一个简单文件的内容：

`byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));`

现在，使用MessageDigest，让我们使用digest方法生成哈希：

```java
MessageDigest md = MessageDigest.getInstance("SHA-256");
byte[] messageHash = md.digest(messageBytes);
```

这里，我们使用了SHA-256算法，这是最常用的算法。其他替代方案为MD5、SHA-384和SHA-512。

5.2.加密生成的哈希

要加密消息，我们需要算法和私钥。这里我们将使用RSA算法。DSA算法是另一个选项。

让我们创建一个密码实例并对其进行初始化以进行加密。然后，我们将调用doFinal（）方法来加密先前的哈希消息：

```java
Cipher cipher = Cipher.getInstance("RSA");
cipher.init(Cipher.ENCRYPT_MODE, privateKey);
byte[] digitalSignature = cipher.doFinal(messageHash);
```

签名可以保存到文件中，以便以后发送：

`Files.write(Paths.get("digital_signature_1"), digitalSignature);`

此时，消息、数字签名、公钥和算法都被发送，接收方可以使用这些信息来验证消息的完整性。

**ERROR**: `java.security.InvalidKeyException: OAEP cannot be used to sign or verify signatures`

- 解决：当 Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING") 时，标准 JCE 的 init 方法不支持 (throws this error if the mode is DECRYPT_MODE, the key is an RSAPublicKey and the padding type is not PAD_NONE or PAD_PKCS1)，可通过加载三方库provider解决，如 `Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider())`，或者升级 JDK > 8 ？。

5.3.验证签名

当我们收到消息时，我们必须验证其签名。为此，我们解密接收到的加密哈希，并将其与我们对接收到的消息所做的哈希进行比较。

让我们看看收到的数字签名：

`byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("digital_signature_1"));`

为了解密，我们创建了一个密码实例。然后我们调用doFinal方法：

```java
Cipher cipher = Cipher.getInstance("RSA");
cipher.init(Cipher.DECRYPT_MODE, publicKey);
byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
```

接下来，我们从收到的消息生成一个新的消息哈希：

```java
byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

MessageDigest md = MessageDigest.getInstance("SHA-256");
byte[] newMessageHash = md.digest(messageBytes);
```

最后，我们检查新生成的消息哈希是否与解密消息哈希匹配：

`boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);`

在这个例子中，我们使用了文本文件消息。txt来模拟我们想要发送的消息，或者我们收到的消息正文的位置。通常，我们希望在签名的旁边收到我们的消息。

6.使用签名类的数字签名

到目前为止，我们已经使用低级API构建了自己的数字签名验证过程。这有助于我们了解它的工作原理，并允许我们对其进行定制。

然而，JCA已经以Signature类的形式提供了一个专用API。

6.1.签署消息

为了开始签名过程，我们首先创建Signature类的实例。为此，我们需要一个签名算法。然后使用私钥初始化签名：

```java
Signature signature = Signature.getInstance("SHA256withRSA");
signature.initSign(privateKey);
```

我们选择的签名算法，本例中的SHA256withRSA，是哈希算法和加密算法的组合。其他替代方案包括SHA1 withRSA、SHA1 withDSA和MD5 withRSA等。

接下来，我们继续对消息的字节数组进行签名：

```java
byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));
signature.update(messageBytes);
byte[] digitalSignature = signature.sign();
```

我们可以将签名保存到文件中，以便以后传输：

`Files.write(Paths.get("digital_signature_2"), digitalSignature);`

6.2.验证签名

为了验证收到的签名，我们再次创建签名实例：

`Signature signature = Signature.getInstance("SHA256withRSA");`

接下来，我们通过调用initVerify方法初始化Signature对象以进行验证，该方法采用公钥：

signature.initVerify(publicKey);

然后，我们需要通过调用update方法将接收到的消息字节添加到签名对象：

```java
byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));
signature.update(messageBytes);
```

最后，我们可以通过调用verify方法来检查签名：

`boolean isCorrect = signature.verify(receivedSignature);`

7.结论

在本文中，我们首先研究了数字签名的工作原理以及如何为数字证书建立信任。然后，我们使用Java Cryptography Architecture中的MessageDigest、Cipher和signature类实现了数字签名。

我们详细了解了如何使用私钥对数据进行签名，以及如何使用公钥验证签名。

## Jaspypt简介

1.概述

在本文中，我们将研究 Jaspypt （Java Simplified Encryption）库。

Jasypt是一个Java库，它允许开发人员以最少的工作量将基本加密功能添加到项目中，而无需深入了解加密协议的实现细节。

2.使用简单加密

假设我们正在构建一个web应用程序，其中用户提交一个帐户私有数据。我们需要将数据存储在数据库中，但存储纯文本是不安全的。

处理此问题的一种方法是将加密数据存储在数据库中，并在为特定用户检索该数据时对其进行解密。

要使用非常简单的算法执行加密和解密，我们可以使用Jasypt库中的 BasicTextEncryptor 类：

```java
BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
String privateData = "secret-data";
textEncryptor.setPasswordCharArray("some-random-data".toCharArray());
```

然后我们可以使用 encrypt() 方法加密纯文本：

```java
String myEncryptedText = textEncryptor.encrypt(privateData);
assertNotSame(privateData, myEncryptedText);
```

如果我们想在数据库中存储给定用户的私有数据，我们可以在不违反任何安全限制的情况下存储myEncryptedText。如果要将数据解密回纯文本，可以使用decrypt() 方法：

```java
String plainText = textEncryptor.decrypt(myEncryptedText);
assertEquals(plainText, privateData);
```

我们看到，解密的数据等于之前加密的纯文本数据。

3.单向加密

前面的示例并不是执行身份验证的理想方法，即当我们要存储用户密码时。理想情况下，我们希望加密密码，而无需解密。当用户尝试登录我们的服务时，我们会加密他的密码，并将其与存储在数据库中的加密密码进行比较。这样我们就不需要对纯文本密码进行操作。

我们可以使用BasicPasswordEncryptor类执行单向加密：

```java
String password = "secret-pass";
BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
String encryptedPassword = passwordEncryptor.encryptPassword(password);
```

然后，我们可以将已经加密的密码与执行登录过程的用户的密码进行比较，而无需解密已经存储在数据库中的密码：

```java
boolean result = passwordEncryptor.checkPassword("secret-pass", encryptedPassword);
assertTrue(result);
```

4.配置加密算法

我们可以使用更强的加密算法，但我们需要为JVM安装 JCE ，才可运行 JasyptUnitTest.java 中的 givenTextPrivateData_whenDecrypt_thenCompareToEncryptedWithCustomAlgorithm 方法。

在Jasypt中，我们可以使用StandardPBEStringEncryptor类使用强加密，并使用setAlgorithm（）方法对其进行自定义：

```java
StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
String privateData = "secret-data";
encryptor.setPassword("some-random-passwprd");
encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
```

让我们将加密算法设置为PBEWithMD5AndTripleDES。

接下来，加密和解密过程看起来与上一个使用BasicTextEncryptor类的过程相同：

```java
String encryptedText = encryptor.encrypt(privateData);
assertNotSame(privateData, encryptedText);

String plainText = encryptor.decrypt(encryptedText);
assertEquals(plainText, privateData);
```

5.使用多线程解密

当我们在多核机器上操作时，我们希望并行处理解密处理。为了获得良好的性能，我们可以使用 [PooledPBEStringEncryptor](http://www.jasypt.org/api/jasypt/1.9.3/org/jasypt/encryption/pbe/PooledPBEStringEncryptor.html) 和 setPoolSize() API创建一个消化池。每个线程都可以由不同的线程并行使用：

```java
PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
encryptor.setPoolSize(4);
encryptor.setPassword("some-random-data");
encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
```

将池大小设置为等于机器的核心数是一个好做法。加密和解密的代码与以前的相同。

6.在其他框架中的使用

最后一点，Jasypt库可以与许多其他库集成，当然包括Spring Framework。

我们只需要创建一个配置，将加密支持添加到Spring应用程序中。如果我们想将敏感数据存储到数据库中，并且我们使用Hibernate作为数据访问框架，我们还可以将Jaspyt与之集成。

有关这些集成以及与其他一些框架的集成的说明，可以在[Jasypt主页](http://www.jasypt.org/)上的指南部分找到。

## Java BouncyCastle简介

1.概述

BouncyCastle是一个Java库，它补充了默认的Java加密扩展（JCE）。

在这篇介绍性文章中，我们将展示如何使用BouncyCastle执行加密操作，例如加密和签名。

2.Maven配置

在开始使用库之前，我们需要将所需的依赖项添加到pom中。xml文件： org.bouncycastle.bcpkix-jdk15on.1.xx 。
请注意，我们总是可以在Maven Central Repository中查找最新的依赖项版本。

3.配置 JCE

4.密码操作

4.1.准备证书和私钥

在开始实现加密函数之前，我们首先需要创建证书和私钥。

出于测试目的，我们可以使用以下资源：

- Baeldung.cer
- Baeldung.p12 (password = “password”)

cer是一种使用国际X.509公钥基础设施标准的数字证书，而Baeldung则是。p12是一个受密码保护的[PKCS12](https://tools.ietf.org/html/rfc7292)密钥库，其中包含私钥。

让我们看看如何在Java中加载这些内容：

```java
Security.addProvider(new BouncyCastleProvider());
CertificateFactory certFactory= CertificateFactory
  .getInstance("X.509", "BC");
 
X509Certificate certificate = (X509Certificate) certFactory
  .generateCertificate(new FileInputStream("Baeldung.cer"));
 
char[] keystorePassword = "password".toCharArray();
char[] keyPassword = "password".toCharArray();
 
KeyStore keystore = KeyStore.getInstance("PKCS12");
keystore.load(new FileInputStream("Baeldung.p12"), keystorePassword);
PrivateKey key = (PrivateKey) keystore.getKey("baeldung", keyPassword);
```

首先，我们使用addProvider（）方法动态添加BouncyCastleProvider作为安全提供者。

这也可以通过编辑 `{JAVA_HOME}/jre/lib/security/java.security` 静态安全文件完成，并添加此行：

`security.provider.N = org.bouncycastle.jce.provider.BouncyCastleProvider`

一旦正确安装了提供程序，我们就使用getInstance（）方法创建了CertificateFactory对象。

getInstance（）方法有两个参数；证书类型“X.509”和安全提供程序“BC”。

certFactory实例随后通过generateCertificate（）方法用于生成X509Certification对象。

同样，我们创建了一个PKCS12 Keystore对象，在该对象上调用load（）方法。

getKey（）方法返回与给定别名关联的私钥。

请注意，PKCS12密钥库包含一组私钥，每个私钥可以有一个特定的密码，这就是为什么我们需要一个全局密码来打开密钥库，需要一个特定密码来检索私钥。

证书和私钥对主要用于非对称加密操作：

- Encryption 加密
- Decryption 解密
- Signature 签名
- Verification 验证

## 如何读取PEM文件以获取公钥和私钥

1.概述

在公钥加密（也称为非对称加密 [asymmetric cryptography](https://www.baeldung.com/cs/symmetric-vs-asymmetric-cryptography) ）中，加密机制依赖于两个相关的密钥，即公钥和私钥。公钥用于加密消息，而只有私钥的所有者才能解密消息。

在本教程中，我们将学习如何从PEM文件读取公钥和私钥。

首先，我们将学习有关公钥加密的一些重要概念。然后，我们将学习如何使用纯Java读取PEM文件。

最后，我们将探索 [BouncyCastle](https://www.bouncycastle.org) 库作为替代方法。

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

4.使用BouncyCastle库

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

[Java](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-security-2) 方法的完整源代码可以在GitHub上找到。

## 使用 Java 完成 SSH 连接

1.简介

SSH，也称为 Secure Shell 或 Secure Socket Shell，是一种网络协议，允许一台计算机通过不安全的网络安全地连接到另一台计算机。在本教程中，我们将展示如何使用 JSch 和 Apache MINA SSHD 库使用 Java 建立与远程 SSH 服务器的连接。

在我们的示例中，我们将首先打开 SSH 连接，然后执行一个命令，读取输出并将其写入控制台，最后关闭 SSH 连接。我们将尽可能简化示例代码。

2.JSch

JSch 是 SSH2 的 Java 实现，它允许我们连接到 SSH 服务器并使用端口转发、X11 转发和文件传输。此外，它在 BSD 风格许可下获得许可，并为我们提供了一种与 Java 建立 SSH 连接的简单方法。

首先，让我们将 [JSch Maven](https://search.maven.org/search?q=g:com.jcraft%20AND%20a:jsch) 依赖添加到我们的 pom.xml 文件中。

2.1.执行

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
- [x] [Intro to Jasypt](https://www.baeldung.com/jasypt)
- [x] [Digital Signatures in Java](https://www.baeldung.com/java-digital-signature)
- [x] [How to Read PEM File to Get Public and Private Keys](https://www.baeldung.com/java-read-pem-file-keys)
- [x] [SSH Connection With Java](https://www.baeldung.com/java-ssh-connection)
