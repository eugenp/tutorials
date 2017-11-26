package com.baeldung.beaninjectiontypes.vehicle;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import com.baeldung.beaninjectiontypes.vehicle.engine.EngineMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleFieldInjectionIntegrationTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public Vehicle vehicle() {
            return new VehicleFieldInjection();
        }

        @Bean
        public Engine engine() {
            return new EngineMock();
        }
    }

    @Autowired
    private Vehicle vehicle;

    @Test
    public void whenUsingFieldInjection_thenEngineMockIsInjected() {
        Assert.assertTrue(vehicle.getEngine().isMock());
    }
}
