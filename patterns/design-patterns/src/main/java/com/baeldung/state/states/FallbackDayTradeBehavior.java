package com.baeldung.state.states;

import com.baeldung.state.context.TradeContext;

public class FallbackDayTradeBehavior implements TradeBehavior {

    @Override
    public void trade(TradeContext ctx) {
        System.out.println("Trading method: Day Trade");

        if (Math.random() > 0.4) {
            ctx.setBehavior(new SwingTradeBehavior());
            System.out.println("Day trading would not go well, falling back to Swing Trade");
        }
    }
}
