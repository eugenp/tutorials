package com.baeldung.deepvsshallowcopy;

import com.baeldung.deepvsshallowcopy.deep.User;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeepCopyTest {

    @Test
    public void whenCreateDeepCopy_thenObjectMutableFieldShouldBeDifferent() throws CloneNotSupportedException {
        User user = new User("John", new Department(1111L, "test"));
        User deepCopyUser = (User) user.clone();
        assertTrue(user.department != deepCopyUser.department, "'department' field should be different object while deep copy");
    }
}
