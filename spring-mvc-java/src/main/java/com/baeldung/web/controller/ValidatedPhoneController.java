package com.baeldung.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.model.ValidatedPhone;

@Controller
@EnableWebMvc
public class ValidatedPhoneController {

    @RequestMapping(value = "/validatePhone", method = RequestMethod.GET)
    public String loadFormPage(Model m) {
        m.addAttribute("validatedPhone", new ValidatedPhone());
        return "phoneHome";
    }

    @RequestMapping(value = "/addValidatePhone", method = RequestMethod.POST)
    public String submitForm(@Valid ValidatedPhone validatedPhone, BindingResult result, Model m) {
        if (result.hasErrors()) {
            return "phoneHome";
        }

        m.addAttribute("message", "Successfully saved phone: " + validatedPhone.toString());
        return "phoneHome";
    }


}
