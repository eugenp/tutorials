package java.com.bealdung;

import java.bealdung.objects.Circle;
import java.bealdung.utils.CopyUtil;
import java.bealdung.objects.Point;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ShallowAndDeepCopyUnitTest {

    @Test
    public void givenCircle_whenDeepCopy_thenDifferentObjects() {
        CopyUtil util=new CopyUtil();
        Point center = new Point(3, 5);
        Circle original;
        original = new Circle(center, 10);
        Circle copied = util.deepCopyCircle(original);
        assertNotSame(original, copied);
        Assert.assertNotSame(original.center, copied.center);
    }

    @Test
    public void givenCircle_whenShallowCopy_thenSameCenter() {
        CopyUtil util=new CopyUtil();
        Point center = new Point(7, 9);
        Circle original = new Circle(center, 15);
        Circle copied = util.shallowCopyCircle(original);
        assertNotSame(original, copied);
        assertSame(original.center, copied.center);
    }
}
