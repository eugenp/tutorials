package com.baeldung.cucumber.tags.controller.ignorescenarios;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeBasedGreeterController {

    @GetMapping("/greetings")
    @ResponseBody
    public String greet(@RequestParam("hours") String hours) {
        String greeting;

        int currentHour = Integer.parseInt(hours.substring(0, 2));

        if (currentHour >= 6 && currentHour < 12) {
            greeting = "Good Morning!";
        } else if (currentHour >= 12 && currentHour < 18) {
            greeting = "Good Afternoon!";
        } else if (currentHour >= 18 && currentHour < 24) {
            greeting = "Good Evening!";
        } else {
            greeting = "Good Night!";
        }

        return greeting;
    }

}