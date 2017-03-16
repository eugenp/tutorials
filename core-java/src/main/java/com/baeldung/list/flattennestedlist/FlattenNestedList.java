package com.baeldung.list.flattennestedlist;

import java.util.ArrayList;
import java.util.List;

public class FlattenNestedList {

    public List<String> flattenListOfLists(List<List<String>> lol) {

        // flatten the list
        List<String> ls = new ArrayList<>();
        lol.forEach((k) -> ls.addAll(k));

        return ls;
    }

}
