package com.baeldung.interpolation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ValidationExamples {

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
