package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.StockRequestPortIF;

/**
 * This will be the interface that will be composed of all the ports that receives requests from the outside to drive the application.
 * 
 * @author vsempere
 *
 */
public interface StockServiceIF extends StockRequestPortIF {

}
