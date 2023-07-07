import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.baeldung.model.Address;
import com.baeldung.model.Employee;

public class DeepCopyUnitTest {
    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
        Employee originalEmployee = new Employee();
        originalEmployee.setId(1);
        originalEmployee.setName("John Doe");

        Address originalAddress = new Address();
        originalAddress.setCity("New York");
        originalAddress.setZipCode("12345");

        originalEmployee.setAddress(originalAddress);

        Employee deepCopy = originalEmployee.deepCopy();

        originalEmployee.getAddress()
            .setCity("Los Angeles");
        originalEmployee.getAddress()
            .setZipCode("56789");

        assertNotSame(originalEmployee.getAddress()
            .getCity(), deepCopy.getAddress()
            .getCity());
        assertNotSame(originalEmployee.getAddress()
            .getZipCode(), deepCopy.getAddress()
            .getZipCode());
    }
}
