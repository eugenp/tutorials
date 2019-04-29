package com.baeldung.bifunction;

import java.util.function.BiFunction;

public class Calculator {
	
    private Integer a = 0;
    private Integer b = 0;
	
    public Calculator(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public Integer calculate(Integer a, Integer b, BiFunction<Integer, Integer, Integer> obj){
        return obj.apply(a, b);
    }
	
    public Integer calculate(BiFunction<Integer, Integer, Integer> obj){
        return obj.apply(a, b);
    }
	
    public static void main(String[] args) {
    	Calculator calculator = new Calculator(3, 7);
    	Integer result = calculator.calculate((a, b) -> (a * a) * (b + 10));    	
    	System.out.println(result);
    }
}
