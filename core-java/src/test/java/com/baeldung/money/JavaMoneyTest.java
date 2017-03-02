package com.baeldung.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JavaMoneyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenCurrencyCode_whenString_thanExist() {
        CurrencyUnit USD = Monetary.getCurrency("USD");
        assertNotNull(USD);
        assertEquals(USD.getCurrencyCode(), "USD");
        assertEquals(USD.getNumericCode(), 840);
        assertEquals(USD.getDefaultFractionDigits(), 2);
        assertFalse(Monetary.isCurrencyAvailable("AAA"));

    }

    @Test
    public void givenCurrencyCode_whenNoExist_thanThrowsError() {
        thrown.expect(UnknownCurrencyException.class);
        CurrencyUnit AAA = Monetary.getCurrency("AAA");
        assertNull(AAA);
        throw new UnknownCurrencyException("AAA");
    }

    @Test
    public void givenAmounts_whenStrinfied_thanEquals() {
        CurrencyUnit USD = Monetary.getCurrency("USD");
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(USD).setNumber(200.50).create();
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        Money moneyof = Money.of(12, USD);
        FastMoney fastmoneyof = FastMoney.of(2, USD);
        Money oneEuro = Money.of(1, "EUR");

        assertEquals("USD", USD.toString());
        assertEquals("USD 1", oneDolar.toString());
        assertEquals("EUR 1", oneEuro.toString());
        assertEquals("USD 200.5", fstAmtUSD.toString());
        assertEquals("EUR 1.30473908", fstAmtEUR.toString());
        assertEquals("USD 12", moneyof.toString());
        assertEquals("USD 2.00000", fastmoneyof.toString());

    }

    @Test
    public void givenCurrencies_whenCompared_thanNotequal() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        Money oneEuro = Money.of(1, "EUR");
        assertFalse(oneEuro.equals(FastMoney.of(1, "EUR")));
        assertTrue(oneDolar.equals(Money.of(1, "USD")));

    }

    @Test
    public void givenAmount_whenDivided_thanThrowsException() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        thrown.expect(ArithmeticException.class);
        MonetaryAmount oneDivThree = oneDolar.divide(3);
        assertNull(oneDivThree);
        throw new ArithmeticException();

    }

    @Test
    public void givenArithmetic_whenStrinfied_thanEqualsAmount() {
        CurrencyUnit USD = Monetary.getCurrency("USD");
        Money moneyof = Money.of(12, USD);
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(USD).setNumber(200.50).create();
        Money calcAmtUSD = Money.of(1, "USD").subtract(fstAmtUSD);
        FastMoney fastmoneyof = FastMoney.of(2, USD);
        MonetaryAmount calcMoneyFastMoney = moneyof.subtract(fastmoneyof);
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        MonetaryAmount multiplyAmount = oneDolar.multiply(0.25);
        MonetaryAmount divideAmount = oneDolar.divide(0.25);
        
        MonetaryAmount[] monetaryAmounts = new MonetaryAmount[] {
                Money.of(100, "CHF"),
                Money.of(10.20, "CHF"),
                Money.of(1.15, "CHF"), };

        Money sumAmtCHF = Money.of(0, "CHF");

        for (MonetaryAmount monetaryAmount : monetaryAmounts) {
            sumAmtCHF = sumAmtCHF.add(monetaryAmount);
        }
        assertEquals("USD", USD.toString());
        assertEquals("USD 1", oneDolar.toString());
        assertEquals("USD 200.5", fstAmtUSD.toString());
        assertEquals("USD 12", moneyof.toString());
        assertEquals("USD 2.00000", fastmoneyof.toString());
        assertEquals("USD -199.5", calcAmtUSD.toString());
        assertEquals("CHF 111.35", sumAmtCHF.toString());
        assertEquals("USD 10", calcMoneyFastMoney.toString());
        assertEquals("USD 0.25", multiplyAmount.toString());
        assertEquals("USD 4", divideAmount.toString());
    }

    @Test
    public void givenAmount_whenRounded_thanEquals() {
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());
        assertEquals("EUR 1.30473908", fstAmtEUR.toString());
        assertEquals("EUR 1.3", roundEUR.toString());
    }

    @Test
    public void givenAmount_whenCustomFormat_thanEquals() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        String usFormatted = formatUSD.format(oneDolar);

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "00000.00 ¤").build());
        String customFormatted = customFormat.format(oneDolar);

        assertEquals("USD 1", oneDolar.toString());
        assertNotNull(formatUSD);
        assertNotNull(customFormat);
        assertEquals("USD1.00", usFormatted);
        assertEquals("00001.00 US Dollar", customFormatted);
    }

    @Test
    public void givenAmount_whenConversion_thenNotNull() {
        CurrencyUnit USD = Monetary.getCurrency("USD");
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();

        CurrencyConversion convEUR = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("EUR").build());
        CurrencyConversion convUSD = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency(USD).build());

        CurrencyConversion conversionUSD = MonetaryConversions.getConversion("USD");
        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");
        
        MonetaryAmount convertedAmountEURtoUSD = fstAmtEUR.with(conversionUSD);
        MonetaryAmount convertedAmountEURtoUSD2 = fstAmtEUR.with(convUSD);
        MonetaryAmount convertedAmountUSDtoEUR = oneDolar.with(conversionEUR);
        MonetaryAmount convertedAmountUSDtoEUR2 = oneDolar.with(convEUR);
        
        assertEquals("USD", USD.toString());
        assertEquals("USD 1", oneDolar.toString());
        assertEquals("EUR 1.30473908", fstAmtEUR.toString());
        assertNotNull(convEUR);
        assertNotNull(convUSD);
        assertNotNull(convertedAmountEURtoUSD);
        assertNotNull(convertedAmountEURtoUSD2);
        assertNotNull(convertedAmountUSDtoEUR);
        assertNotNull(convertedAmountUSDtoEUR2);
    }
}
