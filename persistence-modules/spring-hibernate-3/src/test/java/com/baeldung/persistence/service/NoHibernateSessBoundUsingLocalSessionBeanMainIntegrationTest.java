package com.baeldung.persistence.service;

import com.baeldung.persistence.model.Event;
import org.hibernate.HibernateException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:exceptionDemoPersistenceConfig.xml" })
public class NoHibernateSessBoundUsingLocalSessionBeanMainIntegrationTest {

    @Autowired
    EventService service;
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Event("from local session bean factory"));
    }
    
    @Test
    @Ignore
    public final void whenNoTransBoundToSession_thenException() {
        expectedEx.expect(HibernateException.class);
        expectedEx.expectMessage("No Hibernate Session bound to thread, "
            + "and configuration does not allow creation "
            + "of non-transactional one here");
        service.create(new Event("from local session bean factory"));
    }
}
