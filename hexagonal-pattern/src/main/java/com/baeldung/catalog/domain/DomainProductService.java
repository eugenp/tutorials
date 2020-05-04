package com.baeldung.catalog.domain;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DomainProductService implements ProductService {
    private ProductRepository repository;
    
    public DomainProductService(ProductRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void createProduct(String id, String shortName) {
        repository.findById(id).ifPresent(e -> {throw new BusinessException();});
        repository.save(new Product(id, shortName));
    }
    
    @Override
    public Product getProductById(String id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
    }
    
    @Override
    public void addPrice(String id, ProductPrice price) {
        Product product = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
        product.addPrice(price);
        repository.save(product);
    }
}
