import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowDeepTest {

    @Test
    public void testShallowCopy() {
        String[] friends = {"Alice", "Bob", "Charlie"};
        Person originalPerson = new Person("David", friends);

        Person copiedPerson = new Person(originalPerson.getName(), originalPerson.getFriends());
        copiedPerson.getFriends()[0] = "Eve";

        System.out.println("Original Person's Friends: " + Arrays.toString(originalPerson.getFriends()));
        System.out.println("Copied Person's Friends: " + Arrays.toString(copiedPerson.getFriends()));

        assertArrayEquals(new String[]{"Eve", "Bob", "Charlie"}, originalPerson.getFriends());
        assertArrayEquals(new String[]{"Eve", "Bob", "Charlie"}, copiedPerson.getFriends());

        assertNotSame(originalPerson, copiedPerson);
        assertSame(originalPerson.getName(), copiedPerson.getName()); // String is immutable
        assertSame(originalPerson.getFriends(), copiedPerson.getFriends()); // Array reference is shared
    }

    @Test
    public void testDeepCopy() {
        String[] friends = {"Alice", "Bob", "Charlie"};
        Person originalPerson = new Person("David", friends);

        Person deepCopiedPerson = originalPerson.clone();
        deepCopiedPerson.getFriends()[0] = "Eve";

        System.out.println("Original Person's Friends: " + Arrays.toString(originalPerson.getFriends()));
        System.out.println("Deep Copied Person's Friends: " + Arrays.toString(deepCopiedPerson.getFriends()));

        assertArrayEquals(new String[]{"Alice", "Bob", "Charlie"}, originalPerson.getFriends());
        assertArrayEquals(new String[]{"Eve", "Bob", "Charlie"}, deepCopiedPerson.getFriends());

        assertNotSame(originalPerson, deepCopiedPerson);
        assertSame(originalPerson.getName(), deepCopiedPerson.getName()); // String is immutable

        // For deep copy, compare array content
        assertNotSame(originalPerson.getFriends(), deepCopiedPerson.getFriends()); // Array reference is copied
        assertArrayEquals(originalPerson.getFriends(), deepCopiedPerson.getFriends()); // Array content is equal
    }
}
