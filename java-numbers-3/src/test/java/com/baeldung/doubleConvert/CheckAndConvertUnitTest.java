package com.baeldung.doubleConvert;

import static com.baeldung.doubleConvert.java8.OptionalCheckAndConvert.checkedDoubleOptional;
import static com.baeldung.doubleConvert.vanilla.VanillaCheckAndConvert.checkedDouble;
import static com.baeldung.doubleConvert.vanilla.VanillaCheckAndConvert.checkedDoubleDefault;
import static com.baeldung.doubleConvert.vavr.FunctionalCheckAndConvert.checkDoubleFunctional;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Optional;

public class CheckAndConvertUnitTest {

    @Test
    public void checkAndConvertVanilla() {
        assertThat(checkedDouble("1")).isEqualTo(1.0);
        assertThat(checkedDouble(null)).isNaN();
        assertThat(checkedDouble("")).isNaN();

        assertThat(checkedDoubleDefault("1", 1.0)).isEqualTo(1.0);
        assertThat(checkedDoubleDefault(null, 1.0)).isEqualTo(1.0);
        assertThat(checkedDoubleDefault("", 1.0)).isEqualTo(1.0);
    }

    @Test
    public void checkAndConvertOptional() {
        assertThat(checkedDoubleOptional("1")).isEqualTo(Optional.of(1.0));
        assertThat(checkedDoubleOptional(null)).isEqualTo(Optional.empty());
        assertThat(checkedDoubleOptional("")).isEqualTo(Optional.empty());
        assertThat(checkedDoubleOptional(null).orElse(1.0)).isEqualTo(1.0);
        assertThat(checkedDoubleOptional("").orElse(1.0)).isEqualTo(1.0);
    }

    @Test
    public void checkAndConvertFunctional() {
        assertThat(checkDoubleFunctional("1", 1.0)).isEqualTo(1.0);
        assertThat(checkDoubleFunctional(null, 1.0)).isEqualTo(1.0);
        assertThat(checkDoubleFunctional("", 1.0)).isEqualTo(1.0);
    }
}
