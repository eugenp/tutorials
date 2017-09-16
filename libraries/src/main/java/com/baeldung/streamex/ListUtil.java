package com.baeldung.streamex;

import java.util.List;

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class ListUtil {

    public String join(String... strings) {
        return StreamEx.of(strings).joining("; ");
    }

    public List<String> addToList(List<User> users) {
        return StreamEx.of(users).map(User::getUsername).toList();
    }

    public List<String> getUsernames(List<User> users) {
        return StreamEx.of(users).map(User::getUsername).toList();
    }

    public List<User> getUsers(List<Object> objects) {
        return IntStreamEx.range(objects.size()).mapToObj(objects::get).select(User.class).toList();
    }

}
