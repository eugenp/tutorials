package com.baeldung.thymeleaf.errors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserValidationService validationService;

    @GetMapping("/add")
    public String showAddUserForm(User user) {
        return "errors/addUser";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        String err = validationService.validateUser(user);

        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "errors/addUser";
        }

        repository.save(user);
        model.addAttribute("users", repository.findAll());
        return "errors/home";
    }

}
