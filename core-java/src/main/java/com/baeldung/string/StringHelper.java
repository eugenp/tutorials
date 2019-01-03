package com.baeldung.string;

import java.util.Optional;

/**
 * @author zn.wang
 */
public class StringHelper {

    /**
     * 移除最后一个字符
     * @param s
     * @return
     */
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? s : (s.substring(0, s.length() - 1));
    }

    /**
     * 根据正则表达式，移除最后一个字符
     * @param s
     * @return
     */
    public static String removeLastCharRegex(String s) {
        return (s == null) ? s : s.replaceAll(".$", "");
    }


    /**
     * 移除最后一个空字符串
     * 参考正则表达式：{http://tool.oschina.net/uploads/apidocs/jquery/regexp.html}
     * @param s
     * @return
     */
    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s).filter(str -> str.length() != 0).map(str -> str.substring(0, str.length() - 1)).orElse(s);
    }

    /**
     * 根据正则表达式，移除最后一个空字符串
     * 参考正则表达式：{http://tool.oschina.net/uploads/apidocs/jquery/regexp.html}
     * @param s
     * @return
     */
    public static String removeLastCharRegexOptional(String s) {
        return Optional.ofNullable(s).map(str -> str.replaceAll(".$", "")).orElse(s);
    }
}
