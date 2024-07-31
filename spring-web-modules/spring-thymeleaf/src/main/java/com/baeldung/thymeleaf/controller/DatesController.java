package com.baeldung.thymeleaf.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baeldung.thymeleaf.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DatesController {

    @RequestMapping(value = "/dates", method = RequestMethod.GET)
    public String getInfo(Model model) {
        model.addAttribute("standardDate", new Date());
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("timestamp", Instant.now());
        return "dates.html";
    }

    @RequestMapping(value = "/saveStudent", method = RequestMethod.GET)
    public String displaySaveStudent(Model model) {
        model.addAttribute("student", new Student());
        return "datePicker/saveStudent.html";
    }

    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public String saveStudent(Model model, @ModelAttribute("student") Student student) {
        model.addAttribute("student", student);

        return "datePicker/displayDate.html";
    }

}
