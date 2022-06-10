import org.junit.Assert;
import org.junit.Test;

public class CopyTest {

    @Test
    public void testDeepCopy() {
        ComputerDeep newComputer = CreateCopies.createDeepCopy();
        Assert.assertEquals(newComputer.getCpu().getBrandName(), "Intel");
    }

    @Test
    public void testShallowCopy() {
        ComputerShallow newComputer = CreateCopies.createShallowCopy();
        Assert.assertEquals(newComputer.getCpu().getBrandName(), "AMD");
    }

}
