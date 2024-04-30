package optionaltoarraylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalToArrayListConverter {

    public static List usingIsPresent(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        if (obj.isPresent()) {
            arrayList.add(obj.get());
        }
        return arrayList;
    }

    public static List usingOrElse(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(obj.orElse("Hello, World!"));
        return arrayList;
    }

    public static List usingOrElseGet(Optional<String> obj) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(obj.orElseGet(() -> "Hello, World!"));
        return arrayList;
    }

    public static List usingStream(Optional<String> obj) {
        List<String> arrayList = obj.stream()
            .collect(Collectors.toList());
        return arrayList;
    }

    public static List usingStreamFilter(Optional<String> obj) {
        List<String> arrayList = obj.filter(e -> e.startsWith("H"))
            .stream()
            .collect(Collectors.toList());
        return arrayList;
    }

    public static List usingStreamFlatMap(Optional<List<String>> obj) {
        List<String> arrayList = obj.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        return arrayList;
    }
}
