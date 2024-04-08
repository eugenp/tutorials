import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException{
        Vehicle originalVehicle = new Vehicle("Car", "Red");
        Vehicle copiedVehicle = (Vehicle) originalVehicle.clone();

        assertNotSame(originalVehicle, copiedVehicle);

        assertEquals(originalVehicle.name, copiedVehicle.name);
        assertEquals(originalVehicle.color, copiedVehicle.color);

        originalVehicle.name = "Truck";

        assertNotEquals("Truck", copiedVehicle.name);
        assertEquals("Car", copiedVehicle.name);
    }

    @Test
    public void testShallowCopy() {
        Vehicle originalVehicle = new Vehicle("Car", "Red");
        Vehicle copiedVehicle =  originalVehicle;

        assertSame(originalVehicle, copiedVehicle);

        assertEquals(originalVehicle.name, copiedVehicle.name);
        assertEquals(originalVehicle.color, copiedVehicle.color);

        originalVehicle.name = "Truck";

        assertEquals("Truck", copiedVehicle.name);
    }

}