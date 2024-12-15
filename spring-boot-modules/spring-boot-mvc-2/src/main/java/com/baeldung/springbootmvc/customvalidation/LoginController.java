package com.baeldung.springbootmvc.customvalidation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springbootmvc.customvalidation.model.LoginForm;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class LoginController {

    @PostMapping("loginform")
    public String processLogin(@Valid LoginForm form) {
        return "Success";

    }
}
