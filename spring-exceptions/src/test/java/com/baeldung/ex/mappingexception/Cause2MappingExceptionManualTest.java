package com.baeldung.ex.mappingexception;

import com.baeldung.ex.mappingexception.cause2.persistence.model.Foo;
import com.baeldung.ex.mappingexception.spring.Cause2PersistenceConfig;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause2PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause2MappingExceptionManualTest {

    @Autowired
    private SessionFactory sessionFactory;

    // tests

    // @Test(expected = MappingException.class)
    @Test
    @Transactional
    public final void givenEntityIsPersisted_thenException() {
        sessionFactory.getCurrentSession().saveOrUpdate(new Foo());
    }

}
