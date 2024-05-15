package com.baeldung.ex.nontransientdataaccessexception;

import com.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import com.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class InvalidResourceUsageExceptionManualTest {

    @Autowired
    private IFooService fooService;

    @Autowired
    private DataSource restDataSource;

    @Test(expected = InvalidDataAccessResourceUsageException.class)
    public void whenRetrievingDataUserNoSelectRights_thenInvalidResourceUsageException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);
        jdbcTemplate.execute("revoke select from tutorialuser");

        try {
            fooService.findAll();
        } finally {
            jdbcTemplate.execute("grant select to tutorialuser");
        }
    }

    @Test(expected = BadSqlGrammarException.class)
    public void whenIncorrectSql_thenBadSqlGrammarException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        jdbcTemplate.queryForObject("select * fro foo where id=3", Integer.class);
    }

}
