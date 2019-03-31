package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.UtilizedLiquidityProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.UtilizedLiquidityData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class UtilizedLiquidityProviderForTesting implements UtilizedLiquidityProvider {

    private UtilizedLiquidityData data;

    public UtilizedLiquidityProviderForTesting() {
    }

    public UtilizedLiquidityProviderForTesting(long id, long amount) {
        data = new UtilizedLiquidityData(id, amount);
    }

    @Override
    public UtilizedLiquidityData provide() {
        return data;
    }

    public void setData(long id, long amount) {
        data = new UtilizedLiquidityData(id, amount);
    }
}
