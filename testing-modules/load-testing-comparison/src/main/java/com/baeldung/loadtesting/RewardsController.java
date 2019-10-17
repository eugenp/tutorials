package com.baeldung.loadtesting;

import com.baeldung.loadtesting.model.CustomerRewardsAccount;
import com.baeldung.loadtesting.model.Transaction;
import com.baeldung.loadtesting.repository.CustomerRewardsRepository;
import com.baeldung.loadtesting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
public class RewardsController {

    @Autowired
    private CustomerRewardsRepository customerRewardsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping(path="/transactions/add")
    public @ResponseBody Transaction saveTransactions(@RequestBody Transaction trnsctn){
        trnsctn.setTransactionDate(Calendar.getInstance().getTime());
        Transaction result = transactionRepository.save(trnsctn);
        return result;
    }

    @GetMapping(path="/transactions/findAll/{rewardId}")
    public @ResponseBody Iterable<Transaction> getTransactions(@PathVariable Integer rewardId){
        return transactionRepository.findByCustomerRewardsId(rewardId);
    }

    @PostMapping(path="/rewards/add")
    public @ResponseBody CustomerRewardsAccount addRewardsAcount(@RequestBody CustomerRewardsAccount body) {
        Optional<CustomerRewardsAccount> acct = customerRewardsRepository.findByCustomerId(body.getCustomerId());
        return !acct.isPresent() ? customerRewardsRepository.save(body) : acct.get();
    }

    @GetMapping(path="/rewards/find/{customerId}")
    public @ResponseBody
    Optional<CustomerRewardsAccount> find(@PathVariable Integer customerId) {
        return customerRewardsRepository.findByCustomerId(customerId);
    }

    @GetMapping(path="/rewards/all")
    public @ResponseBody List<CustomerRewardsAccount> findAll() {
        return customerRewardsRepository.findAll();
    }
}
