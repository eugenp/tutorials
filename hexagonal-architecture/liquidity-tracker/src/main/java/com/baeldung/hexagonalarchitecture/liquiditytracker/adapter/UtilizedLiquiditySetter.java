package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public interface UtilizedLiquiditySetter {
    public void set(long id, long operationAmount, long totalAmount);
}
