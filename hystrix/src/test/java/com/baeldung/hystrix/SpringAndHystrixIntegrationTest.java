package com.baeldung.hystrix;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppConfig.class)
public class SpringAndHystrixIntegrationTest {

    @Autowired
    private HystrixController hystrixController;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void givenTimeOutOf15000_whenClientCalledWithHystrix_thenExpectHystrixRuntimeException() throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        hystrixController.withHystrix();
    }

    @Test
    public void givenTimeOutOf15000_whenClientCalledWithOutHystrix_thenExpectSuccess() throws InterruptedException {
        assertThat(hystrixController.withOutHystrix(), equalTo("Success"));
    }

}
