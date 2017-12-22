package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class BeanInjectionTest {

    @Test
    public void whenConstructorInjection_ThenBeanValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ExampleWithConstructorInjection exampleBean = context.getBean(ExampleWithConstructorInjection.class);

        assertEquals("Example", exampleBean.verifyInjection());
    }

    @Test
    public void whenSetterInjection_ThenBeanValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ExampleWithSetterInjection exampleBean = context.getBean(ExampleWithSetterInjection.class);

        assertEquals("Example", exampleBean.verifyInjection());
    }

    @Test
    public void whenProperyInjection_ThenBeanValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ExampleWithPropertyInjection exampleBean = context.getBean(ExampleWithPropertyInjection.class);

        assertEquals("Example", exampleBean.verifyInjection());
    }

}
