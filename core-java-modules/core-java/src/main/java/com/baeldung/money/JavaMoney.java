package com.baeldung.money;

import java.util.Locale;
import java.util.logging.Logger;

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

public class JavaMoney {
    final static Logger LOGGER = Logger.getLogger(JavaMoney.class.getName());
    CurrencyUnit USD;
    MonetaryAmount fstAmtUSD;
    MonetaryAmount fstAmtEUR;
    MonetaryAmount oneDolar;
    MonetaryAmount moneyof;
    MonetaryAmount fastmoneyof;
    MonetaryAmount roundEUR;
    MonetaryAmount calcAmtUSD;
    MonetaryAmount[] monetaryAmounts;
    MonetaryAmount sumAmtCHF;
    MonetaryAmount calcMoneyFastMoney;
    MonetaryAmount convertedAmountEURtoUSD;
    MonetaryAmount convertedAmountEURtoUSD2;
    MonetaryAmount convertedAmountUSDtoEUR;
    MonetaryAmount convertedAmountUSDtoEUR2;
    MonetaryAmount multiplyAmount;
    MonetaryAmount divideAmount;
    MonetaryAmount oneDivThree;
    CurrencyConversion convEUR;
    CurrencyConversion convUSD;
    CurrencyConversion conversionUSD;
    CurrencyConversion conversionEUR;
    MonetaryAmount oneEuro;
    MonetaryAmountFormat formatUSD;
    MonetaryAmountFormat customFormat;
    String usFormatted;
    String customFormatted;
    
    public JavaMoney() {
        USD = Monetary.getCurrency("USD");
        fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(USD).setNumber(200.50).create();
        fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        oneDolar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        moneyof = Money.of(12, USD);
        fastmoneyof = FastMoney.of(2, USD);
 
        LOGGER.info("First Amount in USD : " + fstAmtUSD);
        LOGGER.info("First Amount in EUR : " + fstAmtEUR);
        LOGGER.info("One Dolar : " + oneDolar);
        LOGGER.info("MoneyOf : " + moneyof);
        LOGGER.info("FastMoneyOf : " + fastmoneyof);
        
        try{
            @SuppressWarnings("unused")
            CurrencyUnit AAA = Monetary.getCurrency("AAA");
        } catch (UnknownCurrencyException e) {
            LOGGER.severe("Unknown Currency");
        }

        roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());

        LOGGER.info("Rounded EUR : " + roundEUR);

        calcAmtUSD = Money.of(1, "USD").subtract(fstAmtUSD);

        LOGGER.info("Substracting amounts : " + calcAmtUSD);

        calcMoneyFastMoney = moneyof.subtract(fastmoneyof);

        LOGGER.info("Money & FastMoney operations : " + calcMoneyFastMoney);

        monetaryAmounts = 
                new MonetaryAmount[] { 
                        Money.of(100, "CHF"), 
                        Money.of(10.20, "CHF"), 
                        Money.of(1.15, "CHF"), };
        sumAmtCHF = Money.of(0, "CHF");
        for (MonetaryAmount monetaryAmount : monetaryAmounts) {
            sumAmtCHF = sumAmtCHF.add(monetaryAmount);
        }

        LOGGER.info("Adding amounts : " + sumAmtCHF);

        multiplyAmount = oneDolar.multiply(0.25);
        LOGGER.info("Multiply Amount : " + multiplyAmount);

        divideAmount = oneDolar.divide(0.25);
        LOGGER.info("Divide Amount : " + divideAmount);

        try{
            oneDivThree = oneDolar.divide(3);
        }catch (ArithmeticException e) {
            LOGGER.severe("One divide by Three is an infinite number");
        }
        
        convEUR = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("EUR").build());
        convUSD = MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency(USD).build());

        conversionUSD = MonetaryConversions.getConversion("USD");
        conversionEUR = MonetaryConversions.getConversion("EUR");

        convertedAmountEURtoUSD = fstAmtEUR.with(conversionUSD);
        convertedAmountEURtoUSD2 = fstAmtEUR.with(convUSD);
        convertedAmountUSDtoEUR = oneDolar.with(conversionEUR);
        convertedAmountUSDtoEUR2 = oneDolar.with(convEUR);
        LOGGER.info("C1 - " + convertedAmountEURtoUSD);
        LOGGER.info("C2 - " + convertedAmountEURtoUSD2);
        LOGGER.info("One Euro -> " + convertedAmountUSDtoEUR);
        LOGGER.info("One Euro2 -> " + convertedAmountUSDtoEUR2);

        oneEuro = Money.of(1, "EUR");

        if (oneEuro.equals(FastMoney.of(1, "EUR"))) {
            LOGGER.info("Money == FastMoney");
        } else {
            LOGGER.info("Money != FastMoney");
        }

        if (oneDolar.equals(Money.of(1, "USD"))) {
            LOGGER.info("Factory == Money");
        } else {
            LOGGER.info("Factory != Money");
        }

        formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        usFormatted = formatUSD.format(oneDolar);
        LOGGER.info("One dolar standard formatted : " + usFormatted);

        customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "00000.00 ¤").build());
        customFormatted = customFormat.format(oneDolar);
        LOGGER.info("One dolar custom formatted : " + customFormatted);
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        JavaMoney java9Money = new JavaMoney();
    }
    
}
