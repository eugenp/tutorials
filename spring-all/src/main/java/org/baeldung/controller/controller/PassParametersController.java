package org.baeldung.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * In this controller, Model, ModelMap and ModelAndView are shown as examples.
 * They are all used to pass parameters to JSP pages.
 * 04/09/2017
 *
 * @author Ahmet Cetin
 */
@Controller
public class PassParametersController {
    @RequestMapping(value = "/showViewPage", method = RequestMethod.GET)
    public String passParametersWithModel(Model model) {
        model.addAttribute("message", "Baeldung");
        return "viewPage";
    }

    @RequestMapping(value = "/printViewPage", method = RequestMethod.GET)
    public String passParametersWithModelMap(ModelMap map) {
        map.addAttribute("welcomeMessage", "welcome");
        map.addAttribute("message", "Baeldung");
        return "viewPage";
    }

    @RequestMapping(value = "/goToViewPage", method = RequestMethod.GET)
    public ModelAndView passParametersWithModelAndView() {
        ModelAndView modelAndView = new ModelAndView("viewPage");
        modelAndView.addObject("message", "Baeldung");
        return modelAndView;
    }
}