package com.baeldung.patterns.quick.hexagonal.architecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.patterns.hexagonal.architecture.domain.Stock;
import com.baeldung.patterns.hexagonal.architecture.domain.TradeOrder;
import com.baeldung.patterns.hexagonal.architecture.domain.TradeType;
import com.baeldung.patterns.hexagonal.architecture.domain.StockTrade;

@RunWith(SpringRunner.class)
class QuickHexagonalArchitectureApplicationUnitTest {
    Stock stock = new Stock("GOOGL");

    TradeOrder sellOrder = new TradeOrder(TradeType.SELL, 2, stock, 2819.55);
    TradeOrder buyOrder = new TradeOrder(TradeType.BUY, 2, stock, 2819.55);
    TradeOrder invalidOrder = new TradeOrder(null, null, null, null);

    @Test
    void whenMatchingOrders_thenTradeFulfilled() {
        StockTrade trade = new StockTrade(sellOrder, buyOrder);
        assertThat(trade.fulfill()).isTrue();
    }

    @Test
    void whenIncompatibleOrders_thenTradeRejected() {
        StockTrade trade = new StockTrade(sellOrder, sellOrder);
        assertThrows(IllegalStateException.class, () -> {
            trade.fulfill();
        });
    }

    @Test
    void whenInvalidOrder_thenTradeRejected() {
        StockTrade trade = new StockTrade(invalidOrder, sellOrder);
        assertThrows(IllegalStateException.class, () -> {
            trade.fulfill();
        });
    }

    @Test
    void whenInvalidTrade_thenNotCreated() {
        assertThrows(IllegalStateException.class, () -> {
            new StockTrade(sellOrder, null);
        });
    }
}
