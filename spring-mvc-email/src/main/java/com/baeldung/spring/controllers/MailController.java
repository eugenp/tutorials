package com.baeldung.spring.controllers;

import com.baeldung.spring.mail.EmailServiceImpl;
import com.baeldung.spring.web.dto.MailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Olga on 7/20/2016.
 */
@Controller
@RequestMapping("/mail")
public class MailController {
    @Autowired
    public EmailServiceImpl emailService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String createMail(Model model) {
        model.addAttribute("mailObject", new MailObject());
        return "mail/send";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        emailService.sendSimpleMessage("to@gmail.com", "Test Subject", "Test Message");

        return "redirect:/home";
    }
}
