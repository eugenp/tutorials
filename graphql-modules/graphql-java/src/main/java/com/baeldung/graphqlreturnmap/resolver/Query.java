package com.baeldung.graphqlreturnmap.resolver;

import com.baeldung.graphql.data.Book;
import com.baeldung.graphqlreturnmap.entity.Product;
import com.baeldung.graphqlreturnmap.repository.ProductRepository;
import com.baeldung.graphqlreturnmap.repository.impl.ProductRepositoryImpl;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    @Autowired
    private ProductRepository productRepository;
    public Query(){
        productRepository = new ProductRepositoryImpl();
    }

    public List<Product> getProducts(int pageSize, int pageNumber) {
        return productRepository.getProducts(pageSize, pageNumber);
    }

    public Product getProduct(int id) {
        return productRepository.getProduct(id);
    }

    public List<Book> allBooks() {
        return null;
    }


}
