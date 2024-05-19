import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.copies.Foo;
import com.baeldung.copies.MyClass;
import com.baeldung.copies.MyCopyConstructor;

public class CopyUnitTest {

    @Test
    public void whenShallowCopy_thenAssertionIsTrue() {
        MyClass firstClass = new MyClass(new Foo(1));
        MyClass secondClass = new MyClass(firstClass.getFoo());
        secondClass.getFoo()
          .setI(20);

        Assertions.assertEquals(firstClass.getFoo()
          .getI(), 20);
    }

    @Test
    public void whenDeepCopy_thenAssertionIsTrue() {
        MyCopyConstructor myCopyConstructorOne = new MyCopyConstructor("Test String", new Foo(30));
        MyCopyConstructor myCopyConstructorTwo = new MyCopyConstructor(myCopyConstructorOne);

        Assertions.assertNotEquals(myCopyConstructorOne, myCopyConstructorTwo);
    }

}
