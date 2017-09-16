package com.baeldung.streamex;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

public class MapUtil {

    public Map<String, List<User>> filter(List<User> users) {
        return StreamEx.of(users).groupingBy(User::getRole);
    }

    public Set<String> getActiveUsers(Map<String, User> users) {
        return StreamEx.ofKeys(users, User::isExist).toSet();
    }

    public Map<User, List<String>> filter(Map<String, List<User>> map) {
        return EntryStream.of(map).flatMapValues(List::stream).invert().grouping();
    }

    public Map<String, String> stringMap(Map<Object, Object> map) {
        return EntryStream.of(map).mapKeys(String::valueOf).mapValues(String::valueOf).toMap();
    }

    public Map<String, String> getGroupMembers(Map<String, User> roleToUser, Collection<String> roles) {
        return StreamEx.of(roles).mapToEntry(roleToUser::get).nonNullValues().mapValues(User::getRole).toMap();
    }

}
