package org.baeldung.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return "home";
    }
}
