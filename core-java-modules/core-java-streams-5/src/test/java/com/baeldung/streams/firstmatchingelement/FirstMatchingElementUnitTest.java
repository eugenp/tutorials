package com.baeldung.streams.firstmatchingelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import com.google.common.collect.Iterables;

public class FirstMatchingElementUnitTest {

    private List<User> userList = Lists.newArrayList(new User(1, "David"), new User(2, "John"), new User(3, "Roger"), new User(4, "John"));
    private String searchName = "John";

    @Test
    public void whenUsingIndexOf_thenFindFirstMatchingUserIndex() {
        int index = userList.stream()
          .filter(user -> searchName.equals(user.getUserName()))
          .mapToInt(user -> userList.indexOf(user))
          .findFirst()
          .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenUsingIterator_thenFindFirstMatchingUserIndex() {
        int index = -1;
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (searchName.equals(user.getUserName())) {
                index = userList.indexOf(user);
                break;
            }
        }
        assertEquals(1, index);
    }

    @Test
    public void whenUsingListIterator_thenFindFirstMatchingUserIndex() {
        int index = -1;
        ListIterator<User> listIterator = userList.listIterator();
        while (listIterator.hasNext()) {
            if (searchName.equals(listIterator.next()
                .getUserName())) {
                index = listIterator.previousIndex();
                break;
            }
        }
        assertEquals(1, index);
    }

    @Test
    public void whenUsingIntStream_thenFindFirstMatchingUserIndex() {
        int index = IntStream.range(0, userList.size() - 1)
          .filter(streamIndex -> searchName.equals(userList.get(streamIndex).getUserName()))
          .findFirst()
          .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenUsingTakeWhile_thenFindFirstMatchingUserIndex() {
        int lastIndex = userList.size() - 1;
        int predicateIndex = userList.stream()
          .takeWhile(user -> !(searchName.equals(user.getUserName())))
          .mapToInt(userList::indexOf)
          .max()
          .orElse(-1);

        if (predicateIndex != 1 && predicateIndex != lastIndex) {
            assertEquals(1, predicateIndex + 1);
        } else {
            assertEquals(lastIndex, predicateIndex);
        }
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
