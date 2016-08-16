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
public class HystrixTimeShortCircuitTest {

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
    public void givenCircuitBreakerSetup__whenRemoteSvcCmdExecuted_thenReturnSuccess()
            throws InterruptedException {

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

    String invokeRemoteService(long timeout) throws InterruptedException {
        String response = null;
        try {
            response = new RemoteServiceTestCommand(config,
                    new RemoteServiceTestSimulator(timeout)).execute();
        } catch (HystrixRuntimeException ex) {
            System.out.println("ex = " + ex);
        }
        return response;
    }

}
