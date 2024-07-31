package com.baeldung.ex.beancreationexception;

import com.baeldung.ex.beancreationexception.spring.Cause4ContextWithJavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause4ContextWithJavaConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause4BeanCreationExceptionManualTest {

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        //
    }

}
