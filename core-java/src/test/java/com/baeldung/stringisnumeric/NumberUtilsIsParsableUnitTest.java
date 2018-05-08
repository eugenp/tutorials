package com.baeldung.stringisnumeric;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumberUtilsIsParsableUnitTest {
	@Test
	public void givenApacheCommons_whenUsingIsParsable_thenTrue() {
		assertThat(NumberUtils.isParsable("22")).isTrue();
		assertThat(NumberUtils.isParsable("-23")).isTrue();
		assertThat(NumberUtils.isParsable("2.2")).isTrue();
		
		assertThat(NumberUtils.isParsable("6.2F")).isFalse();
		assertThat(NumberUtils.isParsable("9.8d")).isFalse();
		assertThat(NumberUtils.isParsable("22L")).isFalse();
	}

}
