package com.baeldung.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.baeldung.model.LoginData;

import java.util.Collections;

@Controller
@RequestMapping("/internals")
public class InternalsController {

    private static final String LOGIN = "jack";
    private static final String PASSWORD = "halloween";

    @GetMapping("/")
    public String hello() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(LoginData loginData) {
        if (LOGIN.equals(loginData.getLogin()) && PASSWORD.equals(loginData.getPassword())) {
            return new ModelAndView("success", Collections.singletonMap("login", loginData.getLogin()));
        } else {
            return new ModelAndView("failure", Collections.singletonMap("login", loginData.getLogin()));
        }
    }

}
