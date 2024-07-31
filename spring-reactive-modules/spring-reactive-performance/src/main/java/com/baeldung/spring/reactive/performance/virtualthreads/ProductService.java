package com.baeldung.spring.reactive.performance.virtualthreads;

import java.math.BigDecimal;

import com.baeldung.spring.reactive.performance.KafkaTemplate;
import com.baeldung.spring.reactive.performance.ProductAddedToCartEvent;
import com.baeldung.spring.reactive.performance.model.Price;
import com.baeldung.spring.reactive.performance.model.Product;

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
        Thread.startVirtualThread(() -> computePriceAndPublishMessage(productId, cartId));
    }

    private void computePriceAndPublishMessage(String productId, String cartId) {
        Product product = repository.findById(productId)
          .orElseThrow(() -> new IllegalArgumentException("not found!"));

        Price price = computePrice(productId, product);

        var event = new ProductAddedToCartEvent(productId, price.value(), price.currency(), cartId);
        kafkaTemplate.send(PRODUCT_ADDED_TO_CART_TOPIC, cartId, event);
    }

    private Price computePrice(String productId, Product product) {
        if (product.category().isEligibleForDiscount()) {
            BigDecimal discount = discountService.discountForProduct(productId);
            return product.basePrice().applyDiscount(discount);
        }
        return product.basePrice();
    }

}