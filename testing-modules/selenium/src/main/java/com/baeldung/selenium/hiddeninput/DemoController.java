package com.baeldung.selenium.hiddeninput;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    @GetMapping("/demo")
    public String showForm() {
        return "index";
    }

    @PostMapping(value = "/submit")
    public String handleSubmit(
      @RequestParam String username,
      @RequestParam String password,
      @RequestParam String gender,
      @RequestParam String dateOfBirth,
      @RequestParam String hiddenInput
    ) {
        System.out.println("Username: "+username);
        System.out.println("Password: "+password);
        System.out.println("Gender: "+gender);
        System.out.println("DateOfBirth: "+dateOfBirth);
        System.out.println("HiddenInput: "+hiddenInput);
        return "redirect:/result.html";
    }
}

