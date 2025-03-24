import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyUtilTest {
    @Test
    void testGetSymbol() {
        assertEquals("$", CurrencyUtil.getSymbol("USD"));
        assertEquals("€", CurrencyUtil.getSymbol("EUR"));
    }

    @Test
    void testInvalidCurrencyCode() {
        assertThrows(IllegalArgumentException.class, () -> CurrencyUtil.getSymbol("INVALID"));
    }
}