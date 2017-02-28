package baeldung.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametrizedTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrizedTests.class);

    @Test
    @Parameters({"value", "isEven"})
    public void givenNumberFromXML_ifEvenCheckOK_thenCorrect(int value, boolean isEven) {
        Assert.assertEquals(isEven, value % 2 == 0);
    }

    @DataProvider(name = "numbers")
    public static Object[][] evenNumbers() {
        return new Object[][]{{1, false}, {2, true}, {4, true}};
    }

    @Test(dataProvider = "numbers")
    public void givenNumberFromDataProvider_ifEvenCheckOK_thenCorrect(Integer number, boolean expected) {
        Assert.assertEquals(expected, number % 2 == 0);
    }

    @Test(dataProvider = "numbersObject")
    public void givenNumberObjectFromDataProvider_ifEvenCheckOK_thenCorrect(EvenNumber number) {
        Assert.assertEquals(number.isEven(), number.getValue() % 2 == 0);
    }

    @DataProvider(name = "numbersObject")
    public Object[][] parameterProvider() {
        return new Object[][]{{new EvenNumber(1, false)}, {new EvenNumber(2, true)}, {new EvenNumber(4, true),}};
    }

}


class EvenNumber {
    private int value;
    private boolean isEven;

    public EvenNumber(int number, boolean isEven) {
        this.value = number;
        this.isEven = isEven;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEven() {
        return isEven;
    }

    public void setEven(boolean even) {
        isEven = even;
    }

    @Override
    public String toString() {
        return "EvenNumber{" +
                "value=" + value +
                ", isEven=" + isEven +
                '}';
    }
}


