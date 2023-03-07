import deep.DeepCopy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shallow.ShallowCopy;

import java.util.Arrays;

public class CopyTests {
    @Test
    public void whenModifyingOriginalObject_ThenCopyShouldChange() {
        ShallowCopy obj = new ShallowCopy(new int[]{1,2,3});
        ShallowCopy objCopy = new ShallowCopy(obj);

        obj.getArr()[0] = 0; // or objCopy.getArr()[0] = 0

        Assertions.assertFalse(obj == objCopy); // false
        Assertions.assertTrue(obj.getArr() == objCopy.getArr()); // true
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
        DeepCopy obj = new DeepCopy(new int[]{1,2,3});
        DeepCopy objCopy = new DeepCopy(obj);

        obj.getArr()[0] = 0; // or objCopy.getArr()[0] = 0

        Assertions.assertFalse(obj == objCopy); // false
        Assertions.assertFalse(obj.getArr() == objCopy.getArr()); // false
    }
}
