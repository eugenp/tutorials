package com.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    public RootController() {
        super();
    }

    // API

    @RequestMapping(value = "/admin/x", method = RequestMethod.GET)
    @ResponseBody
    public String sampleAdminPage() {
        return "Hello";
    }


    @RequestMapping(value = "/my-error-page", method = RequestMethod.GET)
    @ResponseBody
    public String sampleErrorPage() {
        return "Error Occurred";
    }

}
