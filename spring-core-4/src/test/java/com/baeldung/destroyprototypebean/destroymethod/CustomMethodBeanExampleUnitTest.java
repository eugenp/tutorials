package com.baeldung.destroyprototypebean.destroymethod;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.destroyprototypebean.destorymethod.CustomMethodBeanExample;

public class CustomMethodBeanExampleUnitTest {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DestroyMethodConfig.class);

    @Test
    public void givenPrototypeBean_whenDestroyBean_thenAssertDestroyed() {
        CustomMethodBeanExample bean = context.getBean(CustomMethodBeanExample.class);

        assertFalse(bean.isDestroyed());
        context.getBeanFactory().destroyBean(bean);
        assertFalse(bean.isDestroyed());

        context.close();
    }

    @Test
    public void givenPrototypeBean_whenManuallyDestroyBean_thenAssertDestroyed() {
        CustomMethodBeanExample bean = context.getBean(CustomMethodBeanExample.class);

        assertFalse(bean.isDestroyed());
        bean.destroy();
        assertTrue(bean.isDestroyed());

        context.close();
    }

}
