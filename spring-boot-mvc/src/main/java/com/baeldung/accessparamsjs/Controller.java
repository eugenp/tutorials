package com.baeldung.accessparamsjs;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
     * Define two model objects (one integer and one string) and pass them to a jsp view.
     *  
     * @param model
     * @return
     */
    @RequestMapping("/jsp/index")
    public ModelAndView jspView(Map<String, Object> model) {
        model.put("number", 1234);
        model.put("message", "Hello from Spring MVC");
        return new ModelAndView("jsp/index");
    }

    /**
     * Define two model objects (one integer and one string) and pass them to a thymeleaf view.
     *  
     * @param model
     * @return
     */
    @RequestMapping("/thymeleaf/index")
    public ModelAndView thymeleafView(Map<String, Object> model) {
        model.put("number", 1234);
        model.put("message", "Hello from Spring MVC");
        return new ModelAndView("thymeleaf/index");
    }

}
