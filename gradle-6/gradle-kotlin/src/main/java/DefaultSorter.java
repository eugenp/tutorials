import model.StockPrice;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class DefaultSorter implements Sorter {

    @Override
    public List<StockPrice> sort(List<StockPrice> list) {
        return list.stream()
            .sorted(comparing(it -> it.timestamp))
            .collect(toList());
    }
}
