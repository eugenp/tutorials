package com.habuma.spitter.email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.habuma.spitter.domain.Spittle;

@Component
public class SpitterEmailServiceImpl implements SpitterEmailService {
  public void sendSpittleEmail(String to, Spittle spittle) {
    sendSimpleSpittleEmail(to, spittle);
  }
  
  //<start id="method_sendSimpleSpittleEmail"/>  
  public void sendSimpleSpittleEmail(String to, Spittle spittle) {  
    SimpleMailMessage message = new SimpleMailMessage(); //<co id="co_createSimpleMailMessage"/>
    
    String spitterName = spittle.getSpitter().getFullName();
    message.setFrom("noreply@spitter.com"); //<co id="co_addressSimpleEmail"/>
    message.setTo(to);
    message.setSubject("New spittle from " + spitterName);
    
    message.setText(spitterName + " says: " + //<co id="co_setSimpleMessage"/> 
            spittle.getText());
    
    mailSender.send(message); //<co id="co_sendSimpleMessage"/>
  }
  //<end id="method_sendSimpleSpittleEmail"/>  
  
  //<start id="method_sendEmailWithAttachment"/> 
  public void sendSpittleEmailWithAttachment(
             String to, Spittle spittle) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = 
          new MimeMessageHelper(message, true); //<co id="co_createHelper"/>

    String spitterName = spittle.getSpitter().getFullName();
    helper.setFrom("noreply@spitter.com");
    helper.setTo(to);
    helper.setSubject("New spittle from " + spitterName);
    
    helper.setText(spitterName + " says: " + spittle.getText());

    FileSystemResource couponImage = 
          new FileSystemResource("/collateral/coupon.png");
    helper.addAttachment("Coupon.png", couponImage); //<co id="co_addAttachment"/>

    mailSender.send(message);    
  }
  //<end id="method_sendEmailWithAttachment"/> 
  
  //<start id="method_sendRichSpitterEmail"/> 
  public void sendRichSpitterEmail(String to, Spittle spittle) throws MessagingException {  
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom("noreply@spitter.com");
    helper.setTo("craig@habuma.com");
    helper.setSubject("New spittle from " + 
            spittle.getSpitter().getFullName());
    
    helper.setText("<html><body><img src='cid:spitterLogo'>" + //<co id="co_setHtml"/> 
        "<h4>" + spittle.getSpitter().getFullName() + " says...</h4>" +
        "<i>" + spittle.getText() + "</i>" +
    		"</body></html>", true);
    
    ClassPathResource image = new ClassPathResource("spitter_logo_50.png");
    helper.addInline("spitterLogo", image); //<co id="co_addInline"/>
    mailSender.send(message);
  }
  //<end id="method_sendRichSpitterEmail"/> 

  //<start id="method_sendTemplatedEmail"/> 
  public void sendTemplatedEmail(String to, Spittle spittle) 
                                               throws MessagingException {  
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    String spitterName = spittle.getSpitter().getFullName();
    helper.setFrom("noreply@spitter.com");
    helper.setTo("craig@habuma.com");
    helper.setSubject("New spittle from " + spitterName);

    //<start id="mergeEmailTemplate"/> 
    Map<String, String> model = new HashMap<String, String>();
    model.put("spitterName", spitterName);
    model.put("spittleText", spittle.getText());
    String emailText = VelocityEngineUtils.mergeTemplateIntoString(
            velocityEngine, "emailTemplate.vm", model );
    //<end id="mergeEmailTemplate"/> 

    //<start id="sendTemplateEmail"/> 
    helper.setText(emailText, true);
    //<end id="sendTemplateEmail"/> 
    
    ClassPathResource image = new ClassPathResource("spitter_logo_50.png");
    helper.addInline("spitterLogo", image); //<co id="co_addInline"/>
    mailSender.send(message);
  }
  //<end id="method_sendTemplatedEmail"/> 

  //<start id="property_autowiredMailSender"/> 
  @Autowired
  JavaMailSender mailSender;
  //<end id="property_autowiredMailSender"/> 

  //<start id="property_velocityEngine"/> 
  @Autowired
  VelocityEngine velocityEngine;
  //<end id="property_velocityEngine"/> 
}
