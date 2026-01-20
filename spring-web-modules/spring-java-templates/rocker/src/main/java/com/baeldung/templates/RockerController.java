package com.baeldung.templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RockerController {
    @GetMapping("/rocker")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView(new RockerView("RockerDemo.rocker.html"));
        modelAndView.addObject("model", new RockerModel("Baeldung"));

        return modelAndView;
    }
}
