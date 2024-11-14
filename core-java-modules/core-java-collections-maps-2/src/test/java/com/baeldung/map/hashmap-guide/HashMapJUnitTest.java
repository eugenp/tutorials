import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashMapJUnitTest {

    @Test
    public void whenUsingObjectAsHashMapGenericParameter_ShouldRequireCast() throws Exception {

        try {
            HashMap<String,Object> objectMap = new HashMap<String,Object>();

            Product eBike = new Product("E-Bike", "A bike with a battery");
            Product roadBike = new Product("Road bike", "A bike for competition");

            objectMap.put("E-Bike", eBike);
            objectMap.put("Road bike", roadBike);

            HashMap<String,Product> productMap = new HashMap<String,Product>();

            for (Map.Entry<String, Object> entry: objectMap.entrySet()) {
                if (entry.getValue() instanceof Product) {
                    productMap.put(entry.getKey(), (Product) entry.getValue());
                }
            }

            Product product = productMap.get("E-Bike");
            String actualDescription = product.getDescription();
            String expectedDescription = new String("A bike with a battery");

            Assertions.assertTrue(actualDescription.equals(expectedDescription));

        } catch(ClassCastException e) {
              System.out.println(e.getMessage());
          }
    }

    @Test
    public void whenUsingProperHashMapGenericParameters_ShouldNotRequireCast() throws Exception {
        HashMap<String,Product> productMap = new HashMap<String,Product>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        productMap.put(eBike.getName(), eBike);
        productMap.put(roadBike.getName(), roadBike);

        Product product = productMap.get("E-Bike");
        String actualDescription = product.getDescription();
        String expectedDescription = new String("A bike with a battery");

        Assertions.assertTrue(actualDescription.equals(expectedDescription));

        HashMap<String,Product> objectMap = productMap;
        Assertions.assertSame(objectMap,productMap);
    }

}
