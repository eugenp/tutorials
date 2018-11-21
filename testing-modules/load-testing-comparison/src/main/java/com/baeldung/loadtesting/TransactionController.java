package com.baeldung.loadtesting;

import com.baeldung.loadtesting.model.Transaction;
import com.baeldung.loadtesting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Deprecated
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping(path="/addTransaction")
    public @ResponseBody
    String saveTransactions(@RequestBody Transaction trnsctn){
        transactionRepository.save(trnsctn);
        return "Saved Transaction.";
    }

    @GetMapping(path="/findAll/{rewardId}")
    public @ResponseBody Iterable<Transaction> getTransactions(@RequestParam Integer id){
        return transactionRepository.findByCustomerRewardsId(id);
    }
}
