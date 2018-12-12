package com.baeldung.hexagonal.miner;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.baeldung.hexagonal.domain.Transaction;

public class FraudMiner implements Miner {

    private Predicate<Transaction> foreignCurrency = t -> !t.getCurrency()
            .equals("EUR");
    private Predicate<Transaction> suspiciousDescription = t -> t.getDescription()
            .contains("suspicious");
    private Predicate<Transaction> bigValue = t -> t.getValue() > 40000;

    public List<Transaction> mine(List<Transaction> transactions) {
        return findFraudulentTranscation(transactions);
    }

    private List<Transaction> findFraudulentTranscation(List<Transaction> transactions) {
        return transactions.stream()
                .filter(foreignCurrency.or(suspiciousDescription)
                        .or(bigValue))
                .collect(Collectors.toList());
    }
}
