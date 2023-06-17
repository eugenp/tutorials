import org.example.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        Person john = new Person("John", 25);
        Person johnCopy = (Person) john.clone();

        // Assert that the copied object has the same values as the original object
        assertEquals(john.getName(), johnCopy.getName());
        assertEquals(john.getAge(), johnCopy.getAge());

        // Assert that the copied object is not the same instance as the original object
        assertNotSame(john, johnCopy);
    }
}