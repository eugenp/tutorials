package com.baeldung.beaninjectiontypes.services;

public class MessageService {

	public boolean sendMessage(String message, String sender, String reciever) {
		System.out.println("To : " + reciever);
		System.out.println("Message - " + message);
		System.out.println("Sender : " + sender);
		return true;
	}
}
