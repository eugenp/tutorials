package com.baeldung.money;

import com.google.common.collect.Lists;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;
import org.junit.Ignore;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Java货币单位手工测试
 * @see {https://my.oschina.net/VILLE/blog/1611920}
 * @see {http://www.importnew.com/14474.html}
 */
public class JavaMoneyUnitManualTest {

    @Test
    public void givenCurrencyCode_whenString_thanExist() {
        //据货币代码来获取货币单位
        CurrencyUnit usd = Monetary.getCurrency("USD");
        //亦或根据国家及地区来获取货币单位
        CurrencyUnit usd2 = Monetary.getCurrency(Locale.US);

        assertNotNull(usd);
        assertEquals(usd.getCurrencyCode(), "USD");
        assertEquals(usd.getNumericCode(), 840);
        assertEquals(usd.getDefaultFractionDigits(), 2);
    }

    @Test(expected = UnknownCurrencyException.class)
    public void givenCurrencyCode_whenNoExist_thanThrowsError() {
        Monetary.getCurrency("AAA");
    }

    /**
     * 注意1：
     *      当且仅当实现类，货币单位，以及数值全部相等时才认为这两个MontetaryAmount实例是相等的。
     * 注意2：
     *      Money与FastMoney是JavaMoney库中MonetaryAmount的两种实现。Money是默认实现，它使用BigDecimal来存储金额。
     * FastMoney是可选的另一个实现，它用long类型来存储金额。根据文档来看，FastMoney上的操作要比Money的快10到15倍左右。
     * 然而，FastMoney的金额大小与精度都受限于long类型。
     */
    @Test
    public void givenAmounts_whenStringified_thanEquals() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        MonetaryAmount fstAmtUSD = Monetary
          .getDefaultAmountFactory()
          .setCurrency(usd)
          .setNumber(200)
          .create();
        Money moneyof = Money.of(12, usd);
        FastMoney fastmoneyof = FastMoney.of(2, usd);

        assertEquals("USD", usd.toString());
        assertEquals("USD 200", fstAmtUSD.toString());
        assertEquals("USD 12", moneyof.toString());
        assertEquals("USD 2.00000", fastmoneyof.toString());
    }

    @Test
    public void givenCurrencies_whenCompared_thanNotequal() {
        MonetaryAmount oneDolar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();
        Money oneEuro = Money.of(1, "EUR");

        assertFalse(oneEuro.equals(FastMoney.of(1, "EUR")));
        assertTrue(oneDolar.equals(Money.of(1, "USD")));
    }

    /**
     * 除法报错
     */
    @Test(expected = ArithmeticException.class)
    public void givenAmount_whenDivided_thanThrowsException() {
        MonetaryAmount oneDolar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();

        MonetaryAmount temp =  oneDolar.divide(3);//除数只能输入(0,1]
        System.out.println("temp:{}" + temp);
        fail(); // if no exception
    }

    /**
     * 累加
     */
    @Test
    public void givenAmounts_whenSummed_thanCorrect() {
        List<MonetaryAmount> monetaryAmounts = Arrays.asList(
                Money.of(100, "CHF"),
                Money.of(10.20, "CHF"),
                Money.of(1.15, "CHF"));

        Money sumAmtCHF = (Money) monetaryAmounts
          .stream()
          .reduce(Money.of(0, "CHF"), MonetaryAmount::add);

        assertEquals("CHF 111.35", sumAmtCHF.toString());
    }

    /**
     * 加、减、乘、除
     */
    @Test
    public void givenArithmetic_whenStringified_thanEqualsAmount() {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        Money moneyof = Money.of(12, usd);
        MonetaryAmount fstAmtUSD = Monetary
          .getDefaultAmountFactory()
          .setCurrency(usd)
          .setNumber(200.50)
          .create();
        MonetaryAmount oneDolar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();
        Money subtractedAmount = Money
          .of(1, "USD")
          .subtract(fstAmtUSD);
        MonetaryAmount multiplyAmount = oneDolar.multiply(0.25);
        MonetaryAmount divideAmount = oneDolar.divide(0.25);

        assertEquals("USD", usd.toString());
        assertEquals("USD 1", oneDolar.toString());
        assertEquals("USD 200.5", fstAmtUSD.toString());
        assertEquals("USD 12", moneyof.toString());
        assertEquals("USD -199.5", subtractedAmount.toString());
        assertEquals("USD 0.25", multiplyAmount.toString());
        assertEquals("USD 4", divideAmount.toString());
    }

    /**
     * 四舍五入
     */
    @Test
    public void givenAmount_whenRounded_thanEquals() {
        MonetaryAmount fstAmtEUR = Monetary
          .getDefaultAmountFactory()
          .setCurrency("EUR")
          .setNumber(1.30473908)
          .create();
        MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());
        assertEquals("EUR 1.30473908", fstAmtEUR.toString());
        assertEquals("EUR 1.3", roundEUR.toString());
    }

    /**
     * 四舍五入
     * 注意：货币提供者并不总是可用的
     */
    @Test
    @Ignore("Currency providers are not always available")
    public void givenAmount_whenConversion_thenNotNull() {
        MonetaryAmount oneDollar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();

        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");

        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);

        assertEquals("USD 1", oneDollar.toString());
        assertNotNull(convertedAmountUSDtoEUR);
    }

    /**
     * 格式化
     */
    @Test
    public void givenLocale_whenFormatted_thanEquals() {
        MonetaryAmount oneDollar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();
        MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        String usFormatted = formatUSD.format(oneDollar);

        assertEquals("USD 1", oneDollar.toString());
        assertNotNull(formatUSD);
        assertEquals("USD1.00", usFormatted);
    }

    /**
     * 格式化：使用自定义格式化风格
     */
    @Test
    public void givenAmount_whenCustomFormat_thanEquals() {
        MonetaryAmount oneDollar = Monetary
          .getDefaultAmountFactory()
          .setCurrency("USD")
          .setNumber(1)
          .create();

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder
          .of(Locale.US)
          .set(CurrencyStyle.NAME)
          .set("pattern", "00000.00 US Dollar")
          .build());
        String customFormatted = customFormat.format(oneDollar);

        assertNotNull(customFormat);
        assertEquals("USD 1", oneDollar.toString());
        assertEquals("00001.00 US Dollar", customFormatted);
    }


    /**
     * 测试：基本算数运算
     */
    @Test
    public void basicArithmeticOperation(){
        CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.US);

        //金额表示
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory().setCurrency(currencyUnit).setNumber(200).create();

        //Money money = Money.of(12, currencyUnit);
        //FastMoney fastMoney = FastMoney.of(2, currencyUnit);

        //货币计算
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency(currencyUnit).setNumber(1).create();

        //"+"
        MonetaryAmount[] monetaryAmounts = new MonetaryAmount[]{
                Money.of(100, "CHF"),
                Money.of(10.20, "CHF"),
                Money.of(1.15, "CHF")};
        Money sumAmtCHF = Money.of(0, "CHF");
        for (MonetaryAmount monetaryAmount : monetaryAmounts) {
            sumAmtCHF = sumAmtCHF.add(monetaryAmount);
        }
        System.out.println("sumAmtCHF:{}" + sumAmtCHF.toString());


        //"-"
        Money calcAmtUSD = Money.of(1, "USD").subtract(fstAmtUSD);
        System.out.println("calcAmtUSD:{}" + calcAmtUSD.toString());

        //"*"
        MonetaryAmount multiplyAmount = oneDollar.multiply(0.25);
        System.out.println("multiplyAmount:{}" + multiplyAmount.toString());

        //"\"
        MonetaryAmount divideAmount = oneDollar.divide(0.25);
        System.out.println("divideAmount:{}" + divideAmount.toString());


        //四舍五入
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1.30473908).create();
        MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());
        System.out.println("roundEUR:{}" + roundEUR.toString());

        //货币格式化以及解析
        MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        String usFormatted = formatUSD.format(oneDollar);
        System.out.println("usFormatted:{}" + usFormatted);

        MonetaryAmount parsed = formatUSD.parse("USD1.00");
        System.out.println("parsed:{}" + parsed.toString());
    }

    /**
     * 过滤、排序、分组
     */
    @Test
    public void filterOrderedGroup(){
        List<MonetaryAmount> amounts = Lists.newArrayList();
        amounts.add(Money.of(2000.00, "EUR"));
        amounts.add(Money.of(4200.00, "USD"));
        amounts.add(Money.of(700.00, "USD"));
        amounts.add(Money.of(13.37, "JPY"));
        amounts.add(Money.of(188000.80, "USD"));

        CurrencyUnit yen = Monetary.getCurrency("JPY");
        CurrencyUnit dollar = Monetary.getCurrency("USD");

        //****1：过滤
        // 根据货币过滤，只返回美金
        List<MonetaryAmount> onlyDollar = amounts.stream().filter(MonetaryFunctions.isCurrency(dollar)).collect(Collectors.toList());

        // 根据货币过滤，只返回美金和日元
        List<MonetaryAmount> onlyDollarAndYen = amounts.stream().filter(MonetaryFunctions.isCurrency(dollar, yen)).collect(Collectors.toList());

        // 过滤出大于或小于某个阈值的金额
        MonetaryAmount tenDollar = Money.of(1000, dollar);
        List<MonetaryAmount> greaterThanTenDollar = amounts.stream().filter(MonetaryFunctions.isCurrency(dollar))
                .filter(MonetaryFunctions.isGreaterThan(tenDollar)).collect(Collectors.toList());
        System.out.println("根据货币过滤，只返回美金:{}" + onlyDollar);
        System.out.println("根据货币过滤，只返回美金和日元:{}" + onlyDollarAndYen);
        System.out.println("过滤出大于或小于某个阈值的金额:{}" + greaterThanTenDollar);
        System.out.println("\n========================");

        //****2：排序与分组
        // Sorting dollar values by number value
        List<MonetaryAmount> sortedByAmount = amounts.stream().sorted(MonetaryFunctions.sortNumber()).collect(Collectors.toList());
        //Sorting by CurrencyUnit
        List<MonetaryAmount> sortedByCurrencyUnit = amounts.stream().sorted(MonetaryFunctions.sortCurrencyUnit()).collect(Collectors.toList());
        //按货币单位进行分组
        Map<CurrencyUnit, List<MonetaryAmount>> groupedByCurrency = amounts.stream().collect(MonetaryFunctions.groupByCurrencyUnit());
        System.out.println("Sorting dollar values by number value:{}" + sortedByAmount);
        System.out.println("Sorting by CurrencyUnit:{}" + sortedByCurrencyUnit);
        System.out.println("按货币单位进行分组:{}" + groupedByCurrency);
        System.out.println("\n========================");

        // 分组并进行汇总
        Map<CurrencyUnit, MonetarySummaryStatistics> summary = amounts.stream().collect(MonetaryFunctions.groupBySummarizingMonetary()).get();
        MonetarySummaryStatistics dollarSummary = summary.get(dollar);
        MonetaryAmount average = dollarSummary.getAverage().with(Monetary.getDefaultRounding());;
        MonetaryAmount min = dollarSummary.getMin();
        MonetaryAmount max = dollarSummary.getMax();
        MonetaryAmount sum = dollarSummary.getSum();
        long count = dollarSummary.getCount();
        System.out.println("分组并进行汇总:average="+average + ";min=" + min +";max=" + max + ";sum=" + sum + ";count=" + count);

    }
}
