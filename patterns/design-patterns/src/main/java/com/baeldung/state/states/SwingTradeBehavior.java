package com.baeldung.state.states;

import com.baeldung.state.context.TradeContext;

public class SwingTradeBehavior implements TradeBehavior {

    @Override
    public void trade(TradeContext ctx) {
        System.out.println("Trading method: Swing Trade");
    }
}
