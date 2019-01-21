package com.baeldung.hexagonal.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * The core domain implementations
 * 
 * @author ysharma2512
 *
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> statement = new ArrayList<>();

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public List<Transaction> getStatement() {
        return statement;
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        balance = balance.add(amount);
        statement.add(new Transaction(Calendar.getInstance()
            .getTime(), amount, "deposit"));
        return true;
    }

    @Override
    public boolean withDraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        statement.add(new Transaction(Calendar.getInstance()
            .getTime(), amount, "withdrawal"));
        return true;
    }

}
