package com.baeldung.thymeleaf.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.thymeleaf.utils.StudentUtils;

@Controller
public class InliningController {

    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String getExampleHTML(Model model) {
        model.addAttribute("title", "Baeldung");
        model.addAttribute("description", "<strong>Thymeleaf</strong> tutorial");
        return "inliningExample.html";
    }

    @RequestMapping(value = "/js", method = RequestMethod.GET)
    public String getExampleJS(Model model) {
        model.addAttribute("students", StudentUtils.buildStudents());
        return "studentCheck.js";
    }

    @RequestMapping(value = "/plain", method = RequestMethod.GET)
    public String getExamplePlain(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("students", StudentUtils.buildStudents());
        return "studentsList.txt";
    }
}
