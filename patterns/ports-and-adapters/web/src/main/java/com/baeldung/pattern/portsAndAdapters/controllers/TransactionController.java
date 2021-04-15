package com.baeldung.pattern.portsAndAdapters.controllers;

import com.baeldung.pattern.portsAndAdapters.core.ports.TransactionService;
import com.baeldung.pattern.portsAndAdapters.info.TransactionInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.baeldung.pattern.portsAndAdapters.info.TransactionInfo.toDomain;

@RequestMapping("/")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void saveNewTransaction(@RequestBody TransactionInfo info){
        transactionService.add(toDomain(info));
    }


}
