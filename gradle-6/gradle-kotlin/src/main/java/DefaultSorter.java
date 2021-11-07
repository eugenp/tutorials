import java.util.List;

import static java.util.stream.Collectors.toList;

public class DefaultSorter implements Sorter {

    @Override
    public List<String> sort(List<String> list) {
        return list.stream().sorted().collect(toList());
    }
}
