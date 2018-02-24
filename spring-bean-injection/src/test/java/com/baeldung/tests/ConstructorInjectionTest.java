
package com.baeldung.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.service.ConstructorInjectionWeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration("com.baeldung")
@ComponentScan
public class ConstructorInjectionTest {

    @Autowired
    ConstructorInjectionWeatherService weather;

    
    @Test
    public void testConstructionInjection() throws Exception {
        String location = "Vienna";
        
        weather.getTemprature(location);
        
        weather.getHumidity(location);
        
        weather.willItRainToday(location);
    }
}
