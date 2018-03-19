package com.baeldung.beaninjectiontypes.demo;

import com.baeldung.beaninjectiontypes.config.duplicate.DuplicateContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DuplicateEncoderServiceTest {

    @Test(expected = UnsatisfiedDependencyException.class)
    public void encodeDuplicate() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DuplicateContextConfig.class);
        SetterEncoderService testObject = (SetterEncoderService) ctx.getBean("constructorEncodingService");
    }
}