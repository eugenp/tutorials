package com.baeldung.hexagonalarchitecture.liquiditytracker.domain.impl;

import org.junit.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.NotEnoughAvailableLiquidityException;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityTrackerImplInternalMethodsTest {
    private static final Logger log = LoggerFactory.getLogger(LiquidityTrackerImplInternalMethodsTest.class);
    
    private final LiquidityTrackerImpl trackerImpl;
    
    public LiquidityTrackerImplInternalMethodsTest() {
        trackerImpl = new LiquidityTrackerImpl();
    }
     
    
    @Test
    public void getAvailableLiquidityTest(){
        long liquidityLimit = 600;
        long utilizedLiquidity = 100;
        
        long availableLiquidity = trackerImpl.getAvailableLiquidity(liquidityLimit, utilizedLiquidity);
        Assert.assertEquals(500, availableLiquidity);
    }
    
    @Test
    public void checkIfThereIsEnoughLiquidityAvailableTest1() {
        long newLiquidityLimit = 300;
        long utilizedLiquidity = 250;
        
        try {
            trackerImpl.checkIfThereIsEnoughLiquidityAvailable(newLiquidityLimit, utilizedLiquidity);
        } catch (NotEnoughAvailableLiquidityException ex) {
            Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should not have been thrown.");
        }
    }

    @Test
    public void checkIfThereIsEnoughLiquidityAvailableTest2() {
        long newLiquidityLimit = 300;
        long utilizedLiquidity = 301;
        
        try {
            trackerImpl.checkIfThereIsEnoughLiquidityAvailable(newLiquidityLimit, utilizedLiquidity);
        } catch (NotEnoughAvailableLiquidityException ex) {
            log.trace("Exception was thown as expected: " + ex);
            return;
        }
        
        Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should have been thrown."); 
    } 
    
    @Test
    public void increaseUtilizedLiquidityAmountTest1() {
        long currentUtilizedLiquidity = 400; 
        long increasingUtilizedLiquidityAmount = 100; 
        long liquidityLimit = 500;
        
        try {
            trackerImpl.increaseUtilizedLiquidityAmount(currentUtilizedLiquidity, increasingUtilizedLiquidityAmount, 
                    liquidityLimit);
        } catch (NotEnoughAvailableLiquidityException e) {
            Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should not have been thrown.");
        }
    }
    
    @Test
    public void increaseUtilizedLiquidityAmountTest2() {
        long currentUtilizedLiquidity = 400; 
        long increasingUtilizedLiquidityAmount = 100; 
        long liquidityLimit = 499;
        
        try {
            trackerImpl.increaseUtilizedLiquidityAmount(currentUtilizedLiquidity, increasingUtilizedLiquidityAmount, 
                    liquidityLimit);
        } catch (NotEnoughAvailableLiquidityException ex) {
            log.trace("Exception was thown as expected: " + ex);
            return;
        }
        
        Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should have been thrown."); 
    }
    
    public void decreaseUtilizedLiquidityAmountTest() {
        long currentUtilizedLiquidity = 200;
        long decreasingAmount = 50;
        
        long newUtilizedLiquidityAmount = trackerImpl.decreaseUtilizedLiquidityAmount(currentUtilizedLiquidity, 
                decreasingAmount);
        
        Assert.assertEquals(150, newUtilizedLiquidityAmount);
    }
}

