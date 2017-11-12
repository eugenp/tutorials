package com.baeldung.serviceinjection;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class UserServiceBUnitTest {

    @Test
    public void givenEmptyList_whenRequesting_thenReturnsEmptyList() {
        Repository<User> dummy = new UserRepositoryDummy();
        UserServiceB service = new UserServiceB();
        service.setRepository(dummy);

        List<User> actualResult = service.getAllUsers();
        assertThat(actualResult, is(empty()));
    }

    @Test
    public void givenListWithOneEntry_whenRequesting_thenReturnedListContainsOne() {
        User user = new User();
        user.setId(21L);
        user.setName("Ernie");
        Repository<User> dummy = new UserRepositoryDummy(Collections.singletonList(user));
        UserServiceB service = new UserServiceB();
        service.setRepository(dummy);

        List<User> actualResult = service.getAllUsers();
        assertThat(actualResult, hasSize(1));
        assertThat(actualResult, hasItem(user));
    }

}
