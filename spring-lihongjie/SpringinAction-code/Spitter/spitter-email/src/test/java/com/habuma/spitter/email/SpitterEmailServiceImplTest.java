package com.habuma.spitter.email;

import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

@Ignore("This test needs to be worked on so that it won't keep stepping on itself")
public class SpitterEmailServiceImplTest {
  private static final int TEST_SMTP_PORT = 1025;
  private SimpleSmtpServer smtpServer;
  private SpitterEmailServiceImpl emailService;

  @Before
  public void setup() {
    smtpServer = SimpleSmtpServer.start(TEST_SMTP_PORT);

    emailService = new SpitterEmailServiceImpl();
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setPort(1025);
    emailService.mailSender = mailSender;
  }
  
  @After
  public void tearDown() {
    smtpServer.stop();
  }
  
  @Test
  public void shouldSendTextOnlyEmail() {
    Spitter spitter = new Spitter();
    spitter.setFullName("Test McTest");
    Spittle spittle = new Spittle();
    spittle.setSpitter(spitter);
    spittle.setText("This is a test");

    assertEquals(0, smtpServer.getReceivedEmailSize());

    emailService.sendSimpleSpittleEmail("craig@habuma.com", spittle);
    
    assertEquals(1, smtpServer.getReceivedEmailSize());
    
    SmtpMessage email = (SmtpMessage) smtpServer.getReceivedEmail().next();
    assertEquals("New spittle from Test McTest", email.getHeaderValue("Subject"));
    assertEquals("noreply@spitter.com", email.getHeaderValue("From"));
    assertEquals("craig@habuma.com", email.getHeaderValue("To"));
    assertEquals("This is a test", email.getBody());
  }
  
  @Test
  public void shouldSendHtmlEmail() throws MessagingException {
    Spitter spitter = new Spitter();
    spitter.setFullName("Test McTest");
    Spittle spittle = new Spittle();
    spittle.setSpitter(spitter);
    spittle.setText("This is a test");

    assertEquals(0, smtpServer.getReceivedEmailSize());

    emailService.sendRichSpitterEmail("craig@habuma.com", spittle);
    
    assertEquals(1, smtpServer.getReceivedEmailSize());
    
    SmtpMessage email = (SmtpMessage) smtpServer.getReceivedEmail().next();
    assertEquals("New spittle from Test McTest", email.getHeaderValue("Subject"));
    assertEquals("noreply@spitter.com", email.getHeaderValue("From"));
    assertEquals("craig@habuma.com", email.getHeaderValue("To"));
    assertEquals("multipart/mixed;", email.getHeaderValue("Content-Type"));
    
  }

}
