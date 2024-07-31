package com.baeldung.controllerprefix;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@Controller
@RequestMapping("/api")
public class ApiPrefixController {

    @GetMapping
    public ModelAndView route(ModelMap model) {
        if(new Random().nextBoolean()) {
            return new ModelAndView("forward:/endpoint1", model);
        }
        else {
            return new ModelAndView("forward:/endpoint2", model);
        }
    }
}

