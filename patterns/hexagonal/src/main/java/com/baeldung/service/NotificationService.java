package com.baeldung.service;

import com.baeldung.model.Message;
import com.baeldung.notification.NotificationPort;

public class NotificationService {

	private NotificationPort notificationSystem;

	public NotificationService(NotificationPort notificationSystem) {
		super();
		this.notificationSystem = notificationSystem;
	}

	public void send(Message message) {
		notificationSystem.send(message);
	}
}
