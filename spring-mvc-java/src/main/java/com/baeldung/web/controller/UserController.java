package com.baeldung.web.controller;

import com.baeldung.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showForm(final Model model) {
        final User user = new User();
        user.setFirstname("John");
        user.setLastname("Roy");
        user.setEmailId("John.Roy@gmail.com");
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processForm(@ModelAttribute(value = "user") final User user, final Model model) {
        // Insert User into DB
        model.addAttribute("name", user.getFirstname() + " " + user.getLastname());
        return "hello";
    }

}
