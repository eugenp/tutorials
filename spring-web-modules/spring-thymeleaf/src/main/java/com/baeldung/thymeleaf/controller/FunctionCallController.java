package com.baeldung.thymeleaf.controller;

import com.baeldung.thymeleaf.utils.StudentUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FunctionCallController {

    @RequestMapping(value = "/function-call", method = RequestMethod.GET)
    public String getExampleHTML(Model model) {
        model.addAttribute("totalStudents", StudentUtils.buildStudents().size());
        model.addAttribute("student", StudentUtils.buildStudents().get(0));
        return "functionCall.html";
    }
}
