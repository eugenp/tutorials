package com.baeldung.staticenum;

public class Outer {

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner.MyEnum value = Outer.Inner.MyEnum.VALUE1;
    }

    public class Inner {
        public static enum MyEnum {
            VALUE1, VALUE2, VALUE3
        }
    }
}

