package com.baeldung.service;

import com.baeldung.domain.Product;
import com.baeldung.port.ProductRepositoryPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductService {

    @Autowired
    private ProductRepositoryPort employeeRepository;

    public void create(String name, String role, long salary){
        employeeRepository.create(name, role, salary);
    }

    public Product view(Integer productId){
        return employeeRepository.getProduct(productId);
    }
}