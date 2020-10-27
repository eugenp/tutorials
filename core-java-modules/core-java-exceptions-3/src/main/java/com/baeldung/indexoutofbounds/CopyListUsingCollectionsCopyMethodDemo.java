package com.baeldung.indexoutofbounds;

import java.util.Collections;
import java.util.List;

public class CopyListUsingCollectionsCopyMethodDemo {
    static void copyList(List<Integer> source, List<Integer> destination) {
        Collections.copy(destination, source);
    }
}
