package com.baeldung.applicationcontext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = TestContextConfig.class)
@ExtendWith(SpringExtension.class)
class ApplicationContextProviderUnitTest {

    @Test
    void whenGetApplicationContext_thenReturnApplicationContext() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        assertNotNull(context);
        System.out.printf("ApplicationContext has %d beans %n", context.getBeanDefinitionCount());
    }

    @Test
    void whenGetBean_thenReturnItemServiceReference() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        assertNotNull(context);

        ItemService itemService = context.getBean(ItemService.class);
        assertNotNull(context);

        System.out.println(itemService.getItem());
    }
}