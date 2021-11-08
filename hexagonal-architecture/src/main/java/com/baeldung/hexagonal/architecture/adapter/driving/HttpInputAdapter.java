package com.baeldung.hexagonal.architecture.adapter.driving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.domain.dto.Account;
import com.baeldung.hexagonal.architecture.port.SignUpServiceInputPort;


@RestController
public class HttpInputAdapter {
    
    @Autowired
    SignUpServiceInputPort signupService;

    @GetMapping("/signup")
    public void signup(@RequestParam(value = "firstName") String firstName,
        @RequestParam(value = "lastName") String lastName) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        signupService.signup(account);
    }


}
