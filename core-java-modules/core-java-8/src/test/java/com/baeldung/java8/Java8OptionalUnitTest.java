package com.baeldung.java8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.java_8_features.Address;
import com.baeldung.java_8_features.CustomException;
import com.baeldung.java_8_features.OptionalAddress;
import com.baeldung.java_8_features.OptionalUser;
import com.baeldung.java_8_features.User;

public class Java8OptionalUnitTest {

    private List<String> list;

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add("One");
        list.add("OneAndOnly");
        list.add("Derek");
        list.add("Change");
        list.add("factory");
        list.add("justBefore");
        list.add("Italy");
        list.add("Italy");
        list.add("Thursday");
        list.add("");
        list.add("");
    }

    @Test
    public void checkOptional_whenAsExpected_thenCorrect() {
        Optional<String> optionalEmpty = Optional.empty();
        assertFalse(optionalEmpty.isPresent());

        String str = "value";
        Optional<String> optional = Optional.of(str);
        assertEquals(optional.get(), "value");

        Optional<String> optionalNullable = Optional.ofNullable(str);
        Optional<String> optionalNull = Optional.ofNullable(null);
        assertEquals(optionalNullable.get(), "value");
        assertFalse(optionalNull.isPresent());

        List<String> listOpt = Optional.of(list).orElse(new ArrayList<>());
        List<String> listNull = null;
        List<String> listOptNull = Optional.ofNullable(listNull).orElse(new ArrayList<>());
        assertTrue(listOpt == list);
        assertTrue(listOptNull.isEmpty());

        Optional<User> user = Optional.ofNullable(getUser());
        String result = user.map(User::getAddress).map(Address::getStreet).orElse("not specified");
        assertEquals(result, "1st Avenue");

        Optional<OptionalUser> optionalUser = Optional.ofNullable(getOptionalUser());
        String resultOpt = optionalUser.flatMap(OptionalUser::getAddress).flatMap(OptionalAddress::getStreet).orElse("not specified");
        assertEquals(resultOpt, "1st Avenue");

        Optional<User> userNull = Optional.ofNullable(getUserNull());
        String resultNull = userNull.map(User::getAddress).map(Address::getStreet).orElse("not specified");
        assertEquals(resultNull, "not specified");

        Optional<OptionalUser> optionalUserNull = Optional.ofNullable(getOptionalUserNull());
        String resultOptNull = optionalUserNull.flatMap(OptionalUser::getAddress).flatMap(OptionalAddress::getStreet).orElse("not specified");
        assertEquals(resultOptNull, "not specified");

    }

    @Test(expected = CustomException.class)
    public void callMethod_whenCustomException_thenCorrect() {
        User user = new User();
        String result = user.getOrThrow();
    }

    private User getUser() {
        User user = new User();
        Address address = new Address();
        address.setStreet("1st Avenue");
        user.setAddress(address);
        return user;
    }

    private OptionalUser getOptionalUser() {
        OptionalUser user = new OptionalUser();
        OptionalAddress address = new OptionalAddress();
        address.setStreet("1st Avenue");
        user.setAddress(address);
        return user;
    }

    private OptionalUser getOptionalUserNull() {
        OptionalUser user = new OptionalUser();
        OptionalAddress address = new OptionalAddress();
        address.setStreet(null);
        user.setAddress(address);
        return user;
    }

    private User getUserNull() {
        User user = new User();
        Address address = new Address();
        address.setStreet(null);
        user.setAddress(address);
        return user;
    }

}
