package com.baeldung.jtademo.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baeldung.jtademo.dto.TransferLog;

@Service
public class AuditService {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditService(@Qualifier("jdbcTemplateAudit") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void log(String fromAccount, String toAccount, BigDecimal amount) {
        jdbcTemplate.update("insert into AUDIT_LOG(FROM_ACCOUNT, TO_ACCOUNT, AMOUNT) values ?,?,?", fromAccount, toAccount, amount);
    }

    public TransferLog lastTransferLog() {
        return jdbcTemplate.query(
          "select FROM_ACCOUNT,TO_ACCOUNT,AMOUNT from AUDIT_LOG order by ID desc",
          rs -> {
              if (!rs.next()) {
                  return null;
              }
              return new TransferLog(
                rs.getString(1),
                rs.getString(2),
                BigDecimal.valueOf(rs.getDouble(3))
              );
        });
    }
}
