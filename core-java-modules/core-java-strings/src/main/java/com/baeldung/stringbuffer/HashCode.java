package com.baeldung.stringbuffer;

public class HashCode {

    public static long getHashCodeString(String string) {
        return string.hashCode();
    }

    public static long getHashCodeSBuffer(StringBuffer strBuff) {
        return strBuff.hashCode();
    }

    public static void main(String[] args) {
        String str = "Spring";
        System.out.println("String HashCode pre concatenation :" + getHashCodeString(str));
        str += "Framework";
        System.out.println("String HashCode post concatenation :" + getHashCodeString(str));

        StringBuffer sBuf = new StringBuffer("Spring");
        System.out.println("StringBuffer HashCode pre concatenation :" + getHashCodeSBuffer(sBuf));
        sBuf.append("Framework");
        System.out.println("StringBuffer HashCode post concatenation :" + getHashCodeSBuffer(sBuf));
    }
}
