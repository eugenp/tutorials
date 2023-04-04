package com.baeldung.list.ignorecase;

import java.util.List;

public class IgnoreCaseSearchUtil {
    public static boolean ignoreCaseContains(List<String> theList, String searchStr) {
        for (String s : theList) {
            if (searchStr.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
