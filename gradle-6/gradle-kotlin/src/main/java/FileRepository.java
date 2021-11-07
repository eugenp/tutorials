import java.util.List;

public class FileRepository implements Repository {

    @Override
    public List<String> getStockPrice(int noOfDays) {
        return List.of("1", "3", "2");
    }
}
