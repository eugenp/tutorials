package com.baeldung.autowire.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InjectionExampleConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class LocationServiceConstructorIntegrationTest {

    @Autowired
    private LocationServiceConstructorInjection locationService;

    @Test
    public void whenUkCoordinates_thenReturnUk() {
        Assert.assertEquals("UK", locationService.countryForCoordinates(1.1d, 0.5d));
    }
}
