package com.baeldung.findastring;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FindAStringInGivenList {


    public List findUsingLoopWithRegex(String search, List<String> list) {

        List<String> matches = new ArrayList<String>();

        String pattern = ".*\\b"+search+"\\b.*";
        Pattern p = Pattern.compile(pattern);

        for(String str: list) {
            if (p.matcher(str).matches()) {
                matches.add(str);
            }
        }

        return matches;
    }


    public List findUsingLoop(String search, List<String> list) {

        List<String> matches = new ArrayList<String>();

        for(String str: list) {
            if (search != null && str.contains(search)) {
                matches.add(str);
            }
        }

        return matches;
    }

    public List findUsingStream(String search, List<String> list) {

        List<String> matchingElements =
                list.stream()
                        .filter(str -> search != null)
                        .filter(str -> str.trim().contains(search))
                        .collect(Collectors.toList());

        return matchingElements;
    }

    public List<String> findUsingGuava(String search, List<String> list) {
        if (search == null) {
            return Lists.newArrayList();
        }

        Iterable<String> result = Iterables.filter(Iterables.filter(list,Predicates.notNull()), Predicates.containsPattern(search));

        return Lists.newArrayList(result.iterator());
    }

    public List<String> findUsingApacheCommon(String search, List<String> list) {
        CollectionUtils.filter(list, PredicateUtils.notNullPredicate());
        Iterable<String> result = IterableUtils.filteredIterable(list, new org.apache.commons.collections4.Predicate<String>() {
            public boolean evaluate(String listElement) {
                return search != null && StringUtils.isNotEmpty(listElement) ? listElement.contains(search) : false;
            }
        });

        return IteratorUtils.toList(result.iterator());
    }

}