package java.com.baeldung.newfeatures;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CompactNumbersUnitTest {

    @Test
    public void givenNumber_thenCompactValues() {
        NumberFormat likesShort = NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.SHORT);
        likesShort.setMaximumFractionDigits(2);
        assertEquals("2.59K", likesShort.format(2592));
        NumberFormat likesLong = NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.LONG);
        likesLong.setMaximumFractionDigits(2);
        assertEquals("2.59 thousand", likesShort.format(2592));
    }
}
