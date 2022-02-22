package com.baeldung.graphqlvsrest.resolver;

import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;
import com.baeldung.graphqlvsrest.repository.ProductRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {

    private ProductRepository productRepository;
    public Mutation(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product saveProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public Product updateProduct(Integer productId, ProductModel productModel) {
        return productRepository.update(productId, productModel);
    }
}
