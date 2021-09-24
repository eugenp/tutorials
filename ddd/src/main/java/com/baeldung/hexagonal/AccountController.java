package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.AccountDto;
import com.baeldung.hexagonal.ports.api.AccountServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServicePort accountServicePort;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountServicePort.addAccount(accountDto));
    }
}
