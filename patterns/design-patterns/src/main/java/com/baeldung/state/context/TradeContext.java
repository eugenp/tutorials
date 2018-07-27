package com.baeldung.state.context;

import com.baeldung.state.states.TradeBehavior;

public class TradeContext {

    private TradeBehavior behavior;

    public TradeContext(TradeBehavior behavior) {
        this.behavior = behavior;
    }

    public TradeBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(TradeBehavior behavior) {
        this.behavior = behavior;
    }

    public void trade() {
        behavior.trade(this);
    }

    @Override
    public String toString() {
        return "TradeContext{" + "behavior=" + behavior + '}';
    }
}
