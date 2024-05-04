import java.util.Comparator;

public class EmployeeComparator {

    public static Comparator<Employee> getComparator(String fieldName) {
        switch (fieldName) {
            case "name":
                return Comparator.comparing(Employee::getName);
            case "salary":
                return Comparator.comparing(Employee::getSalary);
            case "age":
                return Comparator.comparingInt(Employee::getAge);
            case "city":
                return Comparator.comparing(Employee::getCity);
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }
}