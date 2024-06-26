import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nishant.testarticle.objects.AddressSC;
import nishant.testarticle.objects.PersonSC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *  Shallow copy related verifications on PersonSC class
 */
public class PersonSCTest {

    @DisplayName("Verify first level Copying via Shallow clone")
    @Test
    void whenCopiedViaClone_thenFirstLevelCopyAchieved() throws CloneNotSupportedException {
        AddressSC houseAddress = new AddressSC("1", "Lane1", "Delhi");

        PersonSC jack = new PersonSC("Jack", 32, houseAddress);
        PersonSC jill = jack.clone();

        assertEquals(jack.getAge(), jill.getAge());
        assertEquals(jack.getName(), jill.getName());
        verifyAddressObjectContent(jack, jill);

        //compare with same condition in similar method in  PersonDCTest.
        //addresses are same objects!
        assertTrue(jack.getAddress() == jill.getAddress());

    }

    @DisplayName("Verify first level Copying via shallow copy constructor")
    @Test
    void whenCopiedViaShallowConstructor_thenFirstLevelCopyAchieved() {
        AddressSC houseAddress = new AddressSC("1", "Lane1", "Delhi");

        PersonSC jack = new PersonSC("Jack", 32, houseAddress);
        PersonSC jill = new PersonSC(jack);

        assertEquals(jack.getAge(), jill.getAge());
        assertEquals(jack.getName(), jill.getName());
        verifyAddressObjectContent(jack, jill);

        //compare with same condition in similar method in  PersonDCTest.
        //addresses are same objects!
        assertTrue(jack.getAddress() == jill.getAddress());
    }

    @DisplayName("Verify copied object is Not independent")
    @Test
    void whenCopiedViaShallowConstructor_thenCopiedObjectIsNotIndependent() {
        AddressSC houseAddress = new AddressSC("1", "Lane1", "Delhi");

        PersonSC jack = new PersonSC("Jack", 32, houseAddress);
        PersonSC jill = new PersonSC(jack);

        jack.getAddress()
            .setCity("Mumbai");
        assertEquals(jack.getAddress()
            .getCity(), jill.getAddress()
            .getCity());
    }

    /*
     * Verifies that both Person arguments have equal content
     * @param person1
     * @param person2
     */
    static void verifyAddressObjectContent(PersonSC person1, PersonSC person2) {
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
