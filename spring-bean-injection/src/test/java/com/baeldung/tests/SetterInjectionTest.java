
package com.baeldung.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.service.SetterInjectionWeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SetterInjectionTest {

    @Autowired
    SetterInjectionWeatherService weather;

    
    @Test
    public void testConstructionInjection() throws Exception {
        String location = "Vienna";
        
        weather.getTemprature(location);
        
        weather.getHumidity(location);
        
        weather.willItRainToday(location);
    }
}
