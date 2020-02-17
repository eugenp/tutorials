package com.baeldung.hexagonal.adapter.in;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.model.Notification;
import com.baeldung.hexagonal.port.in.NotificationService;
import com.baeldung.hexagonal.web.NotificationWebInterface;

/**
 * Input adapter : Controller class for {@link: Notification}
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */

@RestController
@RequestMapping("/notification")
public class NotificationController implements NotificationWebInterface {

    @Autowired
    NotificationService notificationService;

    @Override
    public void createNotification(Notification notification) {
	notificationService.createNotification(notification);

    }

    @Override
    public Notification getNotification(int id) {
	return notificationService.getNotification(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
	return notificationService.getAllNotifications();
    }

}
