package com.baeldung.service;

import com.baeldung.SpringBeanInjectionApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeanInjectionApplication.class)
public class AccountServiceIntegrationTest {

    @Inject
    @Qualifier("ProviderService")
    private AccountService providerService;

    @Inject
    @Qualifier("UserService")
    private AccountService userService;

    @Resource(name="UserService")
    private AccountService userServiceTwo;

    @Resource(name="ProviderService")
    private AccountService providerServiceTwo;

    @Test
    public void givenUserService_whenCallingGetUserRole_thenWeGetUserRole() {
        String role = userService.getRole();
        String roleTwo = userServiceTwo.getRole();

        assertThat(role).isEqualTo("ROLE_USER");
        assertThat(roleTwo).isEqualTo("ROLE_USER");
    }

    @Test
    public void givenProviderService_whenCallingGetUserRole_thenWeGetProviderRole() {
        String role = providerService.getRole();
        String roleTwo = providerServiceTwo.getRole();

        assertThat(role).isEqualTo("ROLE_PROVIDER");
        assertThat(roleTwo).isEqualTo("ROLE_PROVIDER");
    }
}
