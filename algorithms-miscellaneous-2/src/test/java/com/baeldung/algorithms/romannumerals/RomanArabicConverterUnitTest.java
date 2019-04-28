package com.baeldung.math.romannumerals;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RomanArabicConverterUnitTest {

    @Test
    public void given2018Roman_WhenConvertingToArabic_ThenReturn2018() {

        String roman2018 = "MMXVIII";

        int result = RomanArabicConverter.romanToArabic(roman2018);

        assertThat(result).isEqualTo(2018);
    }

    @Test
    public void given1999Arabic_WhenConvertingToRoman_ThenReturnMCMXCIX() {

        int arabic1999 = 1999;

        String result = RomanArabicConverter.arabicToRoman(arabic1999);

        assertThat(result).isEqualTo("MCMXCIX");
    }

}
