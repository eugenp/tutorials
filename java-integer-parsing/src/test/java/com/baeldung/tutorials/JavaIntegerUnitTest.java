package test.java.com.baeldung.tutorials;

import org.junit.Assert;
import org.junit.Test;


public class JavaIntegerUnitTest {

	@Test
    public void whenValidNumericStringIsPassed_thenShouldNotThrowException() {
	    int number1 = Integer.parseInt("5");
	    int number2 = Integer.parseInt("3", 11);
	    Assert.assertEquals(number1, 5);
	    Assert.assertEquals(number2, 3);
    }
	
	@Test(expected = NumberFormatException.class)
    public void whenInValidNumericStringIsPassed_thenShouldThrowException(){
	    int number1 = Integer.parseInt("abcd");
	    Assert.assertEquals(number1, 5);
    }
	
	@Test(expected = NumberFormatException.class)
    public void whenInValidNumericStringWithRadixPassed_thenShouldThrowException(){
	    int number1 = Integer.parseInt("A", 8);
	    Assert.assertEquals(number1, 5);
    }
	
	@Test
    public void whenValidValueOfNumericStringPassed_thenShouldNotThrowException() {
	    int number1 = Integer.valueOf("5");
	    int number2 = Integer.valueOf("3", 11);
	    int number3 = Integer.valueOf('A');
	    Assert.assertEquals(number1, 5);
	    Assert.assertEquals(number2, 3);
	    Assert.assertEquals(number3, 65);
    }
	
	@Test(expected = NumberFormatException.class)
    public void whenInvalidValueOfNumericStringPassed_thenShouldThrowException(){
	    int number1 = Integer.valueOf("abcd");
	    Assert.assertEquals(number1, 5);
    }
	
	@Test(expected = NumberFormatException.class)
    public void whenInvalidValueOfNumericStringWithRadixPassed_thenShouldThrowException(){
	    int number1 = Integer.parseInt("A", 8);
	    Assert.assertEquals(number1, 5);
    }
}
