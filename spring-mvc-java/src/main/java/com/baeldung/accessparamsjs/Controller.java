package com.baeldung.accessparamsjs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Sample rest controller for the tutorial article 
 * "Access Spring MVC Model object in JavaScript".
 * 
 * @author Andrew Shcherbakov
 *
 */
@RestController
public class Controller {

    /**
     * Define two model objects (one integer and one string) and pass them to the view.
     *  
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView thymeleafView(Map<String, Object> model) {
        model.put("number", 1234);
        model.put("message", "Hello from Spring MVC");
        return new ModelAndView("thymeleaf/index");
    }

}
