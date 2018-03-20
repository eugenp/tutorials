package com.baeldung.springmvcforms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.springmvcforms.domain.Customer;
import com.baeldung.springmvcforms.validator.CustomerValidator;

@Controller
public class CustomerController {

    @Autowired
    CustomerValidator validator;

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("customerHome", "customer", new Customer());
    }

    @PostMapping("/addCustomer")
    public String submit(@Valid @ModelAttribute("customer") final Customer customer, final BindingResult result, final ModelMap model) {
        validator.validate(customer, result);
        if (result.hasErrors()) {
            return "customerHome";
        }
        model.addAttribute("customerId", customer.getCustomerId());
        model.addAttribute("customerName", customer.getCustomerName());
        model.addAttribute("customerContact", customer.getCustomerContact());
        model.addAttribute("customerEmail", customer.getCustomerEmail());
        return "customerView";
    }

}
