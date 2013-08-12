package org.baeldung.ex.mappingexception;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.ex.mappingexception.cause2.persistence.model.Foo;
import org.baeldung.ex.mappingexception.cause2.persistence.service.IFooService;
import org.baeldung.ex.mappingexception.spring.Cause2PersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause2PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause2MappingExceptionIntegrationTest {

    @Autowired
    private IFooService fooApi;

    // tests

    @Test
    public final void givenEntityIsPersisted_thenException() {
        fooApi.create(new Foo(randomAlphabetic(6)));
    }

}
