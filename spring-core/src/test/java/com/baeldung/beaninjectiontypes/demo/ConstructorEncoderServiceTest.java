package com.baeldung.beaninjectiontypes.demo;

import com.baeldung.beaninjectiontypes.config.standard.DefaultContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DefaultContextConfig.class)
public class ConstructorEncoderServiceTest {

    @Autowired
    ConstructorEncoderService testObject;

    @Test
    public void encode() throws Exception {
        String encrypted = testObject.encodeMessage("secret message!");
        assertEquals(encrypted, "secret message!");
    }
}