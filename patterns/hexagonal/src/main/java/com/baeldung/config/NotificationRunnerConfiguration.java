package com.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.baeldung.model.Message;
import com.baeldung.service.NotificationService;

@Configuration
@Profile("default")
public class NotificationRunnerConfiguration implements CommandLineRunner {

	private NotificationService notificationService;

	private Environment environment;

	@Autowired
	public NotificationRunnerConfiguration(NotificationService notificationService, Environment environment) {
		this.notificationService = notificationService;
		this.environment = environment;
	}

	@Override
	public void run(String... args) throws Exception {
		Message message = new Message(environment.getProperty("email.auth.user"),
				environment.getProperty("email.auth.user"), "Baeldung Hexagonal Architecture",
				"Your notification service is running.");
		notificationService.send(message);
	}
}
