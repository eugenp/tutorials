package com.baeldung.spring.simple;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.simple.model.Foo;
import com.baeldung.spring.simple.service.IFooService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { JpaApplication.class})
@DirtiesContext
public class FooServiceIntegrationTest {

    @Autowired
    private IFooService service;

    @Autowired
    private DataSource dataSource;

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenInvalidEntityIsCreated_thenDataException() {
        service.create(new Foo());
    }
}
