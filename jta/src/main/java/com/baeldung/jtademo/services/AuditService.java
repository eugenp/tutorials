package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AuditService {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditService(@Qualifier("jdbcTemplateAudit") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void log(String fromAccount, String toAccount, BigDecimal amount) {
        jdbcTemplate.update("insert into AUDIT_LOG(FROM_ACCOUNT, TO_ACCOUNT, AMOUNT) values ?,?,?", fromAccount, toAccount, amount);
    }
}
