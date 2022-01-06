package com.baeldung.persistence.service;

import com.baeldung.persistence.hibernate.FooSortingPersistenceIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Child;
import com.baeldung.persistence.model.Parent;
import com.baeldung.spring.config.PersistenceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ParentServicePersistenceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParentServicePersistenceIntegrationTest.class);
    
    @Autowired
    private IParentService service;

    @Autowired
    private IChildService childService;

    // tests

    @Test
    public final void whenContextIsBootstrapped_thenNoExceptions() {
        //
    }

    @Test
    public final void whenOneToOneEntitiesAreCreated_thenNoExceptions() {
        final Child childEntity = new Child();
        childService.create(childEntity);

        final Parent parentEntity = new Parent(childEntity);
        service.create(parentEntity);

        LOGGER.debug("Child = {}", childService.findOne(childEntity.getId()));
        LOGGER.debug("Child - parent = {}", childService.findOne(childEntity.getId()).getParent());

        LOGGER.debug("Parent = {}", service.findOne(parentEntity.getId()));
        LOGGER.debug("Parent - child = {}", service.findOne(parentEntity.getId()).getChild());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenChildIsDeletedWhileParentStillHasForeignKeyToIt_thenDataException() {
        final Child childEntity = new Child();
        childService.create(childEntity);

        final Parent parentEntity = new Parent(childEntity);
        service.create(parentEntity);

        childService.delete(childEntity);
    }

    @Test
    public final void whenChildIsDeletedAfterTheParent_thenNoExceptions() {
        final Child childEntity = new Child();
        childService.create(childEntity);

        final Parent parentEntity = new Parent(childEntity);
        service.create(parentEntity);

        service.delete(parentEntity);
        childService.delete(childEntity);
    }

}
