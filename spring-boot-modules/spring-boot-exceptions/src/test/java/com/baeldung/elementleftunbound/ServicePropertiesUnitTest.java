package com.baeldung.elementleftunbound;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = ServiceProperties.class,
        properties = "example.service.timeout=5000"
)
//Disable this annotation to check failure for section 5.
@EnableConfigurationProperties(ServiceProperties.class)
class ServicePropertiesUnitTest {

    @Autowired
    private ServiceProperties serviceProperties;


    // Uncomment this code to see property mismatch in section 4
//    @Test
//    void shouldBindTimeoutPropertyInCorrectly() {
//        assertThat(serviceProperties.getTimeOut()).isEqualTo(5000);
//    }

    @Test
    void shouldBindTimeoutPropertyCorrectly() {
        assertThat(serviceProperties.getTimeout()).isEqualTo(5000);
    }
}

