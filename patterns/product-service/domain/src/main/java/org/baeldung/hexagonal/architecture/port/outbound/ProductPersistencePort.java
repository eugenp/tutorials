package org.baeldung.hexagonal.architecture.port.outbound;

import java.util.List;

import org.baeldung.hexagonal.architecture.ProductDto;

public interface ProductPersistencePort {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long productId);
    ProductDto addProduct(ProductDto product);
    void removeProduct(Long productId);
}
