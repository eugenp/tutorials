package com.baeldung.patterns.hexagonal.architecture.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StockTradeUnitTest {
    Stock stock = new Stock("GOOGL");

    TradeOrder sellOrder = new TradeOrder(TradeType.SELL, 2, stock, 2819.55);
    TradeOrder buyOrder = new TradeOrder(TradeType.BUY, 2, stock, 2819.55);
    TradeOrder invalidOrder = new TradeOrder(null, null, null, null);

    @Test
    public void whenMatchingOrders_thenTradeFulfilled() {
        StockTrade trade = new StockTrade(sellOrder, buyOrder);
        assertThat(trade.fulfill()).isTrue();
    }

    @Test
    public void whenIncompatibleOrders_thenTradeRejected() {
        StockTrade trade = new StockTrade(sellOrder, sellOrder);
        assertThrows(IllegalStateException.class, () -> {
            trade.fulfill();
        });
    }

    @Test
    public void whenInvalidOrder_thenTradeRejected() {
        StockTrade trade = new StockTrade(invalidOrder, sellOrder);
        assertThrows(IllegalStateException.class, () -> {
            trade.fulfill();
        });
    }

    @Test
    public void whenInvalidTrade_thenNotCreated() {
        assertThrows(IllegalStateException.class, () -> {
            new StockTrade(sellOrder, null);
        });
    }

    @Test
    public void whenOrderCancelled_thenStatusIsCancelled() {
        sellOrder.cancel();
        assertThat(sellOrder.getStatus()).isEqualTo(TradeStatus.CANCELED);
    }
}
