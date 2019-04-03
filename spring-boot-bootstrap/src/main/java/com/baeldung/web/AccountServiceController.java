package com.baeldung.web;
/*
 * created by pareshP on 02/04/19
 */


import com.baeldung.persistence.model.Account;
import com.baeldung.web.helper.StorageAdapterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountServiceController {

    private StorageAdapterProvider adapterProvider;

    public AccountServiceController(@Autowired StorageAdapterProvider adapterProvider) {
        this.adapterProvider = adapterProvider;
    }

    @PutMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody Account account) {
        adapterProvider.getStorageAdapter().createAccount(account);
    }

    @GetMapping(value = "/get/{accountId}")
    public Account returnAccount(@PathVariable String accountId) {
        return adapterProvider.getStorageAdapter().returnAccount(accountId);
    }

    @DeleteMapping(value = "/delete/{accountId}")
    public boolean deleteAccount(@PathVariable String accountId) {
        return adapterProvider.getStorageAdapter().deleteAccount(accountId);
    }

    @PostMapping(value = "/update")
    public Account updateAccount(@RequestBody Account account) {
        return adapterProvider.getStorageAdapter().updateAccount(account);
    }
}
