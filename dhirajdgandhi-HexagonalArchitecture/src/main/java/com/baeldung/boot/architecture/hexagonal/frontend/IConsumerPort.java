package com.baeldung.boot.architecture.hexagonal.frontend;

import com.baeldung.boot.architecture.hexagonal.entity.Transaction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IConsumerPort {

    @RequestMapping("/add")
    int addTransaction(@RequestBody Transaction transaction);

    @RequestMapping("/view/{transactionId}")
    String viewTransaction(@PathVariable int transactionId);
}
