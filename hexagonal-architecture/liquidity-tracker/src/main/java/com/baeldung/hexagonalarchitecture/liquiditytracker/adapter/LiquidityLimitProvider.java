package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.LiquidityLimitData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface LiquidityLimitProvider{
    public LiquidityLimitData provide();
}

