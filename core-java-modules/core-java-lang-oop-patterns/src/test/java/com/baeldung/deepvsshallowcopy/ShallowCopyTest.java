package com.baeldung.deepvsshallowcopy;


import com.baeldung.deepvsshallowcopy.shallow.User;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShallowCopyTest {

    @Test
    public void whenCreateShallowCopy_thenObjectMutableFieldIsTheSame() throws CloneNotSupportedException {
        User user = new User("John", new Department(1111L, "test"));
        User shallowCopyUser = (User) user.clone();
        assertTrue(user.department == shallowCopyUser.department, "'department' field should be same object while shallow copy");
    }
}

