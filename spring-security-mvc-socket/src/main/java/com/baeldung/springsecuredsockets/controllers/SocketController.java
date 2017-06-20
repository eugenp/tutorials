package com.baeldung.springsecuredsockets.controllers;

import com.baeldung.springsecuredsockets.transfer.socket.Greeting;
import com.baeldung.springsecuredsockets.transfer.socket.Message;
import com.baeldung.springsecuredsockets.transfer.socket.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SocketController {

    @MessageMapping("/secured/chat")
    @SendTo("/secured/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @MessageMapping("/secured/hello")
    @SendTo("/secured/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        return new Greeting("Hello, " + message.getFrom() + "!");
    }
}
