import org.junit.Assert;
import org.junit.Test;

import com.baeldung.Profession;

public class ShallowDeepCopyUnitTest {

    @Test
    public void whenTwoObjectsAreTheSame_thenShallowCopySuccess(){
        Profession professionA = new Profession("salesMan", 60000.0);
        Profession professionB = professionA;
        professionB.setJobTitle("storeManager");
        Assert.assertEquals("Failed. Both objects have unique different memory location",
            professionA.getJobTitle(), professionB.getJobTitle());

    }

    @Test
    public void whenTwoObjectsAreDifferent_thenDeepCopySuccess(){
        //Deep Copy
        Profession professionY = new Profession("Airplane Pilot", 150000.00);
        Profession professionZ = new Profession(professionY.getJobTitle(), professionY.getSalary()); // Deep copy
        professionZ.setJobTitle("IT Manager");
        Assert.assertNotEquals("Failed. Both objects have the same memory location",
            professionY.getJobTitle(), professionZ.getJobTitle());
    }
}
