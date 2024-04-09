import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class CopyExampleUnitTest {

    @Test
    public void givenOriginalPerson_whenShallowCopying_thenCopiedPersonHasSameAge() {
        Person originalPerson = new Person("John", 30);
        Person copiedPerson = originalPerson.clone();

        assertEquals(30, originalPerson.getAge(), "Original person's age should remain unchanged after shallow copying");
        assertEquals(originalPerson.getAge(), copiedPerson.getAge(), "Copied person should have the same age as the original person after shallow copying");
    }

    @Test
    public void givenOriginalPerson_whenDeepCopying_thenCopiedPersonHasDifferentAge() {
        Person originalPerson = new Person("John", 30);
        Person copiedPerson = originalPerson.deepClone();

        assertEquals(30, originalPerson.getAge(), "Original person's age should remain unchanged after deep copying");
        assertEquals(30, copiedPerson.getAge(), "Copied person's age should be independent of the original person after deep copying");
    }
}
