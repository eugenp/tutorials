package com.baeldung;

import java.util.ArrayList;
import java.util.List;

public class CopyListUsingConstructorDemo {

    static List<Integer> copyList(List<Integer> source) {

        return new ArrayList<>(source);
    }
}
