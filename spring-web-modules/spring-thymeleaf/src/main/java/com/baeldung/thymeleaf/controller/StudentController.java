package com.baeldung.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.thymeleaf.utils.StudentUtils;

/**
 * Handles requests for the student model.
 * 
 */
@Controller
public class StudentController {

    @RequestMapping(value = "/listStudents", method = RequestMethod.GET)
    public String listStudent(Model model) {

        model.addAttribute("students", StudentUtils.buildStudents());

        return "listStudents.html";
    }

}
