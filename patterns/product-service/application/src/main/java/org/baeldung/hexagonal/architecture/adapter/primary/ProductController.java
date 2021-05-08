package org.baeldung.hexagonal.architecture.adapter.primary;

import java.util.List;

import org.baeldung.hexagonal.architecture.ProductDto;
import org.baeldung.hexagonal.architecture.port.inbound.ProductServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
    private ProductServicePort productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return new ResponseEntity<List<ProductDto>>(productService.getProducts(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto product) {
    	return new ResponseEntity<ProductDto>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<ProductDto>(productService.getProductById(productId), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable Long productId) {
        return new ResponseEntity<ProductDto>(HttpStatus.OK);
    }
}