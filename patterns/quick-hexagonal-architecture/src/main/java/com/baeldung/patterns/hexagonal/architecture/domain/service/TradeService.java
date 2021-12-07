package com.baeldung.patterns.hexagonal.architecture.domain.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.baeldung.patterns.hexagonal.architecture.domain.Stock;
import com.baeldung.patterns.hexagonal.architecture.domain.StockTrade;
import com.baeldung.patterns.hexagonal.architecture.domain.TradeOrder;

public interface TradeService {
    Optional<TradeOrder> findTradeOrderById(Serializable id);

    Serializable placeOrder(TradeOrder order);

    boolean cancelOrder(Serializable id);

    Serializable trade(Serializable orderIdA, Serializable orderIdB);

    List<TradeOrder> findMatchingOrders(Serializable orderId);

    void saveStock(Stock Stock);

    Iterable<Stock> findAllStocks();

    Optional<Stock> findStockById(String ticker);

    Iterable<TradeOrder> findAllTradeOrders();

    Iterable<StockTrade> findAllStockTrades();
}
