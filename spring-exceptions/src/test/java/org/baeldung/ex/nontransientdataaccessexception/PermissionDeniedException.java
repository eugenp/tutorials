package org.baeldung.ex.nontransientdataaccessexception;

import org.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class PermissionDeniedException {

    @Autowired
    private IFooService fooService;

    @Test(expected = PermissionDeniedDataAccessException.class)
    public void whenRetrievingDataUserNoSelectRights_thenPermissionDeniedException() {
        final Foo foo = new Foo("foo");
        fooService.create(foo);
    }

}
