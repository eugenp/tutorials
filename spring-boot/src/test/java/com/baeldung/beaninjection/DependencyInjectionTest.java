package com.baeldung.beaninjection;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.beaninjection.config.AppConfig;
import com.baeldung.beaninjection.service.CarService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class DependencyInjectionTest {

    @Resource
    private CarService carService;

    @Test
    public void testConstructorDI() {
        assertTrue("Constructor-based DI isn't working", carService.getCarRepository() != null);
    }

    @Test
    public void testSetterDI() {
        String optionalDependency = carService.getOptionalDependency();
        assertTrue("Setter-based DI isn't working. It is null", optionalDependency != null);
        assertTrue("Setter-based DI isn't working. Value is different than 'yeah'", optionalDependency.equals("yeah"));
    }
}
