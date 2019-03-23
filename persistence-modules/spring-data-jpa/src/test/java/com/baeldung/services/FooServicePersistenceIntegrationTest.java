package com.baeldung.services;

import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.domain.Foo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class}, loader = AnnotationConfigContextLoader.class)
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
