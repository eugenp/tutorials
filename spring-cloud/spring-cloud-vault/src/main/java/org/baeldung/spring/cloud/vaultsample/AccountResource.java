package org.baeldung.spring.cloud.vaultsample;

import org.baeldung.spring.cloud.vaultsample.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountResource {
    @Autowired
    private AccountRepo repo;

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        
        Account acc = repo.findById(id).orElse(null);
        if ( acc != null ) {
            return new ResponseEntity<Account>(acc, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
    } 
}
