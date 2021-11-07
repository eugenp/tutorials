import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static model.StockPrice.stockPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSorterTest {

    DefaultSorter sorter = new DefaultSorter();

    @Test
    void should_sort_stock_prices_descending_order_of_timestamp() {
        // given
        var now = Instant.now();
        var list = List.of(
            stockPrice(new BigDecimal("12.5"), now.minusSeconds(12)),
            stockPrice(new BigDecimal("12.9"), now.minusSeconds(45)),
            stockPrice(new BigDecimal("11.5"), now.minusSeconds(10))
        );

        // when
        var result = sorter.sort(list);

        // then
        assertEquals(result.get(0), stockPrice(new BigDecimal("12.9"), now.minusSeconds(45)));
        assertEquals(result.get(1), stockPrice(new BigDecimal("12.5"), now.minusSeconds(12)));
        assertEquals(result.get(2), stockPrice(new BigDecimal("11.5"), now.minusSeconds(10)));
    }
}