package com.baeldung.activitiwithspring;

import org.activiti.engine.IdentityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.activiti.security.withspring.ActivitiSpringSecurityApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiSpringSecurityApplication.class)
@WebAppConfiguration
public class ActivitiSpringSecurityIntegrationTest {
    @Autowired
    private IdentityService identityService;

    @Test
    public void whenUserExists_thenOk() {
        identityService.setUserPicture("spring_user", null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserNonExistent_thenSpringException() {
        identityService.setUserPicture("user3", null);
    }

}
