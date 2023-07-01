import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.baeldung.model.Address;
import com.baeldung.model.Employee;

public class DeepCopyUnitTest {
    @Test
    public void whenCreatingDeepCopy_thenObjectsShouldNotBeSame() {
        // Create an original Employee object
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        // Create a deep copy of the original Employee object
        Employee deepCopy = originalEmployee.deepCopy();

        // Assert that the original employee is not the same object as the deep copy employee
        assertNotSame(originalEmployee, deepCopy);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
        // Create an original Employee object
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        // Create a deep copy of the original Employee object
        Employee deepCopy = originalEmployee.deepCopy();

        // Modify the original copy
        originalEmployee.getAddress()
            .setCity("Los Angeles");
        originalEmployee.getAddress()
            .setZipCode("56789");

        // Assert that the changes made to the original employee's address is different from deepCopy employee's address
        assertNotSame(originalEmployee.getAddress()
            .getCity(), deepCopy.getAddress()
            .getCity());
        assertNotSame(originalEmployee.getAddress()
            .getZipCode(), deepCopy.getAddress()
            .getZipCode());
    }
}
