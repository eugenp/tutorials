package com.baeldung.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.model.NewUserForm;

@Controller
public class NewUserController {

    @GetMapping("/user")
    public String loadFormPage(Model model) {
        model.addAttribute("newUserForm", new NewUserForm());
        return "userHome";
    }

    @PostMapping("/user")
    public String submitForm(@Valid NewUserForm newUserForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "userHome";
        }

        model.addAttribute("message", "Valid form");
        return "userHome";
    }
}
