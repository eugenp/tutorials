package com.baeldung.dynamicvalidation.config;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;
import com.baeldung.dynamicvalidation.model.Customer;

@Controller
public class CustomerController {

    @Autowired
    private ContactInfoExpressionRepository expressionRepository;

    @GetMapping("/customer")
    public String getCustomerPage(Model model) {
        model.addAttribute("contactInfoType", System.getProperty("contactInfoType"));
        return "customer";
    }

    @PostMapping("/customer")
    public String validateCustomer(@Valid final Customer customer, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "The information is invalid!");
        } else {
            model.addAttribute("message", "The information is valid!");
        }
        model.addAttribute("contactInfoType", System.getProperty("contactInfoType"));
        return "customer";
    }

    @PostMapping("/updateContactInfoType")
    @ResponseBody
    public void updateContactInfoType(@RequestParam final String type) {
        System.setProperty("contactInfoType", type);
    }

    @GetMapping("/contactInfoTypes")
    @ResponseBody
    public List<ContactInfoExpression> getContactInfoType(Model model) {
        return expressionRepository.findAll();
    }

}
