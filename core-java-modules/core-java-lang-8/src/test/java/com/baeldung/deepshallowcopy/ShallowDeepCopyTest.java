package com.baeldung.deepshallowcopy;

import com.baeldung.deepshallowcopy.cloneable.StudentCloneableDeepCopyTest;
import com.baeldung.deepshallowcopy.library.StudentDeepCopyUsingLibraryTest;
import com.baeldung.deepshallowcopy.manual.StudentManualDeepShallowCopyTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StudentCloneableDeepCopyTest.class,
        StudentDeepCopyUsingLibraryTest.class,
        StudentManualDeepShallowCopyTest.class
})
public class ShallowDeepCopyTest {
}
