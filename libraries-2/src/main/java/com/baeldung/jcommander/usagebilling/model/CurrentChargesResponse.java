package com.baeldung.jcommander.usagebilling.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class CurrentChargesResponse {

  private String customerId;
  private BigDecimal amountDue;
  private List<LineItem> lineItems;

  @NoArgsConstructor(access = AccessLevel.PACKAGE)
  @AllArgsConstructor(access = AccessLevel.PACKAGE)
  @Builder
  @Getter
  public static class LineItem {

    private String subscriptionId;
    private BigDecimal amount;
    private BigDecimal quantity;
  }
}
