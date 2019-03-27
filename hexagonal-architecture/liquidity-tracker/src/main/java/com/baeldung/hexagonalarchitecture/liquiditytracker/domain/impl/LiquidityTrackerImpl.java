package com.baeldung.hexagonalarchitecture.liquiditytracker.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.LiquidityLimitProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.LiquidityLimitSetter;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.UtilizedLiquidityProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.LiquidityLimitData;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.UtilizedLiquidityData;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.LiquidityTracker;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.UtilizedLiquiditySetter;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.NotEnoughAvailableLiquidityException;

/**
 * @author Víctor Gil
 *
 *         since March 2019
 */
public class LiquidityTrackerImpl implements LiquidityTracker {
    private static final Logger log = LoggerFactory.getLogger(LiquidityTrackerImpl.class);

    private LiquidityLimitProvider liquidityLimitProvider;

    private LiquidityLimitSetter liquidityLimitSetter;

    private UtilizedLiquidityProvider utilizedLiquidityProvider;

    private UtilizedLiquiditySetter UtilizedLiquiditySetter;

    @Override
    public long getLiquidityLimit() {
        LiquidityLimitData liquidityLimitData = liquidityLimitProvider.provide();

        return liquidityLimitData.getAmount();
    }

    @Override
    public void setLiquidityLimit(long newLiquidityLimit) throws NotEnoughAvailableLiquidityException {
        long utilizedLiquidity = getUtilizedLiquidity();
        
        LiquidityLimitData currentLiquidityLimitData = liquidityLimitProvider.provide();
        
        long newLiquidityLimitId = calculateNewLiquidityLimitId(newLiquidityLimit, utilizedLiquidity, currentLiquidityLimitData.getId());

        liquidityLimitSetter.set(newLiquidityLimitId, newLiquidityLimit);       
    }

    long calculateNewLiquidityLimitId(long newLiquidityLimit, long utilizedLiquidity, long currentLiquidityLimitId) 
        throws NotEnoughAvailableLiquidityException {
        
        if (utilizedLiquidity > newLiquidityLimit)
            throw new NotEnoughAvailableLiquidityException();
        
        return currentLiquidityLimitId + 1;
    }
    
    @Override
    public long getAvailableLiquidity() {
        long liquidityLimit = getLiquidityLimit();
        long utilizedLiquidity = getUtilizedLiquidity();
        
        assert liquidityLimit >= utilizedLiquidity;
        
        return liquidityLimit - utilizedLiquidity;
    }

    @Override
    public long getUtilizedLiquidity() {
        UtilizedLiquidityData utilizedLiquidityData = utilizedLiquidityProvider.provide();
        
        return utilizedLiquidityData.getAmount();
    }

    @Override
    public void increaseUtilizedLiquidity(long amount) {
        // TODO Auto-generated method stub

    }

    @Override
    public void decreaseUtilizedLiquidity(long amount) throws NotEnoughAvailableLiquidityException {
        // TODO Auto-generated method stub

    }

    public void setLiquidityLimitProvider(LiquidityLimitProvider liquidityLimitProvider) {
        this.liquidityLimitProvider = liquidityLimitProvider;
    }

    public void setLiquidityLimitSetter(LiquidityLimitSetter liquidityLimitSetter) {
        this.liquidityLimitSetter = liquidityLimitSetter;
    }

    public void setUtilizedLiquidityProvider(UtilizedLiquidityProvider utilizedLiquidityProvider) {
        this.utilizedLiquidityProvider = utilizedLiquidityProvider;
    }

    public void setUtilizedLiquiditySetter(UtilizedLiquiditySetter utilizedLiquiditySetter) {
        UtilizedLiquiditySetter = utilizedLiquiditySetter;
    }
}
