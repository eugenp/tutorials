package com.baeldung.hexagonalspringboot.infrastucture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalspringboot.domain.Product;
import com.baeldung.hexagonalspringboot.port.IProductSearchRepository;

@Repository
public class ProductSearchRepositoryImpl implements IProductSearchRepository {

    private ArrayList<Product> productsList = new ArrayList<Product>();

    @PostConstruct
    private void loadProducts() {
        productsList.add(new Product(new Long(1), "laptop", "Apple Macbook Pro"));
        productsList.add(new Product(new Long(2), "tablet", "Google Pixel Slate"));
        productsList.add(new Product(new Long(3), "tablet", "Microsoft Surface"));
        productsList.add(new Product(new Long(4), "laptop", "Dell XPS 9570"));
        productsList.add(new Product(new Long(5), "laptop", "HP Inspiron 7580"));
      }

    @Override
    public List<Product> findByCategory(String category) {
        List<Product> productsByCategoryList =
            // Create a Stream from the personList
            productsList.stream()
                .filter(product -> product.getCategory()
                .contentEquals(category))
                .collect(Collectors.toCollection(() -> new ArrayList<Product>()));
        return productsByCategoryList;
    }

    @Override
    public List<Product> findAll() {
        return productsList;
    }
}
