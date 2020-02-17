package com.baeldung.hexagonal.port.in;

import java.util.List;

import com.baeldung.hexagonal.core.model.Notification;

/**
 * Input port : service interface
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */
public interface NotificationService {

    void createNotification(Notification notification);

    Notification getNotification(int id);

    List<Notification> getAllNotifications();

}
