package com.baeldung.spring.controller;

import com.baeldung.spring.domain.Message;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.logging.Logger;

@Controller
public class MessageController {

    @GetMapping({"/"})
    String index(ModelMap model) {
        model.put("message", new Message());
        return "hello";
    }

    @PostMapping({"/welcome"})
    String indexHello(@Valid Message message) {
        Logger logger = Logger.getLogger(MessageController.class.getName());
        logger.info("Message: " + message.getText());
        return "hello";
    }
}
