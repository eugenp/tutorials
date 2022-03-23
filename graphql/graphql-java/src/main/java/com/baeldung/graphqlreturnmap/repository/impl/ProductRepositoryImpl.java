package com.baeldung.graphqlreturnmap.repository.impl;

import com.baeldung.graphqlreturnmap.entity.Attribute;
import com.baeldung.graphqlreturnmap.entity.Product;
import com.baeldung.graphqlreturnmap.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static List<Product> productList = new ArrayList<>();

    public ProductRepositoryImpl() {
        for (int i = 1; i <= 10; i++){
            Product product = new Product();
            product.setId(i);
            product.setName(String.format("Product %d", i));
            product.setDescription(String.format("Product %d description", i));
            product.setCurrency(String.format("Product %d currency", i));
            product.setPrice(Double.valueOf(i^2));
            product.setStock(10);
            product.setAverage_rating(0F);
            product.setImage_url(Arrays.asList(String.format("www.baeldung.com/imageurl/%d", i)));
            product.setVideo_url(Arrays.asList(String.format("www.baeldung.com/videourl/%d", i)));
            product.setAttributes(createAttributes(i));
            productList.add(product);
        }
    }

    private Map<String, Attribute> createAttributes(int i) {
        Map<String, Attribute> attributeMap = new HashMap<>();
        attributeMap.put(String.format("attribute_%d",i), new Attribute(String.format("Attribute%d name",i),"This is custom attribute description","This is custom attribute unit"));
        attributeMap.put("size", new Attribute((i & 1) == 0 ? "Small" : "Large","This is custom attribute description","This is custom attribute unit"));
        return attributeMap;
    }

    @Override
    public List<Product> getProducts(Integer pageSize, Integer pageNumber) {
        return productList.stream().skip(pageSize*pageNumber).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public Product getProduct(Integer id) {
        return productList.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }
}
