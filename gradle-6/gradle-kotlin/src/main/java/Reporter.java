import model.StockPrice;
import model.StockSymbol;

import java.util.List;

public class Reporter {

    private final Repository repository;
    private final Sorter sorter;

    public Reporter(Repository repository, Sorter sorter) {
        this.repository = repository;
        this.sorter = sorter;
    }

    public List<StockPrice> getStockPrice(StockSymbol stockSymbol) {
        return sorter.sort(repository.getStockPrice(stockSymbol));
    }
}
