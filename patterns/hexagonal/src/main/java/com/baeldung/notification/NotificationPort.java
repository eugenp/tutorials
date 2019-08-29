package com.baeldung.notification;

import com.baeldung.model.Message;

public interface NotificationPort {

	public void send(Message message);
}
