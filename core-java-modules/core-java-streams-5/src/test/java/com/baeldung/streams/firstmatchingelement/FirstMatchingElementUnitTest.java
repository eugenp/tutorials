package com.baeldung.streams.firstmatchingelement;


import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;

import com.google.common.collect.Iterables;

public class FirstMatchingElementUnitTest {

    private List<User> userList = List.of(new User(1, "David"), new User(2, "John"), new User(3, "Roger"), new User(4, "John"));
    private String searchName = "John";

    @Test
    public void whenUsingStream_thenFindFirstMatchingUserIndex() {
        AtomicInteger counter = new AtomicInteger(-1);
        int index = userList.stream()
          .filter(user -> {
              counter.getAndIncrement();
              return searchName.equals(user.getUserName());
          })
          .mapToInt(user -> counter.get())
          .findFirst()
          .orElse(-1);

        assertEquals(1, index);
    }

    @Test
    public void whenUsingIntStream_thenFindFirstMatchingUserIndex() {
        int index = IntStream.range(0, userList.size())
          .filter(streamIndex -> searchName.equals(userList.get(streamIndex).getUserName()))
          .findFirst()
          .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenUsingTakeWhile_thenFindFirstMatchingUserIndex() {
        long predicateIndex = userList.stream()
          .takeWhile(user -> !user.getUserName().equals(searchName))
          .count();
        assertEquals(1, predicateIndex);
    }
    
    @Test
    public void whenUsingTakeWhile_thenFindIndexFromNoMatchingElement() {
        List<User> userList = List.of(new User(1, "David"), new User(2, "Vick"), new User(3, "Roger"), new User(4, "James"));
        long predicateIndex = userList.stream()
          .takeWhile(user -> !user.getUserName().equals(searchName))
          .count();
        assertEquals(4, predicateIndex);
    }
    
    @Test
    public void whenUsingGoogleGuava_thenFindFirstMatchingUserIndex() {
        int index = Iterables.indexOf(userList, user -> searchName.equals(user.getUserName()));
        assertEquals(1, index);
    }

    @Test
    public void whenUsingApacheCommons_thenFindFirstMatchingUserIndex() {
        int index = IterableUtils.indexOf(userList, user -> searchName.equals(user.getUserName()));
        assertEquals(1, index);
    }
}
