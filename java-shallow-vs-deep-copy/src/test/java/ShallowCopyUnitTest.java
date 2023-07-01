import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.baeldung.model.Address;
import com.baeldung.model.Employee;

public class ShallowCopyUnitTest {
    @Test
    public void whenCreatingShallowCopy_thenObjectsShouldNotBeSame() {
        // Create an original Employee object
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        try {
            // Perform a shallow copy of the original Employee object
            Employee shallowCopy = (Employee) originalEmployee.clone();

            // Assert that the original employee is not the same object as the shallow copy employee
            assertNotSame(originalEmployee, shallowCopy);
        } catch (CloneNotSupportedException e) {
            fail("Cloning not supported");
        }
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        // Create an original Employee object
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        try {
            // Perform a shallow copy of the original Employee object
            Employee shallowCopy = (Employee) originalEmployee.clone();

            // Modify the cloned employee's address
            originalEmployee.getAddress()
                .setCity("Los Angeles");
            originalEmployee.getAddress()
                .setZipCode("56789");

            // Assert that the changes made to the original employee's address also reflect in the shallow copy employee's address
            assertEquals(originalEmployee.getAddress()
                .getCity(), shallowCopy.getAddress()
                .getCity());
            assertEquals(originalEmployee.getAddress()
                .getZipCode(), shallowCopy.getAddress()
                .getZipCode());
        } catch (CloneNotSupportedException e) {
            fail("Cloning not supported");
        }
    }
}
