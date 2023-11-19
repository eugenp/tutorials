package com.baeldung.apifirstdevelopment.controller;

import org.openapitools.api.AccountApi;
import org.openapitools.model.Account;
import org.openapitools.model.DepositRequest;
import org.springframework.http.ResponseEntity;

public class AccountController implements AccountApi {
    @Override
    public ResponseEntity<Void> depositToAccount(DepositRequest depositRequest) {
        //Execute the business logic through Service/Utils/Repository classes
        return AccountApi.super.depositToAccount(depositRequest);
    }

    @Override
    public ResponseEntity<Account> getAccount() {
        //Execute the business logic through Service/Utils/Repository classes
        return AccountApi.super.getAccount();
    }
}
