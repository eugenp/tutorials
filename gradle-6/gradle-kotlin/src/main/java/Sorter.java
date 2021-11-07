import model.StockPrice;

import java.util.List;

public interface Sorter {

    List<StockPrice> sort(List<StockPrice> stockPrices);
}
