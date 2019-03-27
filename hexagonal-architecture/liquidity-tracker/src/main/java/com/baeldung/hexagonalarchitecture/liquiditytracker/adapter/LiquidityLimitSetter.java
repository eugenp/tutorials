package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface LiquidityLimitSetter {
    public void set(long id, long liquidityLimit);
}
