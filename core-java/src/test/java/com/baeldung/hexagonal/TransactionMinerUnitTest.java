package com.baeldung.hexagonal;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.baeldung.hexagonal.domain.Transaction;
import com.baeldung.hexagonal.miner.FraudMiner;
import com.baeldung.hexagonal.miner.Miner;
import com.baeldung.hexagonal.miner.ProductMiner;
import com.baeldung.hexagonal.repository.FixedTransactionSource;
import com.baeldung.hexagonal.repository.TransactionSource;
import org.junit.Test;

public class TransactionMinerUnitTest {

    @Test
    public void givenFixedTransactions_whenUsingProductMiner_thenExpectedResult() {

        Miner miner = new ProductMiner("coffee", "latte", "cappuccino");
        TransactionSource source = new FixedTransactionSource();
        TransactionMiner miningEngine = new TransactionMiner(source, miner);
        List<Transaction> minedTransactions = miningEngine.mineTransactions();
        assertEquals(4, minedTransactions.size());
    }

    @Test
    public void givenFixedTransactions_whenUsingFraudMiner_thenExpectedResult() {

        Miner miner = new FraudMiner();
        TransactionSource source = new FixedTransactionSource();
        TransactionMiner miningEngine = new TransactionMiner(source, miner);
        List<Transaction> minedTransactions = miningEngine.mineTransactions();
        assertEquals(1, minedTransactions.size());
    }
}
