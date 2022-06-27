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
        
        double sum_ab = a + b;
        System.out.println("a + b =     " + sum_ab);
        
        double abc =  a + b + c;
        System.out.println("a + b + c = " + abc);
        
        double ab_c = sum_ab + c;
        System.out.println("ab + c =    " + ab_c);
        
        double sum_ac = a + c;
        System.out.println("a + c =     " + sum_ac);
        
        double acb = a + c + b;
        System.out.println("a + c + b = " + acb);
        
        double ac_b = sum_ac + b;
        System.out.println("ac + b =    " + ac_b);
        
        double ab = 18.1;
        double ac = 34.67;
        double sum_ab_c = ab + c;
        double sum_ac_b = ac + b;
        System.out.println("ab + c =    " + sum_ab_c);
        System.out.println("ac + b =    " + sum_ac_b);
        
        BigDecimal d = new BigDecimal(String.valueOf(a));
        BigDecimal e = new BigDecimal(String.valueOf(b));
        BigDecimal f = new BigDecimal(String.valueOf(c));
        
        BigDecimal def = d.add(e).add(f);
        BigDecimal dfe = d.add(f).add(e);
        
        System.out.println("d + e + f = " + def);
        System.out.println("d + f + e = " + dfe);
    }
}
