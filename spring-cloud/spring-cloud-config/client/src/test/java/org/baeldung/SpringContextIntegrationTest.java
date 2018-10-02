package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.spring.cloud.config.client.ConfigClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigClient.class)
@WebAppConfiguration
public class SpringContextIntegrationTest {
    @Test
    public void contextLoads() {
    }
}
