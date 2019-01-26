package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.cloud.consul.discovery.DiscoveryClientApplication;
import com.baeldung.spring.cloud.consul.health.ServiceDiscoveryApplication;
import com.baeldung.spring.cloud.consul.properties.DistributedPropertiesApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DiscoveryClientApplication.class, ServiceDiscoveryApplication.class,
		DistributedPropertiesApplication.class })
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
