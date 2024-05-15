package com.baeldung.activitiwithspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.activiti.security.withspring.SpringSecurityActivitiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityActivitiApplication.class)
@WebAppConfiguration
@AutoConfigureTestDatabase
public class ActivitiSpringSecurityIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
