package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.model.Message;
import com.baeldung.service.NotificationService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotificationServiceUnitTest {

	@Autowired
	private Environment environment;

	@Autowired
	private NotificationService notificationService;

	@Test
	public void whenSendingAValidMessage_thenPrintOutput() {
		
		Message message = new Message(environment.getProperty("email.auth.user"),
				environment.getProperty("email.auth.user"), "Baeldung Hexagonal Architecture",
				"Your notification service is running in test mode.");
		notificationService.send(message);
	}

}
