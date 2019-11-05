package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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

}
