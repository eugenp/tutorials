package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.domain.ComputerConstructorInjection;
import com.baeldung.beaninjectiontypes.domain.ComputerSetterInjection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BeanInjectionTypesUnitTest {
    private ApplicationContext javaContext = new AnnotationConfigApplicationContext(Config.class);
    private ApplicationContext XMLContext = new ClassPathXmlApplicationContext("beaninjectiontypes.xml");

    // Java config - Constructor injection
    @Test public void givenJavaBeanConfiguration_whenBeanInjectionRequestedViaConstructor_thenBeanProvided() {
        ComputerConstructorInjection computer = javaContext.getBean(ComputerConstructorInjection.class);

        Assert.assertNotNull(computer);
        Assert.assertNotNull(computer.getCpu());
        Assert.assertNotNull(computer.getRam());
        Assert.assertNotNull(computer.toString());
    }

    // Java config - Setter injection
    @Test public void givenJavaBeanConfiguration_whenBeanInjectionRequestedViaSetter_thenBeanProvided() {
        ComputerSetterInjection computer = javaContext.getBean(ComputerSetterInjection.class);

        Assert.assertNotNull(computer);
        Assert.assertNotNull(computer.getCpu());
        Assert.assertNotNull(computer.getRam());
        Assert.assertNotNull(computer.toString());
    }

    // XML config - Constructor injection
    @Test public void givenXMLBeanConfiguration_whenBeanInjectionRequestedViaConstructor_thenBeanProvided() {
        ComputerConstructorInjection computer = XMLContext.getBean(ComputerConstructorInjection.class);

        Assert.assertNotNull(computer);
        Assert.assertNotNull(computer.getCpu());
        Assert.assertNotNull(computer.getRam());
        Assert.assertNotNull(computer.toString());
    }

    // XML config - Setter injection
    @Test public void givenXMLBeanConfiguration_whenBeanInjectionRequestedViaSetter_thenBeanProvided() {
        ComputerSetterInjection computer = XMLContext.getBean(ComputerSetterInjection.class);

        Assert.assertNotNull(computer);
        Assert.assertNotNull(computer.getCpu());
        Assert.assertNotNull(computer.getRam());
        Assert.assertNotNull(computer.toString());
    }
}
