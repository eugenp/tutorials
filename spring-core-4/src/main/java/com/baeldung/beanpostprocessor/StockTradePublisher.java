package com.baeldung.beanpostprocessor;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

@Subscriber
public class StockTradePublisher {

    private final Set<StockTradeListener> stockTradeListeners = new HashSet<>();

    public void addStockTradeListener(StockTradeListener listener) {
        synchronized (this.stockTradeListeners) {
            this.stockTradeListeners.add(listener);
        }
    }

    public void removeStockTradeListener(StockTradeListener listener) {
        synchronized (this.stockTradeListeners) {
            this.stockTradeListeners.remove(listener);
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    private void handleNewStockTradeEvent(StockTrade trade) {
        // publish to DB, send to PubNub, whatever you want here
        final Set<StockTradeListener> listeners;
        synchronized (this.stockTradeListeners) {
            listeners = new HashSet<>(this.stockTradeListeners);
        }
        listeners.forEach(li -> li.stockTradePublished(trade));
    }
}
