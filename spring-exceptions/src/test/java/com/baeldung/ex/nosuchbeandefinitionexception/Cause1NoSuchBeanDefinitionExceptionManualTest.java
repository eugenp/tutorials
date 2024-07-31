package com.baeldung.ex.nosuchbeandefinitionexception;

import com.baeldung.ex.nosuchbeandefinitionexception.spring.Cause1ContextWithJavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1ContextWithJavaConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Cause1NoSuchBeanDefinitionExceptionManualTest {

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        //
    }

}
