package com.baeldung.streamex;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class StreamExTest {

    StreamUtil streamUtil;
    MapperUtil mapperUtil;
    MapUtil mapUtil;
    MathUtil mathUtil;
    FileUtil fileUtil;

    List<User> users;
    List<Object> objects;

    Map<String, User> userMap;
    Map<String, User> userMapByRole;

    @Before
    public void test() {
        streamUtil = new StreamUtil();
        mapperUtil = new MapperUtil();
        mapUtil = new MapUtil();
        mathUtil = new MathUtil();
        fileUtil = new FileUtil();

        users = new ArrayList<User>();
        users.add(new User("admin1", "p1", "ADMIN"));
        users.add(new User("admin2", "p2", "ADMIN"));

        users.add(new User("user1", "u2", "USER"));
        users.add(new User("user2", "u2", "USER"));

        users.get(2).setActive(false);

        userMap = new HashMap<String, User>();
        userMapByRole = new HashMap<String, User>();

        userMap.put(users.get(0).getUsername(), users.get(0));
        userMap.put(users.get(1).getUsername(), users.get(1));
        userMap.put(users.get(2).getUsername(), users.get(2));
        userMap.put(users.get(3).getUsername(), users.get(3));

        userMapByRole.put(users.get(0).getRole(), users.get(0));
        userMapByRole.put(users.get(1).getRole(), users.get(1));
        userMapByRole.put(users.get(2).getRole(), users.get(2));
        userMapByRole.put(users.get(3).getRole(), users.get(3));

        objects = new ArrayList<Object>();
        objects.addAll(users);
        objects.add(null);
        objects.add("Hello Baeldung");
        objects.add(100);
        objects.add(89l);
    }

    @Test
    public void givenStringArray_expectJoinedString() {
        String[] str = { "A", "B", "C", "D" };
        assertEquals("A;B;C;D", streamUtil.join(";", str));
    }

    @Test
    public void givenListOfUsers_expectListOfUsernames() {
        assertEquals(users.stream().map(User::getUsername).collect(Collectors.toList()),
                mapperUtil.getUsernames(users));
    }

    @Test
    public void givenListOfObjects_expectListOfUsers() {
        assertEquals(users, mapperUtil.getUsers(objects));
    }

    @Test
    public void givenListOfUsers_expectMapOfUsersGroupByRole() {
        assertEquals(users.stream().map(User::getRole).collect(Collectors.toSet()), mapUtil.groupBy(users).keySet());
    }

    @Test
    public void givenMapOfUsers_expectActiveUsers() {
        assertEquals(users.stream().filter(user -> user.isActive()).map(User::getUsername).collect(Collectors.toSet()),
                mapUtil.getActiveUsers(userMap));
    }

    @Test
    public void givenMapOfListedUsers_expectInvertOfUsers() {
        Map<String, List<User>> map = new HashMap<String, List<User>>();
        map.put("ADMIN", users);
        map.put("USER", users);

        assertEquals(users.size(), mapUtil.invert(map).size());
    }

    @Test
    public void givenMapOfObjects_expectMapOfStrings() {
        Map<Object, Object> objectMap = new HashMap<Object, Object>();
        objectMap.put("key1", "val1");
        objectMap.put(1234, 4321);

        Map<String, String> stringMap = new HashMap<String, String>();
        objectMap.forEach((k, v) -> stringMap.put(k.toString(), v.toString()));

        assertEquals(stringMap, mapUtil.convertToStringMap(objectMap));
    }

    @Test
    public void m() {
        Set<String> roles = users.stream().map(User::getRole).collect(Collectors.toSet());
        assertEquals(roles.size(), mapUtil.getRoleOfUsers(userMapByRole, roles).size());
    }

    @Test
    public void givenFile_readFile() throws FileNotFoundException, IOException {
        fileUtil.read(new FileReader(getClass().getClassLoader().getResource("input.txt").getFile()));
    }

    @Test
    public void givenFile_writeFile() throws FileNotFoundException, IOException {
        fileUtil.write(new FileReader(getClass().getClassLoader().getResource("input.txt").getFile()),
                new FileWriter(getClass().getClassLoader().getResource("input.txt").getFile()));
    }

    @Test
    public void givenArrayOfDoubles_expectArrayOfDifferncePairs() {
        double[] numbers = { 10, 100, 1000 };
        double[] expectedDiff = { 90, 900 };

        for (int i = 0; i < expectedDiff.length; i++) {
            assertEquals(expectedDiff[i], mathUtil.getDiffBetweenPairs(numbers)[i], 0);
        }
    }

    @Test
    public void givenShorts_multiplyByMultiplier_expectMultipliedShorts() {
        short[] src = { 1, 2 };
        short multiplier = 20;
        short[] expectedResults = { 20, 40 };
        short[] results = mathUtil.multiply(src, multiplier);

        for (int i = 0; i < results.length; i++) {
            assertEquals(Integer.toBinaryString(0xFFFF & expectedResults[i]),
                    Integer.toBinaryString(0xFFFF & results[i]));
        }
    }

}
