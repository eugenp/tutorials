package com.baeldung.graphqlvsrest.repository.impl;

import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;
import com.baeldung.graphqlvsrest.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
            productList.add(product);
        }
    }

    @Override
    public List<Product> getProducts(Integer pageSize, Integer pageNumber) {
        return productList.stream().skip(pageSize*pageNumber).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public Product getProduct(Integer id) {
        return productList.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Product save(ProductModel productModel) {
        Product product = new Product(productList.size()+1, productModel);
        productList.add(product);
        return product;
    }

    @Override
    public Product update(Integer productId, ProductModel productModel) {
        Product product =  getProduct(productId);
        if (product != null){
            update(product, productModel);
        }
        return product;
    }

    private void update(Product product, ProductModel productModel){
        if (productModel != null) {
            System.out.println(productModel.toString());
            Optional.ofNullable(productModel.getName()).ifPresent(product::setName);
            Optional.ofNullable(productModel.getDescription()).ifPresent(product::setDescription);
            Optional.ofNullable(productModel.getCurrency()).ifPresent(product::setCurrency);
            Optional.ofNullable(productModel.getImage_url()).ifPresent(product::setImage_url);
            Optional.ofNullable(productModel.getStock()).ifPresent(product::setStock);
            Optional.ofNullable(productModel.getStatus()).ifPresent(product::setStatus);
            Optional.ofNullable(productModel.getVideo_url()).ifPresent(product::setVideo_url);
            Optional.ofNullable(productModel.getPrice()).ifPresent(product::setPrice);
        }
    }
}
