package com.baeldung.spring.annotationTest.services;

import org.springframework.stereotype.Component;

@Component
public class MessageService {

	public void sendMessage()
	{
		System.out.println("Sending the message.");
	}
}
