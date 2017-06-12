package com.baeldung.maths;

import java.math.BigDecimal;

public class FloatingPointArithmetic {
    public static void main(String[] args) {
        
        double a = 13.22;
        double b = 4.88;
        double c = 21.45;
        
        System.out.println("a =         " + a);
        System.out.println("b =         " + b);
        System.out.println("c =         " + c);
        
        double ab = a + b;
        System.out.println("a + b =     " + ab);
        
        double abc =  a + b + c;
        System.out.println("a + b + c = " + abc);
        
        double ac = a + c;
        System.out.println("a + c =     " + ac);
        
        double acb = a + c + b;
        System.out.println("a + c + b = " + acb);
        
        double ac_b = ac + b;
        System.out.println("ac + b =    " + ac_b);
        
        BigDecimal d = new BigDecimal(String.valueOf(a));
        BigDecimal e = new BigDecimal(String.valueOf(b));
        BigDecimal f = new BigDecimal(String.valueOf(c));
        
        BigDecimal def;
        BigDecimal dfe;
        
        def = d.add(e).add(f);
        dfe = d.add(f).add(e);
        
        System.out.println("d + e + f = " + def);
        System.out.println("d + f + e = " + dfe);
    }
}
