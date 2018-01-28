package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.spring.core.di")
public class AppConfig {
	
	@Bean
	@Qualifier("emailMessage")
	public Message emailMessage() {
		Message emailMessage = new Message();
		emailMessage.setBody("email message body");
		emailMessage.setFrom("user1");
		emailMessage.setTo("user2");
		return emailMessage;
	}
	
	@Bean
	@Qualifier("smsMessage")
	public Message smsMessage() {
		Message smsMessage = new Message();
		smsMessage.setBody("sms message body");
		smsMessage.setFrom("user2");
		smsMessage.setTo("user1");
		return smsMessage;
	}
}
