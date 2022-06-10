import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CopyTest {

    @Test
    void testDeepCopy() {
        ComputerDeep newComputer = CreateCopies.createDeepCopy();
        Assertions.assertEquals(newComputer.getCpu().getBrandName(), "Intel");
    }

    @Test
    void testShallowCopy() {
        ComputerShallow newComputer = CreateCopies.createShallowCopy();
        Assertions.assertEquals(newComputer.getCpu().getBrandName(), "AMD");
    }

}
