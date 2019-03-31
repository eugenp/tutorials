package com.baeldung.hexagonalarchitecture.liquiditytracker.domain;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class NotEnoughAvailableLiquidityException extends Exception {
    private static final long serialVersionUID = 4674553774364286372L;

    public NotEnoughAvailableLiquidityException(String errorMessage) {
        super(errorMessage);
    }
}
