package com.baeldung.web.controller;

import com.baeldung.model.Account;
import com.baeldung.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class AccountController {

    @CrossOrigin
    @RequestMapping("/{id}")
    public Account retrieve(@PathVariable Long id) {
        // Fetch account from DB
        Employee employee = new Employee(1L, "John", "223334411", "rh");
        Account accountTest = new Account(id, employee);
        return accountTest;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void remove(@PathVariable Long id) {
       // Removing account from DB
    }

}
