package com.baeldung.loadtesting;

import com.baeldung.loadtesting.model.CustomerRewardsAccount;
import com.baeldung.loadtesting.model.Transaction;
import com.baeldung.loadtesting.repository.CustomerRewardsRepository;
import com.baeldung.loadtesting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class RewardsController {

    private CustomerRewardsRepository customerRewardsRepository = new CustomerRewardsRepository();

    private TransactionRepository transactionRepository = new TransactionRepository();

    @PostMapping(path="/transactions/add")
    public @ResponseBody Transaction saveTransactions(@RequestBody Transaction trnsctn){
        if (trnsctn.getTransactionDate() == null) {
            trnsctn.setTransactionDate(new Date());
        }
        return transactionRepository.save(trnsctn);
    }

    @GetMapping(path="/transactions/findAll/{rewardId}")
    public @ResponseBody List<Transaction> getTransactions(@PathVariable Integer rewardId){
        return transactionRepository.findByCustomerRewardsId(rewardId);
    }

    @PostMapping(path="/rewards/add")
    public @ResponseBody CustomerRewardsAccount addRewardsAccount(@RequestBody CustomerRewardsAccount body) {
        Optional<CustomerRewardsAccount> acct = customerRewardsRepository.findByCustomerId(body.getCustomerId());
        return !acct.isPresent() ? customerRewardsRepository.save(body) : acct.get();
    }

    @GetMapping(path="/rewards/find/{customerId}")
    public @ResponseBody
    Optional<CustomerRewardsAccount> find(@PathVariable Integer customerId) {
        return customerRewardsRepository.findByCustomerId(customerId);
    }
}
