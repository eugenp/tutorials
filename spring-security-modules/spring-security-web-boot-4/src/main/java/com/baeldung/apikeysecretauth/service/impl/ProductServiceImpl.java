package com.baeldung.apikeysecretauth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.baeldung.apikeysecretauth.model.Product;
import com.baeldung.apikeysecretauth.model.User;
import com.baeldung.apikeysecretauth.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String[] PRODUCT_OPTS = { "MILK", "EGGS", "CHEESE", "BUTTER" };

    @Override
    public List<Product> getAllProducts(User user) {
        int noOfItems = ThreadLocalRandom.current()
            .nextInt(PRODUCT_OPTS.length);
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < noOfItems; i++) {
            String productName = PRODUCT_OPTS[i];
            String sku = String.format("%s|%s", productName, user.getUserId());
            products.add(new Product(UUID.randomUUID()
                .toString(), productName, sku));
        }
        return products;
    }
}
