package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.StockPort;
import com.baeldung.hexagonal.repository.StockRepository;

/**
 * User: raychenon
 * Date: 12/7/2020
 * <p>

 */
public class StockAdapter implements StockPort {

    private StockRepository stockRepository;

    public StockAdapter(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    @Override
    public int[] getStockPrices(String stockName) {
        return stockRepository.getStock(stockName);
    }
}
