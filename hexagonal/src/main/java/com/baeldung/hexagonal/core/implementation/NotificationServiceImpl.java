package com.baeldung.hexagonal.core.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.adapter.out.NotificationRepositoryImplementation;
import com.baeldung.hexagonal.core.model.Notification;
import com.baeldung.hexagonal.port.in.NotificationService;
import com.baeldung.hexagonal.port.out.NotificationRepository;

/**
 * Implementation of Service
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void createNotification(Notification notification) {
	notificationRepository.createNotification(notification);

    }

    @Override
    public Notification getNotification(int id) {

	return notificationRepository.getNotification(id);
    }

    @Override
    public List<Notification> getAllNotifications() {

	return notificationRepository.getAllNotifications();
    }

}
