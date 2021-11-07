import java.util.List;

public interface Repository {

    List<String> getStockPrice(int noOfDays);
}
