package com.baeldung.beaninjectiontypes.demo;

import com.baeldung.beaninjectiontypes.config.manual.ManualContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class ManualEncoderServiceTest {

    @Test
    public void encodeManual() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ManualContextConfig.class);
        ConstructorEncoderService testObject = (ConstructorEncoderService) ctx.getBean("constructorEncoderService");
        String encrypted = testObject.encodeMessage("secret message!");
        assertEquals(encrypted, "secret message!");
    }
}