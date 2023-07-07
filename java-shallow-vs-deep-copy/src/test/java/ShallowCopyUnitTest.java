import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.baeldung.model.Address;
import com.baeldung.model.Employee;

public class ShallowCopyUnitTest {
    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        try {
            Employee shallowCopy = (Employee) originalEmployee.clone();

            originalEmployee.getAddress()
                .setCity("Los Angeles");
            originalEmployee.getAddress()
                .setZipCode("56789");

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

