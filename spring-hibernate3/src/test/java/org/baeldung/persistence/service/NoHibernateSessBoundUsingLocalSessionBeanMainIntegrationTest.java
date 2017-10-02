package org.baeldung.persistence.service;


import org.baeldung.persistence.model.Event;
import org.hibernate.HibernateException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:exceptionDemoPersistanceConfig.xml" })
public class NoHibernateSessBoundUsingLocalSessionBeanMainIntegrationTest {

    @Autowired
    EventService service;
    
    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Event("from local session bean factory"));
    }
    
    @Test(expected = HibernateException.class)
    @Ignore
    public final void whenNoTransBoundToSession_thenException() {
        service.create(new Event("from local session bean factory"));
    }
}
