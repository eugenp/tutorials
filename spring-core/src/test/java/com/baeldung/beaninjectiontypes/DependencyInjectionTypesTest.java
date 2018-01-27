package com.baeldung.beaninjectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertTrue;

public class DependencyInjectionTypesTest {

    private String weather = "Cool and cloudy with 22 degree Celsius";

    @Test
    public void givenJavaConfig_WhenConstructorInjection_ThenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigConstructorInjection.class);
        WeatherUpdate weatherUpdate = context.getBean("weatherUpdate", WeatherUpdate.class);

        assertTrue(weatherUpdate.getUpdate().toUpperCase().equals(weather.toUpperCase()));
        context.close();
    }

    @Test
    public void givenJavaConfig_WhenSetterInjection_ThenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigSetterInjection.class);
        WeatherUpdate weatherUpdate = context.getBean("weatherUpdate", WeatherUpdate.class);

        assertTrue(weatherUpdate.getUpdate().toUpperCase().equals(weather.toUpperCase()));
        context.close();
    }
}
