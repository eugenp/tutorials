package com.baeldung.spring.core.di;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class ApplicationTest {

	@Autowired
	@Qualifier("emailService")
	MessageService emailServiceBean;

	@Autowired
	@Qualifier("smsService")
	MessageService smsServiceBean;

	@Test
	public void testSendEmailMessage() {
		smsServiceBean.sendMessage();
		
		assertEquals(emailServiceBean.getMessage().getBody(), "email message body");
		assertEquals(emailServiceBean.getMessage().getFrom(), "user1");
		assertEquals(emailServiceBean.getMessage().getTo(), "user2");
	}
	
	@Test
	public void testSendSMSMessage() {
		smsServiceBean.sendMessage();

		assertEquals(smsServiceBean.getMessage().getBody(), "sms message body");
		assertEquals(smsServiceBean.getMessage().getFrom(), "user2");
		assertEquals(smsServiceBean.getMessage().getTo(), "user1");
	}

}
