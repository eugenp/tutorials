package org.baeldung.ex.mappingexception;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.ex.mappingexception.spring.PersistenceConfig;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class MappingExceptionIntegrationTest {

    @Autowired
    private IFooService fooService;

    // tests

    @Test
    public final void givenEntityIsPersisted_thenException() {
        fooService.create(new Foo(randomAlphabetic(6)));
    }

}
