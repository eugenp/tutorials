package com.baeldung.ambiguousmethodcall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyClassUnitTest {
    
    @Test
    void givenMockedMyMethod_whenMyMethod_ThenMockedResult(@Mock MyClass myClass) {
        // Uncomment next line -> Get the ambiguous method call error
        // when(myClass.myMethod(any())).thenReturn(1);
        when(myClass.myMethod(anyInt())).thenReturn(1);
        assertEquals(1, myClass.myMethod(2));
        when(myClass.myMethod(any(MyOwnType.class))).thenReturn("baeldung");
        assertEquals("baeldung", myClass.myMethod(new MyOwnType()));
    }
    
    @Test
    void givenCorrectlyMockedNullMatcher_whenMyMethod_ThenMockedResult(@Mock MyClass myClass) {
        // Uncomment next line -> Get the ambiguous method call error
        // when(myClass.myMethod(isNull())).thenReturn("baeldung");
        when(myClass.myMethod(isNull(Integer.class))).thenReturn(1);
        assertEquals(1, myClass.myMethod((Integer) null));
        when(myClass.myMethod(isNull(MyOwnType.class))).thenReturn("baeldung");
        assertEquals("baeldung", myClass.myMethod((MyOwnType) null));
    }

}
