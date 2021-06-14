package com.baeldung.hexarchitecture.productstore.core.domain.service;

import com.baeldung.hexarchitecture.productstore.core.domain.model.Product;
import com.baeldung.hexarchitecture.productstore.ports.ProductRepository;
import com.baeldung.hexarchitecture.productstore.ports.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

        @Autowired
        ProductRepository productRepository;

        @Override
        public void createProduct(Product product) {
                productRepository.createProduct(product);
        }

        @Override
        public Product getProduct(String id) {
                return productRepository.getProduct(id);
        }
}
