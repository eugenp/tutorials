// Address class
public class Address {
    String city;

    public Address(String city) {
        this.city = city;
    }
}

// Person class with shallow and deep copy implementations
public class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person cloned = (Person) super.clone();
        // Deep copy: Uncomment the next line for deep copy, comment it for shallow copy
        // cloned.address = new Address(this.address.city);
        return cloned;
    }
}

// Unit tests for shallow and deep copy
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CopyTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        Address address = new Address("Mumbai");
        Person person1 = new Person("Patel", address);
        Person person2 = (Person) person1.clone();

        assertEquals("Mumbai", person1.address.city);
        assertEquals("Mumbai", person2.address.city);

        person2.address.city = "Bengaluru";
        assertEquals("Bengaluru", person1.address.city);
        assertEquals("Bengaluru", person2.address.city);
    }

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        Address address = new Address("Mumbai");
        Person person1 = new Person("Patel", address);
        Person person2 = (Person) person1.clone();
        person2.address = new Address(person1.address.city); // Ensure deep copy

        assertEquals("Mumbai", person1.address.city);
        assertEquals("Mumbai", person2.address.city);

        person2.address.city = "Bengaluru";
        assertEquals("Mumbai", person1.address.city);
        assertEquals("Bengaluru", person2.address.city);
    }
}
