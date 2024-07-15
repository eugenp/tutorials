package initialProj;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

class BaeldungTestClass {
    ArrayList<Object> list;

    public BaeldungTestClass() {
        list = new ArrayList<>();
    }

    public BaeldungTestClass(BaeldungTestClass original) {
        list = new ArrayList<>(original.list);
    }
}

public class shallowvsdeepTest {

    @Test
    public void testShallowCopy() {
        BaeldungTestClass firstObj = new BaeldungTestClass();
        firstObj.list.add(20);
        firstObj.list.add("Hello");
        firstObj.list.add(10.5f);

        // Shallow copy
        BaeldungTestClass secObj = firstObj;

        secObj.list.set(1, "World");

        // Test to check shallow copy
        assertEquals("World", firstObj.list.get(1));
        assertEquals("World", secObj.list.get(1));
    }

    @Test
    public void testDeepCopy() {
        BaeldungTestClass firstObj = new BaeldungTestClass();
        firstObj.list.add(20);
        firstObj.list.add("Hello");
        firstObj.list.add(10.5f);

        // Deep copy
        BaeldungTestClass secObj = new BaeldungTestClass(firstObj);

        secObj.list.set(1, "World");

        // Test to check deep copy
        assertEquals("Hello", firstObj.list.get(1));
        assertEquals("World", secObj.list.get(1));
    }
}
