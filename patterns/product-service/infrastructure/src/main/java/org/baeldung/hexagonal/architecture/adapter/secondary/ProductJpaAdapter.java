package org.baeldung.hexagonal.architecture.adapter.secondary;

import java.util.List;
import java.util.Optional;

import org.baeldung.hexagonal.architecture.ProductDto;
import org.baeldung.hexagonal.architecture.port.outbound.ProductPersistencePort;
import org.baeldung.hexagonal.architecture.repository.Product;
import org.baeldung.hexagonal.architecture.repository.ProductRepository;
import org.baeldung.hexagonal.architecture.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements infrastructure interfaces from domain layer
 *
 */
@Service
public class ProductJpaAdapter implements ProductPersistencePort {
	
	@Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        return ProductMapper.INSTANCE.fromProductList(products);
    }
    
    @Override
    public ProductDto addProduct(ProductDto productDto) {
    	Product product = ProductMapper.INSTANCE.toProduct(productDto);
        Product productSaved = productRepository.save(product);
        return ProductMapper.INSTANCE.fromProduct(productSaved);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
        	return ProductMapper.INSTANCE.fromProduct(product.get());
        }
        throw new RuntimeException(String.format("No product with id %d found", productId));
    }

    @Override
    public void removeProduct(Long productId) {
    	productRepository.deleteById(productId);
    }
}