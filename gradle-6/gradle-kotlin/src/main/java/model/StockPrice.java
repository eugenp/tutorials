package model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

final public class StockPrice {

    public final BigDecimal price;
    public final Instant timestamp;

    private StockPrice(BigDecimal price, Instant timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    public static StockPrice stockPrice(BigDecimal price, Instant timestamp) {
        return new StockPrice(price, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPrice that = (StockPrice) o;
        return Objects.equals(price, that.price) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, timestamp);
    }
}
