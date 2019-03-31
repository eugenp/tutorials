package com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class UtilityLimitData{
    private static final Logger log = LoggerFactory.getLogger(UtilityLimitData.class);
    
    private final long id;
    private final long amount;
    
    public UtilityLimitData(long id, long amount){
        this.id = id;
        this.amount = amount;
    }

    public long getId(){
        return id;
    }

    public long getAmount(){
        return amount;
    }
}
