package com.baeldung.decimalformat;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

public class DecimalFormatExamplesUnitTest {

	double d = 1234567.89;

	@Test
	public void givenSimpleDecimal_WhenFormatting_ThenCorrectOutput() {

		assertThat(new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1234567.89");

		assertThat(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1234567.89");

		assertThat(new DecimalFormat("#########.###", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1234567.89");

		assertThat(new DecimalFormat("000000000.000", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("001234567.890");

	}

	@Test
	public void givenSmallerDecimalPattern_WhenFormatting_ThenRounding() {

		assertThat(new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.ENGLISH)).format(d)).isEqualTo("1234567.9");

		assertThat(new DecimalFormat("#", new DecimalFormatSymbols(Locale.ENGLISH)).format(d)).isEqualTo("1234568");

	}

	@Test
	public void givenGroupingSeparator_WhenFormatting_ThenGroupedOutput() {

		assertThat(new DecimalFormat("#,###.#", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1,234,567.9");

		assertThat(new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1,234,568");

	}

	@Test
	public void givenMixedPattern_WhenFormatting_ThenCorrectOutput() {

		assertThat(new DecimalFormat("The # number", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("The 1234568 number");

		assertThat(new DecimalFormat("The '#' # number", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("The # 1234568 number");

	}

	@Test
	public void givenLocales_WhenFormatting_ThenCorrectOutput() {

		assertThat(new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1,234,567.89");

		assertThat(new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.ITALIAN)).format(d))
				.isEqualTo("1.234.567,89");

		assertThat(new DecimalFormat("#,###.##", DecimalFormatSymbols.getInstance(new Locale("it", "IT"))).format(d))
				.isEqualTo("1.234.567,89");

	}

	@Test
	public void givenE_WhenFormatting_ThenScientificNotation() {

		assertThat(new DecimalFormat("00.#######E0", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("12.3456789E5");

		assertThat(new DecimalFormat("000.000000E0", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("123.456789E4");

		assertThat(new DecimalFormat("##0.######E0", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1.23456789E6");

		assertThat(new DecimalFormat("###.000000E0", new DecimalFormatSymbols(Locale.ENGLISH)).format(d))
				.isEqualTo("1.23456789E6");

	}

	@Test
	public void givenString_WhenParsing_ThenCorrectOutput() throws ParseException {

		assertThat(new DecimalFormat("", new DecimalFormatSymbols(Locale.ENGLISH)).parse("1234567.89"))
				.isEqualTo(1234567.89);

		assertThat(new DecimalFormat("", new DecimalFormatSymbols(Locale.ITALIAN)).parse("1.234.567,89"))
				.isEqualTo(1234567.89);

	}

	@Test
	public void givenStringAndBigDecimalFlag_WhenParsing_ThenCorrectOutput() throws ParseException {

		NumberFormat nf = new DecimalFormat("", new DecimalFormatSymbols(Locale.ENGLISH));
		((DecimalFormat) nf).setParseBigDecimal(true);
		assertThat(nf.parse("1234567.89")).isEqualTo(BigDecimal.valueOf(1234567.89));
	}

}
