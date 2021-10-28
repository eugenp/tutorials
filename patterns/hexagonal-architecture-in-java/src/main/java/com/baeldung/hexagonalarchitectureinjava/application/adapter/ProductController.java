package com.baeldung.hexagonalarchitectureinjava.application.adapter;

import com.baeldung.hexagonalarchitectureinjava.application.dto.CreateProductDto;
import com.baeldung.hexagonalarchitectureinjava.application.dto.ProductDto;
import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;
import com.baeldung.hexagonalarchitectureinjava.core.port.primary.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductApi productApi;

    @Autowired
    public ProductController(ProductApi productApi) {
        this.productApi = productApi;
    }

    @PostMapping()
    public void addProduct(@RequestBody CreateProductDto productDto) {
        productApi.addProduct(toProduct(productDto));
    }

    @GetMapping()
    public List<ProductDto> allProducts() {
        return productApi.all()
            .stream()
            .map(this::toProductDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") String productId) {
        Optional<Product> product = productApi.findById(UUID.fromString(productId));
        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
        } else {
            return ResponseEntity.ok(toProductDto(product.get()));
        }
    }

    private Product toProduct(CreateProductDto productDto) {
        return new Product(UUID.randomUUID(), productDto.getName(), productDto.getCategory(), productDto.getPrice());
    }

    private ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId()
            .toString());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
