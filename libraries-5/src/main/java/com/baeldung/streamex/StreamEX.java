package com.baeldung.streamex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import one.util.streamex.DoubleStreamEx;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class StreamEX {

    public static void main(String[] args) {
        // Collector shortcut methods (toList, toSet, groupingBy, joining, etc.)
        List<User> users = Arrays.asList(new User("name"), new User(), new User());
        users.stream().map(User::getName).collect(Collectors.toList());
        List<String> userNames = StreamEx.of(users).map(User::getName).toList();
        Map<Role, List<User>> role2users = StreamEx.of(users).groupingBy(User::getRole);
        StreamEx.of(1, 2, 3).joining("; "); // "1; 2; 3"
        // Selecting stream elements of specific type
        List usersAndRoles = Arrays.asList(new User(), new Role());
        List<Role> roles = IntStreamEx.range(usersAndRoles.size()).mapToObj(usersAndRoles::get).select(Role.class).toList();
        System.out.println(roles);
        // adding elements to Stream
        List<String> appendedUsers = StreamEx.of(users).map(User::getName).prepend("(none)").append("LAST").toList();
        System.out.println(appendedUsers);
        // Removing unwanted elements and using the stream as Iterable:
        for (String line : StreamEx.of(users).map(User::getName).nonNull()) {
            System.out.println(line);
        }
        // Selecting map keys by value predicate:
        Map<String, Role> nameToRole = new HashMap<>();
        nameToRole.put("first", new Role());
        nameToRole.put("second", null);
        Set<String> nonNullRoles = StreamEx.ofKeys(nameToRole, Objects::nonNull).toSet();
        System.out.println(nonNullRoles);
        // Operating on key-value pairs:
        Map<User, List<Role>> users2roles = transformMap(role2users);
        Map<String, String> mapToString = EntryStream.of(users2roles).mapKeys(String::valueOf).mapValues(String::valueOf).toMap();
        // Support of byte/char/short/float types:
        short[] src = { 1, 2, 3 };
        char[] output = IntStreamEx.of(src).map(x -> x * 5).toCharArray();
    }

    public double[] getDiffBetweenPairs(double... numbers) {
        return DoubleStreamEx.of(numbers).pairMap((a, b) -> b - a).toArray();
    }

    public static Map<User, List<Role>> transformMap(Map<Role, List<User>> role2users) {
        Map<User, List<Role>> users2roles = EntryStream.of(role2users).flatMapValues(List::stream).invert().grouping();
        return users2roles;
    }

}
