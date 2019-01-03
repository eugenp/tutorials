package com.baeldung.string;

import java.util.stream.IntStream;

/**
 * 回文串：正读和反读都一样的字符串
 * 例如：“回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
 * @author zn.wang
 */
public class Palindrome {

    /**
     * 判断是否为回文串
     * 算法：
     * （1）使用while循环进行前后字符比较。
     * @param text
     * @return
     */
    public boolean isPalindrome(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        int length = clean.length();
        int forward = 0;
        int backward = length - 1;
        while (backward > forward) {
            char forwardChar = clean.charAt(forward++);
            char backwardChar = clean.charAt(backward--);
            if (forwardChar != backwardChar){
                return false;
            }
        }
        return true;
    }

    /**
     * 回文串进行反转
     * 算法：
     * （1）使用for循环进行原字符串反转，然后使用{@link java.lang.String#equals(Object)}进行比较。
     * @param text
     * @return
     */
    public boolean isPalindromeReverseTheString(String text) {
        StringBuilder reverse = new StringBuilder();
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        char[] plain = clean.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--){
            reverse.append(plain[i]);
        }
        return (reverse.toString()).equals(clean);
    }

    /**
     * 是否为回文串，使用StringBuilder
     * @param text
     * @return
     */
    public boolean isPalindromeUsingStringBuilder(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuilder plain = new StringBuilder(clean);
        StringBuilder reverse = plain.reverse();
        return (reverse.toString()).equals(clean);
    }

    /**
     * 是否为回文串，使用StringBuffer
     * 算法：
     * （1）使用{@link java.lang.StringBuffer#reverse()}对原字符串进行反转；
     * （2）使用{@link java.lang.String#equals(Object)}对原有字符串和反转后的字符串进行相比，一致则为回文串。
     * @param text
     * @return
     */
    public boolean isPalindromeUsingStringBuffer(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuffer plain = new StringBuffer(clean);
        StringBuffer reverse = plain.reverse();
        return (reverse.toString()).equals(clean);
    }

    /**
     * 是否为回文数，使用递归算法
     * @param text
     * @return
     */
    public boolean isPalindromeRecursive(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        return recursivePalindrome(clean, 0, clean.length() - 1);
    }

    /**
     * 递归算法
     * @param text
     * @param forward
     * @param backward
     * @return
     */
    private boolean recursivePalindrome(String text, int forward, int backward) {
        if (forward == backward){
            return true;
        }
        if ((text.charAt(forward)) != (text.charAt(backward))){
            return false;
        }
        if (forward < backward + 1) {
            return recursivePalindrome(text, forward + 1, backward - 1);
        }

        return true;
    }

    /**
     * 是否为回文数，使用IntStream判断
     * 思想：
     * （1）获取字符串的中间位置n/2，进行for循环比较；
     * （2）第i个字符和第n-i-1个字符进行比较(i从0开始，n为字符串的长度)
     *
     * @param text
     * @return
     */
    public boolean isPalindromeUsingIntStream(String text) {
        String temp = text.replaceAll("\\s+", "").toLowerCase();
        int bound = temp.length() / 2;
        for (int i = 0; i < bound; i++) {
            if (temp.charAt(i) != temp.charAt(temp.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
