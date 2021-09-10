package com.baeldung.hexagonalarchitecture.service.implementation;

import com.baeldung.hexagonalarchitecture.domain.Product;
import com.baeldung.hexagonalarchitecture.dto.ProductDto;
import com.baeldung.hexagonalarchitecture.repository.ProductRepository;
import com.baeldung.hexagonalarchitecture.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    public ProductServiceImplementation(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        Product product = productRepository.getProductById(productId);
        if (product != null){
            return new ProductDto(product);
        }
        return null;
    }

    @Override
    public void addProduct(ProductDto productDto) {
        Product product = createProductFromProductDto(productDto);
        productRepository.save(product);
    }

    private Product createProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setManufacturer(productDto.getManufacturer());
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
        product.setCreationDate(new Date(System.currentTimeMillis()));
        /**
         * Add more business decisions if required
         * */
        return product;
    }
}
