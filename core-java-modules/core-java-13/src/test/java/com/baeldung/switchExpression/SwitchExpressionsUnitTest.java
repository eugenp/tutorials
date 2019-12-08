package com.baeldung.switchExpression;

import static java.time.Month.AUGUST;
import static java.time.Month.JUNE;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import java.util.function.Function;

import org.junit.Test;

public class SwitchExpressionsUnitTest {

  @Test
  @SuppressWarnings ("preview")
  public void switchExpression() {

    var month = JUNE;

    var result = switch (month) {
      case JANUARY, JUNE, JULY -> 3;
      case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
      case MARCH, MAY, APRIL -> 2;
      default -> 0;
    };

    assertEquals(result, 3);
  }

  @Test
  @SuppressWarnings ("preview")
  public void switchExpressionWithYieldKeyword() {
    var month = AUGUST;

    var result = switch (month) {
      case JANUARY, JUNE, JULY -> 3;
      case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
      case MARCH, MAY, APRIL, AUGUST -> {
        int monthLength = month.toString().length();
        yield monthLength * 4;
      }
      default -> 0;
    };

    assertEquals(24, result);
  }

  @Test
  @SuppressWarnings ("preview")
  public void switchStatementWithReturnInsideBlock() {

    Function<Month, Integer> func = (month) -> {
      switch (month) {
        case JANUARY, JUNE, JULY -> { return 3; }
        default -> { return 0; }
      }
    };

    assertEquals(Integer.valueOf(3), func.apply(Month.JANUARY));
  }

  @Test
  @SuppressWarnings ("preview")
  public void switchExpressionWithAllCasesCovered() {
    var month = AUGUST;

    var result = switch (month) {
      case JANUARY, JUNE, JULY -> 3;
      case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
      case MARCH, MAY, APRIL, AUGUST -> 2;
    };

    assertEquals(result, 2);
  }
}
