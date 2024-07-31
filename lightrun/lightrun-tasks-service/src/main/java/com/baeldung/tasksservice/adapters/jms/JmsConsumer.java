package com.baeldung.tasksservice.adapters.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.baeldung.tasksservice.service.DeletedUserService;

@Service
public class JmsConsumer {
    @Autowired
    private DeletedUserService deletedUserService;

    @JmsListener(destination = "deleted_user")
    public void receive(String user) {
        deletedUserService.handleDeletedUser(user);
    }
}
