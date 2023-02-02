import org.junit.Assert;
import org.junit.Test;

public class Testing {

    @Test
    public void assertIfPointsEqual(Point p1, Point p2) {
        Assert.assertEquals(p1, p2);
    }

    @Test
    public void assertIfPointsNotEqual(Point p1, Point p2) {
        Assert.assertNotEquals(p1, p2);
    }
}
