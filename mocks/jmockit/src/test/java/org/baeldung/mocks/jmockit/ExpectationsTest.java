package org.baeldung.mocks.jmockit;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
@SuppressWarnings("unchecked")
public class ExpectationsTest {

    @Test
    public void testForAny(@Mocked ExpectationsCollaborator mock) throws Exception {
        new Expectations() {
            {
                mock.methodForAny(anyString, anyInt, anyBoolean, (List<String>) any);
            }
        };
        mock.methodForAny("barfooxyz", 0, Boolean.FALSE, new ArrayList<>());
    }

    @Test
    public void testForWith(@Mocked ExpectationsCollaborator mock) throws Exception {
        new Expectations() {
            {
                mock.methodForWith(withSubstring("foo"), withNotEqual(1), withNotNull(), withInstanceOf(List.class));
            }
        };
        mock.methodForWith("barfooxyz", 2, Boolean.TRUE, new ArrayList<>());
    }

    @Test
    public void testWithNulls(@Mocked ExpectationsCollaborator mock) {
        // more config
        new Expectations() {
            {
                mock.methodForNulls(anyString, null, (List<Integer>) withNull());
            }
        };
        mock.methodForNulls("blablabla", new ArrayList<String>(), null);
    }

    @Test
    public void testWithTimes(@Mocked ExpectationsCollaborator mock) {
        // more config
        new Expectations() {
            {
                // exactly 2 invocations to foo() are expected
                mock.methodForTimes1();
                times = 2;
                // we expect from 1 to 3 invocations to bar()
                mock.methodForTimes2();
                minTimes = 1;
                maxTimes = 3;
                mock.methodForTimes3(); // "minTimes = 1" is implied
            }
        };
        mock.methodForTimes1();
        mock.methodForTimes1();
        mock.methodForTimes2();
        mock.methodForTimes2();
        mock.methodForTimes2();
        mock.methodForTimes3();
    }

    @Test
    public void testCustomArgumentMatching(@Mocked ExpectationsCollaborator mock) {
        new Expectations() {
            {
                mock.methodForArgThat(withArgThat(new BaseMatcher<Object>() {
                    @Override
                    public boolean matches(Object item) {
                        return item instanceof Model && "info".equals(((Model) item).getInfo());
                    }

                    @Override
                    public void describeTo(Description description) {
                        // NOOP
                    }
                }));
            }
        };
        mock.methodForArgThat(new Model());
    }
}
