package org.baeldung.hexagonal.architecture.port.inbound;

import java.util.List;

import org.baeldung.hexagonal.architecture.ProductDto;

public interface ProductServicePort {
    List<ProductDto> getProducts();
    ProductDto getProductById(Long productId);
    ProductDto addProduct(ProductDto product);
    void removeProduct(Long productId);
}
