package com.baeldung.hexagonalarchitecture.liquiditytracker.domain;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface LiquidityTracker{

    public long getLiquidityLimit();
    
    public void setLiquidityLimit(long utilityLimit) throws NotEnoughAvailableLiquidityException;
    
    public long getAvailableLiquidity();
    
    public long getUtilizedLiquidity();
    
    public void increaseUtilizedLiquidity(long amount);
    
    public void decreaseUtilizedLiquidity(long amount) throws NotEnoughAvailableLiquidityException;    
}

