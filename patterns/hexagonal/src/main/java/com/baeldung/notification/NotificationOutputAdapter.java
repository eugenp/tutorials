package com.baeldung.notification;

import com.baeldung.model.Message;

public class NotificationOutputAdapter implements NotificationPort {

	@Override
	public void send(Message message) {
		System.out.println("To=" + message.getTo());
		System.out.println("From=" + message.getFrom());
		System.out.println("Subject=" + message.getSubject());
		System.out.println("Content=" + message.getContent());
	}
}
