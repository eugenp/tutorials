package com.baeldung.springamqpsimple.broadcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BroadcastMessageController {

    private final BroadcastMessageProducer messageProducer;

    @Autowired
    public BroadcastMessageController(BroadcastMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @RequestMapping(value="/broadcast", method= RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public void sendMessage(@RequestBody String message) {
        messageProducer.sendMessages(message);
    }
}
