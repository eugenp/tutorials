import java.util.Arrays;
import java.util.List;

public class TechnologyProcessor {

    List<String> BLACK_LIST = Arrays.asList("soap", "grails", "ant");
    TechnologyStorage technologyStorage = new DbTechnologyStorage();

    public void process(List<String> input) {
        input.removeAll(BLACK_LIST);

        technologyStorage.save(input);

    }
}
