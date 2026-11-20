package com.baeldung.redirect404;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHome(){
        return "forward:/index.html";
    }

    @GetMapping("/user")
    public String getUsers(){
        return "forward:/user.html";
    }

}
