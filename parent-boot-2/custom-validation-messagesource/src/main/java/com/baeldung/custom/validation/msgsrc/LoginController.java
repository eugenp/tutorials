package com.baeldung.custom.validation.msgsrc;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @GetMapping("hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("loginform")
    public String processLogin(@Valid LoginForm form) {
        return "Success";

    }
}
