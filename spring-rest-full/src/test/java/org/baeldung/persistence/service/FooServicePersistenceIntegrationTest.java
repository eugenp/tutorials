package org.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.baeldung.persistence.IOperations;
import org.baeldung.persistence.model.Foo;
import org.baeldung.spring.PersistenceConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Test
    public final void givenPersisted_whenRetrievingEntityWithPageInfo_thenFound() {
        
        service.create(new Foo("A"));
        service.create(new Foo("B"));
        service.create(new Foo("C"));
        service.create(new Foo("D"));
        service.create(new Foo("E"));

        PageRequest pageRequest = new PageRequest(1, 2, new Sort(Sort.Direction.DESC, "name"));
        final Page<Foo> pageResult = service.findPaginated(pageRequest);
        List<Foo> content = pageResult.getContent();
        
        assertEquals("D", content.get(0).getName());
        assertEquals("C", content.get(1).getName());
    }
}
