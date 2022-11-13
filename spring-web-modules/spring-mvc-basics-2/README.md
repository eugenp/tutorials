# Spring MVC Basics 2

This module contains articles about Spring MVC

## Spring电子邮件指南

1.概述

在本教程中，我们将介绍从普通Spring应用程序和SpringBoot应用程序发送电子邮件所需的步骤。对于前者，我们将使用JavaMail库，而后者将使用spring boot starter邮件依赖项。

进一步阅读：

- [ ] [注册–通过电子邮件激活新帐户](https://www.baeldung.com/registration-verify-user-by-email)
- [ ] [Spring Boot Actuator](https://www.baeldung.com/spring-boot-actuators)

2.Maven依赖项

首先，我们需要将依赖项添加到pom.xml。

2.1.弹簧

下面是我们将添加的用于普通Spring框架的内容：org.springframework.spring-context-support

最新版本可在[此处](https://search.maven.org/classic/#search%7Cga%7C1%7Cspring-context-support)找到。

2.2.Spring Boot

添加依赖：org.springframework.boot.spring-boot-starter-mail

Maven Central存储库中提供了最新版本。

3.邮件服务器属性

Spring框架中Java邮件支持的接口和类组织如下：

- MailSender接口：顶级接口，提供发送简单电子邮件的基本功能
- JavaMailSender接口：上述MailSender的子接口。它支持MIME消息，通常与MimeMessageHelper类一起用于创建MimeMessage。建议对该接口使用MimeMessagePreparator机制。
- JavaMailSenderImpl类提供了JavaMailSender接口的实现。它支持MimeMessage和SimpleMailMessage。
- SimpleMailMessage类：用于创建简单的邮件消息，包括发件人、收件人、抄送人、主题和文本字段
- MimeMessagePreparator接口提供了用于准备MIME消息的回调接口。
- MimeMessageHelper类：用于创建MIME消息的助手类。它支持HTML布局中的图像、典型邮件附件和文本内容。

在下面的部分中，我们将展示如何使用这些接口和类。

3.1.Spring Mail服务器属性

例如，可以使用JavaMailSenderImpl定义指定SMTP服务器所需的邮件属性。

对于Gmail，可以如下配置：

```java
@Bean
public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    
    mailSender.setUsername("my.gmail@gmail.com");
    mailSender.setPassword("password");
    
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    
    return mailSender;
}
```

3.2.Spring Boot Mail服务器属性

依赖关系就位后，下一步是在应用程序中指定邮件服务器属性。属性文件使用spring.mail.*命名空间。

我们可以这样指定Gmail SMTP服务器的属性：

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<login user to smtp server>
spring.mail.password=<login password to smtp server>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

一些SMTP服务器需要TLS连接，因此我们使用属性spring.mail.properties.mail.SMTP.starttls。启用以启用TLS保护的连接。

3.2.1.Gmail SMTP属性

我们可以通过Gmail SMTP服务器发送电子邮件。查看[文档](https://support.google.com/mail/answer/13273?hl=en&rd=2)以查看Gmail发送邮件SMTP服务器属性。

我们的应用程序。属性文件已配置为使用Gmail SMTP（请参阅上一节）。

请注意，我们帐户的密码不应该是普通密码，而是为我们的Google帐户生成的应用程序密码。点击此[链接](https://support.google.com/accounts/answer/185833)查看详细信息并生成您的Google App密码。

3.2.2.SES SMTP属性

要使用Amazon SES发送电子邮件，我们设置了应用程序属性：

```properties
spring.mail.host=email-smtp.us-west-2.amazonaws.com
spring.mail.username=username
spring.mail.password=password
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.port=25
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

请注意，亚马逊要求我们在使用之前验证我们的凭证。按照链接验证您的用户名和密码。

4.发送电子邮件

一旦依赖项管理和配置到位，我们就可以使用前面提到的JavaMailSender发送电子邮件。

由于普通的Spring框架和Boot版本都以类似的方式处理电子邮件的撰写和发送，因此我们不必在下面的小节中区分这两者。

4.1.发送简单电子邮件

让我们首先撰写并发送一封不带任何附件的简单电子邮件：

```java
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        ...
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@baeldung.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        ...
    }
}
```

请注意，即使不强制提供发件人地址，许多SMTP服务器也会拒绝此类邮件。这就是为什么我们在EmailService实现中，使用noreply@baeldung.com电子邮件地址。

4.2.发送带有附件的电子邮件

有时，Spring的简单消息传递对于我们的用例来说是不够的。

例如，我们想发送一封附有发票的订单确认电子邮件。在这种情况下，我们应该使用JavaMail库中的MIME多部分消息，而不是SimpleMailMessage。Spring通过org.springframework.mail.JavaMail支持JavaMail消息传递。MimeMessageHelper类。

首先，我们将在EmailServiceImpl中添加一个方法来发送带有附件的电子邮件：

```java
@Override
public void sendMessageWithAttachment(
  String to, String subject, String text, String pathToAttachment) {
    // ...
    
    MimeMessage message = emailSender.createMimeMessage();
     
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
    helper.setFrom("noreply@baeldung.com");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text);
        
    FileSystemResource file 
      = new FileSystemResource(new File(pathToAttachment));
    helper.addAttachment("Invoice", file);

    emailSender.send(message);
    // ...
}
```

4.3.简单电子邮件模板

SimpleMailMessage类支持文本格式。

我们可以通过在配置中定义模板bean来创建电子邮件模板：

```java
@Bean
public SimpleMailMessage templateSimpleMessage() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText(
      "This is the test email template for your email:\n%s\n");
    return message;
}
```

现在，我们可以将此bean用作电子邮件模板，只需为模板提供必要的参数：

```java
@Autowired
public SimpleMailMessage template;
...
String text = String.format(template.getText(), templateArgs);  
sendSimpleMessage(to, subject, text);
```

5.处理发送错误

JavaMail提供SendFailedException来处理无法发送消息的情况。但是，在向错误地址发送电子邮件时，我们可能不会收到此异常。原因如下：

RFC 821中的SMTP协议规范指定了SMTP服务器在尝试向错误地址发送电子邮件时应返回的550返回代码。但大多数公共SMTP服务器都不这样做。相反，他们发送了一封“交付失败”的电子邮件，或者根本没有给出反馈。

例如，Gmail SMTP服务器发送“传送失败”消息。我们的程序中没有例外。

因此，我们有几个选项来处理这种情况：

- 捕获SendFailedException，它永远不会被抛出。
- 在一段时间内检查发件人邮箱中的“传递失败”消息。这并不简单，时间段也不确定。
- 如果我们的邮件服务器根本没有反馈，我们什么也做不了。

## Relevant articles

- [HandlerAdapters in Spring MVC](https://www.baeldung.com/spring-mvc-handler-adapters)
- [Template Engines for Spring](https://www.baeldung.com/spring-template-engines)
- [Spring 5 and Servlet 4 – The PushBuilder](https://www.baeldung.com/spring-5-push)
- [Servlet Redirect vs Forward](https://www.baeldung.com/servlet-redirect-forward)
- [x] [Guide to Spring Email](https://www.baeldung.com/spring-email)
- [Using ThymeLeaf and FreeMarker Emails Templates with Spring](https://www.baeldung.com/spring-email-templates)
- [Request Method Not Supported (405) in Spring](https://www.baeldung.com/spring-request-method-not-supported-405)
- More articles: [[<-- prev]](../spring-mvc-basics/README.md)[[more -->]](../spring-mvc-basics-3/README.md)
