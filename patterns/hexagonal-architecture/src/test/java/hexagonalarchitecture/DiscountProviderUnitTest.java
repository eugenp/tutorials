package hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.adapters.InMemoryProductProvider;
import com.baeldung.hexagonalarchitecture.adapters.InMemoryPromotionProvider;
import com.baeldung.hexagonalarchitecture.domain.StoreManager;
import com.baeldung.hexagonalarchitecture.ports.IDiscountProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class DiscountProviderUnitTest {

    private IDiscountProvider discountProvider =
            new StoreManager(new InMemoryProductProvider(), new InMemoryPromotionProvider());

    @Test
    public void whenMultipleProductRequest_thenDiscountIsCorrect() {
        Assert.assertEquals(28.7, discountProvider.getOrderPrice(Arrays.asList(1, 2)), 0.001);
    }

    @Test
    public void whenProductDoesNotExists_thenThrowException() {
        try {
            discountProvider.getOrderPrice(Collections.singletonList(10));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
            Assert.assertEquals(e.getMessage(), "No inventory for product 10");
        }
    }

    @Test
    public void whenProductIsAskedMultipleTimes_thenPriceIsCorrect() {
        Assert.assertEquals(57.0, discountProvider.getOrderPrice(Arrays.asList(1, 1, 1)), 0.001);
    }
}
