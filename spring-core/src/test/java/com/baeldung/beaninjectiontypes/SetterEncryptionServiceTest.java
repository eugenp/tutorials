package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.config.good.DefaultContextConfig;
import com.baeldung.beaninjectiontypes.demo.SetterEncryptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = DefaultContextConfig.class)
public class SetterEncryptionServiceTest {

    @Autowired
    SetterEncryptionService testObject;

    @Test
    public void encrypt() throws Exception {
        String encrypted = testObject.encryptMessage("secret message!");
        assertEquals(encrypted, "secret message!");
    }

}