package com.baeldung.thymeleaf.methods;

import java.time.Instant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MethodsController {
    @RequestMapping(value = "/methods", method = RequestMethod.GET)
    public String getHome(Model model) {
        model.addAttribute("methodsModel", new MethodsModel("Baeldung"));
        return "methods.html";
    }

    public static class MethodsModel {
        private final String theName;

        public MethodsModel(String theName) {
            this.theName = theName;
        }

        public String getName() {
            return theName;
        }

        public String buildUppercaseName() {
            return getName().toUpperCase();
        }

        public String getNameSubstring(int index) {
            return getName().substring(index);
        }

        public Instant getNow() {
            return Instant.parse("2026-01-29T12:34:56Z");
        }
    }
}
