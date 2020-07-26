package com.baeldung.hexagonalspringboot.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalspringboot.port.IProductSearchRepository;
import com.baeldung.hexagonalspringboot.port.IProductSearchService;

@Service
public class ProductSearchServiceImpl implements IProductSearchService {

    public ProductSearchServiceImpl() {
    }

    @Autowired
    private IProductSearchRepository productSearchRepo;

    @Override
    public List<Product> getproductsByCategory(String category) {
        return productSearchRepo.findByCategory(category);
    }

    @Override
    public List<Product> getAllproducts() {
        return productSearchRepo.findAll();
    }

}
