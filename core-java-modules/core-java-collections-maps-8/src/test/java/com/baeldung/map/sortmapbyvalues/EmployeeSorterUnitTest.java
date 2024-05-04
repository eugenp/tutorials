import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeSorterUnitTest {

    @Test
    void testSortingBySalary() {

        Map<Integer, List<Employee>> employeeMap = new HashMap<>();
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Brent", 5000.0f, 30, "New York"));
        employees.add(new Employee("Aarav", 4000.0f, 25, "Chicago"));
        employees.add(new Employee("Cooper", 4400f, 32, "LA"));

        employeeMap.put(1, employees);

        Map<Integer, List<Employee>> sortedMap = EmployeeSorter.sortEmployeeMap(employeeMap, "salary");

        assertThat(sortedMap.get(1)).containsExactly(
                new Employee("Aarav", 5000.0f, 30, "New York"),
                new Employee("Brent", 4000.0f, 25, "Chicago"),
                new Employee("Cooper", 4400f, 32, "LA")
        );

    }

    @Test
    public void testSortingByAge() {
        Map<Integer, List<Employee>> employeeMap = new HashMap<>();
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Brent", 5000.0f, 30, "New York"));
        employees.add(new Employee("Aarav", 4000.0f, 25, "Chicago"));
        employees.add(new Employee("Cooper", 5400f, 32, "LA"));

        employeeMap.put(1, employees);

        Map<Integer, List<Employee>> sortedMap = EmployeeSorter.sortEmployeeMap(employeeMap, "age");

        assertThat(sortedMap.get(1)).containsExactly(
                new Employee("Aarav", 5000.0f, 30, "New York"),
                new Employee("Brent", 4000.0f, 25, "Chicago"),
                new Employee("Cooper", 5400f, 32, "LA")
        );
    }
}
