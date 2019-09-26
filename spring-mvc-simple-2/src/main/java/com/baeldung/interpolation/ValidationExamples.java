package com.baeldung.interpolation;

import java.util.Formatter;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.DecimalMin;

public class ValidationExamples {

    private static final Formatter formatter = new Formatter();

    @Size(
      min = 5,
      max = 14,
      message = "The author email '${validatedValue}' must be between {min} and {max} characters long"
    )
    private String authorEmail;

    @Min(
      value = 1,
      message = "There must be at least {value} test{value > 1 ? 's' : ''} in the test case"
    )
    private int testCount;

    @DecimalMin(
      value = "50",
      message = "The code coverage ${formatter.format('%1$.2f', validatedValue)} must be higher than {value}%"
    )
    private double codeCoverage;

}
