package DeepVersusShallowCopyExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 * 
 * @author Tim de Vries
 */
public class DeepCopyUnitTest {
    FamilyMemberCloneableFixed walter = null;
    FamilyMemberCloneableFixed skyler = null;
    
    @BeforeEach
    public void configure() {
        walter = new FamilyMemberCloneableFixed(50,"Walter","White");
        walter.setAddress(new Address("308 Negra Arroyo Lane","","Albuquerque","New Mexico","United States","87104"));
        try {
            skyler = (FamilyMemberCloneableFixed)walter.clone();
            skyler.setFirstName("Skyler");
        } catch (CloneNotSupportedException cnse) {
            System.err.println("Clone of (FamilyMemberCloneable): does not support cloning.");
        }
    }
    
    @Test 
    public void givenAClone_whenAgeChanges_thenAgeShouldBeDifferent() {
        skyler.setAge(44);
        assertFalse(skyler.getAge() == walter.getAge());
    }
    
    @Test
    public void whenCloned_thenAddressObjectsShouldBeDifferent() {
        assertFalse(skyler.getAddress().equals(walter.getAddress()));
    }
    
    @Test
    public void whenCloned_thenChangedAddressShouldNotBeEqual() {
        skyler.getAddress().setAddr1("123 Anywhere Street");
        skyler.getAddress().setZipPostal("82705");
        //System.out.println("===================================================");
        //System.out.println(walter.getAddress().toString());
        //System.out.println(skyler.getAddress().toString());
        assertFalse(skyler.getAddress().toString().equals(walter.getAddress().toString()));
    }
}
