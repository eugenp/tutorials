import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyMapUtilTest {
    @Test
    void testGetSymbol() {
        assertEquals("$", CurrencyMapUtil.getSymbol("USD"));
        assertEquals("€", CurrencyMapUtil.getSymbol("EUR"));
        assertEquals("₹", CurrencyMapUtil.getSymbol("INR"));
        assertEquals("Unknown", CurrencyMapUtil.getSymbol("ABC"));
    }
}