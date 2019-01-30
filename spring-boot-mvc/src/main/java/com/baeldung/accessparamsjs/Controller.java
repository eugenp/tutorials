package com.baeldung.accessparamsjs;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {

    @RequestMapping("/index")
    public ModelAndView index(Map<String, Object> model) {
        model.put("number", 1234);
        model.put("message", "Hello from Spring MVC");
        return new ModelAndView("/index");
    }

}
