import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.baeldung.flexibleconstructorbodies.SmallCoffee;

public class FlexibleConstructorBodiesTest {

    @Test
    public void test() {
        SmallCoffee smallCoffee = new SmallCoffee(30,40, "none");
        assertEquals(70, smallCoffee.getTotalVolume());
    }

}
