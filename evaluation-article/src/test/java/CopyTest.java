import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class CopyTest {

    @Test
    public void whenShallowCopy_thenSameNumberOfStudents() {
        Class copy = ShallowCopy.copy();
        assertEquals(4, copy.getStudents().size());
    }

    @Test
    public void whenDeepCopy_thenDifferentNumberOfStudents() {
        Class copy = DeepCopy.copy();
        assertNotEquals(3, copy.getStudents().size());
    }
}
