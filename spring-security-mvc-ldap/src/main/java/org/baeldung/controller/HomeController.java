package org.baeldung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/homepage.html")
    public @ResponseBody String index() {    	
    	return "homepage";
    }
}

