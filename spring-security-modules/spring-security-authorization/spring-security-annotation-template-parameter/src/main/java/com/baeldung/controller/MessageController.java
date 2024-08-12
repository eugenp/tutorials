package com.baeldung.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Message;
import com.baeldung.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{id}")
    public Message readMessage(@PathVariable Integer id) {
        return messageService.readMessage(id);
    }

    @PostMapping
    public String writeMessage(@RequestBody Message message) {
        return messageService.writeMessage(message);
    }
    
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Integer id) {
        return messageService.deleteMessage(id);
    }
}
