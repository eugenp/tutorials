import java.util.List;

public class Reporter {

    private final Repository repository;
    private final Sorter sorter;

    public Reporter(Repository repository, Sorter sorter) {
        this.repository = repository;
        this.sorter = sorter;
    }

    public List<String> getStockPrice(int noOfDays) {
        return sorter.sort(repository.getStockPrice(noOfDays));
    }
}
