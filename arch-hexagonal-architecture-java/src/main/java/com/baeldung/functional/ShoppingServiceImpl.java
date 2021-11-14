package com.baeldung.functional;

import com.baeldung.persistence.ShoppingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingDao shoppingDao;

    @Override
    public void addProduct(Long cartNo, Long productNo, Long quantity) {
        shoppingDao.addProduct(cartNo, productNo, quantity);
    }

    @Override
    public void removeProduct(Long cartNo, Long productNo, Long quantity) {
        // TODO implement it
        throw new RuntimeException("Implement it");
    }

    @Override
    public void checkout(Long cartNo) {
        // TODO implement it
        throw new RuntimeException("Implement it");
    }

}
