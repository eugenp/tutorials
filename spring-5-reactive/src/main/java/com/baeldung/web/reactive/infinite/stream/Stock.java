package com.baeldung.web.reactive.infinite.stream;

import java.io.Serializable;

import lombok.Data;

@Data
public class Stock implements Serializable {

    private String symbol;

    private float price;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stock{");
        sb.append(" symbol=");
        sb.append(symbol);
        sb.append(", price=");
        sb.append(String.format("%.2f", price));
        sb.append('}');
        return sb.toString();
    }

}
