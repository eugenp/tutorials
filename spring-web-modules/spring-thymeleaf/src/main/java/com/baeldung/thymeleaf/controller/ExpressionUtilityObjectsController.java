package com.baeldung.thymeleaf.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to test expression utility objects: dates,
 * 
 */
@Controller
public class ExpressionUtilityObjectsController {

    @RequestMapping(value = "/objects", method = RequestMethod.GET)
    public String getDates(Model model) {
        model.addAttribute("date", new Date());
        model.addAttribute("calendar", Calendar.getInstance());
        model.addAttribute("num", Math.random() * 10);
        model.addAttribute("string", "new text");
        model.addAttribute("emptyString", "");
        model.addAttribute("nullString", null);
        model.addAttribute("array", new int[] { 1, 3, 4, 5 });
        model.addAttribute("set", new HashSet<Integer>(Arrays.asList(1, 3, 8)));
        return "objects.html";
    }

}
