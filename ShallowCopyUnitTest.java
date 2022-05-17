package DeepVersusShallowCopyExample;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShallowCopyUnitTest {
    FamilyMemberCloneable walter;
    FamilyMemberCloneable skyler;
    
    @BeforeEach
    public void configure() {
        walter = new FamilyMemberCloneable(50,"Walter","White");
        walter.setAddress(new Address("308 Negra Arroyo Lane","","Albuquerque","New Mexico","United States","87104"));
        skyler = null;
        try {
            skyler = (FamilyMemberCloneable)walter.clone();
            skyler.setFirstName("Skyler");
        } catch (CloneNotSupportedException cnse) {
            System.err.println("Clone of (FamilyMemberCloneable): does not support cloning.");
        }
    }
    
    @Test
    public void givenAClone_whenSettingFirstNameOfClone_thenFirstNamesShouldBeDifferent() {
        assertFalse(skyler.getFirstName().equals(walter.getFirstName()));
    }
    
    @Test 
    public void whenCloned_thenAgeShouldBeEqual() {
        // demonstrates that super.clone() copies non-reference objects (byte, char, int, short, long, float, and double)
        assertTrue(skyler.getAge() == walter.getAge());
    }

    @Test
    public void whenCloned_thenMembersShouldBeDifferentObjects() {
        assertTrue(!skyler.toString().equals(walter.toString()));
    }
    
    @Test
    public void whenCloned_thenAddressesShouldHaveTheSameValue() {
        //System.out.println(walter.toString());
        //System.out.println(skyler.toString());
        //System.out.println("===================================================");
        //System.out.println(walter.getAddress().toString());
        //System.out.println(skyler.getAddress().toString());
        assertTrue(skyler.getAddress().toString().equals(walter.getAddress().toString()));
    }
    
    @Test
    public void whenCloned_thenAdressesShouldBeEqualAfterChanging() { // note that this is not the behaviour we want
        // some time later, Skyler moves out on her own... so set Skyler's new address
        skyler.getAddress().setAddr1("123 Anywhere Street");
        //System.out.println("===================================================");
        //System.out.println(walter.getAddress().toString());
        //System.out.println(skyler.getAddress().toString());
        
        // this is true, but should be false
        // uncommenting the System.out.println's shows what's going on, no deep copy
        assertTrue(skyler.getAddress().toString().equals(walter.getAddress().toString()));
    }
    
    @Test
    public void whenCloned_thenStringObjectsShouldBeEqual() {
        assertTrue(skyler.getLastName().equals(walter.getLastName()));
    }
}
