package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.config.faulty.FaultyContextConfig;
import com.baeldung.beaninjectiontypes.demo.SetterEncryptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FaultyEncryptionServiceTest {

    @Test(expected = UnsatisfiedDependencyException.class)
    public void encrypt() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FaultyContextConfig.class);
        SetterEncryptionService testObject = (SetterEncryptionService) ctx.getBean("consturctorEncryptionService");
    }


}