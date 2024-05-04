import java.util.*;
import java.util.stream.Collectors;
public class EmployeeSorter {

    public static Map<Integer, List<Employee>> sortEmployeeMap(Map<Integer, List<Employee>> employeeMap, String fieldName) {
        Comparator<Employee> comparator = EmployeeComparator.getComparator(fieldName);

        return employeeMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .sorted(comparator)
                                .collect(Collectors.toList())
                ));
    }
}