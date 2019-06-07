package com.baeldung.hexagonal.application.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.baeldung.hexagonal.domain.service.UserService;

@Controller
public class UserWebController {

    @Autowired
    UserService userService;

    @GetMapping("web/users")
    public String getUserInfo(Model model) {
        model.addAttribute("user", userService.getUser());
        return "users";
    }

}
