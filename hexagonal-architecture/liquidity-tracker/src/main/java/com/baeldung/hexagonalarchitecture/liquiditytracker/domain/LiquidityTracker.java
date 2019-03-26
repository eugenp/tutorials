package com.baeldung.hexagonalarchitecture.liquiditytracker.domain;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface LiquidityTracker{

    public long getUtilityLimit();
    
    public void setUtilityLimit(long utilityLimit) throws NotEnoughAvailableLiquidityException;
    
    public long getAvailableUtility();
    
    public long getUtilizedUtility();
    
    public void increaseUtilizedLiquidity(long amount);
    
    public void decreaseUtilizedLiquidity(long amount) throws NotEnoughAvailableLiquidityException;    
}

