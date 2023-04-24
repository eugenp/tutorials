
public class ObjectCopyUtilsUnitTest {

    @Test
    public void testShallowCopy() {
        MyClass original = new MyClass();
        original.setMyArray(new int[]{1, 2, 3});

        MyClass shallowCopy = original.clone(); 

        assertNotSame(original, shallowCopy);
        assertSame(original.getMyArray(), shallowCopy.getMyArray()); 
    }

    @Test
    public void testDeepCopy() {
        MyClass original = new MyClass();
        original.setMyArray(new int[]{1, 2, 3});

        MyClass deepCopy = new MyClass(original); 

        assertNotSame(original, deepCopy);
        assertNotSame(original.getMyArray(), deepCopy.getMyArray()); 
    }


    @Test
    public void testShallowCopy_ChangesReflectedInOriginal() {
        MyClass original = new MyClass();
        original.setMyArray(new int[]{1, 2, 3});

        MyClass shallowCopy = original.clone();

        shallowCopy.getMyArray()[0] = 99;

        assertEquals(99, original.getMyArray()[0]);
    }

    @Test
    public void testDeepCopy_ChangesNotAffectingOriginal() {
        MyClass original = new MyClass();
        original.setMyArray(new int[]{1, 2, 3});

        MyClass deepCopy = new MyClass(original);

        deepCopy.getMyArray()[0] = 99;

        assertEquals(1, original.getMyArray()[0]);
    }


    public class MyClass implements Cloneable {
        private int[] myArray;

        public MyClass() {
        }

        public MyClass(MyClass other) {
            this.myArray = Arrays.copyOf(other.myArray, other.myArray.length);
        }

        @Override
        public MyClass clone() {
            try {
                
                return (MyClass) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        // Standard Getters and setters for myArray
        public int[] getMyArray() {
            return myArray;
        }

        public void setMyArray(int[] myArray) {
            this.myArray = myArray;
        }
    }


}
