import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShallowCopyUnitTest {
    @Test
    public void givenPersonAndAddress_whenShallowCopy_thenAddressChanges() {
        Address address = new Address("Main St", 42);
        Person original = new Person("John Doe", address);

        ShallowCopyPerson shallowCopy = new ShallowCopyPerson(original.name, original.address);

        address.street = "Changed St";
        address.number = 24;

        assertEquals("Changed St", shallowCopy.address.street);
        assertEquals(24, shallowCopy.address.number);
    }
}
