# Core Java Networking (Part 2)

This module contains articles about networking in Java

## 用Java发送带有附件的电子邮件

1.概述

在本快速教程中，我们将学习如何使用JavaMail API在Java中发送带有单个和多个附件的电子邮件。

2.项目设置

在本文中，我们将使用javax创建一个简单的Maven项目。邮件相关依赖：javax.mail.mail

3.发送带有附件的邮件

首先，我们需要配置[电子邮件服务提供商的凭据](https://www.baeldung.com/java-email#sending-a-plain-text-and-an-html-email)。然后，通过提供电子邮件主机、端口、用户名和密码来创建会话对象。所有这些详细信息都由电子邮件主机服务提供。我们可以为代码使用任何虚假的SMTP测试服务器。

会话对象将作为连接工厂来处理JavaMail的配置和身份验证。

现在我们有了一个Session对象，让我们进一步创建MimeMessage和MimeBodyPart对象。我们使用这些对象创建电子邮件：

```java
Message message = new MimeMessage(session); 
message.setFrom(new InternetAddress(from)); 
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); 
message.setSubject("Test Mail Subject"); 

BodyPart messageBodyPart = new MimeBodyPart(); 
messageBodyPart.setText("Mail Body");
```

在上面的代码片段中，我们创建了MimeMessage对象，其中包含from、to和subject等所需的详细信息。然后，我们得到了一个带有电子邮件正文的MimeBodyPart对象。

现在，我们需要创建另一个MimeBodyPart以在邮件中添加附件：

```java
MimeBodyPart attachmentPart = new MimeBodyPart();
attachmentPart.attachFile(new File("path/to/file"));
```

现在，一个邮件会话有两个MimeBodyPart对象。因此，我们需要创建一个MimeMultipart对象，然后将两个MimeBodyPart对象都添加到其中：

```java
Multipart multipart = new MimeMultipart();
multipart.addBodyPart(messageBodyPart);
multipart.addBodyPart(attachmentPart);
```

最后，MimeMultiPart作为邮件内容和传输添加到MimeMessage对象。调用send（）方法发送消息：

```java
message.setContent(multipart);
Transport.send(message);
```

总之，消息包含MimeMultiPart，它还包含多个MimeBodyPart。这就是我们组装完整电子邮件的方式。

此外，要发送多个附件，只需添加另一个MimeBodyPart即可。

超时参数：“默认无穷大”，出现异常后（比如邮箱服务器繁忙）有可能导致核心业务逻辑在等待往服务器读写消息，形成阻塞。

| 参数                          | 类型  | 解释                                                                                                                                                                                                                                                                                                                      |
|-----------------------------|-----|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| mail.smtp.connectiontimeout | int | Socket connection timeout value in milliseconds. This timeout is implemented by java.net.Socket. Default is infinite timeout.                                                                                                                                                                                           |
| mail.smtp.timeout           | int | Socket read timeout value in milliseconds. This timeout is implemented by java.net.Socket. Default is infinite timeout.                                                                                                                                                                                                 |
| mail.smtp.writetimeout      | int | Socket write timeout value in milliseconds. This timeout is implemented by using a java.util.concurrent.ScheduledExecutorService per connection that schedules a thread to close the socket if the timeout expires. Thus, the overhead of using this timeout is one thread per connection. Default is infinite timeout. |

新版 JAKARTA MAIL 可代替 Java mail。

<https://eclipse-ee4j.github.io/mail/#Download_Jakarta_Mail_Release>

### Relevant Articles

- [Checking if a URL Exists in Java](https://www.baeldung.com/java-check-url-exists)
- [Making a JSON POST Request With HttpURLConnection](https://www.baeldung.com/httpurlconnection-post)
- [Using Curl in Java](https://www.baeldung.com/java-curl)
- [Do a Simple HTTP Request in Java](https://www.baeldung.com/java-http-request)
- [ ] [Sending Emails with Java](https://www.baeldung.com/java-email)
- [Authentication with HttpUrlConnection](https://www.baeldung.com/java-http-url-connection)
- [Download a File From an URL in Java](https://www.baeldung.com/java-download-file)
- [Handling java.net.ConnectException](https://www.baeldung.com/java-net-connectexception)
- [Getting MAC addresses in Java](https://www.baeldung.com/java-mac-address)
- [Sending Emails with Attachments in Java](https://www.baeldung.com/java-send-emails-attachments)
- [[<-- Prev]](../core-java-networking/README.md)
