package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SMSServiceBean implements MessageService {
	
	private Message message;

	public void sendMessage() {
		System.out.println("sending sms message");
		System.out.println("from: " + this.message.getFrom());
		System.out.println("to: " + this.message.getTo());
		System.out.println("body: " + this.message.getBody());
	}
	
	@Autowired
	@Qualifier("smsMessage")
	public void setMessage(Message message) {
		this.message = message;
	}
	
	public Message getMessage() {
		return message;
	}
}
