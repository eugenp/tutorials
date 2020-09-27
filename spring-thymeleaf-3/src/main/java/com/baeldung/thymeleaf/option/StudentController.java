package com.baeldung.thymeleaf.option;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the student model.
 * 
 */
@Controller
public class StudentController {


    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "option/studentForm.html";
    }


}
