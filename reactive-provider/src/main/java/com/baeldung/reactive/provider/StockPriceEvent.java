package com.baeldung.reactive.provider;

import java.util.Date;

public class StockPriceEvent {
    
    public StockPriceEvent(int stockPrice,Date date) {
        this.stockPrice = stockPrice;
        this.date = date;
    }
    
    private int stockPrice;
    
    private Date date;

    public long getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    

}
