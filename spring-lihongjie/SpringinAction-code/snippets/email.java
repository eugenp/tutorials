//<start id="createMimeMessage"/> 
MimeMessage message = mailSender.createMimeMessage();
//<end id="createMimeMessage"/> 


//<start id="createMimeMessageHelper"/> 
MimeMessageHelper helper = new MimeMessageHelper(message, true);
//<end id="createMimeMessageHelper"/> 

//<start id="useMimeMessageHelper"/> 
String spitterName = spittle.getSpitter().getFullName();
helper.setFrom("noreply@spitter.com");
helper.setTo(to);
helper.setSubject("New spittle from " + spitterName);
helper.setText(spitterName + " says: " + spittle.getText());
//<end id="useMimeMessageHelper"/> 


//<start id="addAttachment"/> 
FileSystemResource couponImage = 
      new FileSystemResource("/collateral/coupon.png");
helper.addAttachment("Coupon.png", couponImage);
//<end id="addAttachment"/> 

//<start id="setTextHtml"/> 
helper.setText("<html><body><img src='cid:spitterLogo'>" +
    "<h4>" + spittle.getSpitter().getFullName() + " says...</h4>" +
    "<i>" + spittle.getText() + "</i>" +
		"</body></html>", true);
//<end id="setTextHtml"/> 

//<start id="addInlineImage"/> 
ClassPathResource image = new ClassPathResource("spitter_logo_50.png");
helper.addInline("spitterLogo", image);
//<end id="addInlineImage"/> 


<!--<start id=""/>--> 
<!--<end id=""/>--> 

<!--<start id="bean_mailSender"/>--> 
<bean id="mailSender" 
    class="org.springframework.mail.javamail.JavaMailSenderImpl"
  p:host="${mailserver.host}" />
<!--<end id="bean_mailSender"/>-->

<!--<start id="bean_mailSender_port"/>--> 
<bean id="mailSender" 
    class="org.springframework.mail.javamail.JavaMailSenderImpl"
  p:host="${mailserver.host}" 
  p:port="${mailserver.port}"/>
<!--<end id="bean_mailSender_port"/>-->

<!--<start id="bean_mailSender_username_password"/>--> 
<bean id="mailSender" 
    class="org.springframework.mail.javamail.JavaMailSenderImpl"
  p:host="${mailserver.host}" 
  p:port="${mailserver.port}"
  p:username="${mailserver.username}"
  p:password="${mailserver.password}" />
<!--<end id="bean_mailSender_username_password"/>-->

<!--<start id="jndi_mailSession"/>--> 
<jee:jndi-lookup id="mailSession" 
    jndi-name="mail/Session" resource-ref="true" />
<!--<end id="jndi_mailSession"/>--> 

<!--<start id="bean_mailSender_jndiSession"/>--> 
<bean id="mailSender" 
    class="org.springframework.mail.javamail.JavaMailSenderImpl"
  p:session-ref="mailSession" />
<!--<end id="bean_mailSender_jndiSession"/>-->