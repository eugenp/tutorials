package com.baeldung.concurrent.virtualthreads.vs.webflux.webflux;

import com.baeldung.concurrent.virtualthreads.vs.webflux.KafkaTemplate;
import com.baeldung.concurrent.virtualthreads.vs.webflux.ProductAddedToCartEvent;
import com.baeldung.concurrent.virtualthreads.vs.webflux.model.Price;
import com.baeldung.concurrent.virtualthreads.vs.webflux.model.Product;

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
