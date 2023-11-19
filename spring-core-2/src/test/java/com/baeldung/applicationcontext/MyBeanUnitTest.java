package com.baeldung.applicationcontext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = TestContextConfig.class)
@ExtendWith(SpringExtension.class)
class MyBeanUnitTest {

    @Autowired
    MyBean myBean;

    @Test
    void whenGetApplicationContext_thenReturnApplicationContext() {
        assertNotNull(myBean);
        ApplicationContext context = myBean.getApplicationContext();
        assertNotNull(context);

        System.out.printf("ApplicationContext has %d beans %n", context.getBeanDefinitionCount());
    }

}