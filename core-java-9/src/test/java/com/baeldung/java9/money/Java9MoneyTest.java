package com.baeldung.java9.money;

import com.baeldung.java9.money.Java9Money;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Java9MoneyTest 
    extends TestCase
{
    public Java9MoneyTest( String testName )
    {
        super( testName );
        
    }

    public static Test suite()
    {
        return new TestSuite( Java9MoneyTest.class );
    }

    public void testApp()
    {
        Java9Money j9m = new Java9Money();
        assertEquals("USD", j9m.USD.toString());
        assertEquals("USD 1", j9m.oneDolar.toString());
        assertEquals("EUR 1", j9m.oneEuro.toString());
        assertEquals("USD 200.5", j9m.fstAmtUSD.toString());
        assertEquals("EUR 1.30473908", j9m.fstAmtEUR.toString());
        assertEquals("USD 12", j9m.moneyof.toString());
        assertEquals("USD 2.00000", j9m.fastmoneyof.toString());
        assertEquals("EUR 1.3", j9m.roundEUR.toString());
        assertEquals("USD -199.5", j9m.calcAmtUSD.toString());
        assertEquals("CHF 111.35", j9m.sumAmtCHF.toString());
        assertEquals("USD 10", j9m.calcMoneyFastMoney.toString());
        assertEquals("USD 1.385111007328", j9m.convertedAmountEURtoUSD.toString());
        assertEquals("USD 1.385111007328", j9m.convertedAmountEURtoUSD2.toString());
        assertEquals("EUR 0.9419743782969103", j9m.convertedAmountUSDtoEUR.toString());
        assertEquals("EUR 0.9419743782969103", j9m.convertedAmountUSDtoEUR2.toString());
        assertEquals("USD 0.25", j9m.multiplyAmount.toString());
        assertEquals("USD 4", j9m.divideAmount.toString());
        assertEquals("USD1.00", j9m.usFormatted);
        assertEquals("00001.00 US Dollar", j9m.customFormatted);
        
    }
}
