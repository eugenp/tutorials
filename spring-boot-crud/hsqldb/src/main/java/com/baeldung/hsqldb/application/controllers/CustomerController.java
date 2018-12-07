package com.baeldung.hsqldb.application.controllers;

import com.baeldung.hsqldb.application.entities.Customer;
import com.baeldung.hsqldb.application.repositories.CustomerRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @GetMapping("/signup")
    public String showCustomerForm(Customer customer) {
        return "addcustomer";
    }

    @PostMapping("/addcustomer")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addcustomer";
        }
        
        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }
}
