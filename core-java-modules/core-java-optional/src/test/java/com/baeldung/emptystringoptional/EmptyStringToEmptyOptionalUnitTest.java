package com.baeldung.emptystringoptional;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Predicate;

public class EmptyStringToEmptyOptionalUnitTest {

    @Test
    public void givenEmptyString_whenFilteringOnOptional_thenEmptyOptionalIsReturned() {
        String str = "";
        Optional<String> opt = Optional.ofNullable(str).filter(s -> !s.isEmpty());
        Assert.assertFalse(opt.isPresent());
    }

//    Uncomment code when code base is compatible with Java 11
//    @Test
//    public void givenEmptyString_whenFilteringOnOptionalInJava11_thenEmptyOptionalIsReturned() {
//        String str = "";
//        Optional<String> opt = Optional.ofNullable(str).filter(Predicate.not(String::isEmpty));
//        Assert.assertFalse(opt.isPresent());
//    }

    @Test
    public void givenEmptyString_whenPassingResultOfEmptyToNullToOfNullable_thenEmptyOptionalIsReturned() {
        String str = "";
        Optional<String> opt = Optional.ofNullable(Strings.emptyToNull(str));
        Assert.assertFalse(opt.isPresent());
    }
}
