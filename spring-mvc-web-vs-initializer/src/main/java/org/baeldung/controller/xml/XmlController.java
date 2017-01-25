package org.baeldung.controller.xml;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class XmlController {

    @RequestMapping(value = "/endpoint")
    public ModelAndView handleRequestFromXmlConfiguredServlet() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("from-xml");

        return mv;
    }

}