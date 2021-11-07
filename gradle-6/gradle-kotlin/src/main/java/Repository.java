import model.StockPrice;
import model.StockSymbol;

import java.util.List;

public interface Repository {

    List<StockPrice> getStockPrice(StockSymbol stockSymbol);
}
