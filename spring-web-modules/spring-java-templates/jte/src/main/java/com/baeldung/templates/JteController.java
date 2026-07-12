package com.baeldung.templates;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JteController {
    @GetMapping("/jte")
    public String view(Model model) {
        model.addAttribute("model", new JteModel("JTE"));
        return "JteDemo";
    }
}
