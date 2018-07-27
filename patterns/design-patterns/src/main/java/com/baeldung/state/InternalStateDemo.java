package com.baeldung.state;

import com.baeldung.state.context.TradeContext;
import com.baeldung.state.states.FallbackDayTradeBehavior;

public class InternalStateDemo {

    public static void main(String[] args) {

        TradeContext tradeContext = new TradeContext(new FallbackDayTradeBehavior());
        tradeContext.trade();
        tradeContext.trade();
        tradeContext.trade();
    }
}
