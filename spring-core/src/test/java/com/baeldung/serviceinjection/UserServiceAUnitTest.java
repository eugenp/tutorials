package com.baeldung.serviceinjection;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public class UserServiceAUnitTest {

    @Test
    public void givenUserListIsEmpty_whenRequesting_thenResultIsEmptyList() {
        Repository<User> dummy = new UserRepositoryDummy();
        UserServiceA service = new UserServiceA(dummy);

        List<User> actualResult = service.getAllUsers();

        assertThat(actualResult, is(empty()));
    }

    @Test
    public void givenUserListHasOneEntry_whenRequesting_thenResultContainsOneEntry() {
        User user = new User();
        user.setName("Big Bird");
        user.setId(42L);
        Repository<User> dummy = new UserRepositoryDummy(Collections.singletonList(user));
        UserServiceA service = new UserServiceA(dummy);

        List<User> actualResult = service.getAllUsers();
        assertThat(actualResult, hasSize(1));
        assertThat(actualResult, hasItem(user));
    }

}