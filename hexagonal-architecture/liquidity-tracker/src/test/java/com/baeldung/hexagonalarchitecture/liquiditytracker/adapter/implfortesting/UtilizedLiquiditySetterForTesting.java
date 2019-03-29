package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.UtilizedLiquiditySetter;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class UtilizedLiquiditySetterForTesting implements UtilizedLiquiditySetter{
    private static final Logger log = LoggerFactory.getLogger(UtilizedLiquiditySetter.class);
    
    private long id;
    private long operationAmount;
    private long newTotalAmount;
    
    @Override
    public void set(long id, long operationAmount, long newTotalAmount) {
        log.info("Pretending to set the new utilized liquidity, operation amount: " + operationAmount + 
                ", new id: " + id + ", new total utilized liquidity amount: " + newTotalAmount);
        
        this.id = id;
        this.operationAmount = operationAmount;
        this.newTotalAmount = newTotalAmount;
    }

    public long getId() {
        return id;
    }

    public long getOperationAmount() {
        return operationAmount;
    }

    public long getNewTotalAmount() {
        return newTotalAmount;
    }    
}
