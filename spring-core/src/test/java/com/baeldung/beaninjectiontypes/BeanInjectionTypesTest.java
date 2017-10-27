package com.baeldung.beaninjectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjectiontypes.constructor.AppConfig;
import com.baeldung.beaninjectiontypes.constructor.PC;
import com.baeldung.beaninjectiontypes.setter.Car;
import com.baeldung.beaninjectiontypes.setter.Config;

import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {AppConfig.class, Config.class})
public class BeanInjectionTypesTest {

    @Autowired
    public PC pc;
    @Autowired
    public Car car;

    @Test
    public void whenInjectedByContructor_thenBeanIsProperlyInitialized() {
        assertNotNull(pc);
        assertNotNull(pc.getCpu());
    }

    @Test
    public void whenInjectedBySetter_thenBeanIsProperlyInitialized() {
        assertNotNull(car);
        assertNotNull(car.getType());
    }
}
