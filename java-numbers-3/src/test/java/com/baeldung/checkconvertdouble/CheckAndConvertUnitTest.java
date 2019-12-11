package com.baeldung.checkconvertdouble;

import static com.baeldung.checkconvertdouble.core.CheckAndConvert.checkedDouble;
import static com.baeldung.checkconvertdouble.core.CheckAndConvert.checkedDoubleDefault;
import static com.baeldung.checkconvertdouble.core.CheckAndConvert.checkedDoubleOptional;
import static com.baeldung.checkconvertdouble.thirdparty.CheckAndConvert.checkDoubleFunctional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import com.google.common.primitives.Doubles;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.Optional;

public class CheckAndConvertUnitTest {

    @Test
    public void checkAndConvertVanilla() {
        assertThat(checkedDouble("1")).isEqualTo(1.0d);
        assertThat(checkedDouble(null)).isNaN();
        assertThat(checkedDouble("")).isNaN();

        assertThat(checkedDoubleDefault("1", 1.0d)).isEqualTo(1.0d);
        assertThat(checkedDoubleDefault(null, 1.0d)).isEqualTo(1.0d);
        assertThat(checkedDoubleDefault("", 1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void checkAndConvertOptional() {
        assertThat(checkedDoubleOptional("1")).isEqualTo(Optional.of(1.0d));
        assertThat(checkedDoubleOptional(null)).isEqualTo(Optional.empty());
        assertThat(checkedDoubleOptional("")).isEqualTo(Optional.empty());
        assertThat(checkedDoubleOptional(null).orElse(1.0d)).isEqualTo(1.0d);
        assertThat(checkedDoubleOptional("").orElse(1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void checkAndConvertFunctional() {
        assertThat(checkDoubleFunctional("1", 1.0d)).isEqualTo(1.0d);
        assertThat(checkDoubleFunctional(null, 1.0d)).isEqualTo(1.0d);
        assertThat(checkDoubleFunctional("", 1.0d)).isEqualTo(1.0d);
    }

    @Test
    public void checkAndConvertGuava() {
        assertThat(Doubles.tryParse("1.0")).isEqualTo(1.0d);
        Throwable thrown = catchThrowable(() -> Doubles.tryParse(null));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
        assertThat(Doubles.tryParse("")).isEqualTo(null);
    }

    @Test
    public void checkAndConvertCommonsLang() {
        String testDouble = null;

        assertThat(NumberUtils.toDouble("1.0")).isEqualTo(1.0d);
        assertThat(NumberUtils.toDouble(testDouble)).isEqualTo(0.0d);
        assertThat(NumberUtils.toDouble("")).isEqualTo(0.0d);

        assertThat(NumberUtils.toDouble("1.0", 2.0d)).isEqualTo(1.0d);
        assertThat(NumberUtils.toDouble(testDouble, 2.0d)).isEqualTo(2.0d);
        assertThat(NumberUtils.toDouble("", 2.0d)).isEqualTo(2.0d);
    }
}
