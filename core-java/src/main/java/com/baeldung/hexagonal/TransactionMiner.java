package com.baeldung.hexagonal;

import java.io.PrintStream;
import java.util.List;

import com.baeldung.hexagonal.domain.Transaction;
import com.baeldung.hexagonal.miner.FraudMiner;
import com.baeldung.hexagonal.miner.Miner;
import com.baeldung.hexagonal.miner.ProductMiner;
import com.baeldung.hexagonal.repository.FixedTransactionSource;
import com.baeldung.hexagonal.repository.RandomTransactionSource;
import com.baeldung.hexagonal.repository.TransactionSource;


public class TransactionMiner {

    private TransactionSource source;
    private Miner miner;

    public TransactionMiner(TransactionSource source, Miner miner) {

        this.source = source;
        this.miner = miner;
    }

    public List<Transaction> mineTransactions() {

        List<Transaction> transactions = source.getTransactions();
        return miner.mine(transactions);
    }

    public static void showTransactions(List<Transaction> transactions, PrintStream stream) {

        transactions.forEach(t -> stream.println(t.toString()));
    }

    public static void main(String[] argv) {

        System.out.println("Start mining transactions");

        Miner miner = new FraudMiner();
        TransactionSource source = new RandomTransactionSource(100);
        TransactionMiner miningEngine = new TransactionMiner(source, miner);
        List<Transaction> minedTransactions = miningEngine.mineTransactions();
        showTransactions(minedTransactions, System.out);
    }

}
