package com.baeldung.stringbuffer;

public class HashCodeComparator {

    public static void main(String args[]) {

        System.out.println("String vs StringBuffer Hashcode Comparison:");

        //String
        String str = "Spring";
        System.out.println("String HashCode pre concatenation :" + str.hashCode());
        str = str + "Framework";
        System.out.println("String HashCode post concatenation :" + str.hashCode());

        //StringBuffer
        StringBuffer sBuf = new StringBuffer("Spring");
        System.out.println("StringBuffer HashCode pre concatenation :" + sBuf.hashCode());
        sBuf.append("Framework");
        System.out.println("StringBuffer HashCode post concatenation :" + sBuf.hashCode());
    }
}
