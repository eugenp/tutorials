package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

/**
 * @author VictorGil
 *
 * since March 2019
 */
public interface MessageProcessor {
    public void process(byte[] data);
}
