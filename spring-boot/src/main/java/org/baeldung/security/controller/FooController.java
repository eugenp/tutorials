package org.baeldung.security.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FooController {

    
    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("message", "HELLO");
        return "home";
    }

    @RequestMapping(value = "/admin")
    public String adminPage(Model model, Principal user) {
        model.addAttribute("username", user.getName());

        return "admin";

    }

    // for 403 access denied page
    @RequestMapping(value = "/403")
    public String accesssDenied(Model model, Principal user) {
        model.addAttribute("message", "Hi " + user.getName() + ", you do not have permission to access this page!");
        return "accessDenied";
    }
}
