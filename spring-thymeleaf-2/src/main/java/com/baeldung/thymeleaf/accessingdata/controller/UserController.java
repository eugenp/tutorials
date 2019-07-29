package com.baeldung.thymeleaf.accessingdata.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final ServletContext servletContext;

    public UserController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @ModelAttribute("username")
    public String usernameAttribute() {
        return "john";
    }

    @GetMapping("/getuser")
    public String getUser(Model model) {
        model.addAttribute("username", "john");
        return "accessingdata/user";
    }

    @GetMapping("/getuser2")
    public String getUser2(ModelMap modelMap) {
        modelMap.addAttribute("username", "john");
        return "accessingdata/user";
    }

    @GetMapping("/getuser3")
    public ModelAndView getUser3() {
        ModelAndView modelAndView = new ModelAndView("accessingdata/user");
        modelAndView.addObject("username", "john");
        return modelAndView;
    }

    @GetMapping("/getsessiondata")
    public String getDataFromSession(HttpSession httpSession) {
        httpSession.setAttribute("message", "Hello from Session!");
        return "accessingdata/sessiondata";
    }

    @GetMapping("/getservletcontextdata")
    public String getDataFromServletContext() {
        servletContext.setAttribute("message", "Hello from ServletContext!");
        return "accessingdata/servletcontextdata";
    }
}
