package java.bealdung.utils;

import java.bealdung.objects.Circle;
import java.bealdung.objects.Point;

public class CopyUtil {
    public Circle deepCopyCircle(Circle original) {
        Point copiedPoint = new Point(original.center.x, original.center.y);
        return new Circle(copiedPoint, original.radius);
    }
    public Circle shallowCopyCircle(Circle original) {
        return new Circle(original.center, original.radius);
    }
}
