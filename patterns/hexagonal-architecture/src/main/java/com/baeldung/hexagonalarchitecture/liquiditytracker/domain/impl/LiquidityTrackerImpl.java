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
 * since March 2019
 */
public class LiquidityTrackerImpl implements LiquidityTracker {
    private static final Logger log = LoggerFactory.getLogger(LiquidityTrackerImpl.class);

    private LiquidityLimitProvider liquidityLimitProvider;

    private LiquidityLimitSetter liquidityLimitSetter;

    private UtilizedLiquidityProvider utilizedLiquidityProvider;

    private UtilizedLiquiditySetter utilizedLiquiditySetter;

    @Override
    public long getLiquidityLimit() {
        LiquidityLimitData liquidityLimitData = liquidityLimitProvider.provide();

        long liquidityLimit = liquidityLimitData.getAmount();
        log.trace("Current liquidity limit: " + liquidityLimit);

        return liquidityLimitData.getAmount();
    }

    long getLiquidityLimitId() {
        LiquidityLimitData liquidityLimitData = liquidityLimitProvider.provide();

        long liquidityLimitId = liquidityLimitData.getId();
        log.trace("Current liquidity limit id/version: " + liquidityLimitId);

        return liquidityLimitData.getId();
    }

    @Override
    public void setLiquidityLimit(long newLiquidityLimit) throws NotEnoughAvailableLiquidityException {
        long utilizedLiquidity = getUtilizedLiquidity();

        long currentLiquidityLimitId = getLiquidityLimitId();

        // this method can easily be unit-tested
        checkIfThereIsEnoughLiquidityAvailable(newLiquidityLimit, utilizedLiquidity);

        liquidityLimitSetter.set(currentLiquidityLimitId + 1, newLiquidityLimit);
    }

    void checkIfThereIsEnoughLiquidityAvailable(long newLiquidityLimit, long utilizedLiquidity)
            throws NotEnoughAvailableLiquidityException {
        if (utilizedLiquidity > newLiquidityLimit) {
            String errorMessage = "There is not enough liquidity available, trying to set a new liquidity limit: "
                    + newLiquidityLimit + " which is smaller than the current utilized liquidity: " + utilizedLiquidity;
            log.error(errorMessage);
            throw new NotEnoughAvailableLiquidityException(errorMessage);
        }
    }

    @Override
    public long getAvailableLiquidity() {

        // this method can easily be unit-tested
        long availableLiquidity = getAvailableLiquidity(getLiquidityLimit(), getUtilizedLiquidity());
        log.trace("Current available liquidity: " + availableLiquidity);

        return availableLiquidity;
    }

    long getAvailableLiquidity(long liquidityLimit, long utilizedLiquidity) {
        assert liquidityLimit >= utilizedLiquidity;

        return liquidityLimit - utilizedLiquidity;
    }

    @Override
    public long getUtilizedLiquidity() {
        UtilizedLiquidityData utilizedLiquidityData = utilizedLiquidityProvider.provide();

        long utilizedLiquidity = utilizedLiquidityData.getAmount();
        log.trace("Current utilized liquidity: " + utilizedLiquidity);

        return utilizedLiquidity;
    }

    @Override
    public void increaseUtilizedLiquidity(long amount) throws NotEnoughAvailableLiquidityException {
        UtilizedLiquidityData currentData = utilizedLiquidityProvider.provide();
        UtilizedLiquidityData newData = increaseUtilizedLiquidity(currentData.getAmount(), amount, currentData.getId(),
                getLiquidityLimit());
        utilizedLiquiditySetter.set(newData.getId(), amount, newData.getAmount());
    }

    UtilizedLiquidityData increaseUtilizedLiquidity(long currentUtilizedLiquidity, long increasingAmount,
            long currentId, long liquidityLimit) throws NotEnoughAvailableLiquidityException {
        UtilizedLiquidityData newData = new UtilizedLiquidityData(++currentId,
                increaseUtilizedLiquidityAmount(currentUtilizedLiquidity, increasingAmount, liquidityLimit));
        return newData;
    }

    long increaseUtilizedLiquidityAmount(long currentUtilizedLiquidity, long increasingUtilizedLiquidityAmount,
            long liquidityLimit) throws NotEnoughAvailableLiquidityException {
        long liquidityAvailable = liquidityLimit - currentUtilizedLiquidity;

        if (increasingUtilizedLiquidityAmount > liquidityAvailable) {
            String errorMessage = "There is not enough liquidity available: " + liquidityAvailable
                    + ", trying to increase the utilized liquidity by " + increasingUtilizedLiquidityAmount
                    + " and the current value is " + currentUtilizedLiquidity + ". Current liquidity limit is "
                    + liquidityLimit;
            NotEnoughAvailableLiquidityException exception = new NotEnoughAvailableLiquidityException(errorMessage);
            log.error(errorMessage);
            throw exception;
        }

        return currentUtilizedLiquidity + increasingUtilizedLiquidityAmount;
    }

    @Override
    public void decreaseUtilizedLiquidity(long amount) {
        UtilizedLiquidityData utilizedLiquidityData = utilizedLiquidityProvider.provide();
        long newUtilizedLiquidityAmount = decreaseUtilizedLiquidityAmount(utilizedLiquidityData.getAmount(), amount);
        utilizedLiquiditySetter.set(utilizedLiquidityData.getId() + 1, amount, newUtilizedLiquidityAmount);
    }

    long decreaseUtilizedLiquidityAmount(long currentUtilizedLiquidity, long decreasingAmount) {
        return currentUtilizedLiquidity - decreasingAmount;
    }

    // Setter methods
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
        this.utilizedLiquiditySetter = utilizedLiquiditySetter;
    }
}
