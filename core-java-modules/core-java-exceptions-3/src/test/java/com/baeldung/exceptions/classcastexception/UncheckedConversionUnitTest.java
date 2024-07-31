package com.baeldung.exceptions.classcastexception;

import org.junit.Test;

public class UncheckedConversionUnitTest {

    @Test(expected = ClassCastException.class)
    public void givenPollutedGenericType_whenGetProperty_thenClassCastException() {
        Box<Long> originalBox = new Box<>();
        Box raw = originalBox;
        raw.setContent(2.5);
        Box<Long> bound = (Box<Long>) raw;

        //An incompatible element was found in the raw box.
        Long content = bound.getContent();
    }
}
