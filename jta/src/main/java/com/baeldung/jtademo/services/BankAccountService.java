package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class BankAccountService {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankAccountService(@Qualifier("jdbcTemplateAccount") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE-? where ID=?", amount, fromAccountId);
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE+? where ID=?", amount, toAccountId);
    }
}
