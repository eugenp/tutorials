package org.baeldung.customscope;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomScopeTest {

    @Test
    public final void whenRegisterCustomScopeConfig_thenMyBeanScopeIsCustomScope() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        try{
            ctx.register(CustomScopeConfig.class);
            ctx.refresh();
            
            BeanDefinition myBeanDefinition = ctx.getBeanDefinition("myBean");
            
            assertThat(myBeanDefinition.getScope(), equalTo("customScope"));
            assertThat(myBeanDefinition.getBeanClassName(), equalTo(MyBean.class.getName()));

            MyBean myBean = (MyBean) ctx.getBean("myBean");
            
            assertNotNull(myBean);
            myBean.sayHello();
        }
        finally {
            ctx.close();
        }
    }
}
