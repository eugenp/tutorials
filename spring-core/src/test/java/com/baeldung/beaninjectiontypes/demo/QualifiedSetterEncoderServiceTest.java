package com.baeldung.beaninjectiontypes.demo;

import com.baeldung.beaninjectiontypes.config.duplicate.DuplicateContextConfig;
import com.baeldung.beaninjectiontypes.config.filter.FilteredDuplicateContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class QualifiedSetterEncoderServiceTest {

    @Test
    public void encodeDuplicate() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FilteredDuplicateContextConfig.class);
        QualifiedSetterEncoderService testObject = (QualifiedSetterEncoderService) ctx.getBean("qualifiedSetterEncoderService");
        String encrypted = testObject.encodeMessage("secret message!");
        assertEquals(encrypted, "secret message!");
    }
}