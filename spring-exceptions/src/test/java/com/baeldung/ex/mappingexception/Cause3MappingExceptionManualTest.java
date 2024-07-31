package com.baeldung.ex.mappingexception;

import com.baeldung.ex.mappingexception.cause3.persistence.model.Foo;
import com.baeldung.ex.mappingexception.spring.Cause3PersistenceConfig;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause3PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause3MappingExceptionManualTest {

    @Autowired
    private SessionFactory sessionFactory;

    // tests

    @Test(expected = MappingException.class)
    @Transactional
    public final void givenEntityIsPersisted_thenException() {
        sessionFactory.getCurrentSession().saveOrUpdate(new Foo());
    }

}
