package com.baeldung.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoginServiceImpl.class)
public class LoginServiceUnitTest {

    @MockBean
    CounterService counterService;

    @Autowired
    LoginServiceImpl loginService;

    @Test
    public void whenLoginUserIsAdmin_thenSuccessCounterIsIncremented() {
        boolean loginResult = loginService.login("admin", "secret".toCharArray());
        assertThat(loginResult, is(true));
        verify(counterService, times(1)).increment("counter.login.success");
    }

    @Test
    public void whenLoginUserIsNotAdmin_thenFailureCounterIsIncremented() {
        boolean loginResult = loginService.login("user", "notsecret".toCharArray());
        assertThat(loginResult, is(false));
        verify(counterService, times(1)).increment("counter.login.failure");
    }

}
