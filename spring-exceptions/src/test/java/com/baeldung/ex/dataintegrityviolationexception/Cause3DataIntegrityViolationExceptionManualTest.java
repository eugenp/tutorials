package com.baeldung.ex.dataintegrityviolationexception;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.baeldung.ex.dataintegrityviolationexception.spring.Cause3DataContextWithJavaConfig;
import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause3DataContextWithJavaConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause3DataIntegrityViolationExceptionManualTest {

    @Autowired
    private IFooService fooService;

    // tests

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenEntityWithLongNameIsCreated_thenDataException() {
        fooService.create(new Foo(randomAlphabetic(2048)));
    }

}
