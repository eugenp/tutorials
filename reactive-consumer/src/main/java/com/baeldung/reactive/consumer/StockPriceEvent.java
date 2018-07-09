package com.baeldung.reactive.consumer;

import java.util.Date;

public class StockPriceEvent {
    
    private int stockPrice;
    
    private Date date;
    
    public StockPriceEvent(int stockPrice,Date date) {
        this.stockPrice = stockPrice;
        this.date = date;
    }
    
    public StockPriceEvent() {

    }

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
