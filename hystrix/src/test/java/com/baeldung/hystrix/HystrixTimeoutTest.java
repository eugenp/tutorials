package com.baeldung.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.collapser.RequestCollapserFactory;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HystrixTimeoutTest {

    private HystrixCommand.Setter config;
    private HystrixCommandProperties.Setter commandProperties ;


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        commandProperties = HystrixCommandProperties.Setter();
        config = HystrixCommand
          .Setter
          .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup1"));
    }

    @Test
    public void givenInputBob_andDefaultSettings_thenReturnHelloBob(){
        assertThat(new CommandHelloWorld("Bob").execute(), equalTo("Hello Bob!"));
    }

    @Test
    public void givenServiceTimeoutEqualTo100_andDefaultSettings_thenReturnSuccess() throws InterruptedException {
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(100)).execute(), equalTo("Success"));
    }

    @Test
    public void givenServiceTimeoutEqualTo10000_andDefaultSettings_thenExpectHRE() throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(10_000)).execute();
    }

    @Test
    public void givenServiceTimeoutEqualTo5000_andExecutionTimeoutEqualTo10000_thenReturnSuccess()
            throws InterruptedException {
        commandProperties.withExecutionTimeoutInMilliseconds(10_000);
        config.andCommandPropertiesDefaults(commandProperties);
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
    }

    @Test
    public void givenServiceTimeoutEqualTo15000_andExecutionTimeoutEqualTo5000_thenExpectHRE()
            throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        commandProperties.withExecutionTimeoutInMilliseconds(5_000);
        config.andCommandPropertiesDefaults(commandProperties);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(15_000)).execute();
    }

    @Test
    public void givenServiceTimeoutEqual_andExecutionTimeout_andThreadPool_thenReturnSuccess()
            throws InterruptedException {
        commandProperties.withExecutionTimeoutInMilliseconds(10_000);
        config.andCommandPropertiesDefaults(commandProperties);
        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(10)
                .withCoreSize(3)
                .withQueueSizeRejectionThreshold(10));
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
    }

    @Test
    public void givenCircuitBreakerSetup_thenReturnSuccess() throws InterruptedException {

        commandProperties.withExecutionTimeoutInMilliseconds(1000);

        commandProperties.withCircuitBreakerSleepWindowInMilliseconds(4000);
        commandProperties.withExecutionIsolationStrategy(
                HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
        commandProperties.withCircuitBreakerEnabled(true);
        commandProperties.withCircuitBreakerRequestVolumeThreshold(1);

        config.andCommandPropertiesDefaults(commandProperties);

        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(1)
                .withCoreSize(1)
                .withQueueSizeRejectionThreshold(1));

        assertThat(this.invokeRemoteService(10000), equalTo(null));
        assertThat(this.invokeRemoteService(10000), equalTo(null));
        Thread.sleep(5000);

        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
    }

    public String invokeRemoteService(long timeout) throws InterruptedException{
        String response = null;
        try{
            response = new RemoteServiceTestCommand(config,
                    new RemoteServiceTestSimulator(timeout)).execute();
        }catch(HystrixRuntimeException ex){
            System.out.println("ex = " + ex);
        }
        return response;
    }

}
