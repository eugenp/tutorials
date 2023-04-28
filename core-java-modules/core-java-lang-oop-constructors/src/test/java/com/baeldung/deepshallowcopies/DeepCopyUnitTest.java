import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeepCopyUnitTest {
    @Test
    public void givenPersonAndAddress_whenDeepCopy_thenAddressNotChanges() {
        Address address = new Address("Main St", 42);
        Person original = new Person("John Doe", address);

        DeepCopyPerson deepCopy = new DeepCopyPerson(original.name, original.address);

        address.street = "Changed St";
        address.number = 24;

        assertEquals("Main St", deepCopy.address.street);
        assertEquals(42, deepCopy.address.number);
    }
}
