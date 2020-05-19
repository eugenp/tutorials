package com.baeldung.ex.beancreationexception;

import com.baeldung.ex.beancreationexception.spring.Cause7ContextWithJavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause7ContextWithJavaConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause7BeanCreationExceptionManualTest {

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        //
    }

}
