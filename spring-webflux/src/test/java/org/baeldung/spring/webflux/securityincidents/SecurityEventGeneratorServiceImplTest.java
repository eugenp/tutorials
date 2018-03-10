package org.baeldung.spring.webflux.securityincidents;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;
import org.baeldung.spring.webflux.securityincidents.service.SecurityEventGeneratorService;
import org.baeldung.spring.webflux.securityincidents.service.SecurityEventGeneratorServiceImpl;
import org.junit.Before;
import reactor.core.publisher.Flux;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class SecurityEventGeneratorServiceImplTest {

    private static final Object UUID_LENGTH = 36;
    private SecurityEventGeneratorService securityEventGeneratorService;

    @Before
    public void setUp() throws Exception {
        securityEventGeneratorService = new SecurityEventGeneratorServiceImpl();
    }

    @Test
    public void whenConsumingService_thenReturnsExpectedContent() throws Exception {

        Flux<SecurityEvent> quoteFlux = securityEventGeneratorService.getSecurityEventStream();

        quoteFlux.take(10)
            .subscribe(securityEvent -> {
                
                assertThat(securityEvent.getId()
                    .toString()
                    .length()).isEqualTo(UUID_LENGTH);
                assertThat(securityEvent.getName()
                    .length()).isPositive();
                assertThat(securityEvent.getTimestamp()).isInThePast();
            });

    }

    @Test
    public void whenConsumingService_thenShowReceivedContent() throws Exception {

        Flux<SecurityEvent> quoteFlux = securityEventGeneratorService.getSecurityEventStream();

        Consumer<SecurityEvent> println = System.out::println;
        Consumer<Throwable> errorHandler = e -> System.err.println("Some Error Occurred");
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable allDone = () -> countDownLatch.countDown();

        quoteFlux.take(10)
            .subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }

}
