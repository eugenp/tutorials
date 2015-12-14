package org.baeldung.springjavamail;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailTest {

    @Test
    public void whenSendEmail_thenSuccess() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "MailBeans.xml");

        JavaMailSender javaMailSender = (JavaMailSender) applicationContext
                .getBean("mailSender");

        SimpleMailMessage welcomeMailMessage = (SimpleMailMessage) applicationContext
                .getBean("welcomeMessage");
        welcomeMailMessage.setTo("to_an_email@gmail.com");

        javaMailSender.send(welcomeMailMessage);

        SimpleMailMessage promotionMessage = (SimpleMailMessage) applicationContext
                .getBean("promotionMessage");
        promotionMessage.setTo("to_an_email@gmail.com");
        javaMailSender.send(promotionMessage);

        ((ClassPathXmlApplicationContext) applicationContext).close();
    }
}
