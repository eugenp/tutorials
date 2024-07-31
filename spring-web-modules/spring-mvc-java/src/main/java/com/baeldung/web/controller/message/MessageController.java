package com.baeldung.web.controller.message;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/message")
public class MessageController {

    @RequestMapping(value = "/showForm", method = RequestMethod.GET)
    public String showForm() {
        return "message";
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processForm(@RequestParam("message") final String message, final Model model) {
        model.addAttribute("message", message);
        return "message";
    }

}
