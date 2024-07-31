package com.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import com.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import com.baeldung.persistence.model.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DataRetrievalExceptionManualTest {

    @Autowired
    private DataSource restDataSource;

    @Test(expected = DataRetrievalFailureException.class)
    public void whenRetrievingNonExistentValue_thenDataRetrievalException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        jdbcTemplate.queryForObject("select * from foo where id=3", Integer.class);
    }

    @Test(expected = IncorrectResultSetColumnCountException.class)
    public void whenRetrievingMultipleColumns_thenIncorrectResultSetColumnCountException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);
        try {
            jdbcTemplate.execute("insert into foo(id,name) values (1,'a')");
            jdbcTemplate.queryForList("select id,name from foo where id=1", Foo.class);
        } finally {
            jdbcTemplate.execute("delete from foo where id=1");
        }
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void whenRetrievingMultipleValues_thenIncorrectResultSizeException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        jdbcTemplate.execute("insert into foo(name) values ('a')");
        jdbcTemplate.execute("insert into foo(name) values ('a')");

        jdbcTemplate.queryForObject("select id from foo where name='a'", Integer.class);
    }

}
