package com.baeldung.state;

import com.baeldung.state.context.TradeContext;
import com.baeldung.state.states.DayTradeBehavior;
import com.baeldung.state.states.PositionTradeBehavior;
import com.baeldung.state.states.SwingTradeBehavior;

public class ExplicitStateDemo {

    public static void main(String[] args) {

        TradeContext tradeContext = new TradeContext(new SwingTradeBehavior());
        tradeContext.trade();

        tradeContext.setBehavior(new PositionTradeBehavior());
        tradeContext.trade();

        tradeContext.setBehavior(new DayTradeBehavior());
        tradeContext.trade();
    }
}
