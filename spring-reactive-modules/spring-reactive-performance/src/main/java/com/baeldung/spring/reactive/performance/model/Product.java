package com.baeldung.spring.reactive.performance.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "products")
public record Product (
    @Id
    String id,
    String name,
    BigDecimal basePriceValue,
    String currency,
    Category category
) {

    public Price basePrice() {
        return new Price(basePriceValue, currency);
    }

    public enum Category {
        ELECTRONICS(false),
        CLOTHING(true),
        ACCESSORIES(false),
        GARDENING(false),
        SPORTS(true);

        private final boolean eligibleForPromotion;

        Category(boolean eligibleForPromotion) {
            this.eligibleForPromotion = eligibleForPromotion;
        }

        public boolean isEligibleForDiscount() {
            return eligibleForPromotion;
        }

        public boolean notEligibleForPromotion() {
            return !eligibleForPromotion;
        }
    }
}