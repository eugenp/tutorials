import org.junit.Assert;
import org.junit.Test;
import com.baeldung.Entity;

public class Test123 {

   @Test
   public void testDeepCopyWithDistinctValues(){
       Entity entity1;
       Entity entity2;

       entity1 = new Entity();
       entity2 = new Entity();

       entity1.setItem(23);

       Assert.assertNotEquals(entity1.getItem(),entity2.getItem());
   }



}
