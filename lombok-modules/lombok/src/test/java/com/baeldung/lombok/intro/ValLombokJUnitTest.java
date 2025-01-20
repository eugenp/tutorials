import java.util.ArrayList;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokValJUnitTest {

    @Test
    public void whenUsingVal_TypeInferred() {

        val list = new ArrayList<String>();
        list.add("Hello, Lombok!");
     
        String actualElement = list.get(0);
        String expectedElement = new String("Hello, Lombok!");

        Assertions.assertTrue(actualElement.equals(expectedElement)); 
    }
}
