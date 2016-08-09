package org.baeldung.ex.nontransientdataaccessexception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class CleanupFailureExceptionTest {

    private static final Logger LOG = Logger.getLogger(CleanupFailureExceptionTest.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IFooService fooService;

    @Test
    public void whenCleanupAfterSaving_thenCleanupException() {
        try {
            final Foo fooEntity = new Foo("foo");
            fooService.create(fooEntity);
        } finally {
            try {
                sessionFactory.close();
            } catch (final CleanupFailureDataAccessException exc) {
                LOG.log(Level.SEVERE, exc.getMessage());
            }
        }
    }
}
