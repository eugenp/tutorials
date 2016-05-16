package com.baeldung.java8;

import com.baeldung.java_8_features.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Alex Vengrov
 */
public class Java8Features {

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
    public void callMethods_whenExpectedResults_thenCorrect() {

        Vehicle vehicle = new VehicleImpl();
        String overview = vehicle.getOverview();
        long[] startPosition = vehicle.startPosition();
        String producer = Vehicle.producer();

        assertEquals(overview, "ATV made by N&F Vehicles");
        assertEquals(startPosition[0], 23);
        assertEquals(startPosition[1], 15);
        assertEquals(producer, "N&F Vehicles");
    }

    @Test
    public void checkStreamOperations_whenWorkAsSuppose_thenCorrect() {

        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamArr = Arrays.stream(arr);
        Stream<String> streamOf = Stream.of("a", "b", "c");
        assertEquals(streamArr.count(), 3);

        long count = list.stream().distinct().count();
        assertEquals(count, 9);

        list.parallelStream().forEach(element -> doWork(element));

        Stream<String> streamFilter = list.stream().filter(element -> element.isEmpty());
        assertEquals(streamFilter.count(), 2);

        List<String> uris = new ArrayList<>();
        uris.add("C:\\My.txt");
        Stream<Path> streamMap = uris.stream().map(uri -> Paths.get(uri));
        assertEquals(streamMap.count(), 1);

        List<Detail> details = new ArrayList<>();
        details.add(new Detail());
        details.add(new Detail());
        Stream<String> streamFlatMap = details.stream()
                .flatMap(detail -> detail.getParts().stream());
        assertEquals(streamFlatMap.count(), 4);

        boolean isValid = list.stream().anyMatch(element -> element.contains("h"));
        boolean isValidOne = list.stream().allMatch(element -> element.contains("h"));
        boolean isValidTwo = list.stream().noneMatch(element -> element.contains("h"));
        assertTrue(isValid);
        assertFalse(isValidOne);
        assertFalse(isValidTwo);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(1);
        integers.add(1);
        Integer reduced = integers.stream().reduce(23, (a, b) -> a + b);
        assertTrue(reduced == 26);

        List<String> resultList = list.stream()
                .map(element -> element.toUpperCase())
                .collect(Collectors.toList());
        assertEquals(resultList.size(), list.size());
        assertTrue(resultList.contains(""));
    }

    @Test
    public void checkMethodReferences_whenWork_thenCorrect() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        boolean isReal = users.stream().anyMatch(u -> User.isRealUser(u));
        boolean isRealRef = users.stream().anyMatch(User::isRealUser);
        assertTrue(isReal);
        assertTrue(isRealRef);

        User user = new User();
        boolean isLegalName = list.stream().anyMatch(user::isLegalName);
        assertTrue(isLegalName);

        long count = list.stream().filter(String::isEmpty).count();
        assertEquals(count, 2);

        Stream<User> stream = list.stream().map(User::new);
        List<User> userList = stream.collect(Collectors.toList());
        assertEquals(userList.size(), list.size());
        assertTrue(userList.get(0) instanceof User);
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
        String result = user.map(User::getAddress)
                .map(Address::getStreet)
                .orElse("not specified");
        assertEquals(result, "1st Avenue");

        Optional<OptionalUser> optionalUser = Optional.ofNullable(getOptionalUser());
        String resultOpt = optionalUser.flatMap(OptionalUser::getAddress)
                .flatMap(OptionalAddress::getStreet)
                .orElse("not specified");
        assertEquals(resultOpt, "1st Avenue");

        Optional<User> userNull = Optional.ofNullable(getUserNull());
        String resultNull = userNull.map(User::getAddress)
                .map(Address::getStreet)
                .orElse("not specified");
        assertEquals(resultNull, "not specified");

        Optional<OptionalUser> optionalUserNull = Optional.ofNullable(getOptionalUserNull());
        String resultOptNull = optionalUserNull.flatMap(OptionalUser::getAddress)
                .flatMap(OptionalAddress::getStreet)
                .orElse("not specified");
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

    private void doWork(String string) {
        //just imitate an amount of work
    }
}
