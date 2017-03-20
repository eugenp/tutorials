package com.baeldung.dispatcherexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ExampleController {

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public String modelExample(Model model, @RequestParam(value = "text") String yourText) {
        model.addAttribute("yourText", yourText);
        return "ExampleOne";
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public void responseExampleTwo (HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(value = "text") String name) {
        try {
            request.setAttribute("name", name);
            request.getRequestDispatcher("ExampleTwo.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @GetMapping("/new")
    public String newExample(ModelMap model, @RequestParam(value = "text") String hey) {
        model.addAttribute("hey", hey);
        return "ExampleThree";
    }
}

