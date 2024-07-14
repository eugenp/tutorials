package com.baeldung.sublistindexoutofboundsexception;

import java.util.Collections;
import java.util.List;

public class SubListUtils {

    public static List<String> safeSubList(List<String> myList, int fromIndex, int toIndex) {
        if (myList == null || fromIndex >= myList.size() || toIndex <= 0 || fromIndex >= toIndex) {
            return Collections.emptyList();
        }

        return myList.subList(Math.max(0, fromIndex), Math.min(myList.size(), toIndex));
    }

}
