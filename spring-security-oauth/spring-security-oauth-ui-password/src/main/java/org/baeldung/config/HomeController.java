package org.baeldung.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET, value = "/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public void getRefreshToken(@CookieValue(value = "refreshToken", defaultValue = "") String cookie, HttpServletResponse response) {
        response.addHeader("refreshToken", cookie);
    }
}
