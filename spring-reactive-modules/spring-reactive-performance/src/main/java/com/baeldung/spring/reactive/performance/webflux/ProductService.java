package com.baeldung.spring.reactive.performance.webflux;

import com.baeldung.spring.reactive.performance.KafkaTemplate;
import com.baeldung.spring.reactive.performance.ProductAddedToCartEvent;
import com.baeldung.spring.reactive.performance.model.Price;
import com.baeldung.spring.reactive.performance.model.Product;

import reactor.core.publisher.Mono;

class ProductService {
    private final String PRODUCT_ADDED_TO_CART_TOPIC = "product-added-to-cart";

    private final ProductRepository repository;
    private final DiscountService discountService;
    private final KafkaTemplate<String, ProductAddedToCartEvent> kafkaTemplate;

    public ProductService(ProductRepository repository, DiscountService discountService) {
        this.repository = repository;
        this.discountService = discountService;
        this.kafkaTemplate = new KafkaTemplate<>();
    }

    public void addProductToCart(String productId, String cartId) {
        repository.findById(productId)
          .switchIfEmpty(Mono.error(() -> new IllegalArgumentException("not found!")))
          .flatMap(this::computePrice)
          .map(price -> new ProductAddedToCartEvent(productId, price.value(), price.currency(), cartId))
          .subscribe(event -> kafkaTemplate.send(PRODUCT_ADDED_TO_CART_TOPIC, cartId, event));
    }

    private Mono<Price> computePrice(Product product) {
        if (product.category().isEligibleForDiscount()) {
            return discountService.discountForProduct(product.id())
              .map(product.basePrice()::applyDiscount);
        }
        return Mono.just(product.basePrice());
    }

}