
package com.baeldung.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.service.FieldInjectionWeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FieldInjectionTest {

    @Autowired
    FieldInjectionWeatherService weather;

    
    @Test
    public void testConstructionInjection() throws Exception {
        String location = "Vienna";
        
        weather.getTemprature(location);
        
        weather.getHumidity(location);
        
        weather.willItRainToday(location);
    }
}
