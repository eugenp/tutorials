package com.baeldung.signature;

public class StaticBinding {

    public Number sum(Integer term1, Integer term2) {
        System.out.println("Adding integers");
        return term1 + term2;
    }

    public Number sum(Number term1, Number term2) {
        System.out.println("Adding numbers");
        return term1.doubleValue() + term2.doubleValue();
    }

    public Number sum(Object term1, Object term2) {
        System.out.println("Adding objects");
        return term1.hashCode() + term2.hashCode();
    }

    public Number sum(Object term1, Object... term2) {
        System.out.println("Adding variable arguments: " + term2.length);
        int result = term1.hashCode();
        for (Object o : term2) {
            result += o.hashCode();
        }
        return result;
    }

    public static void main(String[] args) {
        StaticBinding obj = new StaticBinding();

        obj.sum(2, 3); // "Adding integers" due to auto-boxing from int to Integer
        obj.sum(Integer.valueOf(2), Integer.valueOf(3)); // "Adding integers" due to exact parameter types
        obj.sum(2, 0x1); // "Adding integers" due to type promotion from byte to int

        obj.sum((Number) 2, (Number) 3); // "Adding numbers" due to explicit cast to Number
        obj.sum(2.0d, 3.0d); // "Adding numbers" due to auto-boxing from double to Double
        obj.sum(Float.valueOf(2), Float.valueOf(3)); // "Adding numbers" due to polimorphism

        obj.sum((Object) 2, (Object) 3); // "Adding objects" due to explicit cast to Object
        obj.sum(2, "John"); // "Adding objects" due to polimorphism

        obj.sum(new Object(), new Object(), new Object()); // "Adding variable arguments 2"
        obj.sum(new Object(), new Object[]{new Object()}); // "Adding variable arguments 1"
    }
}
