import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nishant.testarticle.objects.AddressDC;
import nishant.testarticle.objects.PersonDC;

/**
 *  Deep copy related verifications on PersonDC class
 */
public class PersonDCTest {

    @DisplayName("Verify deep Copying via deep clone")
    @Test
    void whenCopiedViaDeepClone_thenDeepCopyAchieved() throws CloneNotSupportedException {
        AddressDC houseAddress = new AddressDC("1", "Lane1", "Delhi");

        PersonDC jack = new PersonDC("Jack", 32, houseAddress);
        PersonDC jill = jack.clone();

        assertEquals(jack.getAge(), jill.getAge());
        assertEquals(jack.getName(), jill.getName());

        verifyAddressObjectContent(jack, jill);

        //compare this with the same condition in similar method in  PersonSCTest.
        //addresses are entirely different objects!
        assertFalse(jack.getAddress() == jill.getAddress());
    }

    @DisplayName("Verify deep Copying via deep copy constructor")
    @Test
    void whenCopiedViaDeepConstructor_thenDeepCopyAchieved() {
        AddressDC houseAddress = new AddressDC("1", "Lane1", "Delhi");

        PersonDC jack = new PersonDC("Jack", 32, houseAddress);
        PersonDC jill = new PersonDC(jack);

        assertEquals(jack.getAge(), jill.getAge());
        assertEquals(jack.getName(), jill.getName());

        verifyAddressObjectContent(jack, jill);

        //compare this with the same condition in similar method in  PersonSCTest.
        //addresses are entirely different objects!
        assertFalse(jack.getAddress() == jill.getAddress());

    }

    @DisplayName("Verify deep Copying via Java Serialization")
    @Test
    void whenCopiedViaJavaSerialization_thenDeepCopyAchieved() throws IOException, ClassNotFoundException {
        AddressDC houseAddress = new AddressDC("1", "Lane1", "Delhi");

        PersonDC jack = new PersonDC("Jack", 32, houseAddress);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(jack);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        PersonDC jill = (PersonDC) inputStream.readObject();

        assertEquals(jack.getAge(), jill.getAge());
        assertEquals(jack.getName(), jill.getName());

        verifyAddressObjectContent(jack, jill);

        //compare this with the same condition in similar method in  PersonSCTest.
        //addresses are entirely different objects!
        assertFalse(jack.getAddress() == jill.getAddress());
    }

    @DisplayName("Verify construcor copied object is Independent")
    @Test
    void whenCopiedViaDeepConstructor_thenCopiedObjectIsIndependent() throws IOException, ClassNotFoundException {
        AddressDC houseAddress = new AddressDC("1", "Lane1", "Delhi");

        PersonDC jack = new PersonDC("Jack", 32, houseAddress);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(jack);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        PersonDC jill = (PersonDC) inputStream.readObject();

        jack.getAddress()
            .setCity("Mumbai");
        assertNotEquals(jack.getAddress()
            .getCity(), jill.getAddress()
            .getCity());
    }

    @DisplayName("Verify serialization copied object is Independent ")
    @Test
    void whenCopiedViaSerialization_thenCopiedObjectIsIndependent() {
        AddressDC houseAddress = new AddressDC("1", "Lane1", "Delhi");

        PersonDC jack = new PersonDC("Jack", 32, houseAddress);
        PersonDC jill = new PersonDC(jack);

        jack.getAddress()
            .setCity("Mumbai");
        assertNotEquals(jack.getAddress()
            .getCity(), jill.getAddress()
            .getCity());
    }

    /**
     * Verifies that both Person arguments have equal content
     * @param person1
     * @param person2
     */
    static void verifyAddressObjectContent(PersonDC person1, PersonDC person2) {
        assertEquals(person1.getAddress()
            .getCity(), person2.getAddress()
            .getCity());
        assertEquals(person1.getAddress()
            .getLane(), person2.getAddress()
            .getLane());
        assertEquals(person1.getAddress()
            .getHouseNumber(), person2.getAddress()
            .getHouseNumber());
    }

}
