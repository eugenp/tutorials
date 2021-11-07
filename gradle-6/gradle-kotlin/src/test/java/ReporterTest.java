import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static model.StockPrice.stockPrice;
import static model.StockSymbol.stockSymbol;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ReporterTest {

    Repository repository = mock(Repository.class);
    Sorter sorter = mock(Sorter.class);

    Reporter reporter = new Reporter(repository, sorter);

    @Test
    void should_sort_values_returned_by_repository_and_return() {
        // given
        var stock = stockSymbol("AAP");
        given(repository.getStockPrice(stock))
            .willReturn(List.of(
                stockPrice(new BigDecimal("1.32"), Instant.parse("2021-01-01T15:00:00Z")),
                stockPrice(new BigDecimal("1.57"), Instant.parse("2021-05-05T15:00:00Z")),
                stockPrice(new BigDecimal("1.89"), Instant.parse("2021-02-03T15:00:00Z"))));
        given(sorter.sort(any()))
            .willReturn(List.of(
                stockPrice(new BigDecimal("1.57"), Instant.parse("2021-05-05T15:00:00Z")),
                stockPrice(new BigDecimal("1.89"), Instant.parse("2021-02-03T15:00:00Z")),
                stockPrice(new BigDecimal("1.32"), Instant.parse("2021-01-01T15:00:00Z"))));

        // when
        var result = reporter.getStockPrice(stock);

        // then
        assertEquals(result, List.of(
            stockPrice(new BigDecimal("1.57"), Instant.parse("2021-05-05T15:00:00Z")),
            stockPrice(new BigDecimal("1.89"), Instant.parse("2021-02-03T15:00:00Z")),
            stockPrice(new BigDecimal("1.32"), Instant.parse("2021-01-01T15:00:00Z"))));
    }
}