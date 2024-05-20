import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitTests {
    @Test
    void shallowCopyTest() {
        Address address1 = new Address("New York");
        PersonShallow personShallow1 = new PersonShallow("John", address1);
        PersonShallow personShallow2 = new PersonShallow(personShallow1);

        personShallow2.address.city = "Los Angeles";

        System.out.println("Person 1's name: " + personShallow1.name);
        System.out.println("Person 1's city: " + personShallow1.address.city);
        System.out.println("Person 2's name: " + personShallow2.name);
        System.out.println("Person 2's city: " + personShallow2.address.city);

        assertEquals("Los Angeles", personShallow1.address.city);
        assertEquals("Los Angeles", personShallow2.address.city);
    }

    @Test
    void deepCopyTest() {
        Address address2 = new Address("New York");
        PersonDeep personDeep1 = new PersonDeep("John", address2);
        PersonDeep personDeep2 = new PersonDeep(personDeep1);

        personDeep2.address.city = "Los Angeles";

        System.out.println("Person 1's name: " + personDeep1.name);
        System.out.println("Person 1's city: " + personDeep1.address.city);
        System.out.println("Person 2's name: " + personDeep2.name);
        System.out.println("Person 2's city: " + personDeep2.address.city);

        assertEquals("New York", personDeep1.address.city);
        assertEquals("Los Angeles", personDeep2.address.city);
    }
}

