package com.baeldung.deepshallowcopy;

import com.baeldung.deepshallowcopy.cloneable.PersonCloneableDeepCopyTest;
import com.baeldung.deepshallowcopy.manual.PersonManualDeepShallowCopyTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PersonCloneableDeepCopyTest.class,
        PersonManualDeepShallowCopyTest.class,
})
public class ShallowDeepCopyTest {
}