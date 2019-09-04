package com.baeldung.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.baeldung.notification.NotificationApacheMailAdapter;
import com.baeldung.notification.NotificationOutputAdapter;
import com.baeldung.service.NotificationService;

@Configuration
public class NotificationConfiguration {

	private Environment environment;

	@Autowired
	public NotificationConfiguration(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public NotificationService buildNotificationService() {
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			return new NotificationService(new NotificationOutputAdapter());
		} else {
			return new NotificationService(new NotificationApacheMailAdapter(environment.getProperty("email.host"),
					Integer.parseInt(environment.getProperty("email.host.port")),
					environment.getProperty("email.auth.user"), environment.getProperty("email.auth.password")));
		}
	}

}
