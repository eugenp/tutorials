package com.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

import com.baeldung.persistence.IOperations;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.PersistenceConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooServicePersistenceIntegrationTest extends AbstractServicePersistenceIntegrationTest<Foo> {

    @Autowired
    private IFooService service;

    // tests

    @Test
    public final void whenContextIsBootstrapped_thenNoExceptions() {
        //
    }

    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Foo(randomAlphabetic(6)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenInvalidEntityIsCreated_thenDataException() {
        service.create(new Foo());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenEntityWithLongNameIsCreated_thenDataException() {
        service.create(new Foo(randomAlphabetic(2048)));
    }

    // custom Query method

    @Test
    public final void givenUsingCustomQuery_whenRetrievingEntity_thenFound() {
        final String name = randomAlphabetic(6);
        service.create(new Foo(name));

        final Foo retrievedByName = service.retrieveByName(name);
        assertNotNull(retrievedByName);
    }

    // work in progress

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Ignore("Right now, persist has saveOrUpdate semantics, so this will no longer fail")
    public final void whenSameEntityIsCreatedTwice_thenDataException() {
        final Foo entity = new Foo(randomAlphabetic(8));
        service.create(entity);
        service.create(entity);
    }

    // API

    @Override
    protected final IOperations<Foo> getApi() {
        return service;
    }

}
