package com.baeldung.destroyprototypebean.predestroy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PreDestroyBeanExampleUnitTest {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PreDestroyConfig.class);

    @Test
    public void givenPrototypeBean_whenDestroyBean_thenAssertDestroyed() {
        PreDestroyBeanExample bean = context.getBean(PreDestroyBeanExample.class);

        assertFalse(bean.isDestroyed());
        context.getBeanFactory().destroyBean(bean);
        assertTrue(bean.isDestroyed());

        context.close();
    }

}
