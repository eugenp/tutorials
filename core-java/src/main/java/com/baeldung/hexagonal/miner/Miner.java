package com.baeldung.hexagonal.miner;

import java.util.List;

import com.baeldung.hexagonal.domain.Transaction;

public interface Miner {

    List<Transaction> mine(List<Transaction> transactions);
}
