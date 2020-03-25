package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.ReceiveMessagesService;
import com.baeldung.hexagonal.adapters.SendMessageService;
import com.baeldung.hexagonal.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baeldung.hexagonal.domain.Message;

import java.util.List;

@Controller
public class MessagesController {

    private SendMessageService sendMessageService;

    private ReceiveMessagesService receiveMessagesService;

    public MessagesController(SendMessageService sendMessageService, ReceiveMessagesService receiveMessagesService) {
        this.sendMessageService = sendMessageService;
        this.receiveMessagesService = receiveMessagesService;
    }

    @PostMapping(path = "/chat/send/{user}")
    @ResponseBody
    boolean sendMessage(@PathVariable("user") User user, @RequestBody Message message) {
        return sendMessageService.sendMessage(user, message);
    }

    @GetMapping(path = "/chat/receive/{user}")
    @ResponseBody
    List<Message> receiveMessages(@PathVariable("user") User user) {
        return receiveMessagesService.receiveMessages(user);
    }
}
