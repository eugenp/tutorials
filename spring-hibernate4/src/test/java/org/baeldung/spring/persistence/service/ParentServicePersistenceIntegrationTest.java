package org.baeldung.spring.persistence.service;

import org.baeldung.spring.persistence.config.PersistenceConfig;
import org.baeldung.spring.persistence.model.Child;
import org.baeldung.spring.persistence.model.Parent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ParentServicePersistenceIntegrationTest {

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
    public final void whenEntityIsCreated_thenNoExceptions() {
        final Child childEntity = new Child();
        childService.create(childEntity);

        service.create(new Parent(childEntity));

        System.out.println();
    }

}
