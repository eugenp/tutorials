package com.baeldung.entityCollection;

import java.util.*;
import java.util.stream.Collectors;

public class IdExtractor {

    public static List<Long> extractIdsClassic(List<User> users) {
        List<Long> ids = new ArrayList<>();
        for (User user : users) {
            ids.add(user.getId());
        }
        return ids;
    }

    public static List<Long> extractIdsStream(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public static Set<Long> extractUniqueIds(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
