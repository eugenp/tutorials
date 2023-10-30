package com.baeldung.restclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsLogic accountsLogic;

    @GetMapping("/client-info")
    public Map<String, String> getClientInfo(HttpServletRequest request) {
        return accountsLogic.getClientInfo(request);
    }
}
