package com.baeldung.findastring;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FindAStringInGivenList {


    public List<String> findUsingLoopWithRegex(String search, List<String> list) {

        List<String> matches = new ArrayList<String>();

        String pattern = ".*"+search+".*";
        Pattern p = Pattern.compile(pattern);

        for(String str: list) {
            if (p.matcher(str).matches()) {
                matches.add(str);
            }
        }

        return matches;
    }


    public List<String> findUsingLoop(String search, List<String> list) {

        List<String> matches = new ArrayList<String>();

        for(String str: list) {
            if (str.contains(search)) {
                matches.add(str);
            }
        }

        return matches;
    }

    public List<String> findUsingStream(String search, List<String> list) {

        List<String> matchingElements =
                list.stream()
                        .filter(str -> str.trim().contains(search))
                        .collect(Collectors.toList());

        return matchingElements;
    }

    public List<String> findUsingGuava(String search, List<String> list) {
        Iterable<String> result = Iterables.filter(list, Predicates.containsPattern(search));

        return Lists.newArrayList(result.iterator());
    }

    public List<String> findUsingCommonsCollection(String search, List<String> list) {
        Iterable<String> result = IterableUtils.filteredIterable(list, new org.apache.commons.collections4.Predicate<String>() {
            public boolean evaluate(String listElement) {
                return listElement.contains(search);
            }
        });

        return IteratorUtils.toList(result.iterator());
    }

}