package com.baeldung.money;

import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.MonetaryConversions;

import com.baeldung.money.JavaMoney;

import junit.framework.TestCase;

public class JavaMoneyTest 
    extends TestCase
{
    JavaMoney j9m;
    public JavaMoneyTest( String testName )
    {
        super( testName );
        j9m = new JavaMoney();
    }

    public void testAmounts()
    {
        assertEquals("USD", j9m.USD.toString());
        assertEquals("USD 1", j9m.oneDolar.toString());
        assertEquals("EUR 1", j9m.oneEuro.toString());
        assertEquals("USD 200.5", j9m.fstAmtUSD.toString());
        assertEquals("EUR 1.30473908", j9m.fstAmtEUR.toString());
        assertEquals("USD 12", j9m.moneyof.toString());
        assertEquals("USD 2.00000", j9m.fastmoneyof.toString());
        
    }
    
    public void testArithmetic(){
        assertEquals("USD -199.5", j9m.calcAmtUSD.toString());
        assertEquals("CHF 111.35", j9m.sumAmtCHF.toString());
        assertEquals("USD 10", j9m.calcMoneyFastMoney.toString());
        assertEquals("USD 0.25", j9m.multiplyAmount.toString());
        assertEquals("USD 4", j9m.divideAmount.toString());
    }
    
    public void testRounding(){
        assertEquals("EUR 1.3", j9m.roundEUR.toString());
    }
    
    public void testFormatting(){
        assertEquals("USD1.00", j9m.usFormatted);
        assertEquals("00001.00 US Dollar", j9m.customFormatted);
    }
    
    public void testConversion(){
        
        assertNotNull(MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("EUR").build()));  
        assertNotNull(MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("USD").build()));  
        assertNotNull(j9m.convertedAmountEURtoUSD);
        assertNotNull(j9m.convertedAmountEURtoUSD2);
        assertNotNull(j9m.convertedAmountUSDtoEUR);
        assertNotNull(j9m.convertedAmountUSDtoEUR2);
    }
}
