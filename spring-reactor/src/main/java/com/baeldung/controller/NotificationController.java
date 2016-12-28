package com.baeldung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.doman.NotificationData;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Controller
public class NotificationController {

    @Autowired
    private EventBus eventBus;

    @RequestMapping(value = "/startNotification/{param}", method = RequestMethod.GET)
    public void startNotification(@PathVariable("param") String param) {

        int notificationSize = Integer.parseInt(param);

        for (int i = 0; i < notificationSize; i++) {

            NotificationData data = new NotificationData();
            data.setId(i);

            eventBus.notify("notificationConsumer", Event.wrap(data));

            System.out.println("Notification " + i + ": notification task submitted successfully");
        }

    }

}
