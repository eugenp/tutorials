import model.StockSymbol;

public class InvalidStockSymbol extends RuntimeException {

    public InvalidStockSymbol(StockSymbol stockSymbol) {
        super("Stock symbol " + stockSymbol.symbol + " is invalid");
    }
}
