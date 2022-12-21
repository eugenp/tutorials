package com.baeldung.samples.domain;

import com.baeldung.samples.config.ObservedAspectConfiguration;
import io.micrometer.observation.tck.TestObservationRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.micrometer.observation.tck.TestObservationRegistryAssert.assertThat;

//@AutoConfigureObservability
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = GreetingService.class)
@Import(ObservedAspectConfiguration.class)
@EnableAutoConfiguration
// or simply use the whole context with @SpringBootTest
@EnableTestObservationRegistry
class GreetingServiceObservationTest {

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
