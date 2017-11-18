package org.baeldung.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController2 {

    public SecurityController2() {
        super();
    }

    // API

    @RequestMapping(value = "/username2", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(final Principal principal) {
        return principal.getName();
    }

}
