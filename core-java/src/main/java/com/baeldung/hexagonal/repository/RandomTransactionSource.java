package com.baeldung.hexagonal.repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baeldung.hexagonal.domain.Transaction;

public class RandomTransactionSource implements TransactionSource {

    private List<Transaction> transactions;
    private Random randomGenerator = new Random();

    public RandomTransactionSource(int size) {

        transactions = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            transactions.add(createRandom());
        }
    }

    private Transaction createRandom() {
        Transaction transaction = new Transaction();

        String currency = randomGenerator.nextInt(10) > 2 ? "EUR" : "RUB";
        transaction.setCurrency(currency);

        String description = randomGenerator.nextInt(10) > 2 ? "Normal transaction" : "I am suspicious";
        transaction.setDescription(description);

        String from = randomGenerator.nextInt(10) > 2 ? "Ordinary person" : "I try to hide";
        transaction.setFromAccount(from);

        String to = randomGenerator.nextInt(10) > 2 ? "Simple person" : "Nobody must know about me";
        transaction.setToAccount(to);

        int daysInPast = randomGenerator.nextInt(100);
        transaction.setTimestamp(ZonedDateTime.now().minusDays(daysInPast));

        int value = randomGenerator.nextInt(50000);
        transaction.setValue(value);

        return transaction;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
