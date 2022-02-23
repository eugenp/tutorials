package com.baeldung.wrapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo(Model model) {
        return "index";
    }

}
