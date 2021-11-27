package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model;

public enum StockLevel {
    UNDEFINED(-1), GREEN(0), YELLOW(1), RED(2), OVER_STOCK(3);

    private final int code;

    private StockLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
