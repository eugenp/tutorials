package org.baeldung.springjavamail;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailTest {

    private static final String TO_EMAIL = "to-email@gmail.com";

    @Test
    public void whenSendEmail_thenSuccess() {
        boolean exceptionThrown = false;
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "MailBeans.xml");
            JavaMailSender javaMailSender = (JavaMailSender) applicationContext
                    .getBean("mailSender");

            SimpleMailMessage welcomeMailMessage = (SimpleMailMessage) applicationContext
                    .getBean("welcomeMessage");
            welcomeMailMessage.setTo(TO_EMAIL);

            javaMailSender.send(welcomeMailMessage);

            SimpleMailMessage promotionMessage = (SimpleMailMessage) applicationContext
                    .getBean("promotionMessage");
            promotionMessage.setTo(TO_EMAIL);
            javaMailSender.send(promotionMessage);

            ((ClassPathXmlApplicationContext) applicationContext).close();
        } catch (RuntimeException e) {
            exceptionThrown = true;
        }
        Assert.assertEquals(exceptionThrown, false);
    }
}
