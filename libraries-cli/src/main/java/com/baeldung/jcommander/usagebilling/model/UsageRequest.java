package com.baeldung.jcommander.usagebilling.model;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class UsageRequest {

    private String customerId;
    private String subscriptionId;
    private PricingType pricingType;
    private Integer quantity;
    private BigDecimal price;
    private Instant timestamp;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
          .append("\nUsage: {")
          .append("\n\tcustomer: ")
          .append(this.customerId)
          .append("\n\tsubscription: ")
          .append(this.subscriptionId)
          .append("\n\tquantity: ")
          .append(this.quantity)
          .append("\n\ttimestamp: ")
          .append(this.timestamp)
          .append("\n\tpricingType: ")
          .append(this.pricingType);

        if (PricingType.PRE_RATED == this.pricingType) {
            sb
              .append("\n\tpreRatedAt: ")
              .append(this.price);
        }

        sb.append("\n}\n");
        return sb.toString();
    }

    public enum PricingType {
        PRE_RATED, UNRATED
    }
}
