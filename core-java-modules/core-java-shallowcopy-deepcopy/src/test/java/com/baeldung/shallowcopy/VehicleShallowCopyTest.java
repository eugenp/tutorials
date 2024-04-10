package shallowcopy;

import junit.framework.TestCase;
import org.junit.Test;
import utils.Engine;


public class VehicleShallowCopyTest extends TestCase {

    @Test
    public void testShallowCopy() throws CloneNotSupportedException{
        Engine engine = new Engine(300);
        VehicleShallowCopy originalVehicleShallowCopy = new VehicleShallowCopy("Car", engine);
        VehicleShallowCopy copiedVehicleShallowCopy = (VehicleShallowCopy) originalVehicleShallowCopy.clone();

        originalVehicleShallowCopy.engine.horsepower = 400;

        assertEquals(originalVehicleShallowCopy.engine.horsepower, copiedVehicleShallowCopy.engine.horsepower);
        assertSame(originalVehicleShallowCopy.engine, copiedVehicleShallowCopy.engine);
    }

}