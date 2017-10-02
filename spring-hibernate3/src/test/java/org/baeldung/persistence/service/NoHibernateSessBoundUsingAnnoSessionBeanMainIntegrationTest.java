package org.baeldung.persistence.service;


import org.baeldung.persistence.model.Event;
import org.baeldung.spring.PersistenceXmlConfig;
import org.junit.Ignore;
import org.junit.Test;
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
    
    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Event("from Annotation Session Bean Factory"));
    }
    
    @Test(expected = HibernateSystemException.class)
    @Ignore
    public final void whenNoTransBoundToSession_thenException() {
        service.create(new Event("from Annotation Session Bean Factory"));
    }

}
