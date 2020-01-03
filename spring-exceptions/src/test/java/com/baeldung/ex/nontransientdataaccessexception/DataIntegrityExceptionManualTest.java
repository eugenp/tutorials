package com.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import com.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DataIntegrityExceptionManualTest {

    @Autowired
    private IFooService fooService;

    @Autowired
    private DataSource restDataSource;

    @Test(expected = DataIntegrityViolationException.class)
    public void whenSavingNullValue_thenDataIntegrityException() {
        final Foo fooEntity = new Foo();
        fooService.create(fooEntity);
    }

    @Test(expected = DuplicateKeyException.class)
    public void whenSavingDuplicateKeyValues_thenDuplicateKeyException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        try {
            jdbcTemplate.execute("insert into foo(id,name) values (1,'a')");
            jdbcTemplate.execute("insert into foo(id,name) values (1,'b')");
        } finally {
            jdbcTemplate.execute("delete from foo where id=1");
        }
    }

}
