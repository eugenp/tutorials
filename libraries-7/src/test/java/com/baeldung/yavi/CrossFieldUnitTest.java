package com.baeldung.yavi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;

public class CrossFieldUnitTest {
    record Range(int start, int end) {}

    private Validator<Range> validator = ValidatorBuilder.of(Range.class)
        .constraint(Range::start, "start", c -> c.greaterThan(0))
        .constraint(Range::end, "end", c -> c.greaterThan(0))
        .constraintOnTarget(range -> range.end > range.start, "end", "range.endGreaterThanStart", "\"end\" must be greater than \"start\"")
        .build();

    @Test
    void givenValidatorForRange_whenEndIsBeforeStart_thenValidationFails() {
        ConstraintViolations result = validator.validate(new Range(50, 10));
        assertFalse(result.isValid());

        assertEquals(1, result.size());
        assertEquals("end", result.get(0).name());
        assertEquals("range.endGreaterThanStart", result.get(0).messageKey());

    }

}
