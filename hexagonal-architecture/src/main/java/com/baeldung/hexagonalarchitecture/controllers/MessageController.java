package com.baeldung.hexagonalarchitecture.controllers;


import com.baeldung.hexagonalarchitecture.models.Message;
import com.baeldung.hexagonalarchitecture.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    @Qualifier("FBMessageService")
    MessageService fbMessageService;

    @PostMapping("/fb_message")
    public Message handleFBMessage(@RequestParam("message") String message) {
        logger.debug("Saving fb message {}", message);
        Message fromDB = fbMessageService.saveMessage(message);
        return fromDB;
    }

    @GetMapping("/get_messages")
    public Iterable<Message> getFBMessages(@RequestParam("source") int source) {
        logger.error("getting message by source ", source);
        switch (source) {
            case Message.MESSAGE_SOURCE_FB:
                return fbMessageService.getMessages();
            default:
                throw new IllegalArgumentException("Invalid value for source");
        }
    }
}