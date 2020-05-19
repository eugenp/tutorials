package com.stackify.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import com.stackify.daos.UserDAO;
import com.stackify.models.User;

public class DynamicTests {
    
    @TestFactory
    public Collection<DynamicTest> dynamicTestCollection() {
        return Arrays.asList(DynamicTest.dynamicTest("Dynamic Test", () -> assertTrue(1 == 1)));
    }

    @TestFactory
    public Stream<DynamicTest> dynamicUserTestCollection() {
        List<User> inputList = Arrays.asList(new User("john@yahoo.com", "John"), new User("ana@yahoo.com", "Ana"));

        Function<User, String> displayNameGenerator = (input) -> "Saving user: " + input;

        UserDAO userDAO = new UserDAO();
        ThrowingConsumer<User> testExecutor = (input) -> {
            userDAO.add(input);
            assertNotNull(userDAO.findOne(input.getEmail()));
        };

        return DynamicTest.stream(inputList.iterator(), displayNameGenerator, testExecutor);
    }
}
