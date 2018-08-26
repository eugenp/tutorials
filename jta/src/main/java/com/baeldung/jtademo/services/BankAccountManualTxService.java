package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;

@Component
public class BankAccountManualTxService {
    @Resource
    UserTransaction userTransaction;

    @Autowired
    @Qualifier("jdbcTemplateAccount")
    JdbcTemplate jdbcTemplate;

    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount) throws Exception {
        userTransaction.begin();
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE-? where ID=?", amount, fromAccountId);
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE+? where ID=?", amount, toAccountId);
        userTransaction.commit();
    }
}
