package com.baeldung.hexagonal.miner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.baeldung.hexagonal.domain.Transaction;

public class ProductMiner implements  Miner {

    private List<String> keywords;

    private Predicate<Transaction> checkForKeywords = t -> {
        for(String k : keywords) {
            if (t.getDescription()
                    .toLowerCase()
                    .contains(k)
                    || t.getToAccount()
                    .toLowerCase()
                    .contains(k)) {
                return true;
            }
        }
        return false;
    };

    public ProductMiner(String ... keywords) {

        this.keywords = new ArrayList<>();
        this.keywords.addAll(Arrays.asList(keywords));
    }

    public List<Transaction> mine(List<Transaction> transactions) {

        return transactions.stream()
                .filter(checkForKeywords)
                .collect(Collectors.toList());
    }
}
