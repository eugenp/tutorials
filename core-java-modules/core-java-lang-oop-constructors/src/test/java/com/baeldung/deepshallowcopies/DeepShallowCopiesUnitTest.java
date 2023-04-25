import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeepShallowCopiesUnitTest {
    @Test
    public void givenPersonAndAddress_whenShallowAndDeepCopy_thenDifferentEffects() {
        Address address = new Address("Main St", 42);
        Person original = new Person("John Doe", address);

        ShallowCopyPerson shallowCopy = new ShallowCopyPerson(original.name, original.address);
        DeepCopyPerson deepCopy = new DeepCopyPerson(original.name, original.address);

        address.street = "Changed St";
        address.number = 24;

        assertEquals("Changed St", shallowCopy.address.street);
        assertEquals(24, shallowCopy.address.number);
        assertEquals("Main St", deepCopy.address.street);
        assertEquals(42, deepCopy.address.number);
    }
}
