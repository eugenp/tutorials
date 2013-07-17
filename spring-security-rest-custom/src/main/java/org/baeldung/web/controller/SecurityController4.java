package org.baeldung.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController4 {

    public SecurityController4() {
        super();
    }

    // API

    @RequestMapping(value = "/username4", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(final HttpServletRequest request) {
        final Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

}
