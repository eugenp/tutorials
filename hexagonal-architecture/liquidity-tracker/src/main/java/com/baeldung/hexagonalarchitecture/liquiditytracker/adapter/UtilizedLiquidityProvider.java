package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.UtilizedLiquidityData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface UtilizedLiquidityProvider{
    public UtilizedLiquidityData provide();    
}

