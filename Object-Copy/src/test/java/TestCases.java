import org.junit.Assert;
import org.junit.Test;
import com.baeldung.Entity;

public class TestCases {
   @Test
   public void testDeepCopyWithDistinctValues(){
       Entity entity1;
       Entity entity2;

       entity1 = new Entity();
       entity2 = new Entity();

       entity1.setItem(23);

       Assert.assertNotEquals(entity1.getItem(),entity2.getItem());
   }

   @Test
    public void testDistinctObjects(){
       Entity entity1 = new Entity();
       Entity entity2 = new Entity();

       int hashCode1 = System.identityHashCode(entity1);
       int hashCode2 = System.identityHashCode(entity2);

       Assert.assertNotEquals(hashCode1, hashCode2);
   }

   @Test
    public void testShallowCopyWithDistinctValues(){
       Entity entity1 = new Entity();
       Entity entity2 = entity1;

       entity2.setItem(800);

       Assert.assertEquals(entity1.getItem(),entity2.getItem());
   }

    @Test public void testStringObjectWithShallowCopy(){
       String original = "Hello Baeldung";

       String duplicate = original;

       Assert.assertEquals(duplicate.hashCode(),original.hashCode()); }

    @Test
    public void testStringObjectUpdateWithShallowCopy(){
       String original = "Hello Baeldung";

       String duplicate = original;

        duplicate = "Hello Everyone";

       Assert.assertNotEquals(duplicate.hashCode(),original.hashCode());
    }



    @Test
    public void testIntegerWithShallowCopy() {
        int original = 9;
        int duplicate = original;

        duplicate = 12;

        Assert.assertNotEquals(duplicate, original);
    }
}
