import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyUtilTest {
    @Test
    void givenValidCurrencyCode_whenGetSymbol_thenReturnsCorrectSymbol() {
        assertEquals("$", CurrencyUtil.getSymbol("USD"));
        assertEquals("€", CurrencyUtil.getSymbol("EUR"));
    }

    @Test
    void givenInvalidCurrencyCode_whenGetSymbol_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> CurrencyUtil.getSymbol("INVALID"));
    }
}