package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceBean implements MessageService {
	
	private Message message;
	
	@Autowired
	public EmailServiceBean(Message emailMessage) {
		this.message = emailMessage;
	}
	
	public void sendMessage() {
		System.out.println("sending email message");
		System.out.println("from: " + this.message.getFrom());
		System.out.println("to: " + this.message.getTo());
		System.out.println("body: " + this.message.getBody());
	}
	
	public Message getMessage() {
		return message;
	}
}
