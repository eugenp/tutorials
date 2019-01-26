package com.baeldung.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.thymeleaf.model.Course;

/**
 * Handles requests for the student model.
 * 
 */
@Controller
public class CourseRegistrationController {

    @RequestMapping(value = "/registerCourse", method = RequestMethod.POST)
    public String register(@ModelAttribute Course course, Model model) {
        model.addAttribute("successMessage", "You have successfully registered for course: " + course.getName() + ".");
        return "courseRegistration.html";
    }

    @RequestMapping(value = "/registerCourse", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("course", new Course());
        return "courseRegistration.html";
    }

}
