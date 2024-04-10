package deepcopy;

import junit.framework.TestCase;
import org.junit.Test;
import utils.Engine;

import static org.junit.Assert.assertNotEquals;

public class VehicleDeepCopyTest extends TestCase {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        Engine engine = new Engine(300);
        VehicleDeepCopy originalVehicle = new VehicleDeepCopy("Car", engine);
        VehicleDeepCopy copiedVehicle = (VehicleDeepCopy) originalVehicle.clone();

        originalVehicle.engine.horsepower = 400;

        assertNotEquals(originalVehicle.engine.horsepower, copiedVehicle.engine.horsepower);
    }

}