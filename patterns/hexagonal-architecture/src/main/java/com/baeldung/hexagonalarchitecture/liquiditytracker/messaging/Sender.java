package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface Sender {

    public void sendLiquidityLimit(long value);
    
    public void sendAvailableLiquidity(long value);
    
    public void sendUtilizedLiquidity(long value); 
    
    public void sendSetLiquidityLimitError(String errorMessage);
    
    public void increaseUtilizedLiquidityError(String errorMessage);
}
