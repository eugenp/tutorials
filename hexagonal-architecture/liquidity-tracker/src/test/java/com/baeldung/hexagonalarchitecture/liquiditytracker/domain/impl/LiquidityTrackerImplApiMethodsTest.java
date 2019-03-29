package com.baeldung.hexagonalarchitecture.liquiditytracker.domain.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting.LiquidityLimitProviderForTesting;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting.LiquidityLimitSetterForTesting;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting.UtilizedLiquidityProviderForTesting;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.implfortesting.UtilizedLiquiditySetterForTesting;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.LiquidityTracker;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.NotEnoughAvailableLiquidityException;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityTrackerImplApiMethodsTest {
    private static final Logger log = LoggerFactory.getLogger(LiquidityTrackerImplApiMethodsTest.class);
    
    // The following two references point to the same object
    private LiquidityTrackerImpl trackerImpl;
    private LiquidityTracker tracker;
    
    private LiquidityLimitProviderForTesting liquidityLimitProvider;
    
    private UtilizedLiquidityProviderForTesting utilizedLiquidityProvider;
    
    private LiquidityLimitSetterForTesting liquidityLimitSetter;
    
    private UtilizedLiquiditySetterForTesting utilizedLiquiditySetter;
    
    public LiquidityTrackerImplApiMethodsTest(){
        trackerImpl = new LiquidityTrackerImpl();
        
        liquidityLimitProvider = new LiquidityLimitProviderForTesting();
        utilizedLiquidityProvider = new UtilizedLiquidityProviderForTesting();
        
        liquidityLimitSetter = new LiquidityLimitSetterForTesting();
        utilizedLiquiditySetter = new UtilizedLiquiditySetterForTesting();
        
        
        trackerImpl.setLiquidityLimitProvider(liquidityLimitProvider);
        trackerImpl.setLiquidityLimitSetter(liquidityLimitSetter);
        
        trackerImpl.setUtilizedLiquidityProvider(utilizedLiquidityProvider);
        trackerImpl.setUtilizedLiquiditySetter(utilizedLiquiditySetter);
        
        tracker = (LiquidityTracker) trackerImpl; 
    }
    
    @Test
    public void testAvailableLiquidityMethod() {
        liquidityLimitProvider.setData(1, 1000);
        utilizedLiquidityProvider.setData(1, 100);
        
        long availableLiquidity = tracker.getAvailableLiquidity();
        log.trace("Available liquidity: " + availableLiquidity);
        Assert.assertEquals(900, availableLiquidity);
    }
    
    @Test
    public void testUtilizedLiquidityMethod() {
        liquidityLimitProvider.setData(1, 1000);
        utilizedLiquidityProvider.setData(1, 500);
        
        long utilizedLiquidity = tracker.getUtilizedLiquidity();
        log.trace("Utilized liquidity: " + utilizedLiquidity);
        Assert.assertEquals(500, utilizedLiquidity);
    }

    @Test
    public void testIncreaseUtilizedLiquidityMethod1(){
        liquidityLimitProvider.setData(20, 1000);
        utilizedLiquidityProvider.setData(1, 100);
        
        try {
            tracker.increaseUtilizedLiquidity(300);
        } catch (NotEnoughAvailableLiquidityException ex) {
            Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should not have been thrown.");
        }
        
        log.trace("New utilized liquidity amount after increasing it: " + utilizedLiquiditySetter.getNewTotalAmount());
        Assert.assertEquals(400, utilizedLiquiditySetter.getNewTotalAmount());
        Assert.assertEquals(2, utilizedLiquiditySetter.getId());    
    }
    
    @Test
    public void testIncreaseUtilizedLiquidityMethod2(){
        liquidityLimitProvider.setData(11, 1000);
        utilizedLiquidityProvider.setData(1, 100);
        
        try {
            tracker.increaseUtilizedLiquidity(950);
        } catch (NotEnoughAvailableLiquidityException ex) {
            log.trace("Exception was thown as expected: " + ex);
            return;
        }
        
        Assert.fail(NotEnoughAvailableLiquidityException.class.getSimpleName() + " should have been thrown.");       
    }
    
    @Test
    public void testDecreaseUtilizedLiquidityMethod(){
        liquidityLimitProvider.setData(9, 700);
        utilizedLiquidityProvider.setData(1, 400); 
        
        tracker.decreaseUtilizedLiquidity(300);
        
        log.trace("New utilized liquidity amount after decreasing it: " + utilizedLiquiditySetter.getNewTotalAmount());
        Assert.assertEquals(100, utilizedLiquiditySetter.getNewTotalAmount());
        Assert.assertEquals(2, utilizedLiquiditySetter.getId());  
    }
}

