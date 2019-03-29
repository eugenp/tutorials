package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.LiquidityLimitSetter;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityLimitSetterForTesting implements LiquidityLimitSetter{
    private static final Logger log = LoggerFactory.getLogger(LiquidityLimitSetterForTesting.class);
    
    private long id;
    private long liquidityLimit;
    
    @Override
    public void set(long id, long newLiquidityLimit) {
        log.info("Pretending to set the new liquidity limit, new id: " + id +
                ", new liquidity limit amount: " + newLiquidityLimit);        
    }

    public long getId() {
        return id;
    }

    public long getLiquidityLimit() {
        return liquidityLimit;
    }
}
