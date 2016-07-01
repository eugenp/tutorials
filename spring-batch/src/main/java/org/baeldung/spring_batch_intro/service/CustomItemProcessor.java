package org.baeldung.spring_batch_intro.service;

import org.baeldung.spring_batch_intro.model.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Transaction, Transaction> {

    public Transaction process(Transaction item) {
        System.out.println("Processing..." + item);
        return item;
    }
}