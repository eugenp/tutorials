package com.baeldung.allequalelements;

import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class VerifyAllEqualListElements {

    public boolean verifyAllEqualUsingALoop(List<String> list) {
        for (String s : list) {
            if(!s.equals(list.get(0)))
                return false;
        }
        return true;
    }

    public boolean verifyAllEqualUsingStream(List<String> list) {
          return list.stream().distinct().limit(2).count() <= 1;
    }

    public boolean verifyAllEqualAnotherUsingStream(List<String> list) {
        return list.isEmpty() || list.stream().allMatch(list.get(0)::equals);
    }


    public boolean verifyAllEqualUsingGuava(List<String> list) {
        return Iterables.all(list, new Predicate<String>() {
            public boolean apply(String s) {
                return s.equals(list.get(0));
            }
        });
    }

    public boolean verifyAllEqualUsingApacheCommon(List<String> list) {
        return IterableUtils.matchesAll(list, new org.apache.commons.collections4.Predicate<String>() {
            public boolean evaluate(String s) {
                return s.equals(list.get(0));
            }
        });
    }

}