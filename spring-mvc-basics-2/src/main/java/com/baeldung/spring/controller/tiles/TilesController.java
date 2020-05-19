package com.baeldung.spring.controller.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TilesController {

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        return "home";
    }

    @RequestMapping(value = { "/apachetiles" }, method = RequestMethod.GET)
    public String productsPage(ModelMap model) {
        return "apachetiles";
    }

    @RequestMapping(value = { "/springmvc" }, method = RequestMethod.GET)
    public String contactUsPage(ModelMap model) {
        return "springmvc";
    }
}
