package com.stackify.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.stackify.daos.UserDAO;
import com.stackify.models.User;

import com.stackify.test.conditions.DisabledOnEnvironment;

public class UsersTest implements DatabaseConnectionTest {

    private static UserDAO userDAO;

    private static Logger logger = LogManager.getLogger(UsersTest.class);

    @BeforeAll
    public static void addData() {
        userDAO = new UserDAO();
        userDAO.createTable();

        User user1 = new User("john@gmail.com", "John");
        User user2 = new User("ana@gmail.com", "Ana");
        userDAO.add(user1);
        userDAO.add(user2);
    }

    @Test
    @DisplayName("Test Get Users")
    public void testGetUsersNumber() {
        assertEquals(2, userDAO.findAll().size());

    }

    @Test
    @DisplayName("Test Get Users")
    public void testGetUsersNumberWithInfo(TestInfo testInfo) {
        assertEquals(2, userDAO.findAll().size());
        assertEquals("Test Get Users", testInfo.getDisplayName());
        assertEquals(UsersTest.class, testInfo.getTestClass().get());
        logger.info("Running test method:" + testInfo.getTestMethod().get().getName());
    }

    @Test
    public void testGetUser() {
        User user = userDAO.findOne("john@gmail.com");
        assertEquals("John", user.getName(), "User name:" + user.getName() + " incorrect");
    }

    @Test
    public void testClassicAssertions() {
        User user1 = userDAO.findOne("john@gmail.com");
        User user2 = userDAO.findOne("john@yahoo.com");

        assertNotNull(user1);
        assertNull(user2);

        user2 = new User("john@yahoo.com", "John");
        assertEquals(user1.getName(), user2.getName(), "Names are not equal");
        assertFalse(user1.getEmail().equals(user2.getEmail()), "Emails are equal");
        assertNotSame(user1, user2);
    }

    @Test
    @Disabled
    public void testGroupedAssertions() {
        User user = userDAO.findOne("john@gmail.com");
        assertAll("user", () -> assertEquals("Johnson", user.getName()), () -> assertEquals("johnson@gmail.com", user.getEmail()));
    }

    @Test
    public void testIterableEquals() {
        User user1 = new User("john@gmail.com", "John");
        User user2 = new User("ana@gmail.com", "Ana");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        assertIterableEquals(users, userDAO.findAll());
    }

    @Test
    public void testLinesMatch() {
        List<String> expectedLines = Collections.singletonList("(.*)@(.*)");
        List<String> emails = Arrays.asList("john@gmail.com");
        assertLinesMatch(expectedLines, emails);
    }

    @Test
    void testThrows() {
        User user = null;
        Exception exception = assertThrows(NullPointerException.class, () -> user.getName());
        logger.info(exception.getMessage());
    }

    @Test
    @DisabledOnEnvironment({ "dev", "prod")
    void testFail() {
        fail("this test fails");
    }

    @Test
    void testAssumptions() {
        List<User> users = userDAO.findAll();
        assumeFalse(users == null);
        assumeTrue(users.size() > 0);

        User user1 = new User("john@gmail.com", "John");

        assumingThat(users.contains(user1), () -> assertTrue(users.size() > 1));
    }

    @ParameterizedTest
    @ValueSource(strings = { "john@gmail.com", "ana@gmail.com" })
    public void testParameterized(String email) {
        assertNotNull(userDAO.findOne(email));
    }

    @AfterAll
    public static void removeData() {
        userDAO.deleteAll();
    }

    @Nested
    class DeleteUsersTest {
        @Test
        public void addUser() {
            User user = new User("bob@gmail.com", "Bob");
            userDAO.add(user);
            assertNotNull(userDAO.findOne("bob@gmail.com"));

            userDAO.delete("bob@gmail.com");
            assertNull(userDAO.findOne("bob@gmail.com"));
        }
    }

}
