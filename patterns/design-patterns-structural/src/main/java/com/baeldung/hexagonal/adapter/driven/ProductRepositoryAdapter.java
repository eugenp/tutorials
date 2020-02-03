package com.baeldung.hexagonal.adapter.driven;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.domain.ProductVO;
import com.baeldung.hexagonal.ports.driven.IRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductRepositoryAdapter implements IRepository<Product, ProductVO> {

    private static ProductRepositoryAdapter instance;
    Map<UUID, Product> inMemory = new HashMap<>();

    private ProductRepositoryAdapter() {
    }

    public static ProductRepositoryAdapter getInstance() {
        if (instance == null)
            instance = new ProductRepositoryAdapter();

        return instance;
    }

    @Override
    public Product create(ProductVO vo) {
        Product newDog = new Product(vo.getName());
        inMemory.put(newDog.getId(), newDog);
        return newDog;
    }

    @Override
    public Collection<Product> list() {
        return inMemory.values();
    }
}

