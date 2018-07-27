package com.baeldung.state.states;

import com.baeldung.state.context.TradeContext;

public interface TradeBehavior {
    void trade(TradeContext ctx);
}
