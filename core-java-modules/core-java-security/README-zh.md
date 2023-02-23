# Core Java Security

This module contains articles about core Java Security

## Java SASL简介

1. 概述

    在本教程中，我们将了解简单认证和安全层（[SASL](https://tools.ietf.org/html/rfc4422)）的基础知识。我们将了解Java如何支持采用SASL来保护通信安全。

    在这个过程中，我们将使用简单的客户端和服务器通信，用SASL来保护它。

2. 什么是SASL？

    SASL是互联网协议中认证和数据安全的一个框架。它的目的是使互联网协议与特定的认证机制脱钩。随着我们的深入，我们会更好地理解这个定义的一部分。

    通信中对安全的需求是隐含的。让我们试着在客户端和服务器通信的背景下理解这一点。通常，客户端和服务器通过网络交换数据。当务之急是双方能够相互信任并安全地发送数据。

    1. SASL的作用是什么？

        在一个应用程序中，我们可能使用SMTP来发送电子邮件，使用LDAP来访问目录服务。但这些协议都可能支持另一种认证机制，如Digest-MD5或Kerberos。

        如果有一种方法可以让协议更明确地交换认证机制呢？这正是SASL出现的地方。支持SASL的协议可以无一例外地支持任何一种SASL机制。

        因此，应用程序可以协商一个合适的机制并采用该机制进行认证和安全通信。

    2. SASL是如何工作的？

        现在，我们已经看到SASL在整个安全计划中的位置，让我们了解它是如何工作的。

        SASL是一个挑战-回应(challenge-response)框架。在这里，服务器向客户发出挑战，而客户则根据挑战发送响应。挑战和响应是任意长度的字节数组，因此，可以携带任何机制特定的数据。

        ![SASL交换](pic/SASL-Exchange.jpg)

        这种交换可以持续多次迭代，最后在服务器不再发出挑战时结束。

        此外，客户端和服务器可以在认证后协商一个安全层。然后，所有后续通信都可以利用这个安全层。然而，请注意，有些机制可能只支持认证。

        在此必须理解，SASL只提供了一个交换挑战和响应数据的框架。它没有提到任何关于数据本身或如何交换的问题。这些细节留给采用SASL的应用程序来处理。

3. Java中的SASL支持

    Java中有一些API，支持用SASL开发客户端和服务器端应用程序。该API不依赖于实际机制本身。使用Java SASL API的应用程序可以根据所需的安全功能选择一种机制。

    1. Java SASL API

        作为 "javax.security.sasl" 包的一部分，需要注意的关键接口是SaslServer和SaslClient。

        SaslServer代表SASL的服务器端机制。

        让我们看看如何实例化一个SaslServer。

        ```java
        SaslServer ss = Sasl.createSaslServer(
        mechanism, 
        protocol, 
        serverName, 
        props, 
        callbackHandler);
        ```

        我们正在使用工厂类Sasl来实例化SaslServer。方法createSaslServer接受几个参数。

        - mechanism - IANA(Internet Assigned Numbers Authority)注册的SASL支持机制的名称
        - protocol - 正在进行认证的协议的名称
        - serverName - 服务器的完全合格的主机名
        - props - 一组用于配置认证交换的属性
        - callbackHandler - 所选机制用于获得进一步信息的回调处理程序。

        在上述内容中，只有前两个是强制性的，其余的都是可忽略的。

        SaslClient表示SASL的客户端机制。让我们看看如何实例化一个SaslClient。

        ```java
        SaslClient sc = Sasl.createSaslClient(
        mechanisms, 
        authorizationId, 
        protocol, 
        serverName, 
        props,
        callbackHandler);
        ```

        这里，我们再次使用工厂类Sasl来实例化我们的SaslClient。createSaslClient所接受的参数列表与之前的基本相同。

        然而，也有一些细微的差别。

        - mechanisms - 在这里，这是一个可供尝试的机制列表。
        - authorizationId - 这是一个依赖于协议的标识，用于授权。

        其余的参数在含义上和它们的可选性上都差不多。

    2. Java SASL安全提供者

        在Java SASL API的下面是提供安全功能的实际机制。这些机制的实现是由在Java密码学架构（[JCA](https://docs.oracle.com/javase/9/security/java-cryptography-architecture-jca-reference-guide.htm)）中注册的安全提供者提供的。

        可以有多个安全提供者在JCA注册。每一个都可以支持一个或多个SASL机制。

        Java将SunSASL作为一个安全提供者，默认情况下，它被注册为JCA提供者。然而，这可以被删除或与任何其他可用的提供者重新排序。

        此外，我们总是可以提供一个自定义的安全提供者。这将要求我们实现SaslClient和SaslServer接口。在这样做的过程中，我们也可以实现我们的自定义安全机制!

4. 通过一个例子看SASL

    现在我们已经看到了如何创建一个SaslServer和一个SaslClient，是时候了解如何使用它们了。我们将开发客户端和服务器组件。这些组件将反复交换挑战和响应以实现认证。我们将在这里的简单例子中使用DIGEST-MD5机制。

    1. 客户端和服务器回调处理程序

        正如我们之前看到的，我们需要为SaslServer和SaslClient提供CallbackHandler的实现。现在，CallbackHandler是一个简单的接口，定义了一个方法--handle。这个方法接受一个数组的Callback。

        在这里，Callback为安全机制提供了一种从调用应用程序收集认证数据的方式。例如，一个安全机制可能需要一个用户名和密码。有相当多的Callback实现，如NameCallback和PasswordCallback可供使用。

        让我们看看我们如何为服务器定义一个CallbackHandler，首先。

        sasl/ServerCallbackHandler.java

        现在，让我们看看我们客户端的Callbackhandler。

        sasl/ClientCallbackHandler.java

        澄清一下，我们在回调数组中循环，只处理特定的回调。我们必须处理的是特定的机制，在这里是DIGEST-MD5。

    2. SASL认证

        所以，我们已经写好了我们的客户端和服务器端CallbackHandler。我们还为DIGEST-MD5机制实例化了SaslClient和SaslServer。

        现在是时候看看它们的运行情况了。

        参见：sasl/SaslUnitTest.givenHandlers_whenStarted_thenAutenticationWorks()

        让我们试着理解这里发生了什么。

        - 首先，我们的客户端从服务器获得默认的挑战书
        - 然后，客户端评估该挑战并准备一个响应
        - 这种挑战--响应的交换又持续了一个周期
        - 在这个过程中，客户端和服务器利用回调处理程序来收集机制所需的任何其他数据。
        - 我们的认证到此结束，但在现实中，它可以重复多个周期。

        一个典型的挑战和响应字节数组的交换是通过网络进行的。但是，在这里为了简单起见，我们假设是本地通信。

    3. SASL安全通信

        正如我们前面所讨论的，SASL是一个能够支持安全通信的框架，不仅仅是认证。然而，这只有在底层机制支持的情况下才有可能。

        首先，让我们先检查一下我们是否已经能够协商出一个安全的通信。

        ```java
        String qop = (String) saslClient.getNegotiatedProperty(Sasl.QOP);
        assertEquals("auth-conf", qop);
        ```

        在这里，QOP代表保护的质量。这是客户端和服务器在认证过程中协商的事情。一个 "auth-int" 的值表示认证和完整性。而 "auth-conf" 的值则表示认证、完整性和保密性。

        一旦我们有了安全层，我们就可以利用它来保护我们的通信。

        让我们来看看我们如何确保客户端的出站通信。

        ```java
        byte[] outgoing = "Baeldung".getBytes();
        byte[] secureOutgoing = saslClient.wrap(outgoing, 0, outgoing.length);
        // 通过网络将secureOutgoing发送给服务器
        ```

        而且，类似地，服务器可以处理传入的通信。

        ```java
        // 通过网络接收来自客户端的secureIncoming
        byte[] incoming = saslServer.unwrap(secureIncoming, 0, netIn.length);
        assertEquals("Baeldung", new String(incoming, StandardCharsets.UTF_8));
        ```

5. 真实世界中的SASL

    所以，我们现在对SASL是什么以及如何在Java中使用它有了一定的了解。但是，通常情况下，这并不是我们最终要使用SASL的目的，至少在我们的日常生活中是这样。

    正如我们前面看到的，SASL主要是为LDAP和SMTP等协议服务的。虽然，越来越多的应用程序开始使用SASL--例如，Kafka。那么，我们如何使用SASL来验证这些服务？

    假设我们已经为Kafka Broker配置了SASL，并选择了PLAIN作为机制。PLAIN只是意味着它使用纯文本的用户名和密码的组合进行认证。

    现在让我们看看如何配置一个Java客户端来使用SASL/PLAIN对Kafka Broker进行认证。

    我们首先提供一个简单的JAAS配置，"kafka_jaas.conf"。

    ```java
    KafkaClient {
    org.apache.kafka.common.security.plain.PlainLoginModule required
    username="username"
    password="password";
    };
    ```

    我们在启动JVM时利用了这个JAAS配置。

    `-Djava.security.auth.login.config=kafka_jaas.conf`

    最后，我们必须添加一些属性来传递给我们的生产者和消费者实例。

    ```java
    security.protocol=SASL_SSL
    sasl.mechanism=PLAIN
    ```

    这就是它的全部内容了。不过这只是[Kafka客户端配置](https://www.baeldung.com/kafka-connectors-guide)的一小部分。除了PLAIN之外，Kafka还支持GSSAPI/Kerberos的认证。

6. SASL的比较

    尽管SASL在提供一种机制中立的客户端和服务器通信的认证和安全方面相当有效。然而，SASL并不是这方面的唯一解决方案。

    Java本身也提供了其他机制来实现这一目标。我们将简要地讨论它们，并了解它们与SASL的对比情况。

    - Java Secure Socket Extension（[JSSE](https://docs.oracle.com/javase/9/security/java-secure-socket-extension-jsse-reference-guide.htm)）。JSSE是Java中的一组包，为Java实现安全套接字层（SSL）。它提供了数据加密、客户和服务器认证以及消息完整性。与SASL不同，JSSE依赖于公钥基础设施（PKI）来工作。因此，SASL比JSSE更灵活、更轻便。
    - Java GSS API（[JGSS](https://docs.oracle.com/javase/9/security/java-generic-security-services-java-gss-api1.htm)）。JGGS是通用安全服务应用编程接口（GSS-API）的Java语言绑定。GSS-API是一个IETF标准，用于应用程序访问安全服务。在Java中，在GSS-API下，Kerberos是唯一支持的机制。Kerberos同样需要一个Kerberised的基础设施来工作。与SASL相比，这里的选择是有限的，而且是重量级的。

    总的来说，SASL是一个非常轻量级的框架，并通过可插拔机制提供各种安全功能。采用SASL的应用程序在实现正确的安全功能集方面有很多选择，这取决于需求。

7. 结语

    总而言之，在本教程中，我们了解了SASL框架的基础知识，它提供了认证和安全通信。我们还讨论了Java中用于实现SASL客户端和服务器端的API。

    我们看到了如何通过JCA提供者使用安全机制。最后，我们还讨论了SASL在不同协议和应用程序中的使用。

    像往常一样，我们可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-security)上找到这些代码。

## Relevant Articles

- [Guide to the Cipher Class](http://www.baeldung.com/java-cipher-class)
- [Introduction to SSL in Java](http://www.baeldung.com/java-ssl)
- [Java KeyStore API](http://www.baeldung.com/java-keystore)
- [Encrypting and Decrypting Files in Java](http://www.baeldung.com/java-cipher-input-output-stream)
- [SSL Handshake Failures](https://www.baeldung.com/java-ssl-handshake-failures)
- [Enabling TLS v1.2 in Java 7](https://www.baeldung.com/java-7-tls-v12)
- [The Java SecureRandom Class](https://www.baeldung.com/java-secure-random)
- [x] [An Introduction to Java SASL](https://www.baeldung.com/java-sasl)
- [A Guide to Java GSS API](https://www.baeldung.com/java-gss)
- [Intro to the Java SecurityManager](https://www.baeldung.com/java-security-manager)
- More articles: [[next -->]](../core-java-security-2/README.md)
