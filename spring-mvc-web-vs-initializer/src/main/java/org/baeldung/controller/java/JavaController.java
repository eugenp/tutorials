package org.baeldung.controller.java;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JavaController {

    @RequestMapping(value = "/endpoint")
    public ModelAndView handleRequestFromJavaConfiguredServlet() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("from-java");

        return mv;
    }

}