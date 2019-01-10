package com.baeldung.maths;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class FloatingPointArithmeticUnitTest {

    /**
     * 注意：double类型的数据进行相加，不能直接用+
     */
    @Test
    public void givenDecimalNumbers_whenAddedTogether_thenGetExpectedResult() {
        double a = 13.22;
        double b = 4.88;
        double c = 21.45;
        double result = 39.55;
        
        double abc = a + b + c;
        double acb = a + c + b;
        System.out.println("abc:{}" + abc);
        System.out.println("acb:{}" + acb);
        
        Assert.assertEquals(result, abc, 0);
        Assert.assertNotEquals(result, acb, 0);
        System.out.println();

        double ab = 18.1;
        double ac = 34.67;
        
        double ab_c = ab + c;
        double ac_b = ac + b;
        System.out.println("ab_c:{}" + ab_c);
        System.out.println("ac_b:{}" + ac_b);
        
        Assert.assertEquals(result, ab_c, 0);
        Assert.assertNotEquals(result, ac_b, 0);
        System.out.println();

        BigDecimal d = new BigDecimal(String.valueOf(a));
        BigDecimal e = new BigDecimal(String.valueOf(b));
        BigDecimal f = new BigDecimal(String.valueOf(c));
        BigDecimal sum = new BigDecimal("39.55");
        System.out.println("d:{}" + d);
        System.out.println("e:{}" + e);
        System.out.println("f:{}" + f);
        System.out.println("sum:{}" + sum);
        
        BigDecimal def = d.add(e).add(f);
        BigDecimal dfe = d.add(f).add(e);
        System.out.println("def:{}" + def);
        System.out.println("dfe:{}" + dfe);

        Assert.assertEquals(0, def.compareTo(sum));
        Assert.assertEquals(0, dfe.compareTo(sum));

        BigDecimal temp = new BigDecimal(String.valueOf(acb));//double是有精度的，所以下面的比较不一样
        System.out.println("temp:{}" + temp);

        Assert.assertNotEquals(0, sum.compareTo(temp));
    }
}
