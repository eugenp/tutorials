package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.UtilityLimitData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface LiquidityLimitProvider{
    public UtilityLimitData provide();
}

