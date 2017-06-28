package com.baeldung.springsecuredsockets.controllers;

import com.baeldung.springsecuredsockets.transfer.socket.Message;
import com.baeldung.springsecuredsockets.transfer.socket.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static final Logger log = LoggerFactory.getLogger(SocketController.class);
    private static final String SUBSCRIBE = "/secured/history";

    private OutputMessage sendWrapper(Message msg) {
        OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        // Alternatively, you can use this instead of SendTo and returning an Output Message
        //this.simpMessagingTemplate.convertAndSend(SUBSCRIBE, out);
        return out;
    }

    @MessageMapping("/secured/chat")
    @SendTo(SUBSCRIBE)
    public OutputMessage send(Message msg) throws Exception {
        return sendWrapper(msg);
    }
}
