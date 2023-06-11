package com.baeldung.customvalidator;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ValidatedPhoneController {

    @GetMapping("/validatePhone")
    public String loadFormPage(Model m) {
        m.addAttribute("validatedPhone", new ValidatedPhone());
        return "phoneHome";
    }

    @PostMapping("/addValidatePhone")
    public String submitForm(@Valid ValidatedPhone validatedPhone, BindingResult result, Model m) {
        if (result.hasErrors()) {
            return "phoneHome";
        }

        m.addAttribute("message", "Successfully saved phone: " + validatedPhone.toString());
        return "phoneHome";
    }

}
