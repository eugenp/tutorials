package org.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import org.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import org.baeldung.ex.nontransientexception.cause.Cause5NonTransientConfig;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause5NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class CannotGetJdbcConnectionExceptionManualTest {

    @Autowired
    private DataSource restDataSource;

    @Test(expected = CannotGetJdbcConnectionException.class)
    public void whenJdbcUrlIncorrect_thenCannotGetJdbcConnectionException() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);
        jdbcTemplate.execute("select * from foo");
    }
}
