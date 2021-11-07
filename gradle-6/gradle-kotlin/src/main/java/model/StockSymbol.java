package model;

import java.util.Objects;

final public class StockSymbol {

    public final String symbol;

    private StockSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static StockSymbol stockSymbol(String symbol) {
        return new StockSymbol(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockSymbol that = (StockSymbol) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
