package com.baeldung.web.controller;

import com.baeldung.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("/")
    public String showForm(final Model model) {
        final User user = new User();
        user.setFirstname("John");
        user.setLastname("Roy");
        user.setEmailId("John.Roy@gmail.com");
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute(value = "user") final User user, final Model model) {
        // Insert User into DB
        model.addAttribute("name", user.getFirstname() + " " + user.getLastname());
        return "hello";
    }

}
