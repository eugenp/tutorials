package com.baeldung.thymeleaf.attribute;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckedAttributeController {

    @GetMapping("/checked")
    public String displayCheckboxForm(Model model) {
        Engine engine = new Engine(true);
        model.addAttribute("engine", engine);
        model.addAttribute("flag", true);
        return "attribute/index";
    }

    private static class Engine {
        private Boolean active;

        public Engine(Boolean active) {
            this.active = active;
        }

        public Boolean getActive() {
            return active;
        }
    }
}
