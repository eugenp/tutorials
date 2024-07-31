package com.baeldung.pagination.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pagination.model.Product;
import com.baeldung.pagination.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductPaginationController {

    private final ProductRepository productRepository;

    @GetMapping("/products")
    public Mono<Page<Product>> findAllProducts(Pageable pageable) {
        return this.productRepository.findAllBy(pageable)
          .collectList()
          .zipWith(this.productRepository.count())
          .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

}
