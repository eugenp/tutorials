package com.baeldung.commons.lang3.test;

import com.baeldung.commons.lang3.beans.User;
import com.baeldung.commons.lang3.beans.UserInitializer;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LazyInitializerUnitTest {
    
    @Test
    public void givenLazyInitializerInstance_whenCalledget_thenCorrect() throws ConcurrentException {
        UserInitializer userInitializer = new UserInitializer();
        assertThat(userInitializer.get()).isInstanceOf(User.class);
    } 
}
