package org.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockService {
    
    private StockDao stockDao;
    
    public void updateStock(Product product) {
        stockDao.update(product);
    }

    @Autowired
    public void setStockDao(StockDao stockDao) {
        this.stockDao = stockDao;
    }

}
