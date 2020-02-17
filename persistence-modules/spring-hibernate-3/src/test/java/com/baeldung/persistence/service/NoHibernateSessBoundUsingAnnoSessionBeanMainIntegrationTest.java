package com.baeldung.persistence.service;

import com.baeldung.persistence.model.Event;
import com.baeldung.spring.PersistenceXmlConfig;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceXmlConfig.class }, loader = AnnotationConfigContextLoader.class)
public class NoHibernateSessBoundUsingAnnoSessionBeanMainIntegrationTest {
    
    @Autowired
    EventService service;
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Event("from Annotation Session Bean Factory"));
    }
    
    @Test
    @Ignore
    public final void whenNoTransBoundToSession_thenException() {
        expectedEx.expect(HibernateSystemException.class);
        expectedEx.expectMessage("No Hibernate Session bound to thread, "
            + "and configuration does not allow creation of "
            + "non-transactional one here");
        service.create(new Event("from Annotation Session Bean Factory"));
    }

}
