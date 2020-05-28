package com.baeldung.postprocessor;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PostProcessorConfiguration.class})
public class StockTradeIntegrationTest {

    @Autowired
    private StockTradePublisher stockTradePublisher;

    @Test
    public void givenValidConfig_whenTradePublished_thenTradeReceived() {
        Date tradeDate = new Date();
        StockTrade stockTrade = new StockTrade("AMZN", 100, 2483.52d, tradeDate);
        AtomicBoolean assertionsPassed = new AtomicBoolean(false);
        StockTradeListener listener = trade -> assertionsPassed.set(this.verifyExact(stockTrade, trade));
        this.stockTradePublisher.addStockTradeListener(listener);
        try {
            GlobalEventBus.post(stockTrade);
            await().atMost(Duration.ofSeconds(2L))
              .untilAsserted(() -> assertThat(assertionsPassed.get()).isTrue());
        } finally {
            this.stockTradePublisher.removeStockTradeListener(listener);
        }
    }

    private boolean verifyExact(StockTrade stockTrade, StockTrade trade) {
        return Objects.equals(stockTrade.getSymbol(), trade.getSymbol())
          && Objects.equals(stockTrade.getTradeDate(), trade.getTradeDate())
          && stockTrade.getQuantity() == trade.getQuantity()
          && stockTrade.getPrice() == trade.getPrice();
    }
}
