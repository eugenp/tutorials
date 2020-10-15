package com.baeldung.loadtesting.repository;

import com.baeldung.loadtesting.model.Transaction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TransactionRepository {

    private AtomicInteger transactionIds = new AtomicInteger();
    private final Map<Integer, ConcurrentLinkedDeque<Transaction>> transactionsByCustomerRewardsId = new ConcurrentHashMap<>();

    public List<Transaction> findByCustomerRewardsId(Integer rewardsId) {
        return transactionsByCustomerRewardsId.get(rewardsId).stream().collect(Collectors.toList());
    }

    public Transaction save(Transaction trnsctn) {
        if (trnsctn.getId() == null) {
            trnsctn.setId(transactionIds.getAndIncrement());
        }
        if (trnsctn.getCustomerRewardsId() != null) {
            transactionsByCustomerRewardsId.computeIfAbsent(trnsctn.getCustomerRewardsId(), key -> new ConcurrentLinkedDeque<>()).offer(trnsctn);
        }
        return trnsctn;
    }
}
