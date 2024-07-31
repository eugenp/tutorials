package com.baeldung.spring.rsocket.server;

import com.baeldung.spring.rsocket.model.MarketData;
import com.baeldung.spring.rsocket.model.MarketDataRequest;
import io.rsocket.RSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.rsocket.server.port=7000"})
public class MarketDataRSocketControllerLiveTest {

    @Autowired
    private RSocketRequester rSocketRequester;

    @SpyBean
    private MarketDataRSocketController rSocketController;

    @Test
    public void whenGetsFireAndForget_ThenReturnsNoResponse() throws InterruptedException {
        final MarketData marketData = new MarketData("X", 1);
        rSocketRequester.route("collectMarketData")
                        .data(marketData)
                        .send()
                        .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(rSocketController).collectMarketData(any());
    }

    @Test
    public void whenGetsRequest_ThenReturnsResponse() throws InterruptedException {
        final MarketDataRequest marketDataRequest = new MarketDataRequest("X");
        rSocketRequester.route("currentMarketData")
                        .data(marketDataRequest)
                        .send()
                        .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(rSocketController).currentMarketData(any());
    }

    @Test
    public void whenGetsRequest_ThenReturnsStream() throws InterruptedException {
        final MarketDataRequest marketDataRequest = new MarketDataRequest("X");
        rSocketRequester.route("feedMarketData")
                        .data(marketDataRequest)
                        .send()
                        .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(rSocketController).feedMarketData(any());
    }

    private void sleepForProcessing() throws InterruptedException {
        Thread.sleep(1000);
    }

    @TestConfiguration
    public static class ClientConfiguration {

        @Bean
        @Lazy
        public RSocket rSocket() {

            RSocketRequester.Builder builder = RSocketRequester.builder();

            return builder
                    .rsocketConnector(
                            rSocketConnector ->
                                    rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                    .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                    .tcp("localhost", 7000)
                    .rsocket();
        }

        @Bean
        @Lazy
        RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
            return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
        }
    }
}
