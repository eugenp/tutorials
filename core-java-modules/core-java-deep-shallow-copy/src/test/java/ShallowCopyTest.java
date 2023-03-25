import static org.junit.jupiter.api.Assertions.*;

class ShallowCopyTest {

    @org.junit.jupiter.api.Test
    void whenCloningAClass_thenReturnShallowCopy() throws CloneNotSupportedException {
        int[] nums = { 1, 2, 3 };
        ShallowCopy obj1 = new ShallowCopy(10, nums);
        ShallowCopy obj2 = (ShallowCopy) obj1.clone();
        obj1.value = 20;
        nums[0] = 10;
        assertEquals(obj1.value, 20);
        assertEquals(obj2.value, 10);
        assertEquals(obj1.data[0], 10);
        assertEquals(obj2.data[0], 10);
    }
}