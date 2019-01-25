package com.baeldung.initializationguide;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

public class UserUnitTest {

    @Test
    public void givenUserInstance_whenIntializedWithNew_thenInstanceIsNotNull() {
        User user = new User("Alice", 1);
        System.out.println("user:{}" + user);
        assertThat(user).isNotNull();
    }

    @Test
    public void givenUserInstance_whenInitializedWithReflection_thenInstanceIsNotNull() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        User user = User.class.getConstructor(String.class, int.class)
            .newInstance("Alice", 2);
        System.out.println("user:{}" + user);
        assertThat(user).isNotNull();
    }

    @Test
    public void givenUserInstance_whenCopiedWithClone_thenExactMatchIsCreated() throws CloneNotSupportedException {
        User user = new User("Alice", 3);
        User clonedUser = (User) user.clone();
        System.out.println("user.equals(clonedUser):{}" + user.equals(clonedUser));
        assertThat(clonedUser).isEqualTo(user);
    }

    /**
     * 默认使用无参方法初始化
     */
    @Test
    public void givenUserInstance_whenValuesAreNotInitialized_thenUserNameAndIdReturnDefault() {
        User user = new User();
        assertThat(user.getName()).isNull();
        assertThat(user.getId() == 0);
    }
}
