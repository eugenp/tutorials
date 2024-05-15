package com.baeldung.web;

import com.baeldung.persistence.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private FooService fooService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("foos", fooService.findAll());
        return "index";
    }

}
