import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CopyExampleTest {
    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person2 = (Person) person1.clone();

        person2.name = "Jane";
        person2.address.city = "Los Angeles";

        // Test to verify shallow copy affects original object
        assertEquals("Jane", person2.name);
        assertEquals("Los Angeles", person2.address.city);
        assertEquals("Los Angeles", person1.address.city);
    }

    @Test
    public void testDeepCopy() {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person3 = person1.deepClone();

        person3.name = "Mike";
        person3.address.city = "San Francisco";

        // Test to verify deep copy does not affect original object
        assertEquals("Mike", person3.name);
        assertEquals("San Francisco", person3.address.city);
        assertEquals("John", person1.name);
        assertEquals("New York", person1.address.city);
    }
}
