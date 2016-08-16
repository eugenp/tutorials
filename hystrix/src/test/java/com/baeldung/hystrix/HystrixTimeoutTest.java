package com.baeldung.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.JVM)
public class HystrixTimeoutTest {

    private HystrixCommand.Setter config;
    private HystrixCommandProperties.Setter commandProperties;


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
    public void givenInputBobAndDefaultSettings_whenExecuted_thenReturnHelloBob(){
        assertThat(new CommandHelloWorld("Bob").execute(), equalTo("Hello Bob!"));
    }

    @Test
    public void givenSvcTimeoutOf100AndDefaultSettings_whenExecuted_thenReturnSuccess()
            throws InterruptedException {

        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup1"));

        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(100)).execute(),
                equalTo("Success"));
    }

    @Test
    public void givenSvcTimeoutOf10000AndDefaultSettings__whenExecuted_thenExpectHRE() throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(10_000)).execute();
    }

    @Test
    public void givenSvcTimeoutOf5000AndExecTimeoutOf10000__whenExecuted_thenReturnSuccess()
            throws InterruptedException {
        commandProperties.withExecutionTimeoutInMilliseconds(10_000);
        config.andCommandPropertiesDefaults(commandProperties);

        assertThat(new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(500)).execute(),
                equalTo("Success"));
    }

    @Test
    public void givenSvcTimeoutOf15000AndExecTimeoutOf5000__whenExecuted_thenExpectHRE()
            throws InterruptedException {
        exception.expect(HystrixRuntimeException.class);
        commandProperties.withExecutionTimeoutInMilliseconds(5_000);
        config.andCommandPropertiesDefaults(commandProperties);
        new RemoteServiceTestCommand(config, new RemoteServiceTestSimulator(15_000)).execute();
    }

    @Test
    public void givenSvcTimeoutOf500AndExecTimeoutOf10000AndThreadPool__whenExecuted_thenReturnSuccess()
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
}
