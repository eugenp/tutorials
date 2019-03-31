package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.LiquidityLimitProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.LiquidityLimitData;

/**
 * @author Víctor Gil
 *
 *         since March 2019
 */
public class LiquidityLimitProviderForTesting implements LiquidityLimitProvider {

    private LiquidityLimitData data;

    public LiquidityLimitProviderForTesting() {
    }

    public LiquidityLimitProviderForTesting(long id, long amount) {
        data = new LiquidityLimitData(id, amount);
    }

    @Override
    public LiquidityLimitData provide() {
        return data;
    }

    public void setData(long id, long amount) {
        data = new LiquidityLimitData(id, amount);
    }
}
