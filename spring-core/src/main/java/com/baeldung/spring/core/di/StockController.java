package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StockController {

    private final StockService stockService;

    // @Autowired not required if using a single constructor in class definition
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    public void displayStockDetails(int stockId) {
        Stock stock = stockService.getStock(stockId);
        if (stock != null) {
            stock.displayStock();
        } else {
            System.out.println("Stock not available!");
        }
    }
}
