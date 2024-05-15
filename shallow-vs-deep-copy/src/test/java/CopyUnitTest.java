import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.copies.MyClass;
import com.baeldung.copies.MyCopyConstructor;

public class CopyUnitTest {

    @Test
    public void whenShallowCopy_thenAssertionIsTrue() {
        MyClass initialObj = new MyClass();
        initialObj.setI(13);

        MyClass copiedObj = initialObj;
        copiedObj.setI(15);

        Assertions.assertEquals(initialObj.getI(), 15);
    }

    @Test
    public void whenDeepCopy_thenAssertionIsTrue() {
        MyCopyConstructor myCopyConstructorOne = new MyCopyConstructor("Test String");
        MyCopyConstructor myCopyConstructorTwo = new MyCopyConstructor(myCopyConstructorOne);

        Assertions.assertNotEquals(myCopyConstructorOne, myCopyConstructorTwo);
    }
}
