package java.com.baeldung.objectcopy;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HouseDeepTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {

        GarageDeep originalGarage = new GarageDeep("Original Garage", "Poltava, Shevchenka 14");
        HouseDeep originalHouse = new HouseDeep("Original House", "Poltava, Shevchenka 20", originalGarage);

        HouseDeep copiedHouse = (HouseDeep) originalHouse.clone();

        assertNotSame("The copied house should not be the same object as the original", originalHouse, copiedHouse);
        assertNotSame("The garage in the copied house should not be the same object as the original", originalHouse.getGarage(), copiedHouse.getGarage());

        copiedHouse.setName("Copied House");
        copiedHouse.getGarage().setName("Copied Garage");

        assertEquals("Original House", originalHouse.getName());
        assertEquals("Original Garage", originalHouse.getGarage().getName());
    }
}
