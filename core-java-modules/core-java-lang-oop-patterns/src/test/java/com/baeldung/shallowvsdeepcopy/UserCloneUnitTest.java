package com.baeldung.shallowvsdeepcopy;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCloneUnitTest {

    @Test
    public void givenUser_WhenCopyCreated_thenCopyIsShallow(){
        Address address = new Address("abc", "444-0000", "pqr", "USA");
        UserWithShallowClone user = new UserWithShallowClone("baeldung", address,32);

        UserWithShallowClone userClone = (UserWithShallowClone)user.clone();
        assertThat(userClone.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    public void givenUserWithDeepCloneMethod_WhenCopyCreated_thenCopyIsDeep(){
        Address address = new Address("abc", "444-0000", "pqr", "USA");
        UserWithDeepClone user = new UserWithDeepClone("baeldung", address,32);
        UserWithDeepClone userClone = (UserWithDeepClone)user.clone();
        assertThat(userClone.getAddress()).isNotEqualTo(user.getAddress());
    }
}