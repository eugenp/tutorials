package com.baeldung.javaobject;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CloneUnitTest {

    @Test
    public void givenShallowCloneMethod_WhenCopyIsCreated_thenCopyIsShallow(){
        Address address = new Address("Main St", "123", "New York", "USA");
        ShallowClone user = new ShallowClone("John", address,11);

        ShallowClone userClone = (ShallowClone)user.clone();
        assertThat(userClone.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    public void givenDeepCloneMethod_WhenCopyIsCreated_thenCopyIsDeep(){
        Address address = new Address("Main St", "123", "New York", "USA");
        DeepClone user = new DeepClone("John", address,11);
        DeepClone userClone = (DeepClone)user.clone();
        assertThat(userClone.getAddress()).isNotEqualTo(user.getAddress());
    }
}


