package org.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import org.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DataRetrievalExceptionTest {

    @Autowired
    private DataSource restDataSource;

    @Test(expected = DataRetrievalFailureException.class)
    public void whenRetrievingNonExistentValue_thenDataRetrievalException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        jdbcTemplate.queryForObject("select * from foo where id=3", Integer.class);
    }
}
