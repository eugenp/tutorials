package com.baeldung.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.collapser.RequestCollapserFactory;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HystrixTimeoutTest {

    private static HystrixCommand.Setter config;
    private static HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        config = HystrixCommand
          .Setter
          .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup1"));
    }

    @Test
    public void givenInputBob_andDefaultSettings_thenReturnHelloBob(){
        assertThat(new CommandHelloWorld("Bob").execute(), equalTo("Hello Bob!"));
    }

    @Test
    public void givenTimeoutEqualTo100_andDefaultSettings_thenReturnSuccess() throws InterruptedException {
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(100)).execute(), equalTo("Success"));
    }

    @Test
    public void givenTimeoutEqualTo10000_andDefaultSettings_thenExpectHystrixRuntimeException() throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(10_000)).execute();
    }

    @Test
    public void givenTimeoutEqualTo5000_andExecutionTimeoutEqualTo10000_thenReturnSuccess() throws InterruptedException {
        commandProperties.withExecutionTimeoutInMilliseconds(10_000);
        config.andCommandPropertiesDefaults(commandProperties);
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(5_000)).execute(), equalTo("Success"));
    }

    @Test
    public void givenTimeoutEqualTo15000_andExecutionTimeoutEqualTo10000_thenExpectHystrixRuntimeException() throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        commandProperties.withExecutionTimeoutInMilliseconds(10_000);
        config.andCommandPropertiesDefaults(commandProperties);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(15_000)).execute();
    }

}
