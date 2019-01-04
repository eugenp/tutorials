package com.baeldung;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FriendTest {

    @Test
    public void filter_with_for_each_loop() throws Exception {
        List<Friend> friends = Arrays.asList(new Friend("alice"), new Friend("bob"), new Friend("claire"));
        List<String> likes = Arrays.asList("alice", "bob");

        List<Friend> friendsILike = new ArrayList<>();

        for (String like : likes) {
            for (Friend friend : friends) {
                if (friend.getName().equals(like)) {
                    friendsILike.add(friend);
                }
            }
        }

        assertThat(friendsILike.size(), is(2));
    }

    @Test
    public void use_lambda_with_list() throws Exception {
        List<Friend> friends = Arrays.asList(new Friend("alice"), new Friend("bob"), new Friend("claire"));
        List<String> likes = Arrays.asList("alice", "bob");

        List<Friend> friendsILike = friends.stream()
                .filter(friend -> likes.contains(friend.getName()))
                .collect(Collectors.toList());

        assertThat(friendsILike.size(), is(2));
    }

    @Test
    public void use_lambda_with_hashset() throws Exception {
        List<Friend> friends = Arrays.asList(new Friend("alice"), new Friend("bob"), new Friend("claire"));
        List<String> likes = Arrays.asList("alice", "bob");

        List<Friend> friendsILike = friends.stream()
                .filter(friend -> new HashSet<>(likes).contains(friend.getName()))
                .collect(Collectors.toList());

        assertThat(friendsILike.size(), is(2));
    }

    @Test
    public void use_guava() throws Exception {
        List<Friend> friends = Arrays.asList(new Friend("alice"), new Friend("bob"), new Friend("claire"));
        List<String> likes = Arrays.asList("alice", "bob");

        Set<Friend> friendsSet = new HashSet<>(friends);
        Set<Friend> likesSet = likes.stream().map(Friend::new).collect(Collectors.toSet());

        Sets.SetView<Friend> friendsILike = Sets.intersection(friendsSet, likesSet);

        assertThat(friendsILike.size(), is(2));
    }

    @Test
    public void use_apache_commons() throws Exception {
        List<Friend> friends = Arrays.asList(new Friend("alice"), new Friend("bob"), new Friend("claire"));
        List<String> likes = Arrays.asList("alice", "bob");

        Set<Friend> friendsSet = new HashSet<>(friends);
        Set<Friend> likesSet = likes.stream().map(Friend::new).collect(Collectors.toSet());

        SetUtils.SetView<Friend> friendsILike = SetUtils.intersection(friendsSet, likesSet);

        assertThat(friendsILike.size(), is(2));
    }
}
