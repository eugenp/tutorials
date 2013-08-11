package org.baeldung.ex.dataIntegrityviolationexception;

import org.baeldung.ex.dataIntegrityviolationexception.spring.Cause2DataContextWithJavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause2DataContextWithJavaConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause2DataIntegrityViolationExceptionIntegrationTest {

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        //
    }

}
