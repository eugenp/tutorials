package com.baeldung.service;

import com.baeldung.doman.NotificationData;

public interface NotificationService {

	public void initiateNotofication(NotificationData notificationData) throws InterruptedException;
	
}
