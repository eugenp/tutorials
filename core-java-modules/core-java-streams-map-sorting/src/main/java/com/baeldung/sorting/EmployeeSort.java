import java.util.*;
import java.util.stream.Collectors;

public class EmployeeSort {
    public Map<Integer, List<Employee>> sortBySalary(Map<Integer, List<Employee>> employeeMap) {

        Map<Integer, List<Employee>> sortedMap = employeeMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .sorted(Comparator.comparing(Employee::getSalary))
                                .collect(Collectors.toList())
                ));

        sortedMap.forEach((key, value) -> {
            System.out.println("Key: " + key);
            value.forEach(System.out::println);
        });
        return sortedMap;
    }
}
