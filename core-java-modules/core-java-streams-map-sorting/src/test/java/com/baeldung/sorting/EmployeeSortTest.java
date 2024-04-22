import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeSortTest {
    private Map<Integer, List<Employee>> employeeMap;

    @BeforeEach
    void setUp() {
        employeeMap = new HashMap<>();
        employeeMap.put(1, Arrays.asList(
                new Employee("Alice", 3000f, 30, "New York"),
                new Employee("John", 4400f, 30, "LA"),
                new Employee("Bob", 2500f, 25, "Boston")));
        employeeMap.put(2, Arrays.asList(
                new Employee("Charlie", 3200f, 28, "Chicago"),
                new Employee("Lily", 5000f, 35, "Las Vegas"),
                new Employee("Dave", 2800f, 32, "Denver")));
    }

    @Test
    void testSortingBySalary() {

        EmployeeSort employeeSort = new EmployeeSort();
        Map<Integer, List<Employee>> sortedMap = employeeSort.sortBySalary(employeeMap);

        assertEquals(2500f, sortedMap.get(1).get(0).getSalary());
        assertEquals(3000f, sortedMap.get(1).get(1).getSalary());
        assertEquals(4400f, sortedMap.get(1).get(2).getSalary());
        assertEquals(2800f, sortedMap.get(2).get(0).getSalary());
        assertEquals(3200f, sortedMap.get(2).get(1).getSalary());
        assertEquals(5000f, sortedMap.get(2).get(2).getSalary());
    }

}