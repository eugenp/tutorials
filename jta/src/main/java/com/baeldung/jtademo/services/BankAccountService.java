package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountService {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankAccountService(@Qualifier("jdbcTemplateAccount") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE-? where ID=?", amount, fromAccountId);
        jdbcTemplate.update("update ACCOUNT set BALANCE=BALANCE+? where ID=?", amount, toAccountId);
    }

    public BigDecimal balanceOf(String accountId) {
        return jdbcTemplate.query("select BALANCE from ACCOUNT where ID=?", new Object[] { accountId }, (ResultSetExtractor<BigDecimal>) (rs) -> {
            rs.next();
            return new BigDecimal(rs.getDouble(1));
        });
    }
}
