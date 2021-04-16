package com.baeldung.pattern.portsAndAdapters.controllers;

import com.baeldung.pattern.portsAndAdapters.core.ports.TransactionService;
import com.baeldung.pattern.portsAndAdapters.info.TransactionInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.baeldung.pattern.portsAndAdapters.info.TransactionInfo.toDomain;

@RequestMapping("/")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    int saveNewTransaction(@RequestBody TransactionInfo info) {
        return transactionService.add(toDomain(info));
    }
}
