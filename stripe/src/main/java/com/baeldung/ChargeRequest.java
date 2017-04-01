package com.baeldung;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = false)
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }
    String description;
    int amount; // cents
    Currency currency;
    String stripeEmail;
    String stripeToken;
}
