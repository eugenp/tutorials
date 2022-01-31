package com.baeldung.graphqlvsrest.resolver;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.repository.OrderRepository;
import com.baeldung.graphqlvsrest.repository.ProductRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    private ProductRepository productRepository;
    public Query(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(int pageSize, int pageNumber) {
        return productRepository.getProducts(pageSize, pageNumber);
    }

    public Product getProduct(int id) {
        return productRepository.getProduct(id);
    }


}
