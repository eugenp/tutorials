package com.baeldung.springamqpsimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MessageController {

    private final MessageProducer messageProducer;

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @RequestMapping(value="/messages", method= RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public void sendMessage(@RequestBody String message) {
        messageProducer.sendMessage(message);
    }
}
