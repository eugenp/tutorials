package org.baeldung.hexagonal.architecture;

import java.util.List;

import org.baeldung.hexagonal.architecture.port.inbound.ProductServicePort;
import org.baeldung.hexagonal.architecture.port.outbound.ProductPersistencePort;

class ProductServiceImpl implements ProductServicePort {

    private final ProductPersistencePort productPersistencePort;

    ProductServiceImpl(ProductPersistencePort productRepository) {
        this.productPersistencePort = productRepository;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productPersistencePort.getAllProducts();
    }

    @Override
    public ProductDto getProductById(Long productId) {
        return productPersistencePort.getProductById(productId);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return productPersistencePort.addProduct(product);
    }

    @Override
    public void removeProduct(Long productId) {
        productPersistencePort.removeProduct(productId);
    }
}
