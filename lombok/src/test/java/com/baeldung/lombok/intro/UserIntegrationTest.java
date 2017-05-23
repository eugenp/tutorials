package com.baeldung.lombok.intro;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class UserIntegrationTest {

    @Test
    public void givenAnnotatedUser_thenHasEmptyConstructor() {
        /* User user = */ new User();
    }

    @Test
    public void givenAnnotatedUser_thenHasGettersAndSetters() {
        User user = new User("testnickname", "Test", "JUnit", "123456");

        Assert.assertEquals("testnickname", user.getNickname());
        Assert.assertEquals("Test", user.getFirstName());
        Assert.assertEquals("JUnit", user.getLastName());
        Assert.assertEquals("123456", user.getPhoneNr());

        user.setNickname("testnickname2");
        user.setFirstName("Test2");
        user.setLastName("JUnit2");
        user.setPhoneNr("654321");

        Assert.assertEquals("testnickname2", user.getNickname());
        Assert.assertEquals("Test2", user.getFirstName());
        Assert.assertEquals("JUnit2", user.getLastName());
        Assert.assertEquals("654321", user.getPhoneNr());
    }

    @Test
    public void givenAnnotatedUser_thenHasProtectedSetId() throws NoSuchMethodException {
        Method setIdMethod = User.class.getDeclaredMethod("setId", Long.class);
        int modifiers = setIdMethod.getModifiers();
        Assert.assertTrue(Modifier.isProtected(modifiers));
    }

    @Test
    public void givenAnnotatedUser_thenImplementsHasContactInformation() {
        User user = new User("testnickname3", "Test3", "JUnit3", "987654");
        Assert.assertTrue(user instanceof HasContactInformation);

        Assert.assertEquals("Test3", user.getFirstName());
        Assert.assertEquals("JUnit3", user.getLastName());
        Assert.assertEquals("987654", user.getPhoneNr());
        Assert.assertEquals("Test3 JUnit3", user.getFullName());

        user.setFirstName("Test4");
        user.setLastName("JUnit4");
        user.setPhoneNr("456789");

        Assert.assertEquals("Test4", user.getFirstName());
        Assert.assertEquals("JUnit4", user.getLastName());
        Assert.assertEquals("456789", user.getPhoneNr());
        Assert.assertEquals("Test4 JUnit4", user.getFullName());
    }

    @Test
    public void givenAnnotatedUser_whenHasEvents_thenToStringDumpsNoEvents() {
        User user = new User("testnickname", "Test", "JUnit", "123456");
        List<UserEvent> events = Arrays.asList(new UserEvent(user), new UserEvent(user));
        user.setEvents(events);
        Assert.assertFalse(user.toString().contains("events"));
    }

}
