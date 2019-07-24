package ports;

import domain.CalculatorService;
import org.junit.Test;
import ports.Calculator;

import static org.junit.Assert.assertEquals;

public class CalculatorServiceTest {
    @Test
    public void givenTwoFactors_whenTheyAreMultiplied_thenTheProductIsReturned() {
        Calculator calculator = new CalculatorService();

        int product = calculator.multiply(2, 2);

        assertEquals(4, product);
    }
}