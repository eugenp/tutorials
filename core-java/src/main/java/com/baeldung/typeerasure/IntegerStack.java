package com.baeldung.typeerasure;

/**
 * @author zn.wang
 */
public class IntegerStack extends Stack<Integer> {

    public IntegerStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(Integer value) {
        System.out.println("Pushing into my integerStack");
        super.push(value);
    }
}
