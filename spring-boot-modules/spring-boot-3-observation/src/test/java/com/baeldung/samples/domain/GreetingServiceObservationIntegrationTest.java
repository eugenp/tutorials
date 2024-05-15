package com.baeldung.samples.domain;

import io.micrometer.observation.tck.TestObservationRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.micrometer.observation.tck.TestObservationRegistryAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = GreetingService.class)
@EnableAutoConfiguration
@EnableTestObservation
class GreetingServiceObservationIntegrationTest {

    @Autowired
    GreetingService service;
    @Autowired
    TestObservationRegistry registry;

    @Test
    void testObservation() {
        // invoke service
        service.sayHello();
        assertThat(registry)
          .hasObservationWithNameEqualTo("greetingService")
          .that()
          .hasBeenStarted()
          .hasBeenStopped();
    }

}
