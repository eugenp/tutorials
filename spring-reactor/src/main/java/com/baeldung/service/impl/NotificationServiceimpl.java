package com.baeldung.service.impl;

import org.springframework.stereotype.Service;

import com.baeldung.doman.NotificationData;
import com.baeldung.service.NotificationService;

@Service
public class NotificationServiceimpl implements NotificationService {
	
	@Override
	public void initiateNotofication(NotificationData notificationData) {

		System.out.println("Notification service started for Notification ID: " +notificationData.getId());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Notification service ended for Notification ID: " +notificationData.getId());
	}

}
