package nishant.testarticle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nishant.testarticle.objects.Address;
import nishant.testarticle.objects.Person;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Copy related verifications on Person class
 */
public class PersonUnitTest {

    //Deep Copy Related tests

    @DisplayName("Verify deep Copying via deep clone")
    @Test
    void whenCopiedViaDeepClone_thenDeepCopyAchieved() throws CloneNotSupportedException {
        Address houseAddress = new Address("1", "Lane1", "Delhi");

        Person jack = new Person("Jack", 32, houseAddress);
        Person jill = jack.clone();

        verifyEqualityAndIndependence(jack, jill, true);
    }

    @DisplayName("Verify deep Copying via deep copy constructor")
    @Test
    void whenCopiedViaDeepConstructor_thenDeepCopyAchieved() {
        Address houseAddress = new Address("1", "Lane1", "Delhi");

        Person jack = new Person("Jack", 32, houseAddress);
        Person jill = new Person(jack, true);

        verifyEqualityAndIndependence(jack, jill, true);
    }

    @DisplayName("Verify deep Copying via Java Serialization")
    @Test
    void whenCopiedViaJavaSerialization_thenDeepCopyAchieved() throws IOException, ClassNotFoundException {
        Address houseAddress = new Address("1", "Lane1", "Delhi");

        Person jack = new Person("Jack", 32, houseAddress);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(jack);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        Person jill = (Person) inputStream.readObject();

        verifyEqualityAndIndependence(jack, jill, true);
    }

    //Shallow Copy Related tests

    @DisplayName("Verify shallow Copying via shallow copy constructor")
    @Test
    void whenCopiedViaShallowConstructor_thenShallowCopyAchieved() {
        Address houseAddress = new Address("1", "Lane1", "Delhi");

        Person jack = new Person("Jack", 32, houseAddress);
        Person jill = new Person(jack, false);

        verifyEqualityAndIndependence(jack, jill, false);
    }

    /**
     * Verifies that two Person objects are equal by comparing individual field references.
     * DeepModeComparison flag tests that objects can be changed independently.
     *
     * @param person1
     * @param person2
     * @param deepModeComparison which mode of comparison is to be done.
     *                           Additionally, Deep Mode means totally independent copies and Shallow means just surface level ,
     *                           hence not independent
     */
    private static void verifyEqualityAndIndependence(Person person1, Person person2, boolean deepModeComparison) {
        assertEquals(person1.getAge(), person2.getAge());
        assertEquals(person1.getName(), person2.getName());

        verifyAddressObjectContent(person1, person2);

        if (deepModeComparison) {
            assertFalse(person1.getAddress() == person2.getAddress());
        } else {
            assertTrue(person1.getAddress() == person2.getAddress());
        }

        //verify that the copy is totally independent
        person1.getAddress()
            .setCity("Mumbai");

        if (deepModeComparison) {
            assertNotEquals(person1.getAddress()
                .getCity(), person2.getAddress()
                .getCity());
        } else {
            assertEquals(person1.getAddress()
                .getCity(), person2.getAddress()
                .getCity());
        }
    }

    /**
     * Verifies that both Person objects have equal address content
     *
     * @param person1
     * @param person2
     */
    static void verifyAddressObjectContent(Person person1, Person person2) {
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
