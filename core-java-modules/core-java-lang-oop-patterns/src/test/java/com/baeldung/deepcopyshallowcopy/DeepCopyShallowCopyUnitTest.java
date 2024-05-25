package com.baeldung.deepcopyshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Ignore;
import org.junit.Test;

public class DeepCopyShallowCopyUnitTest {
    @Test
    public void whenCreatingShallowCopyWithAssignmentMethod_thenObjectsShouldBeSame() {

        Address address = new Address("Baker St", 10);
        PersonShallow pm = new PersonShallow("Brad", 25, address);

        PersonShallow shallowCopy = pm;
        assertThat(shallowCopy).isEqualTo(pm);
    }

    @Test
    public void whenCreatingDeepCopyWithCloneMethod_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {

        Address address = new Address("Baker St", 10);
        PersonDeep pd = new PersonDeep("Brad", 25, address);

        PersonDeep deepCopy = pd.clone();
        assertThat(deepCopy).isNotEqualTo(pd);
    }

}