package com.baeldung.stringisnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumberUtilsIsCreatableUnitTest {
	@Test
	public void givenApacheCommons_whenUsingIsParsable_thenTrue() {
		// Valid Numbers
		assertThat(NumberUtils.isCreatable("22")).isTrue();
		assertThat(NumberUtils.isCreatable("5.05")).isTrue();
		assertThat(NumberUtils.isCreatable("-200")).isTrue();
		 
		// Invalid Numbers
		assertThat(NumberUtils.isCreatable("abc")).isFalse();
		assertThat(NumberUtils.isCreatable(" 22 ")).isFalse();
	}
}
