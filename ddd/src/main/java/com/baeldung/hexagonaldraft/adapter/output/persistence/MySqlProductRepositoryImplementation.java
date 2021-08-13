package com.baeldung.hexagonaldraft.adapter.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonaldraft.domain.model.Product;
import com.baeldung.hexagonaldraft.port.outbound.ProductRepository;

@Primary
@Component
public class MySqlProductRepositoryImplementation implements ProductRepository {

    @Autowired
    private MySqlSpringDataRepository mysqlSpringDataRepository;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) mysqlSpringDataRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer productId) {
        return mysqlSpringDataRepository.findById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        mysqlSpringDataRepository.save(product);
        return product;
    }

    @Override
    public Optional<Product> removeProduct(Integer productId) {
        Optional<Product> product = mysqlSpringDataRepository.findById(productId);
        mysqlSpringDataRepository.deleteById(productId);
        return product;

    }
}
