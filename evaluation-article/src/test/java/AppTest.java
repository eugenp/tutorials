import models.Person;
import models.PersonCloneable;
import models.PersonSerializeable;
import models.PhoneNumber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.DeepCopyUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void givenPersonObject_whenShallowCopied_thenCopyHasSameDataButDifferentReference() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber("123-456-7890"));

        Person person1 = new Person("John Doe", 30, phoneNumbers);
        Person person2 = DeepCopyUtil.shallowCopy(person1);

        assertNotEquals(person1, person2);
        assertEquals("John Doe", person1.getName());
        assertEquals("John Doe", person2.getName());
        person2.setName("Jane Doe");
        assertEquals("John Doe", person1.getName());
        assertNotEquals("John Doe", person2.getName());

        // now changing the addressList of person2
        person2.getPhoneNumbers()
            .add(new PhoneNumber("456-789-0123"));
        assertEquals(2, person1.getPhoneNumbers()
            .size());
    }

    @Test
    void givenPersonCloneableObject_whenCloned_thenShallowCopyIsCreated() throws CloneNotSupportedException {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber("123-456-7890"));
        PersonCloneable original = new PersonCloneable("John Doe", 30, phoneNumbers);

        PersonCloneable clone = original.clone();

        // Modify the clone
        clone.setName("Jane Doe");
        clone.setAge(25);
        clone.getPhoneNumbers()
            .add(new PhoneNumber("987-654-3210"));

        // Test that modifying the clone doesn't affect the original's primitive fields
        assertEquals("John Doe", original.getName());
        assertEquals(30, original.getAge());

        // Test that modifying the clone's phoneNumbers affects the original (shallow copy)
        assertEquals(2, original.getPhoneNumbers()
            .size());
        assertEquals(2, clone.getPhoneNumbers()
            .size());
    }

    @Test
    void givenPersonSerializableObject_whenDeepCopied_thenCopyIsIndependentOfOriginal() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber("123-456-7890"));
        PersonSerializeable original = new PersonSerializeable("John Doe", 30, phoneNumbers);
        PersonSerializeable copy = DeepCopyUtil.deepCopy(original);

        assertNotSame(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getAge(), copy.getAge());

        copy.setName("Jane Doe");
        copy.setAge(25);
        copy.getPhoneNumbers()
            .add(new PhoneNumber("987-654-3210"));

        // Test that modifying the clone doesn't affect the original's primitive fields
        assertEquals("John Doe", original.getName());
        assertEquals(30, original.getAge());

        // Test that modifying the serialized phoneNumbers doesn't affects the original
        // It only affects the serialized copy one. (Deep Copy)
        assertEquals(1, original.getPhoneNumbers()
            .size());
        assertEquals(2, copy.getPhoneNumbers()
            .size());
    }
}
