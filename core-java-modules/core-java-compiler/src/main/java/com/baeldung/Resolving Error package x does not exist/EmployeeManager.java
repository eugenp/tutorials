
// Importing Employee class from another package
import com.baeldung.model.Employee;

// Importing external Apache Commons Lang library
import org.apache.commons.lang3.StringUtils;

/**
 * EmployeeManager class demonstrates
 * - Importing internal packages
 * - Using external libraries
 * - Proper classpath configuration
 */
public class EmployeeManager {

    public static void main(String[] args) {

        Employee emp = new Employee("Alice", 101);

        // Convert employee info to uppercase using external library
        String result = StringUtils.upperCase(emp.getInfo());

        System.out.println(result);
    }
}
