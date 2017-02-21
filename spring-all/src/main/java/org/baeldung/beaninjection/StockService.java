package org.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    @Autowired
    private StockDao stockDao;
    
    public void updateStock(Product product) {
        stockDao.update(product);
    }

}
