package java.com.baeldung.newfeatures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUnitTest {

    @Test
    public void givenString_thenRevertValue() {
        String text = "Baeldung";
        String transformed = text.transform(value -> new StringBuilder(value).reverse().toString());
        assertEquals("gnudleaB", transformed);
    }
}
