package com.baeldung.commons.lang3.test;

import com.baeldung.commons.lang3.beans.User;
import java.lang.reflect.Method;
import org.apache.commons.lang3.reflect.MethodUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class MethodUtilsUnitTest {

    @Test
    public void givenMethodUtilsClass_whenCalledgetAccessibleMethod_thenCorrect() {
        Method method = MethodUtils.getAccessibleMethod(User.class, "getName");
        assertThat(method).isInstanceOf(Method.class);
    }   
}
