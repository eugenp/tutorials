package com.baeldung;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ParametrizedLongRunningUnitTest {

    @Test
    @Parameters({"value", "isEven"})
    public void givenNumberFromXML_ifEvenCheckOK_thenCorrect(int value, boolean isEven) {
        assertEquals(isEven, value % 2 == 0);
    }

    @DataProvider(name = "numbers")
    public static Object[][] evenNumbers() {
        return new Object[][]{{1, false}, {2, true}, {4, true}};
    }

    @Test(dataProvider = "numbers")
    public void givenNumberFromDataProvider_ifEvenCheckOK_thenCorrect(Integer number, boolean expected) {
        assertEquals(expected, number % 2 == 0);
    }

    @Test(dataProvider = "numbersObject")
    public void givenNumberObjectFromDataProvider_ifEvenCheckOK_thenCorrect(EvenNumber number) {
        assertEquals(number.isEven(), number.getValue() % 2 == 0);
    }

    @DataProvider(name = "numbersObject")
    public Object[][] parameterProvider() {
        return new Object[][]{{new EvenNumber(1, false)}, {new EvenNumber(2, true)}, {new EvenNumber(4, true),}};
    }

    class EvenNumber {
        private int value;
        private boolean isEven;

        EvenNumber(int number, boolean isEven) {
            this.value = number;
            this.isEven = isEven;
        }

        int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        boolean isEven() {
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
}




