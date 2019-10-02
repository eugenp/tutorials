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

  public enum PricingType {
    PRE_RATED, UNRATED
  }
}
