package com.baeldung.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.spring.domain.User;

@Controller
public class UserController {

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @GetMapping("/registration-thymeleaf")
    public String getRegistrationThymeleaf(Model model) {
        model.addAttribute("user", new User());
        return "registration-thymeleaf";
    }
    
    @GetMapping("/registration-freemarker")
    public String getRegistrationFreemarker(Model model) {
        model.addAttribute("user", new User());
        return "registration-freemarker";
    }
    
    @GetMapping("/registration-groovy")
    public String getRegistrationGroovy(Model model) {
        model.addAttribute("user", new User());
        return "registration-groovy";
    }
    
    @GetMapping("/registration-jade")
    public String getRegistrationJade(Model model) {
        model.addAttribute("user", new User());
        return "registration-jade";
    }
    
    @PostMapping("/register")
    @ResponseBody
    public void register(User user){
        System.out.println(user.getEmail());
    }

}
