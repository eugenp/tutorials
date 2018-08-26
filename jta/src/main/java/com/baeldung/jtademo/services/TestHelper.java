package com.baeldung.jtademo.services;

import com.baeldung.jtademo.dto.TransferLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TestHelper {
    final JdbcTemplate jdbcTemplateAccount;

    final JdbcTemplate jdbcTemplateAudit;

    @Autowired
    public TestHelper(@Qualifier("jdbcTemplateAccount") JdbcTemplate jdbcTemplateAccount, @Qualifier("jdbcTemplateAudit") JdbcTemplate jdbcTemplateAudit) {
        this.jdbcTemplateAccount = jdbcTemplateAccount;
        this.jdbcTemplateAudit = jdbcTemplateAudit;
    }

    public void runAccountDbInit() throws SQLException {
        runScript("account.sql", jdbcTemplateAccount.getDataSource());
    }

    public void runAuditDbInit() throws SQLException {
        runScript("audit.sql", jdbcTemplateAudit.getDataSource());
    }

    private void runScript(String scriptName, DataSource dataSouorce) throws SQLException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource script = resourceLoader.getResource(scriptName);
        try (Connection con = dataSouorce.getConnection()) {
            ScriptUtils.executeSqlScript(con, script);
        }
    }

    public TransferLog lastTransferLog() {
        return jdbcTemplateAudit.query("select FROM_ACCOUNT,TO_ACCOUNT,AMOUNT from AUDIT_LOG order by ID desc", (ResultSetExtractor<TransferLog>) (rs) -> {
            if(!rs.next()) return null;
            return new TransferLog(rs.getString(1), rs.getString(2), BigDecimal.valueOf(rs.getDouble(3)));
        });
    }

    public BigDecimal balanceOf(String accountId) {
        return jdbcTemplateAccount.query("select BALANCE from ACCOUNT where ID=?", new Object[] { accountId }, (ResultSetExtractor<BigDecimal>) (rs) -> {
            rs.next();
            return new BigDecimal(rs.getDouble(1));
        });
    }
}
