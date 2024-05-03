package com.baeldung.spring.data.jpa.querymap;

import org.springframework.data.util.Streamable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Users implements Streamable<User> {

    private final Streamable<User> userStreamable;

    public Users(Streamable<User> userStreamable) {
        this.userStreamable = userStreamable;
    }

    @Override
    public Iterator<User> iterator() {
        return userStreamable.iterator();
    }

    public Map<Long, User> getUserIdToUserMap() {
        return stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    public List<User> getAllUsersWithShortNames(int maxNameLength) {
        return stream()
            .filter(s -> s.getFirstName().length() <= maxNameLength)
            .collect(Collectors.toList());
    }

    public Map<Character, List<User>> groupUsersAlphabetically() {
        return stream().collect(Collectors.groupingBy(s -> getFristCharacter(s.getFirstName())));
    }

    private Character getFristCharacter(final String string) {
        return string.substring(0, 1).toUpperCase().charAt(0);
    }
}
