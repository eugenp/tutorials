import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static model.StockPrice.stockPrice;
import static model.StockSymbol.stockSymbol;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReporterIntegrationTest {

    Reporter reporter = new Reporter(new InMemoryRepository(), new DefaultSorter());

    @Test
    void should_return_sorted_list_of_stock_prices() {
        // given
        var stock = stockSymbol("FFB");

        // when
        var result = reporter.getStockPrice(stock);

        // then
        assertEquals(result, List.of(
            stockPrice(new BigDecimal("4.57"), Instant.parse("2021-02-05T15:00:00Z")),
            stockPrice(new BigDecimal("9.89"), Instant.parse("2021-09-03T15:00:00Z")),
            stockPrice(new BigDecimal("5.32"), Instant.parse("2021-05-01T15:00:00Z")))
        );
    }
}
