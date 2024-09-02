import models.Person;
import models.PersonCloneable;
import models.PersonSerializeable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.DeepCopyUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class AppTest {

    @Test
    void testShallowCopy() {
        Person person1 = new Person("John Doe", 30);
        Person person2 = person1;
        Assertions.assertSame(person1, person2);
        Assertions.assertEquals("John Doe", person1.getName());
        Assertions.assertEquals("John Doe", person2.getName());
        person2.setName("Jane Doe");
        Assertions.assertEquals("Jane Doe", person1.getName());
        Assertions.assertEquals("Jane Doe", person2.getName());
    }

    @Test
    void testDeepCopy() {
        PersonCloneable person1 = new PersonCloneable("John Doe", 30);
        try {
            PersonCloneable person2 = person1.clone();
            Assertions.assertNotSame(person1, person2);
            Assertions.assertEquals("John Doe", person1.getName());
            Assertions.assertEquals("John Doe", person2.getName());
            person2.setName("Jane Doe");
            Assertions.assertEquals("John Doe", person1.getName());
            Assertions.assertEquals("Jane Doe", person2.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeepCopyWithNestedObject() {
        PersonSerializeable original = new PersonSerializeable("Jane Doe", 30);
        original.addHobby("Reading");
        original.addHobby("Swimming");
        PersonSerializeable copy = DeepCopyUtil.deepCopy(original);
        assertNotSame(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getAge(), copy.getAge());
        assertEquals(original.getHobbies(), copy.getHobbies());
        assertNotSame(original.getHobbies(), copy.getHobbies());
    }
}
