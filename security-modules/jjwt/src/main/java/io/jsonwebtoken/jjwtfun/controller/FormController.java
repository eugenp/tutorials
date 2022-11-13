package io.jsonwebtoken.jjwtfun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class FormController {

    @RequestMapping(value = "/jwt-csrf-form", method = GET)
    public String csrfFormGet() {
        return "jwt-csrf-form";
    }

    @RequestMapping(value = "/jwt-csrf-form", method = POST)
    public String csrfFormPost(@RequestParam(name = "_csrf") String csrf, Model model) {
        model.addAttribute("csrf", csrf);
        return "jwt-csrf-form-result";
    }

    @RequestMapping("/expired-jwt")
    public String expiredJwt() {
        return "expired-jwt";
    }
}
