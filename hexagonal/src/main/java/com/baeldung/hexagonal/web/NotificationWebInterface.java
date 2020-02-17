package com.baeldung.hexagonal.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.core.model.Notification;

/**
 * Web interface
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */
public interface NotificationWebInterface {

    @PostMapping
    void createNotification(@RequestBody Notification notification);

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable int id);

    @GetMapping
    public List<Notification> getAllNotifications();
}
