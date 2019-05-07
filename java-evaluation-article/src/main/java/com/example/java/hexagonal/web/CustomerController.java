package com.example.java.hexagonal.web;

import com.example.java.hexagonal.dao.CustomerDao;
import com.example.java.hexagonal.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping(path = "/get/{customerId}", produces = "application/json")
    public @ResponseBody
    Customer getCustomerById(@PathVariable String customerId) {
        return customerDao.getById(Long.valueOf(customerId));
    }
}
