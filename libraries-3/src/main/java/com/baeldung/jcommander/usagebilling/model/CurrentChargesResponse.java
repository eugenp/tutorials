package com.baeldung.jcommander.usagebilling.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class CurrentChargesResponse {

    private String customerId;
    private BigDecimal amountDue;
    private List<LineItem> lineItems;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
          .append("Current Month Charges: {")
          .append("\n\tcustomer: ")
          .append(this.customerId)
          .append("\n\ttotalAmountDue: ")
          .append(this.amountDue.setScale(2, RoundingMode.HALF_UP))
          .append("\n\tlineItems: [");

        for (LineItem li : this.lineItems) {
            sb
              .append("\n\t\t{")
              .append("\n\t\t\tsubscription: ")
              .append(li.subscriptionId)
              .append("\n\t\t\tamount: ")
              .append(li.amount.setScale(2, RoundingMode.HALF_UP))
              .append("\n\t\t\tquantity: ")
              .append(li.quantity)
              .append("\n\t\t},");
        }

        sb.append("\n\t]\n}\n");
        return sb.toString();
    }

    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @Builder
    @Getter
    public static class LineItem {

        private String subscriptionId;
        private BigDecimal amount;
        private Integer quantity;
    }
}
